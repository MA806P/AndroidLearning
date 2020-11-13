package com.example.locke.myapplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.locke.myapplication.js.JavaScriptFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {
    private static final String JS_CALLAPP = "function CallApp(name,params,callback){if(params==null){params=[]};let data={name:name,params:params,callback:null};if(callback!=null){var callback_name='C'+Math.random().toString(36).substr(2);window[callback_name]=function(obj){callback(obj);delete window[callback_name]};data.callback=callback_name}Android.call(JSON.stringify(data))};";
    private String url;
    private ProgressBar progressBar;
    private WebView webview;
    private String token;
    private MainActivity activity;
    public WebViewFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param url Parameter 2.
     * @return A new instance of fragment WebViewFragment.
     */
    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        fragment.url = url;
        return fragment;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_web_view, container, false);
        //赋值Activity，方便后面使用
        activity = (MainActivity) getActivity();
        // 进度条
        progressBar = inflate.findViewById(R.id.progressBar);
        webview = inflate.findViewById(R.id.webview);
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //处理进度条
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    if (progressBar.getVisibility() == View.GONE)
                        progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
                //super.onProgressChanged(view, newProgress);
            }
        });

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //判断是不是安全域名，是的话，就注册CallApp方法给webview
                if (isSafeDomain()) {
                    registerCallApp();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //收到错误信息的话，显示错误页面
                loadErrorPage();
            }

            //安全域名，加token
            // Handle API until level 21
            @SuppressWarnings("deprecation")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                //如果是安全域名，则请求任何的地址，都自动带上token这类信息
                if (isSafeDomain()) {
                    return getNewResponse(url);
                }

                return super.shouldInterceptRequest(view, url);
            }

            // Handle API 21+
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                //如果是安全域名，则请求任何的地址，都自动带上token这类信息
                if (isSafeDomain()) {
                    String url = request.getUrl().toString();
                    return getNewResponse(url);
                }
                return super.shouldInterceptRequest(view, request);
            }
        });

        //开启webview的远程调试，在chrome里头输入chrome://inspect/#devices地址可以远程调试
        WebView.setWebContentsDebuggingEnabled(true);

        //开启webview的js
        webview.getSettings().setJavaScriptEnabled(true);

        //注册一个js的接口
        webview.addJavascriptInterface(this, "Android");

        //加载指定的url
        loadUrl(url);

        return inflate;
    }

    /**
     * 网页js调用原生的统一接口方法，data是json字符串
     * name是调用原生的方法名称，params是参数，callback是原生返回数据给js的回调方法的名称
     * @param data
     */
    @JavascriptInterface
    public void call(String data) {
        Log.d("JS:", data);
        String name = null, callback = null;
        //解释name， params， callback参数
        JSONArray params = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(data);
            name = jsonObject.getString("name"); //名字，对应js类的类名
            params = jsonObject.getJSONArray("params"); //参数
            callback = jsonObject.getString("callback");  //回调函数名
        } catch (JSONException e) {
            e.printStackTrace();
            activity.showToast("原始参数错误，请检查！");
        }

        /**
         * 根据name去找对象，然后尝试将对象实例化，所有的js方法可以调用的原生方法都被封装成了JavaScriptFunction
         * 这个抽象基类的派生类，这个抽象类里头默认有一个execute方法，只要传入参数，回调方法，和fragment本身
         * execute方法回调用抽象方法run，run才是真正对应的原生方法业务逻辑代码，run返回可以返回一个json对象，这个对象
         * execute里头会用callback把json对象通过回调webview的js的回调方法的形式回调告诉给webview
         */
        try {
            Class cls = Class.forName("com.example.locke.myapplication.js." + name);
            JavaScriptFunction javaScriptFunction = (JavaScriptFunction) cls.newInstance();
            javaScriptFunction.execute(params, this, callback);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            activity.showToast("找不到原始的对象！");
        } catch (IllegalAccessException | java.lang.InstantiationException e) {
            e.printStackTrace();
            activity.showToast("实例对象失败！");
        }

    }

    /**
     * 调用js的回调方法
     * @param name
     * @param data
     */
    public void jsCallback(String name, JSONObject data) {
        webview.evaluateJavascript("javascript:window." + name + "(" + data.toString() + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d("JS:", value);
            }
        });
    }

    /**
     * 注册CallApp这个函数到webview中去，这样只要在webview中的页面，通过window.CallApp就能调用原生的方法
     */
    private void registerCallApp() {
        webview.evaluateJavascript("javascript:" + JS_CALLAPP, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                Log.d("JS", "Register CallApp " + value);
            }
        });
    }

    /**
     * 加载错误页面
     */
    private void loadErrorPage() {
        webview.loadUrl("file:///android_asset/missing.html");
    }

    /**
     * 加载地址
     * @param url
     */
    public void loadUrl(String url) {
        webview.loadUrl(url);
    }

    /**
     * 判断是不是安全域名
     * @return
     */
    public boolean isSafeDomain() {
        MainActivity activity = (MainActivity) getActivity();
        return activity.isSafeDomain(url);
    }

    /**
     * 修改每个请求的header头信息，加上token
     * @param url
     * @return
     */
    private WebResourceResponse getNewResponse(String url) {

        try {
            OkHttpClient httpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder().url(url.trim());
            String token = getToken();
            if (token != null && token.isEmpty()) {
                builder.addHeader("Authorization", getToken()); // Example header
                //builder.addHeader("api-key", "YOUR_API_KEY") // Example header
            }

            Request request = builder.build();

            Response response = httpClient.newCall(request).execute();

            return new WebResourceResponse(
                    null,
                    response.header("content-encoding", "utf-8"),
                    response.body().byteStream()
            );

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取token，这个token是由页面设置进app的sp储存空间内的了
     * @return
     */
    public String getToken() {
        MainActivity activity = (MainActivity) getActivity();
        token = activity.getData("token");
        return token;
    }

    /**
     * 返回一个主activity
     * @return
     */
    public MainActivity getMainActivity() {
        return activity;
    }

    /**
     * 销毁的时候，把webview也销毁掉
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.removeAllViews();
        webview.destroy();
    }
}
