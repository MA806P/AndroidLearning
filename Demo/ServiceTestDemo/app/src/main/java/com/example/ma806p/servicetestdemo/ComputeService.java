package com.example.ma806p.servicetestdemo;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class ComputeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return new ComputeBinder();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        double yuwen = intent.getDoubleExtra("yuwen", 0);
        double shuxue = intent.getDoubleExtra("shuxue", 0);
        double yingyu = intent.getDoubleExtra("yingyu", 0);
        double result = inServiceCall(yuwen, shuxue, yingyu);
        Log.i("ComputeService", "result = "+result);


        Intent intent1 = new Intent("compute.test");
        intent1.putExtra("result", result);
        sendBroadcast(intent1);

        return super.onStartCommand(intent, flags, startId);
    }

    public  double inServiceCall(double... scores) {
        int count = scores.length;
        if (count == 0) {
            return 0;
        }
        double sum = 0;
        for (double s : scores) {
            sum += s;
        }
        return sum/count;
    }



    public class ComputeBinder extends Binder {
        public double calcAvg(double... scores) {
            return inServiceCall(scores);
        }

    }

}
