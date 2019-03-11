package com.hzy.wanandroid.http;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by hzy on 2019/1/21
 * 如果是正式模式则关闭，测试模式则开启
 *
 * @author Administrator
 * */
public class DevelopmentModeManager {

    public static void initStetho(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    public static void initLeakCanary(Application app) {
        if (LeakCanary.isInAnalyzerProcess(app)) {
            //此过程专用于LeakCanary进行堆分析。在此过程中不应初始化应用程序。
            return;
        }
        LeakCanary.install(app);
    }

}
