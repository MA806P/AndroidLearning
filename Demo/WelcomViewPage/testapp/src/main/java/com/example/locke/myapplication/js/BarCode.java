package com.example.locke.myapplication.js;

import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import com.example.locke.myapplication.BarcodeActivity;
import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BarCode extends JavaScriptFunction {
    String callback;
    WebViewFragment fragment;

    @Override
    JSONObject run(JSONArray params, final WebViewFragment fragment) throws JSONException {
        final MainActivity activity = fragment.getMainActivity();
        activity.onRequestPermissions(Manifest.permission.CAMERA, new MainActivity.PermissionsResult() {
            @Override
            public void success() {
                Intent intent = new Intent(activity, BarcodeActivity.class);
                activity.toActivity(intent, new MainActivity.ActivityResult() {
                    @Override
                    public void result(int requestCode, int resultCode, @Nullable Intent data) {
                        JSONObject result = new JSONObject();
                        if(data!=null){
                            String code = data.getStringExtra("code");
                            String format  = data.getStringExtra("format");
                            try {
                                result.put("code", code);
                                result.put("format", format);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            callback(callback, result, fragment);
                        }
                        callback(callback, result, fragment);
                    }
                });
            }

            @Override
            public void failed() {
                activity.showToast("没有权限使用相机，请检查授权！");
            }
        });
        return new JSONObject();
    }

    @Override
    public void execute(JSONArray params, WebViewFragment fragment, String callback) {
        MainActivity activity = (MainActivity) fragment.getActivity();
        this.callback = callback;
        this.fragment = fragment;
        try {
            JSONObject result = run(params, fragment);
        } catch (JSONException e) {
            e.printStackTrace();
            activity.showToast("参数错误，请检查！");
        }
    }

}
