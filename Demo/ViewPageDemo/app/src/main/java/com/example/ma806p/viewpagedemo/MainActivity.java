package com.example.ma806p.viewpagedemo;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.annotation.NonNull;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {

    //private View[] views;
    private Fragment[] fragments;
    private ViewPager viewPager;
    private String[] titles = {"View1", "View2", "View3"};
    private PagerTabStrip pagerTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //设置ViewPager的适配器
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    //初始化
    private void init(){
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        //顶部指示栏
        pagerTabStrip = (PagerTabStrip)findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setBackgroundColor(Color.LTGRAY);
        pagerTabStrip.setTextColor(Color.YELLOW);
        pagerTabStrip.setTabIndicatorColor(Color.BLACK);

//        views = new View[3];
//        //得到布局的解析器
//        LayoutInflater inflater = LayoutInflater.from(this);
//        //得到view
//        views[0] = inflater.inflate(R.layout.layout_view1, null);
//        views[1] = inflater.inflate(R.layout.layout_view2, null);
//        views[2] = inflater.inflate(R.layout.layout_view3, null);


        fragments = new Fragment[3];
        fragments[0] = new MyFragment1();
        fragments[1] = new MyFragment2();
        fragments[2] = new MyFragment3();


    }


    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public int getCount() {
            return fragments.length;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


//    class MyAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return views.length;
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView(views[position]);
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//
//            container.addView(views[position]);
//            return views[position];
//        }
//    }


}
