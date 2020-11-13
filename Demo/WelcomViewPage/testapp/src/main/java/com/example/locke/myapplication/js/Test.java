package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test extends JavaScriptFunction {

    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a", 10000);
        return jsonObject;
    }
}
