package com.hzy.wanandroid.dragger.module;



import com.hzy.wanandroid.App;
import com.hzy.wanandroid.dragger.ContextLife;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    App provideApplicationContext() {
        return application;
    }

}
