package com.hzy.wanandroid.ui.activity.register;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/2/23
 *
 * @author Administrator
 */
public class RegisterPresenter extends BasePAV<RegisterContract.View> implements RegisterContract.Presenter {


    @Inject
    public RegisterPresenter() {

    }

    @Override
    public void postRegister(String username, String password, String password2) {
        mView.showLoading();
        App.apiService(ApiService.class)
                .postRegister(username, password, password2)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.closeLoading();
                    if (responseBean != null) {
                        mView.updateView(responseBean);
                    }
                }, throwable -> {
                    mView.closeLoading();
                    mView.onFail();
                });
    }

}
