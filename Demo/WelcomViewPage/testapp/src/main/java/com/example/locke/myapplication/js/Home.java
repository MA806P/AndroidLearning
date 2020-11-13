package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 返回主页窗体
 */
public class Home extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) {
        MainActivity activity = fragment.getMainActivity();
        if (activity != null) {
            activity.goHome();
        }

        return new JSONObject();
    }
}
