package com.hzy.wanandroid.fragment.PubList;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/2/18
 **/
public class PubListPresenter extends BasePAV<PubListContract.View> implements PubListContract.Presenter {


    @Inject
    public PubListPresenter() {

    }


    @Override
    public void getList(int id, int page) {
        App.apiService(ApiService.class)
                .getPublicAddr(id, page)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateList(responseBean.getData());
                }, throwable -> {
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
