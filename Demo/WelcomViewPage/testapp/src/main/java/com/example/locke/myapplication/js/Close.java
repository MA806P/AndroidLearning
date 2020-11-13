package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 关闭窗口，如果没有参数，就是关闭当前窗口，如果指定名称，就是关闭指定名称的窗口（画面会闪一下，暂时没想到办法怎么解决）
 */
public class Close extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        final MainActivity activity = fragment.getMainActivity();
        if (activity != null) {
            String name = null;
            if(params.length()>0){
                name = params.getString(0);
            }
            final String finalName = name;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.removePage(finalName);
                }
            });
        }
        return new JSONObject();
    }
}
