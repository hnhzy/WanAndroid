package com.hzy.wanandroid.fragment.subsystem;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/2/22
 **/
public class SubSysPresenter extends BasePAV<SubSysContract.View> implements SubSysContract.Presenter {

    @Inject
    public SubSysPresenter() {

    }

    @Override
    public void getData(int cid,int page) {
        App.apiService(ApiService.class)
                .getsubSystem(page,cid)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateView(responseBean.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }


}
