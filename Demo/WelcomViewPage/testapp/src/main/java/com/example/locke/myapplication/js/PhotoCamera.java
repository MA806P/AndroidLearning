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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 打开照相机，拍照结束后，图片会以base64的模式回调回来，这里头应该要加上图片压缩和处理等能力比较好
 */
public class PhotoCamera extends JavaScriptFunction {
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

        activity.onRequestPermissions(Manifest.permission.CAMERA, new MainActivity.PermissionsResult() {
            @Override
            public void success() {
                tackCamera(activity);
            }

            @Override
            public void failed() {
                activity.showToast("没有权限使用相机，请检查授权！");
            }
        });

        return new JSONObject();
    }

    private void tackCamera(final MainActivity activity) {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = null;
        File imageFile = null;
        if (takeIntent.resolveActivity(activity.getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
            imageFile = createImageFile(activity);//创建用来保存照片的文件
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                /*7.0以上要通过FileProvider将File转化为Uri*/
                imageUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".photo", imageFile);
            } else {
                /*7.0以下则直接使用Uri的fromFile方法将File转化为Uri*/
                imageUri = Uri.fromFile(imageFile);
            }
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将用于输出的文件Uri传递给相机
        }

        //final Uri finalImageUri = imageUri;
        final File finalImageFile = imageFile;
        final Uri finalImageUri = imageUri;
        activity.toActivity(takeIntent, new MainActivity.ActivityResult() {
            @Override
            public void result(int requestCode, int resultCode, @Nullable Intent data) {
                if (resultCode == MainActivity.RESULT_OK) {
                    //Bitmap bit = data.getParcelableExtra("data");

                    //InputStream inputStream = activity.getContentResolver().openInputStream(finalImageUri);


                    /*try {
                        InputStream inputStream = activity.getContentResolver().openInputStream(finalImageUri);
                        Bitmap bit = BitmapFactory.decodeStream(inputStream);
                        bit = Helper.ResizeBitmap(bit, 1024, 1024);
                        OutputStream outputStream = activity.getContentResolver().openOutputStream(finalImageUri);
                        bit.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        String base64 = Helper.BitmapToBase64(bit);
                        base64 = "data:image/jpg;base64," + base64;
                        JSONObject result = new JSONObject();
                        try {
                            result.put("value", base64);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        callback(callback, result, fragment);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }*/

                    //Log.i("bit", String.valueOf(bit));
                    //压缩处理图片代码
                    Luban.with(activity.getApplicationContext())
                            .load(Uri.fromFile(finalImageFile))
                            .ignoreBy(100)
                            .setTargetDir(getPath(activity))
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(File file) {
                                    finalImageFile.delete();
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

                    //压缩处理完图片，将图片转成Base64编码，方便页面使用
                        /*String base64 = Helper.BitmapToBase64(bit);
                        base64 = "data:image/jpg;base64," + base64;
                        JSONObject result = new JSONObject();
                        result.put("value", base64);

                        callback(callback, result, fragment);*/

                }
            }
        });
    }

    public Uri getMediaFileUri() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "APP1");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return Uri.fromFile(mediaFile);
    }

    /**
     * 创建用来存储图片的文件，以时间来命名就不会产生命名冲突
     *
     * @return 创建的图片文件
     */
    private File createImageFile(Activity activity) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = null;
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
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
