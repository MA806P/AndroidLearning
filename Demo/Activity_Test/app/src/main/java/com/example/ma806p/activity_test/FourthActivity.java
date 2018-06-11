package com.example.ma806p.activity_test;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FourthActivity extends AppCompatActivity {



    //onCreate  onStart onResume  //开始显示
    // onPause  onStop  onDestroy //返回退出
    // onPause  onStop //回到桌面
    // onStart  onResume //从桌面回到界面


    //例子开启线程计数，当页面到桌面时，停止计数，当回到本页面继续计数
    //模拟当有电话进来时，游戏停止，电话结束后，回到游戏页面继续进行

    private Button btnCount;
    private TextView tvCount;
    private int count = 0;
    private boolean mainFlag = false;
    private Handler handler = new Handler();
    private boolean isPause = true;

    //创建，只被调用过一次
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        Log.i("Test", "onCreate");


        new Thread() {
            @Override
            public void run() {
                mainFlag = true;
                while (mainFlag) {
                    if (isPause == false) { //是否暂停
                        count++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //主线程显示计数
                                tvCount.setText(count + "");
                            }
                        });
                    }
                }

            }
        }.start();



        tvCount = (TextView)findViewById(R.id.textViewCount);
        btnCount = (Button)findViewById(R.id.buttonCountControl);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //控制线程，停止或启动
                isPause = !isPause;
            }
        });

    }


    //加载到内存了
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Test", "onStart");
    }

    //可见，显示出来获得焦点
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Test", "onResume");

        isPause = false;
    }


    //可见，失去焦点
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Test", "onPause");

        isPause = true;
    }

    //不可见
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Test", "onStop");
    }

    //在整个生命周期中只调用一次
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Test", "onDestroy");

        mainFlag = false;
    }
}
