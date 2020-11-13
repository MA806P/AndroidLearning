package com.example.locke.myapplication.js;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.locke.myapplication.BuildConfig;
import com.example.locke.myapplication.Helper;
import com.example.locke.myapplication.MainActivity;
import com.example.locke.myapplication.WebViewFragment;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 打开系统相册，选择图片后图片以base64的模式在回调中返回回来
 * 这里还要优化图片压缩和处理等动作
 */
public class PhotoAlbum extends JavaScriptFunction {
    String callback;
    WebViewFragment fragment;

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

    @Override
    public JSONObject run(JSONArray params, WebViewFragment fragment) throws JSONException {
        final MainActivity activity = fragment.getMainActivity();

        activity.onRequestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, new MainActivity.PermissionsResult() {
            @Override
            public void success() {
                choosePhoto(activity);
            }

            @Override
            public void failed() {
                activity.showToast("没有权限查看相册，请检查授权！");
            }
        });

        return new JSONObject();
    }

    private void choosePhoto(final MainActivity activity) {
        final Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.toActivity(intentToPickPic, new MainActivity.ActivityResult() {
            @Override
            public void result(int requestCode, int resultCode, @Nullable Intent data) {
                if (resultCode == MainActivity.RESULT_OK && data != null) {
                    Uri imgUri = data.getData();
                    if (imgUri != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            //相册会返回一个由相册安全策略定义的Uri，app使用这个Uri直接放入裁剪程序会不识别，抛出[暂不支持此类型：华为7.0]
                            //formatUri会返回根据Uri解析出的真实路径
                            String imgPathSel = Helper.FormatUri(activity.getApplicationContext(), imgUri);
                            imgUri = Uri.fromFile(new File(imgPathSel));
                            //根据真实路径转成File,然后通过应用程序重新安全化，再放入裁剪程序中才可以识别
                            //imgUri = FileProvider.getUriForFile(activity.getApplicationContext(), BuildConfig.APPLICATION_ID + ".photo", new File(imgPathSel));
                        }

                        //压缩处理图片代码
                        Luban.with(activity.getApplicationContext())
                                .load(imgUri)
                                .ignoreBy(100)
                                .setTargetDir(getPath(activity))
                                .setCompressListener(new OnCompressListener() {
                                    @Override
                                    public void onStart() {

                                    }

                                    @Override
                                    public void onSuccess(File file) {
                                        Bitmap bit = BitmapFactory.decodeFile(file.getAbsolutePath());
                                        Log.i("bit", String.valueOf(bit));
                                        //bit = Helper.ResizeBitmap(bit, 1024, 1024);

                                        //压缩处理完图片，将图片转成Base64编码，方便页面使用
                                        String base64 = Helper.BitmapToBase64(bit);
                                        base64 = "data:image/jpg;base64," + base64;
                                        JSONObject result = new JSONObject();
                                        try {
                                            result.put("value", base64);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        callback(callback, result, fragment);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).launch();
                    }
                }
            }
        });
    }

    private String getPath(Activity activity) {
        String path = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }
}
