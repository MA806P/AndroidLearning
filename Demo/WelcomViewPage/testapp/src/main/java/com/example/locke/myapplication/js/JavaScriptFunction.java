package com.example.locke.myapplication.js;

import android.support.v4.app.FragmentActivity;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 根据name去找对象，然后尝试将对象实例化，所有的js方法可以调用的原生方法都被封装成了JavaScriptFunction
 * 这个抽象基类的派生类，这个抽象类里头默认有一个execute方法，只要传入参数，回调方法，和fragment本身
 * execute方法回调用抽象方法run，run才是真正对应的原生方法业务逻辑代码，run返回可以返回一个json对象，这个对象
 * execute里头会用callback把json对象通过回调webview的js的回调方法的形式回调告诉给webview
 */
public abstract class JavaScriptFunction {
    /**
     * 原生方法的主业务逻辑所在
     * @param params
     * @param fragment
     * @return
     * @throws JSONException
     */
    abstract JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException;

    /**
     * 调用的入口
     * @param params
     * @param fragment
     * @param callback
     */
    public void execute(JSONArray params, WebViewFragment fragment, String callback){
        MainActivity activity = (MainActivity) fragment.getActivity();
        try {
            JSONObject result = run(params, fragment);
            callback(callback, result, fragment);
        } catch (JSONException e) {
            e.printStackTrace();
            activity.showToast("参数错误，请检查！");
        }
    }

    /**
     * 回调方法
     * @param callback
     * @param result
     * @param fragment
     */
    protected void callback(String callback, JSONObject result, final WebViewFragment fragment){
        if (callback != null) {
            final String finalCallback = callback;
            final JSONObject finalResult = result;
            FragmentActivity activity = fragment.getActivity();
            //将回调放到UI主线程中运行，这样有些回调会弹窗什么的才会工作
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    fragment.jsCallback(finalCallback, finalResult);
                }
            });
        }
    }
}
