package com.example.ma806p.activity_test;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
//        String hello = intent.getStringExtra("hello"); //上个页面传递过来的值，键值对可以传多个值
//        int text = intent.getIntExtra("text", -1); //没有值默认-1

        Bundle bundle = intent.getExtras();
        String hello = bundle.getString("hello");
        int text = bundle.getInt("test");


        TextView tv = new TextView(this);
        tv.setText(hello + " " + text);
        tv.setTextSize(40);
        tv.setTextColor(Color.RED);
        setContentView(tv);

    }
}
