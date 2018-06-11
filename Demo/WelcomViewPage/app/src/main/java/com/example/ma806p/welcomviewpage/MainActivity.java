package com.example.ma806p.welcomviewpage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private View[] views;
    private ViewPager viewPager;
    private ImageView[] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        views = new View[3];
        LayoutInflater inflater = LayoutInflater.from(this);
        views[0] = inflater.inflate(R.layout.layout_view1, null);
        views[1] = inflater.inflate(R.layout.layout_view2, null);
        views[2] = inflater.inflate(R.layout.layout_view3, null);

        viewPager = (ViewPager)findViewById(R.id.welcom_page_view);
        viewPager.setAdapter(new MyAdapter());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedImage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //获取到滚动的指示图片
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.dot_layout);
        int count = linearLayout.getChildCount();
        imageViews = new ImageView[count];

        for (int i = 0; i < count; i++) {
            imageViews[i] = (ImageView)linearLayout.getChildAt(i);
            imageViews[i].setTag(i);
            imageViews[i].setEnabled(true);
            imageViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = (int)v.getTag();
                    viewPager.setCurrentItem(item);
                    selectedImage(item);
                }
            });
        }
        imageViews[0].setEnabled(false);

    }


    private void selectedImage(int index) {
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setEnabled(true);
        }
        imageViews[index].setEnabled(false);
        //Log.i("test", index+"");

    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views[position]);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(views[position]);
            return views[position];
        }
    }

}
