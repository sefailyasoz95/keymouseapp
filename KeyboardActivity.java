package com.example.sefai.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.InputStream;
import java.io.OutputStream;

public class KeyboardActivity extends AppCompatActivity {
    private TextView tvKeyboard;
    private EditText EditTextArea;
    private Button btnOk, butonEnter,butonSpace,butonDelete;
    private Toolbar toolbar3;

    @SuppressLint("ClickableViewAccessibility")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        butonEnter = findViewById(R.id.buttonEnter);

        EditTextArea=findViewById(R.id.editText);
        butonDelete = findViewById(R.id.buttonDelete);
        butonSpace = findViewById(R.id.buttonSpace);
        toolbar3 = findViewById(R.id.toolbar3);
        toolbar3.setTitle("KeyMouse");
        toolbar3.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar3);


        butonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("Enter");
                EditTextArea.setText("");
            }
        });
        butonSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("Space");

            }
        });
        butonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("Bcksp");
                EditTextArea.setText("");
            }
        });

        EditTextArea.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                char ch = 0;
                if(i+i1>=0&&(i+i1<charSequence.length())){
                    ch=charSequence.charAt(i+i1);
                }
                MesajSender mesajSender = new MesajSender();
                mesajSender.execute(ch);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                i = new Intent(KeyboardActivity.this,KeyboardActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.action_mouse:
                i = new Intent(KeyboardActivity.this,MouseActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }
}
