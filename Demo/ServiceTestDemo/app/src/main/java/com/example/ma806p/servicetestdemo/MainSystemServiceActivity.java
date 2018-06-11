package com.example.ma806p.servicetestdemo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainSystemServiceActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_system_service);

        //得到系统服务
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //闹铃
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }


    //调整系统音量
    public void changeBtnAction(View view) {

        // streamType 调整系统音量 类型，手机 闹铃 音乐
        audioManager.adjustStreamVolume(AudioManager.STREAM_VOICE_CALL, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        // audioManager.setStreamVolume(streamType, index, flags)
    }


    //设置闹铃提醒
    public void alarmBtnAction (View view) {
        long time = System.currentTimeMillis();
        time += 3000;

        Intent intent = new Intent("heying.mysystemservice");

        PendingIntent pi = PendingIntent.getService(this, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);
    }

}
