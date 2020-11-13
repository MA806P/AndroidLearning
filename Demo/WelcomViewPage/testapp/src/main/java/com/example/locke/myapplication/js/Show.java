package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 显示一个webview窗口
 */
public class Show extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        String name = params.getString(0);
        if (activity != null) {
            if (activity.hasPage(name)) {
                activity.goPage(name);
            }
        }

        return new JSONObject();
    }
}
