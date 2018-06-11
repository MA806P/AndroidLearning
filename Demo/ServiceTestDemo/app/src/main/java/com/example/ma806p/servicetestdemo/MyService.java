package com.example.ma806p.servicetestdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyService extends Service {


    //只有在生命周期才调用一次
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("MyService", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MyService", "onDestroy");
    }


    //每一次startService 启动会被调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("MyService", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("MyService", "onBind");
        return new MyBinder();
    }


    class MyBinder extends Binder {

    }

}
