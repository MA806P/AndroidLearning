package com.example.locke.myapplication.js;

import android.app.Service;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 震动一下手机，第一个参数为震动的毫秒数
 */
public class Vibrator extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        android.os.Vibrator vibrator = (android.os.Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
        int time = 500;
        if (params.length() > 1) {
            time = params.getInt(0);
        }
        vibrator.vibrate(time);
        return new JSONObject();
    }
}
