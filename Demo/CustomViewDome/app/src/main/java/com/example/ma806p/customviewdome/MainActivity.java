package com.example.ma806p.customviewdome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //myView = new MyView(this);
        //setContentView(myView);

        myView = (MyView)findViewById(R.id.main_my_view_id);
        myView.setFlag(true);
        new Thread(myView).start();


//        MyTitle myTitle = new MyTitle(this);
//        setContentView(myTitle);

    }

    @Override
    protected void onDestroy() {
        myView.setFlag(false);
        super.onDestroy();
    }
}
