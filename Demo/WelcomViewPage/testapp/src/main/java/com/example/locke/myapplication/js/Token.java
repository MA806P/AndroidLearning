package com.example.locke.myapplication.js;

import android.view.View;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 设置token，这样在安全域名下的请求就会自动带上token
 *
 */
public class Token extends JavaScriptFunction {
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        MainActivity activity = fragment.getMainActivity();
        JSONObject jsonObject = new JSONObject();
        if(activity != null){
            String token = params.getString(0);
            if(token!=null && !token.isEmpty()){
                activity.setData("token", token);
            }else{
                token = activity.getData("token");
            }
            jsonObject.put("token", token);
        }
        return new JSONObject();
    }
}
