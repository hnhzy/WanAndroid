package com.hzy.wanandroid.dragger.module;

import android.app.Activity;


import com.hzy.wanandroid.dragger.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author Administrator
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
