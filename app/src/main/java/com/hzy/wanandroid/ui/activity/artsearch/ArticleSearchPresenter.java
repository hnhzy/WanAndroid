package com.hzy.wanandroid.ui.activity.artsearch;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/2/26
 *
 * @author Administrator
 * */
public class ArticleSearchPresenter extends BasePAV<ArticleSearchContract.View> implements ArticleSearchContract.Presenter {

    @Inject
    public ArticleSearchPresenter() {

    }


    @Override
    public void getData(int page, String k) {
        App.apiService(ApiService.class)
                .query(page, k)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.updateView(responseBean.getData());
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void getGridData() {
        App.apiService(ApiService.class)
                .getHotSearch()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.updateGridView(responseBean.getData());
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }


    @Override
    public void collectArticle(int id,int position) {
        App.apiService(ApiService.class)
                .insideCollect(id)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateCollect(responseBean, position);
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void unCollectArticle(int id, int position) {
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
