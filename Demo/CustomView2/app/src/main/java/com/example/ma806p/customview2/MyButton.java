package com.example.ma806p.customview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.support.v7.widget.AppCompatButton;



public class MyButton extends Button {

    private int cx, cy, radius;
    private Paint mPaint;
    private int paintColor;

    public MyButton(Context context) {
        super(context);
        init();
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPaintColor(int color) {
        paintColor = color;
        mPaint.setColor(paintColor);
        invalidate();
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.RED);

        this.setText(" ");
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int w = getWidth();
        int h = getHeight();

        cx = w/2;
        cy = h/2;

        radius = cx > cy ? cy - 10 : cx - 10;

        canvas.drawCircle(cx, cy, radius, mPaint);
        super.onDraw(canvas);
    }

    //点击事件, 按下抬起设置圆颜色
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case  MotionEvent.ACTION_DOWN:
                setPaintColor(Color.YELLOW);
                return true;

            case MotionEvent.ACTION_UP:
                setPaintColor(Color.BLUE);
                return true;
        }


        return super.onTouchEvent(event);
    }
}
