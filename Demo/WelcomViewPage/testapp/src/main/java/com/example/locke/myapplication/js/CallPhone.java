package com.example.locke.myapplication.js;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 打电话，第一参数就是电话号码
 */
public class CallPhone extends JavaScriptFunction{
    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        final MainActivity activity = fragment.getMainActivity();
        final String phone = params.getString(0);

        activity.onRequestPermissions(Manifest.permission.CALL_PHONE, new MainActivity.PermissionsResult() {
            @Override
            public void success() {
                callphone(activity, phone);
            }

            @Override
            public void failed() {
                activity.showToast("没有权限拨打电话，请检查授权！");
            }
        });

        return new JSONObject();
    }

    private void callphone(MainActivity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
//        activity.startActivity(intent);
        activity.toActivity(intent, new MainActivity.ActivityResult() {
            @Override
            public void result(int requestCode, int resultCode, @Nullable Intent data) {

            }

        });
    }
}
