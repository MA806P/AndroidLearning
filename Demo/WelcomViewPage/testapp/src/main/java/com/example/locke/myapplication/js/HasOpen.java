package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 判断指定名称的窗体是否存在，第一个参数是窗体名称
 */
public class HasOpen extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        String name = params.getString(0);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("open", activity.hasPage(name));

        return jsonObject;
    }
}
