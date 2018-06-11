package com.example.ma806p.networktest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText etState;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etState = (EditText)findViewById(R.id.editText);
        imageView = (ImageView)findViewById(R.id.imageView);
    }


    public void testNetworkState(View view) {
        //获取网络连接状态
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

//        @SuppressLint("MissingPermission") NetworkInfo[] networkInfo = cm.getAllNetworkInfo();
//        if (networkInfo == null) {
//            return;
//        }
//        StringBuilder sb = new StringBuilder();
//        for(NetworkInfo info:networkInfo) {
//            sb.append(info.toString() + "\n\n");
//        }
//        etState.setText(sb.toString());


        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        etState.setText(networkInfo.getTypeName() + "," + networkInfo.isConnected());
    }

    public void optionNetwork(View view) {

        Tools.checkNetworkState(this);
        if (!Tools.NETWORK_STATE) {
            //在通知栏显示通知, 网络不可用，跳转设置页面连接网络
            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);//网络设置页面
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);


            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setContentTitle("标题")//设置通知栏标题
                    .setContentText("内容") //设置通知栏显示内容
                    .setTicker("通知到来") //通知首次出现在通知栏，带上升动画效果的
                    .setContentIntent(contentIntent) //设置通知栏点击意图
                    //.setNumber(number) //设置通知集合的数量
                    //.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    //.setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
                    //.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                    //.setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                    //.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                    .setSmallIcon(R.mipmap.ic_launcher_round);//设置通知小ICON
            //Notification notification = mBuilder.getNotification(); //Level11以及以上版本时
            Notification notification = mBuilder.build(); //Level16以及以上版本时

            notificationManager.notify(1, notification);
        } else {

            //网络可用，从网络获取数据
            new MyTask().execute(Tools.WEB_URL);
        }
    }


    //子线程，执行获取网络数据
    class MyTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {

            String path = strings[0];

            //创建URL
            URL url = null;
            try {
                url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                //设置属性
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setDoInput(true);
                conn.connect();
                //得到响应代码
                int responseCode = conn.getResponseCode();
                if (responseCode == 200) {
                    Log.i("test", "ok");
                    InputStream is = conn.getInputStream();
                    Bitmap bmp = BitmapFactory.decodeStream(is);
                    return bmp;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Bitmap bitmap) {

            if (bitmap != null) {
                //得到网络图片，在ImageView中显示出来
                imageView.setImageBitmap(bitmap);
            }

            super.onPostExecute(bitmap);
        }
    }

}
