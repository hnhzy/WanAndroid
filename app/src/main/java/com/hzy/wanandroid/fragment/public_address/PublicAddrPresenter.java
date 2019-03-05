package com.hzy.wanandroid.fragment.public_address;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/29
 **/
public class PublicAddrPresenter extends BasePAV<PublicAddrContract.View> implements PublicAddrContract.Presenter {

    @Inject
    public PublicAddrPresenter(){

    }

    @Override
    public void getData() {
        App.apiService(ApiService.class)
                .getPublicAddrList()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateView(responseBean.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }

//    @Override
//    public void getList(int id, int page) {
//        App.apiService(ApiService.class)
//                .getPublicAddr(id,page)
//                .compose(RxSchedulers.io_main())
//                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
//                .subscribe(responseBean -> {
//                    mView.updateList(responseBean.getData());
//                }, throwable -> {
//                    mView.onFail();
//                });
//    }
}
