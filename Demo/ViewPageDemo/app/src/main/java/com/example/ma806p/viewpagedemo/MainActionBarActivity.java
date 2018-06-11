package com.example.ma806p.viewpagedemo;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActionBarActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private Fragment[] fragments = {new MyFragment1(), new MyFragment2()};
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_action_bar);

        //获取ActionBar实例
        actionBar = getActionBar();
        //出错这里返回的null，不知道啥原因
        //Log.i("testastastastasta", actionBar.toString());

        //设置导航模式
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        MyTabClickListener listener = new MyTabClickListener();

        ActionBar.Tab tab1 = actionBar.newTab();
        tab1.setText("TAB1");
        //tab1.setTag(0);
        tab1.setTabListener(listener);

        ActionBar.Tab tab2 = actionBar.newTab();
        tab2.setText("TAB2");
        tab2.setTabListener(listener);

        actionBar.addTab(tab1);
        actionBar.addTab(tab2);

        viewPager = (ViewPager)findViewById(R.id.action_bar_view_pager);
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ActionBar.Tab tab = actionBar.getTabAt(position);
                actionBar.selectTab(tab);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    class MyTabClickListener implements ActionBar.TabListener {

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            if (viewPager != null) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
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
    }


}
