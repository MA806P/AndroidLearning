package com.example.ma806p.fragment_use;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainBActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b);

        Intent intent = getIntent();
        String item = intent.getStringExtra("item");

        FragmentB fragmentB = (FragmentB)getSupportFragmentManager().findFragmentById(R.id.mainBActivity_fragment_b);
        fragmentB.setText("you selected text = " + item);
    }
}
