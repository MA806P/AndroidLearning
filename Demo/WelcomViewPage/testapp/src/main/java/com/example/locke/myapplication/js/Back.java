package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 窗口回退
 */
public class Back extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) {
        MainActivity activity = fragment.getMainActivity();
        if (activity != null) {
            activity.backPage();
        }
        return new JSONObject();
    }
}
