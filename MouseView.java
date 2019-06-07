package com.example.sefai.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MouseView extends View {

    Path path = new Path();
    Path.Direction direction;
    Paint paint = new Paint();

    @Override
    public boolean onCapturedPointerEvent(MotionEvent event) {
        /* float verticalOffSet = event.getY();
        Log.d("test","y velocity"+verticalOffSet);*/
        return true;
    }

    public MouseView(Context context) {
        super(context);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float startX = getX();
        float startY = getY();

        //canvas.drawPath(path,paint);
        canvas.drawLine(startX,startY,startX+10,startY+10,paint);

        super.onDraw(canvas);
    }
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event){
        float Startx=0,Starty=0,Endx=0,Endy=0;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Startx = getX();
                Starty = getY();
                break;
            case MotionEvent.ACTION_UP:
                Endx = getX();
                Endy = getY();
                break;
        }


        Log.e("",(Startx-Endx)+" "+(Starty-Endy));
        invalidate();
        return true;
    }
}
