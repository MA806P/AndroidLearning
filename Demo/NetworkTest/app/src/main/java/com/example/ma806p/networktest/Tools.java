package com.example.ma806p.networktest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Tools {

    public static final String WEB_URL = "http://a.hiphotos.baidu.com/image/pic/item/b812c8fcc3cec3fd7469a5eeda88d43f869427d1.jpg";
    public static boolean NETWORK_STATE = true;

    public static void checkNetworkState(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            Toast.makeText(context, "无可用网络。。。", Toast.LENGTH_LONG).show();
            NETWORK_STATE = false;
        } else {
            if (networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Toast.makeText(context, "移动网络。。。", Toast.LENGTH_LONG).show();
                    NETWORK_STATE = true;
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    Toast.makeText(context, "WIFI。。。", Toast.LENGTH_LONG).show();
                    NETWORK_STATE = true;
                }
            }
        }

    }

}
