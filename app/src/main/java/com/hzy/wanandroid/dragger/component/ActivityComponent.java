package com.hzy.wanandroid.dragger.component;

import android.app.Activity;


import com.hzy.wanandroid.dragger.ActivityScope;
import com.hzy.wanandroid.dragger.module.ActivityModule;
import com.hzy.wanandroid.ui.addtodo.AddToDoActivity;
import com.hzy.wanandroid.ui.article_search.ArticleSearchActivity;
import com.hzy.wanandroid.ui.freq_web.FreqWebActivity;
import com.hzy.wanandroid.ui.login.LoginActivity;
import com.hzy.wanandroid.ui.mycollect.MyCollectActivity;
import com.hzy.wanandroid.ui.register.RegisterActivity;
import com.hzy.wanandroid.ui.search.SearchActivity;

import dagger.Component;

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
