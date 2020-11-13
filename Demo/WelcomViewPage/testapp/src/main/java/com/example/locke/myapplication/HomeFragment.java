package com.example.locke.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * 主画面，默认App启动的时候就用这个页面，页面里头包含了一个viewpager和底部的并排按钮以及多个webview，不同的App以后要根据实际情况来定义了
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private ViewPager viewpager;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        BottomNavigationView navigation = view.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewpager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_dashboard:
                        viewpager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
                        viewpager.setCurrentItem(2);
                        return true;
                }
                return false;
            }
        });

        viewpager = view.findViewById(R.id.viewpager);
        viewpager.setOffscreenPageLimit(3);
        MyFragmentPagerAdapter pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        viewpager.setAdapter(pagerAdapter);


        return view;
    }

    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }*/

    @Override
    public void onDestroy() {
        PagerAdapter adapter = viewpager.getAdapter();
        if (adapter instanceof MyFragmentPagerAdapter) {
            MyFragmentPagerAdapter myFragmentPagerAdapter = (MyFragmentPagerAdapter) adapter;
            myFragmentPagerAdapter.destroy();
        }
        super.onDestroy();
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = new ArrayList<>();

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(WebViewFragment.newInstance("file:///android_asset/index.html"));
            fragments.add(WebViewFragment.newInstance("https://v4.bootcss.com/"));
            fragments.add(WebViewFragment.newInstance("file:///android_asset/index2.html"));
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void destroy() {
            for (Fragment fragment : fragments) {
                fragment.onDestroy();
            }
        }
    }
}
