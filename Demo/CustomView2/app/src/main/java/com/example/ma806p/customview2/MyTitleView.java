package com.example.ma806p.customview2;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyTitleView extends LinearLayout {

    private TextView tvTitle;
    private Button button;
    private Context context;


    private void init() {
        tvTitle = new TextView(context);
        tvTitle.setText("This two");
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setGravity(Gravity.CENTER_HORIZONTAL);

        button = new Button(context);
        button.setText("Back");
        button.setTextColor(Color.WHITE);


        LayoutParams params = new LayoutParams(280, LayoutParams.WRAP_CONTENT, 1);
        tvTitle.setLayoutParams(params);

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(Color.YELLOW);

        this.addView(tvTitle);
        this.addView(button);

    }


    public MyTitleView(Context context) {
        super(context);



        View view = inflater(context);
        this.addView(view);

        this.context = context;
        //手动添加控件
        init();
    }

    public MyTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }





    public View inflater(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.coustom_view, null);
        return view;
    }
}
