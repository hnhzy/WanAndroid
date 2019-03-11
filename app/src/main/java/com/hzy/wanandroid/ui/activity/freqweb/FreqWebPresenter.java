package com.hzy.wanandroid.ui.activity.freqweb;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/3/4
 *
 * @author Administrator
 *
 * */
public class FreqWebPresenter extends BasePAV<FreqWebContract.View> implements FreqWebContract.Presenter {

    @Inject
    public FreqWebPresenter() {

    }

    @Override
    public void getData() {
        App.apiService(ApiService.class)
                .getFreUsedWeb()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateView(responseBean.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }
}
