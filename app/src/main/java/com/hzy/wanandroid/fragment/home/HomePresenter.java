package com.hzy.wanandroid.fragment.home;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/22
 **/
public class HomePresenter extends BasePAV<HomeContract.View> implements HomeContract.Presenter {

    public static final String TAG = "HomePresenter";

    @Inject
    public HomePresenter() {

    }

    @Override
    public void getBannerList() {
        App.apiService(ApiService.class)
                .getBannerList()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    Log.d(TAG, "BannerList--" + responseBean.toString());
                    mView.updateBanner(responseBean.getData());
                }, throwable -> {
                    Log.d(TAG, "Throwable--" + throwable.toString());
                    mView.onFail();
                });


    }

    @Override
    public void getArticleList(int page) {
        App.apiService(ApiService.class)
                .getArticle(page)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    Log.d(TAG, "ArticleList--" + responseBean.toString());
                    mView.updateArticle(responseBean.getData());
                }, throwable -> {
                    Log.d(TAG, "ArticleList--" + throwable.toString());
                    mView.onFail();
                });
    }

    @Override
    public void collectArticle(String title, String author, String link, int position) {
        App.apiService(ApiService.class)
                .outsideCollect(title, author, link)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateCollect(responseBean, position);
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void unCollectArticle(int id, String title, String author, String link, int position) {
        App.apiService(ApiService.class)
                .articleListUncollect(id)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateUnCollect(responseBean, position);
                }, throwable -> {
                    mView.onFail();
                });

    }

}
