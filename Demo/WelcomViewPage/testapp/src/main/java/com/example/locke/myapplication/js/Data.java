package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 设置变量保存到App本身的SP空间内，持久化
 * 第一个参数是key，第二个参数是value
 * 如果只有key，就是获取变量，如果key和value都有，则是更新变量
 */
public class Data extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        JSONObject jsonObject = new JSONObject();
        if (activity != null) {
            String key = params.getString(0);
            String value;

            if (params.length() > 1) {
                value = params.getString(1);
                activity.setData(key, value);
            } else {
                value = activity.getData(key);
            }
            jsonObject.put("key", key);
            jsonObject.put("value", value);
        }
        return new JSONObject();
    }
}
