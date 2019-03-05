package com.hzy.wanandroid.fragment.system;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/28
 **/
public class SystemPresenter extends BasePAV<SystemContract.View> implements SystemContract.Presenter {

    @Inject
    public  SystemPresenter(){

    }


    @Override
    public void getSystemData() {
        App.apiService(ApiService.class)
                .getSystemData()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(response -> {
                    mView.updateView(response.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }
}
