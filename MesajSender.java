package com.example.sefai.test;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static android.content.Context.WIFI_SERVICE;

public class MesajSender extends AsyncTask<Character, Void, Character> {
    Socket s;
    DataOutputStream dos;
    PrintWriter pw;
    public String ipAdress;

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }


    @Override
    protected Character doInBackground(Character... characters) {
        char message = characters[0];
            try{
                s = new Socket("192.168.2.89",49150); //192.168.2.89
                pw = new PrintWriter(s.getOutputStream());
                pw.write(message);
                pw.flush();
                //pw.close();
                //s.close();

            }catch(IOException e){
                e.printStackTrace();
            }

        return message;
    }
}
