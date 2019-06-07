package com.example.sefai.test;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btnMouse,btnKeyboard,buttonOkey;
    private Toolbar toolbar;
    private EditText editTextIP;
    public String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        btnMouse=findViewById(R.id.btn_Mouse);
        btnKeyboard=findViewById(R.id.btn_Keyboard);
        editTextIP = findViewById(R.id.editTextIP);
        buttonOkey=findViewById(R.id.buttonOkey);

        btnKeyboard.setOnClickListener(this);
        btnMouse.setOnClickListener(this);

        toolbar.setTitle("KeyMouse");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        // WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        //= Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

//        buttonOkey.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ip = editTextIP.getText().toString();
//                MesajSender mesajSender = new MesajSender();
//                mesajSender.setIpAdress(ip);
//                MessageSender messageSender = new MessageSender();
//                messageSender.setIpAdress(ip);
//                Toast.makeText(MainActivity.this, messageSender.getIpAdress(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.action_keyboard:
                i = new Intent(MainActivity.this,KeyboardActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.action_mouse:
                i = new Intent(MainActivity.this,MouseActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    public void onClick(View view){
        int viewID=view.getId();
        Intent i;
        switch (viewID){
            case R.id.btn_Keyboard:
                i = new Intent(MainActivity.this,KeyboardActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.btn_Mouse:
                i = new Intent(MainActivity.this,MouseActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
