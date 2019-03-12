package com.hzy.wanandroid.ui.activity.collect.addcollect;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

import retrofit2.http.Field;

/**
 * Created by hzy on 2019/3/12
 *
 * @author Administrator
 *
 * */
public class AddCollectPresenter extends BasePAV<AddCollectContract.View> implements AddCollectContract.Presenter{


    @Inject
    public AddCollectPresenter(){}

    @Override
    public void addCollect(String title,String author,String link) {
        App.apiService(ApiService.class)
                .outsideCollect(title,author,link)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.updateView(responseBean);
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }


}
