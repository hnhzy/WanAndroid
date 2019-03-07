package com.hzy.wanandroid.dragger.component;


import com.hzy.wanandroid.App;
import com.hzy.wanandroid.dragger.ContextLife;
import com.hzy.wanandroid.dragger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    @ContextLife("Application")
    App getContext();  // 提供App的Context

}
