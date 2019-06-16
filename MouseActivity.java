package com.example.sefai.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MouseActivity extends AppCompatActivity {

    private Button buttonLeftClick,buttonRightClick;
    int startX,endX,x,xx;
    int startY,endY,y,yy;
    private Toolbar toolbar2;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new MouseView(this));
        setContentView(R.layout.activity_mouse);

        toolbar2 = findViewById(R.id.toolbar2);
        buttonLeftClick = findViewById(R.id.buttonLeftClick);
        buttonRightClick = findViewById(R.id.buttonRightClick);

        toolbar2.setTitle("KeyMouse");
        toolbar2.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar2);

        buttonRightClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("ClkRg");
            }
        });

        buttonLeftClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("ClkLf");
            }
        });
        buttonLeftClick.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute("2Clck");
                return false;
            }
        });
    }


    public int touchEvent1(MotionEvent event1){
        startX = (int) event1.getX();
        return startX;
    }

    public int touchEvent2(MotionEvent event2){
        startY = (int) event2.getY();
        return startY;
    }

    public int touchEvent3(MotionEvent event3){
        endX = (int) event3.getX();
        return endX;
    }
    public int touchEvent4(MotionEvent event4){
        endY = (int) event4.getY();
        return endY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        MessageSender messageSender1 = new MessageSender();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x = touchEvent1(event);
                y = touchEvent2(event);

                break;
            case MotionEvent.ACTION_MOVE:
                xx = touchEvent3(event);
                yy = touchEvent4(event);
                int xxx = xx - x;
                int yyy = yy - y;
                if(yyy < 10 ){
                    messageSender1.execute("MosUp");
                    break;
                }
                else if(xxx < 10) {
                    messageSender1.execute("MLeft");
                    break;
                }
                else if(yyy > 10 ){
                    messageSender1.execute("MosDw");
                    break;
                }
                else if(xxx > 10){
                    messageSender1.execute("MRght");
                    break;
                }
            default:
                break;
        }

        return true;
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
                i = new Intent(MouseActivity.this,KeyboardActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.action_mouse:
                i = new Intent(MouseActivity.this,MouseActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

}
