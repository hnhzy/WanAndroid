package com.hzy.wanandroid.ui.activity;

import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvc.BaseActivity;
import com.hzy.wanandroid.widget.TitleBarLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * X5WebView 网页加载
 *
 * @author hzy
 */
public class X5WebView extends BaseActivity {

    @BindView(R.id.X5WebView)
    WebView webView;

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    private String mUrl = "http://www.baidu.com/";
    private String mTitle;

    @Override
    protected int getLayout() {
        return R.layout.x5_web;
    }

    @Override
    protected void initView() {
        mUrl = getIntent().getStringExtra("mUrl");
        mTitle = getIntent().getStringExtra("mTitle");
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle(mTitle);
        mTitleBar.setLeftBack(v -> finish());
    }

    @Override
    protected void initData() {
        myWeb(mUrl);
    }

    private void myWeb(String url) {
        webView = findViewById(R.id.X5WebView);
        WebChromeClient webChromeClient = new WebChromeClient();
        webView.setWebChromeClient(webChromeClient);
        WebSettings webSettings = webView.getSettings();

        /**
         * 屏幕适配
         */
        DisplaySetting(webSettings);

        /**
         * WebView设置
         */
        webSetting(webSettings);

        //去除QQ浏览器推广广告
        getWindow().getDecorView().addOnLayoutChangeListener((v, left, top, right, bottom,
                                                              oldLeft, oldTop, oldRight,
                                                              oldBottom) -> {
            ArrayList<View> outView = new ArrayList<View>();
            getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器",
                    View.FIND_VIEWS_WITH_TEXT);
            if (outView.size() > 0) {
                outView.get(0).setVisibility(View.GONE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {


                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);

            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
            }

        });

        webView.loadUrl(url);

    }

    /**
     * 屏幕适配
     */
    private void DisplaySetting(WebSettings webSettings) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        if (mDensity == 120) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == 160) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 240) {
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
    }

    /**
     * WebView设置
     */
    private void webSetting(WebSettings webSettings) {
        // 保存表单数据
        webSettings.setSaveFormData(true);
        //关闭webview中缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);
        webView.requestFocus(); //此句可使html表单可以接收键盘输入
        webView.setFocusable(true);
        webSettings.setSavePassword(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        // 启动应用缓存
        webSettings.setAppCacheEnabled(false);
        //支持缩放，默认为true。是下面那个的前提。
        webSettings.setSupportZoom(true);
        //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // WebView是否支持多个窗口。
        webSettings.setSupportMultipleWindows(true);
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //其他细节操作

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //支持通过JS打开新窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //JS在HTML里面设置了本地存储localStorage，java中使用localStorage
        webSettings.setDomStorageEnabled(true);
        // 则必须打开

        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
        //webView.setLayerType();
        webView.setDrawingCacheEnabled(true);
    }


    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}