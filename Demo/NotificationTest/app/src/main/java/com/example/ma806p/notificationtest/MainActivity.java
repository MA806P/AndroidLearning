package com.example.ma806p.notificationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    private static final int NOTIFICATION_FLAG = 1;

    private MyReceiver2 myReceiver2;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //-------
        myReceiver2 = new MyReceiver2();
        IntentFilter filter = new IntentFilter("myreceiver.test2");
        //以代码方式注册广播
        registerReceiver(myReceiver2, filter);


    }

    @Override
    protected void onDestroy() {
        //取消注册，接收者无法收到广播
        unregisterReceiver(myReceiver2);
        super.onDestroy();
    }

    //------------- 广播
    public void broadcaAction(View view) {

        Log.i("main", "sendBroadcast");

        switch (view.getId()) {
            case R.id.button2:
                //发送广播
                Intent intent = new Intent("myreceiver.test");
                sendBroadcast(intent);
                break;

            case R.id.button3:
                //发送广播
                Intent intent2 = new Intent("myreceiver.test2");
                sendBroadcast(intent2);
                break;
        }

    }



    public void toDemoActivity(View view) {

        Intent intent = new Intent(this, MainDemoActivity.class);
        startActivity(intent);
        finish(); //自动调用 onDestroy();
    }







    //---------------- 通知
    public void notificationAction(View view) {

        Log.i("main activity", "log");

        //早期版本，兼容2.x
//        Intent intent = new Intent(this, MainDemoActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//        Notification notification = new Notification();
//        notification.icon = R.mipmap.ic_launcher_round;
//        notification.tickerText = "new message";
//        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        notification.setLatestEventInfo(this, "Title", "Content", pendingIntent);


//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentInfo("补充内容");
//        builder.setContentText("主内容区");
//        builder.setContentTitle("通知标题");
//        builder.setSmallIcon(R.mipmap.ic_launcher_round);
//        builder.setTicker("新消息");
//        builder.setAutoCancel(true);
//        builder.setWhen(System.currentTimeMillis());
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();




        Intent intent = new Intent(this, MainDemoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setContentTitle("标题")//设置通知栏标题
                .setContentText("内容") //设置通知栏显示内容
                .setTicker("通知到来") //通知首次出现在通知栏，带上升动画效果的
                .setContentIntent(pendingIntent) //设置通知栏点击意图
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

        notificationManager.notify(NOTIFICATION_FLAG, notification);
    }



}
