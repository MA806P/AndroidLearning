package com.example.ma806p.animationdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    AnimationDrawable drawable;
    private ImageView image;

    Animation animation;
    private ImageView image2;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.imageView);
        drawable = (AnimationDrawable)image.getBackground();

        image2 = (ImageView)findViewById(R.id.imageView2);
//        animation = AnimationUtils.loadAnimation(this, R.drawable.rotate_anim);
//        animation.setFillAfter(true);//设置保存最后一帧， 动画结束后不还原
        animation = AnimationUtils.loadAnimation(this, R.animator.set_animation); //组合动画

    }



    public void animationBegin(View view) {
        drawable.start();

        image2.startAnimation(animation);
    }

    public void animationEnd(View view) {
        drawable.stop();
    }


    public void toNextPageDemo(View view) {

        Intent intent = new Intent(this, MainAnimationActivity.class);
        startActivity(intent);

    }


}
