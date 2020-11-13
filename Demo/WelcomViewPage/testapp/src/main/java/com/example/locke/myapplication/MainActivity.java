package com.example.locke.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
 * 主Activity，
 */
public class MainActivity extends AppCompatActivity {
    public static final String HOME = "home"; //主页的tag名字
    public static final String DATA = "data"; //数据储存的前缀

    private PermissionsResult permissionsResult = null; //主动授权时候的接口定义，这里头可能有bug，因为这样只能同时提起一个授权，超过1个就会乱
    private ActivityResult activityResult = null; //窗口切换回来的时候，负责回调数据的类，这里头可能有bug，也就是只能回调1次，多次可能会出问题


    private List<String> safeDomain = new ArrayList<>(); //定义安全域名

    private Fragment currentFragment = null; //当前顶层的fragment对象
    private Stack<String> backStack = new Stack<>(); //fragment的后退堆栈，没用fragmentmanager自家的，之后可以考虑一下

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //定义安全域名，这些域名才会注册CallApp的方法
        safeDomain.add("192.168.3.87");
        safeDomain.add("192.168.31.103");
        safeDomain.add("192.168.3.131");
        safeDomain.add("192.168.3.87");
        safeDomain.add("192.168.5.24");

        //把加入主页
        addPage(HOME, HomeFragment.newInstance());
        //goHome();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onDestroy();
        }
    }

    /**
     * 跳到指定页面
     *
     * @param name
     */
    public void goPage(String name) {
        toPage(name);
    }

    /**
     * 回到主页
     */
    public void goHome() {
        backStack.clear();
        toPage(HOME);
    }

    /**
     * 判断是否存在某个页面
     *
     * @param name
     * @return
     */
    public boolean hasPage(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
        return fragment != null;
    }

    /**
     * 加一个WebViewFragment到FragmentManager里头去
     *
     * @param name
     * @param url
     */
    public void addPage(String name, String url) {
        WebViewFragment page = createPage(url);
        toPage(name, page);
    }

    /**
     * 加一个Fragment到FragmentManager里头去
     *
     * @param name
     * @param fragment
     */
    public void addPage(String name, Fragment fragment) {
        toPage(name, fragment);
    }

    /**
     * 创建一个WebViewFragment
     *
     * @param url
     * @return
     */
    private WebViewFragment createPage(String url) {
        return WebViewFragment.newInstance(url);
    }

    /**
     * 跳到指定页面
     *
     * @param name
     */
    public void toPage(String name) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
        toPage(name, fragment);
    }


    public void toPage(String name, Fragment fragment) {
        //fragment不等于null 并且 不是当前的Fragment
        if (fragment != null && fragment != getCurrentFragment()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //如果没有添加
            if (!fragment.isAdded()) {
                //如果当前不为null，尝试隐藏他
                if (currentFragment != null) {
                    transaction
                            .setCustomAnimations(R.anim.slide_left_out, R.anim.slide_right_out)
                            .hide(currentFragment);
                }
                //把新的fragment加到FragmentManager里头去
                transaction
                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                        .add(R.id.container, fragment, name);
            } else {
                //这是已经在FragmentManager里头的Fragment
                transaction
                        .setCustomAnimations(R.anim.slide_left_out, R.anim.slide_right_out)
                        .hide(currentFragment)
                        .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out)
                        .show(fragment);
            }
            //设置当前的Fragment
            currentFragment = fragment;
            //处理回退堆栈
            String currentKey = backStack.isEmpty() ? null : backStack.peek();
            if (!name.equals(currentKey)) {
                backStack.remove(name);
                backStack.push(name);
            }
            //transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /**
     * 移除一个页面
     *
     * @param name
     */
    public void removePage(String name) {
        if (name == null || name.isEmpty()) {
            name = backStack.peek();
        }
        if (this.hasPage(name)) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(name);
            if (currentFragment == fragment) {
                backPage();
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction
                    .setCustomAnimations(R.anim.slide_left_out, R.anim.slide_right_out)
                    .remove(fragment).commit();
            if (fragment != null)
                fragment.onDestroy();
        }
    }

    /**
     * 回退
     */
    public void backPage() {
        if (backStack.size() > 1) {
            backStack.pop();
            String key = backStack.peek();
            toPage(key);
        }
    }


    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    /**
     * 判断是否安全域名
     *
     * @param url
     * @return
     */
    public boolean isSafeDomain(String url) {
        Uri uri = Uri.parse(url);
        String domain = uri.getHost();
        return safeDomain.contains(domain);
    }

    /**
     * 获取持久化数据
     *
     * @param key
     * @return
     */
    public String getData(String key) {
        key = DATA + "_" + key;
        SharedPreferences preferences = getSharedPreferences(DATA, Context.MODE_PRIVATE);
        return preferences.getString(key, null);
    }

    /**
     * 保存持久化数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean setData(String key, String value) {
        key = DATA + "_" + key;
        SharedPreferences preferences = getSharedPreferences(DATA, Context.MODE_PRIVATE);
        return preferences.edit().putString(key, value).commit();
    }

    /**
     * 判断持久化数据是否存在
     *
     * @param key
     * @return
     */
    public boolean hasData(String key) {
        key = DATA + "_" + key;
        SharedPreferences preferences = getSharedPreferences(DATA, Context.MODE_PRIVATE);
        return preferences.contains(key);
    }

    /**
     * 显示面包屑toast
     *
     * @param message
     * @param type
     */
    public void showToast(final String message, final int type) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), message,
                        type).show();
            }
        });
    }

    public void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
    }

    /**
     * 发起主动权限申请
     *
     * @param permissions
     * @param result
     */
    public void onRequestPermissions(String permissions, PermissionsResult result) {
        this.permissionsResult = result;
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                permissions);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permissions}, 1001);
        } else {
            result.success();
        }
    }

    /**
     * 主动权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 申请同意
                    permissionsResult.success();
                } else {
                    //申请拒绝
                    showToast("您拒绝了授权，功能可能无法正常工作！");
                    permissionsResult.failed();
                }
            }
        }
    }

    /**
     * 主动权限申请的回调类
     */
    public interface PermissionsResult {
        void success();

        void failed();
    }

    /**
     * 窗口切换的回调类
     */
    public interface ActivityResult {
        void result(int requestCode, int resultCode, @Nullable Intent data);
    }

    /**
     * 发起窗口切换
     *
     * @param intent
     * @param result
     */
    public void toActivity(Intent intent, ActivityResult result) {
        this.activityResult = result;
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 & activityResult != null) {
            activityResult.result(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
