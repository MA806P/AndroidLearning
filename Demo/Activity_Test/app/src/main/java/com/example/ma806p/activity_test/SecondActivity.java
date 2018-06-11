package com.example.ma806p.activity_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        final Intent intent = getIntent();
        final int num1 = intent.getIntExtra("value1", -1);
        final int num2 = intent.getIntExtra("value2", -1);

        btnBack = (Button)findViewById(R.id.secondBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("result", num1 + num2);
                setResult(0x002, intent);
                finish();
            }
        });




    }





}
