package com.example.locke.myapplication.js;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 关闭App本身
 */
public class CloseApp extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        final MainActivity activity = fragment.getMainActivity();
        if (activity != null) {
            activity.finish();
        }
        return new JSONObject();
    }
}
