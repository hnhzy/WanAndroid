package com.hzy.wanandroid.fragment.navi;

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
public class NaviPresenter extends BasePAV<NaviContract.View> implements NaviContract.Presenter {

    @Inject
    public NaviPresenter() {

    }

    @Override
    public void getData() {
        App.apiService(ApiService.class)
                .getNaviData()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateView(responseBean.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }
}
