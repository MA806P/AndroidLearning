package com.example.ma806p.activity_test;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public TextView resultShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultShow = (TextView)findViewById(R.id.textViewResult);
    }


    public void toSecondActivity(View view) {
        Intent intent = new Intent();
        //直接说明所要启动的Activity是哪个
        intent.setClass(this, SecondActivity.class);

        intent.putExtra("value1", 10);
        intent.putExtra("value2", 12);

        //启动
        //startActivity(intent);
        startActivityForResult(intent, 0x0001);
    }


    //当下个页面有返回时自动回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //requestCode上面设置的，resultCode下个页面设置code
        if (requestCode == 0x0001 && resultCode == 0x0002) {
            int result = data.getIntExtra("result", -1);
            resultShow.setText(result + ""); //这个要注意一下，如果只写setText(result)一直报错，可能是int类型的不能setText吧
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public void toThirdActivity(View view) {
        Intent intent = new Intent();
        //设置逻辑动作名
        intent.setAction("heying.action.third");
        //添加类别
        intent.addCategory("heying.action.catrgory.third");
        //传递到下一个界面的值
        //intent.putExtra("hello", "just say hello");
        Bundle bundle = new Bundle();
        bundle.putString("hello", "hello from bundle");
        bundle.putInt("test", 10);
        intent.putExtras(bundle);


        startActivity(intent);
    }


    public void toFourthActivity(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FourthActivity.class);
        startActivity(intent);
    }


    public void toSystemActivity(View view) {
        //调用系统内部的界面
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("content://contacts/people")); //联系人
        startActivity(intent);
    }



    //-------- activity 启动模式
    public void launchSingleTop(View view) {
        Intent intent = new Intent();
        intent.setClass(this, AAAActivity.class);
        startActivity(intent);
    }


    //singleTop
    //singleTask
    //singleInstance



}
