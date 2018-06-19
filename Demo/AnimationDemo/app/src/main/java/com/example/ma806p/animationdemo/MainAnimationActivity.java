package com.example.ma806p.animationdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainAnimationActivity extends AppCompatActivity {

    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_animation);

        imageView = (ImageView)findViewById(R.id.imageView3);
    }


    public void valueAnimRun(View view) {

        ValueAnimator animator = ValueAnimator.ofFloat(0, 300);
        animator.setTarget(imageView);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue(); //当前运行动画的值
                imageView.setTranslationY(value);
            }
        });
        animator.start();

    }

    public void objectAnimRun(View view) {

//        //参数：使用动画的对象  使用动画的属性名  变化的值float。。。可以是数组
//        ObjectAnimator.ofFloat(imageView, "alpha", 0, 1) //两个值开始位置，结束位置
//        .setDuration(3000)
//        .start();


        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue(); //当前运行动画的值
                imageView.setScaleX(value);
                imageView.setScaleY(value);
            }
        });
        animator.start();

    }


}
