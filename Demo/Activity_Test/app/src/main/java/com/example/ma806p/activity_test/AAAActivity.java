package com.example.ma806p.activity_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class AAAActivity extends AppCompatActivity {

    private TextView tvInfo;
    private TextView tvInfoTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaa);

        tvInfo = (TextView)findViewById(R.id.tvInfo);
        tvInfoTime = (TextView)findViewById(R.id.tvInfoTime);

        tvInfo.setText(this.toString());

    }


    //当前栈中存在实例
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        tvInfoTime.setText(new Date().toLocaleString());
    }

    public void launchSelf(View view) {
        Intent intent = new Intent(this, AAAActivity.class);
        startActivity(intent);
    }

    public void lauchMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
