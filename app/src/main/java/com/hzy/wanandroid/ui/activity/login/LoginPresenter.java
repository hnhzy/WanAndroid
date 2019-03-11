package com.hzy.wanandroid.ui.activity.login;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/18
 * LoginPresenter
 *
 * @author hzy
 *
 */
public class LoginPresenter extends BasePAV<LoginContract.View> implements LoginContract.Presenter {

    @Inject
    LoginPresenter() {

    }

    @Override
    public void postLogin(String username, String password) {
        mView.showLoading();
        App.apiService(ApiService.class)
                .postLogin(username, password)
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
