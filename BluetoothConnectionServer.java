package com.example.sefai.test;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

public class BluetoothConnectionServer {
    private static final String TAG = "BluetoothConnectionServ";
    private static final String appName = "MYAPP";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");
    private AcceptThread mInsecureAcceptThread;
    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;
    private ConnectedThread mConnectedThread;

    public BluetoothConnectionServer(Context context){
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }


    private class AcceptThread extends Thread{
        private final BluetoothServerSocket mmServerSocket;
        public AcceptThread(){
            BluetoothServerSocket tmp = null;

            try {
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName,MY_UUID_INSECURE);
                Log.d(TAG, "AcceptThread: SettingUpServerusing"+MY_UUID_INSECURE);
            } catch (IOException e) {

            }
            mmServerSocket = tmp;
        }
        public void run(){
            Log.d(TAG, "run: AcceptThread Running");
            BluetoothSocket socket = null;

            try {
                socket = mmServerSocket.accept();
                Toast.makeText(mContext, "Bağlantı kuruluyor...", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {

            }
            if (socket!=null){
                connected(socket,mmDevice);
            }

        }

        public void cancel(){
            Toast.makeText(mContext, "Bağlantı iptal ediliyor...", Toast.LENGTH_SHORT).show();
            try{
                mmServerSocket.close();
            }catch (IOException e){
                Toast.makeText(mContext, "Bağlantı iptal hatası...", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class ConnectThread extends Thread{
        private BluetoothSocket mmSocket;
        public ConnectThread(BluetoothDevice device, UUID uuid){
            mmDevice = device;
            deviceUUID = uuid;
        }
        public void run(){
            BluetoothSocket tmp = null;
            Log.i(TAG, "run: mConnectThread");
            try {
                tmp = mmDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
            //run çağırılıp bağlantı yapılmışsa keşfedilebilirliği kapat. Ram e yük bindiriyor.
            mBluetoothAdapter.cancelDiscovery();

            try {
                //sadece başarılı bir bağlantı olduunda çağıralacak bir block
                mmSocket.connect();
            } catch (IOException e) {
                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID");
            }
            connected(mmSocket,mmDevice);
        }
        public void cancel(){
            try {
                mmSocket.close();
            }catch (IOException e){


            }
        }
    }
    public synchronized void start(){
        Log.d(TAG, "start: ");

        // her hangi bir thread in bağlantı yapmaya çalışmasını iptal ediyoruz
        if (mConnectThread != null){
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null){
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    /**
     * AcceptThread başlatılır ve her hagi bir bağlantı isteğinin gelmesini bekler
     *
     * daha sonrasında ConnecThread başlar ve diğer cihazın AcceptThread i ile bağlantı kurmaya çalışır
     **/
    public void startClient(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startClient: started");

        mProgressDialog = ProgressDialog.show(mContext,"Bluetooth Bağlanıyor...","Lütfen Bekleyin",true);
        mConnectThread = new ConnectThread(device,uuid);
        mConnectThread.start();
    }
    private class ConnectedThread extends Thread{
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket){
            Log.d(TAG, "ConnectThread: starting");
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut=null;
            //bağlantı sağlandığı için progressDialog kapatıldı
            mProgressDialog.dismiss();
            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmInStream=tmpIn;
            mmOutStream=tmpOut;
        }

        @Override
        public void run() {
            byte [] buffer = new byte[1024];
            int bytes;
            while (true){
                try {
                    bytes = mmInStream.read(buffer);
                    String incomingMessage = new String(buffer,0,bytes);
                    Log.d(TAG, "InputStream: "+incomingMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
        public void write(byte[] bytes){
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: writing to outputstream"+text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {

            }
        }
        public void cancel(){
            try {
                mmSocket.close();
            }catch (IOException E){

            }
        }
    }
    public void connected(BluetoothSocket mmSocket,BluetoothDevice mmDevice){
        Log.d(TAG, "connected: starting");
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
    }


/*
public void Write(byte[] out){
        ConnectThread r;
        r = mConnectThread;
        r.Write(out);

    }
*/

}
