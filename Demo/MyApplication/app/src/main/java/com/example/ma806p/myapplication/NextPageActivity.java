package com.example.ma806p.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NextPageActivity extends AppCompatActivity {

    private LinearLayout linearOrientation;
    private Button horizontalBtn, verticalBtn;

    private LinearLayout linearGravity;
    private CheckBox[] chk;
    private int[] gravitys = {Gravity.TOP, Gravity.BOTTOM, Gravity.LEFT, Gravity.RIGHT, Gravity.CENTER};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView2);
        textView.setText(message);



        //线性布局，排列方式
        linearOrientation = (LinearLayout)findViewById(R.id.LineLayoutOrientationTest);

        OrientationChangeListener orientationListener = new OrientationChangeListener();
        horizontalBtn = (Button)findViewById(R.id.horizontal_btn);
        horizontalBtn.setOnClickListener(orientationListener);

        verticalBtn = (Button)findViewById(R.id.vertical_btn);
        verticalBtn.setOnClickListener(orientationListener);


        //线性布局，对齐方式
        linearGravity = (LinearLayout)findViewById(R.id.LineLayoutGravityTest);

        CheckBoxGravityChangeListener checkLinstener = new CheckBoxGravityChangeListener();
        int count = linearGravity.getChildCount(); //得到容器中子控件的个数
        chk = new CheckBox[count];
        for (int i = 0; i<count; i++) {
            chk[i] = (CheckBox)linearGravity.getChildAt(i); //得到容器子控件
            chk[i].setOnCheckedChangeListener(checkLinstener);
        }


    }

    class OrientationChangeListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            //修改布局排列方式
            switch (v.getId()) {
                case R.id.horizontal_btn:
                    linearOrientation.setOrientation(LinearLayout.HORIZONTAL);
                    break;
                case R.id.vertical_btn:
                    linearOrientation.setOrientation(LinearLayout.VERTICAL);
                    break;
            }
        }
    }



    class CheckBoxGravityChangeListener implements CompoundButton.OnCheckedChangeListener {

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            //修改布局对齐方式
            //考虑互斥性，左对齐和右对齐不共存
            if (buttonView == chk[0]) {
                if (isChecked) {
                    chk[1].setChecked(false);
                    chk[4].setChecked(false);
                }
            }

            if (buttonView == chk[1]) {
                if (isChecked) {
                    chk[0].setChecked(false);
                    chk[4].setChecked(false);
                }
            }

            if (buttonView == chk[2]) {
                if (isChecked) {
                    chk[3].setChecked(false);
                    chk[4].setChecked(false);
                }
            }

            if (buttonView == chk[3]) {
                if (isChecked) {
                    chk[2].setChecked(false);
                    chk[4].setChecked(false);
                }
            }

            if (buttonView == chk[4]) {
                if (isChecked) {
                    chk[0].setChecked(false);
                    chk[1].setChecked(false);
                    chk[2].setChecked(false);
                    chk[3].setChecked(false);
                }
            }

            int checkedGravity = 0;
            for (int i=0; i<chk.length; i++) {
                if (chk[i].isChecked()) {
                    checkedGravity |= gravitys[i];
                }
            }

            linearGravity.setGravity(checkedGravity);


        }
    }


}
