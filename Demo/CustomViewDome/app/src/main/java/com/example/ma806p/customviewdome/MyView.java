package com.example.ma806p.customviewdome;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * TODO: document your custom view class.
 */
public class MyView extends View implements Runnable {


    //-----------
    private Paint mPaint;
    private Bitmap mBitmap;
    private int x;
    private boolean flag;

    //-------------以代码的方式动态添加View到容器时调用
    public MyView(Context context) {
        super(context);
        init();
    }

    //--------------当以XML布局文件的方式使用时，自动调用
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {


        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.abc);
        mBitmap = Bitmap.createScaledBitmap(mBitmap, 1080, 300, true);

    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    //------------- 画布绘制，当View需要呈现出来时，自动调用
    @Override
    protected void onDraw(Canvas canvas) {

        //----- 画圆
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(50, 50, 50, mPaint);

        //canvas.drawBitmap(mBitmap, 10, 200, null);

        super.onDraw(canvas);
        moveBackground(canvas);
    }


    private void moveBackground(Canvas canvas) {

        x -= 10;
        int x2 = mBitmap.getWidth() - (-x);
        if (x2 <= 0) {
            x = 0;
            canvas.drawBitmap(mBitmap, x, 300, null);
        } else {
            canvas.drawBitmap(mBitmap, x, 300, null);
            canvas.drawBitmap(mBitmap, x2, 300, null);
        }

    }

    @Override
    public void run() {
        while (flag) {

            //不可直接调用 onDraw 方法
            postInvalidate(); //在线程中要求View重新呈现

        }
    }
}
