package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 主动展示一个面包屑toast内容
 */
public class Toast extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        String message = params.getString(0);
        int time = android.widget.Toast.LENGTH_LONG;
        if (params.length() > 1 && params.getBoolean(1)) {
            time = android.widget.Toast.LENGTH_SHORT;
        }
        if (activity != null) {
            activity.showToast(message, time);
        }
        return new JSONObject();
    }
}
