package com.hzy.wanandroid.dragger.component;


import com.hzy.wanandroid.App;
import com.hzy.wanandroid.dragger.ContextLife;
import com.hzy.wanandroid.dragger.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Administrator
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    /**
     * 提供App的Context
     * @return
     */
    @ContextLife("Application")
    App getContext();

}
