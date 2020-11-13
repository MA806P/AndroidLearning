package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 打开一个窗口（webview），第一个参数是名称，第二个参数是url，同名的窗口会被移到前面，但是地址不会被重新加载，建议打开之前用hasOpen判断
 */
public class Open extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        String name = params.getString(0);
        String url = params.getString(1);
        if (activity != null) {
            if (activity.hasPage(name)) {
                activity.goPage(name);
            } else {
                activity.addPage(name, url);
            }
        }

        return new JSONObject();
    }
}
