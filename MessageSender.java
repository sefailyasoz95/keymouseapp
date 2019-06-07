package com.example.sefai.test;

import android.os.AsyncTask;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, String> {

    Socket s;
    DataOutputStream dos;
    PrintWriter pw;
    public String ipAdress;


    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    @Override
    protected String doInBackground(String... voids) {
        String message = voids[0];
        try{

             s = new Socket("192.168.2.89",49150);
             pw = new PrintWriter(s.getOutputStream());
             pw.write(message);
             pw.flush();
            // pw.close();
             //s.close();

        }catch(IOException e){
            e.printStackTrace();
    }
    return message;
    }


}
