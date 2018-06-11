package com.example.ma806p.viewpagedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainMyViewPageActivity extends FragmentActivity {

    private TextView[] tvTitles;
    private LinearLayout tab_content_layout;
    private String[] titles = {"title1", "title2", "title3"};

    private ViewPager viewPager;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_page_top);

        init();

        //设置适配器
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }

    private void init() {

        viewPager = (ViewPager)findViewById(R.id.my_view_page_id);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fragments = new Fragment[3];
        fragments[0] = new MyFragment1();
        fragments[1] = new MyFragment2();
        fragments[2] = new MyFragment3();

        tab_content_layout = (LinearLayout)findViewById(R.id.layout_my_view_page_content);
        int count = tab_content_layout.getChildCount();
        tvTitles = new TextView[count];

        //从容器中得到子控件
        for (int i = 0; i < count; i++) {
            tvTitles[i] = (TextView)tab_content_layout.getChildAt(i);
            tvTitles[i].setText(titles[i]);
            tvTitles[i].setTag(i);
            tvTitles[i].setEnabled(true);
            tvTitles[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = (Integer)v.getTag();
                    viewPager.setCurrentItem(item);
                    selectedTitle(item);
                }
            });

        }

        tvTitles[0].setEnabled(false);//不可被单击
        tvTitles[0].setBackgroundColor(Color.BLUE);//演示为选中项

    }

    private void selectedTitle(int index) {
        for (int i = 0; i < tvTitles.length; i++) {
            tvTitles[i].setBackgroundColor(Color.GRAY);
            tvTitles[i].setEnabled(true);
        }

        tvTitles[index].setBackgroundColor(Color.BLUE);
        tvTitles[index].setEnabled(false);

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

}
