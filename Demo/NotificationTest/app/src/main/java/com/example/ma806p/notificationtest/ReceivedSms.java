package com.example.ma806p.notificationtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReceivedSms extends BroadcastReceiver {

    //当接收到广播，处理方法
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MyReceiver", "get broadcast");

        Toast.makeText(context, "接收到了一个短消息", Toast.LENGTH_SHORT).show();
    }
}
