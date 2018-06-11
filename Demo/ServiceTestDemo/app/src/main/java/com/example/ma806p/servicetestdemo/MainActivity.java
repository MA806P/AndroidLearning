package com.example.ma806p.servicetestdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接成功
            Log.i("Activity", "onServiceConnected");
            binder = (ComputeService.ComputeBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("Activity", "onServiceDisconnected");
        }
    };



    private EditText yuwen, shuxue, yingyu;
    private TextView resultShow;
    private ComputeService.ComputeBinder binder = null;

    private computeTestReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent("heying.computeService");
        intent.setPackage(getPackageName());
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
        //启动之后不能直接计算，还没有连接binder

        yuwen = (EditText)findViewById(R.id.editTextYuwen);
        shuxue = (EditText)findViewById(R.id.editTextShuxue);
        yingyu = (EditText)findViewById(R.id.editTextYingyu);
        resultShow = (TextView)findViewById(R.id.textViewResult);


        //通知注册3
        IntentFilter filter = new IntentFilter("compute.test");
        receiver = new computeTestReceiver();
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    //计算点击按钮
    public void computeClickAction(View view) {
        try {

            //从文本框中读取输入的成绩
            double yuwenD = Double.parseDouble(yuwen.getText().toString());
            double shuxueD = Double.parseDouble(shuxue.getText().toString());
            double yingyuD = Double.parseDouble(yingyu.getText().toString());

            //计算平均成绩并显示出来
            if (binder != null) {
                double result = binder.calcAvg(yuwenD, shuxueD, yingyuD);
                resultShow.setText("平均成绩：" + result);
            }
        } catch (NumberFormatException ex) {

        } catch (Exception ex) {

        }

    }




    public void testAction(View view) {

        Intent intent = new Intent("heying.computeService");
        intent.setPackage(getPackageName());

        double yuwenD = Double.parseDouble(yuwen.getText().toString());
        intent.putExtra("yuwen", yuwenD);

        double shuxueD = Double.parseDouble(shuxue.getText().toString());
        intent.putExtra("shuxue", shuxueD);

        double yingyuD = Double.parseDouble(yingyu.getText().toString());
        intent.putExtra("yingyu", yingyuD);

        startService(intent);

    }

    class computeTestReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            double result = intent.getDoubleExtra("result", 0);
            resultShow.setText("平均成绩(from broadcast)：" + result);
        }
    }





    // ---------- 连接 Test
    public void serviceClickAction(View view) {

        //Log.i("test", view.getId() + " ");

        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnStart:
                intent = new Intent("heying.myservice");
                intent.setPackage(getPackageName());
                startService(intent); //onCreate -> onStartCommand
                break;
            case R.id.btnEnd:
                intent = new Intent("heying.myservice");
                intent.setPackage(getPackageName());
                stopService(intent);
                break;

            case R.id.btnBind:
                intent = new Intent("heying.myservice");
                intent.setPackage(getPackageName());
                bindService(intent, conn, BIND_AUTO_CREATE); //bindService必须要有一个连接存在
                break;

            case R.id.btnUnBind:
                intent = new Intent("heying.myservice");
                intent.setPackage(getPackageName());
                unbindService(conn);
                break;
        }

    }

}
