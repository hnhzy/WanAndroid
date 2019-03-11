package com.hzy.wanandroid.dragger.component;

import android.app.Activity;


import com.hzy.wanandroid.dragger.ActivityScope;
import com.hzy.wanandroid.dragger.module.ActivityModule;
import com.hzy.wanandroid.ui.activity.artsearch.ArticleSearchActivity;
import com.hzy.wanandroid.ui.activity.freqweb.FreqWebActivity;
import com.hzy.wanandroid.ui.activity.login.LoginActivity;
import com.hzy.wanandroid.ui.activity.mycollect.MyCollectActivity;
import com.hzy.wanandroid.ui.activity.pubsearch.SearchActivity;
import com.hzy.wanandroid.ui.activity.register.RegisterActivity;
import com.hzy.wanandroid.ui.activity.todo.add.AddToDoActivity;

import dagger.Component;

/**
 * @author Administrator
 */
@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(SearchActivity searchActivity);

    void inject(ArticleSearchActivity articleSearchActivity);

    void inject(FreqWebActivity freqWebActivity);

    void inject(MyCollectActivity myCollectActivity);

    void inject(AddToDoActivity addToDoActivity);
}
