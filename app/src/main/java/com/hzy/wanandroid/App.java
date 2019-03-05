package com.hzy.wanandroid;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.hzy.wanandroid.dragger.component.AppComponent;
import com.hzy.wanandroid.dragger.component.DaggerAppComponent;
import com.hzy.wanandroid.dragger.module.AppModule;
import com.hzy.wanandroid.http.HttpManager;
import com.hzy.wanandroid.utils.SettingUtil;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.tencent.smtt.sdk.QbSdk;

import java.util.Calendar;

/**
 * Created by hzy on 2019/1/14
 **/
public class App extends MultiDexApplication {

    private static App instance;
    public static Context AppContext;
    private HttpManager mHttpManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext = getApplicationContext();
        mHttpManager = new HttpManager();//初始化网络
        initTheme();
        LoadWebX5();

        SharedPreferencesUtil.getInstance(this,"WanAndroid");

        if (BuildConfig.DEBUG) {//判断是否是调试模式，如果是则开启Stetho、LeakCanary
//            DevelopmentModeManager.initStetho(this);
//            DevelopmentModeManager.initLeakCanary(this);
        }
    }

    private void initTheme() {
        SettingUtil settingUtil = SettingUtil.getInstance();

        // 获取是否开启 "自动切换夜间模式"
        if (settingUtil.getIsAutoNightMode()) {

            int nightStartHour = Integer.parseInt(settingUtil.getNightStartHour());
            int nightStartMinute = Integer.parseInt(settingUtil.getNightStartMinute());
            int dayStartHour = Integer.parseInt(settingUtil.getDayStartHour());
            int dayStartMinute = Integer.parseInt(settingUtil.getDayStartMinute());

            Calendar calendar = Calendar.getInstance();
            int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            int currentMinute = calendar.get(Calendar.MINUTE);

            int nightValue = nightStartHour * 60 + nightStartMinute;
            int dayValue = dayStartHour * 60 + dayStartMinute;
            int currentValue = currentHour * 60 + currentMinute;

            if (currentValue >= nightValue || currentValue <= dayValue) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                settingUtil.setIsNightMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                settingUtil.setIsNightMode(false);
            }

        } else {
            // 获取当前主题
            if (settingUtil.getIsNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    //关联apiService
    public static <T> T apiService(Class<T> clz) {
        return getInstance().mHttpManager.getService(clz);
    }


    // synchronized 修饰静态方法，表示调用前要获得类的锁；非静态方法上表示要获得对象的锁
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
        QbSdk.initX5Environment(getApplicationContext(),  cb);
    }
}
