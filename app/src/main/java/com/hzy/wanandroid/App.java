package com.hzy.wanandroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.hzy.wanandroid.dragger.component.AppComponent;
import com.hzy.wanandroid.dragger.component.DaggerAppComponent;
import com.hzy.wanandroid.dragger.module.AppModule;
import com.hzy.wanandroid.http.DevelopmentModeManager;
import com.hzy.wanandroid.http.HttpManager;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by hzy on 2019/1/14
 *
 * @author hzy
 */
public class App extends MultiDexApplication {

    private static App instance;
    public static Context AppContext;
    private HttpManager mHttpManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext = getApplicationContext();
        //初始化网络
        mHttpManager = new HttpManager();
        LoadWebX5();

        SharedPreferencesUtil.getInstance(this, "WanAndroid");

        //判断是否是调试模式，如果是则开启Stetho、LeakCanary
        if (BuildConfig.DEBUG) {
            DevelopmentModeManager.initStetho(this);
            DevelopmentModeManager.initLeakCanary(this);
        }
    }


    /**
     * 关联apiService
     *
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T apiService(Class<T> clz) {
        return getInstance().mHttpManager.getService(clz);
    }

    public static synchronized App getInstance() {
        return instance;
    }


    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();
    }

    private void LoadWebX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {

            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getInstance(), cb);
    }
}
