package com.hzy.wanandroid.ui.todo.fragment;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/3/5
 **/
public class ToDoPresenter extends BasePAV<ToDoContract.View> implements ToDoContract.Presenter {

    @Inject
    public ToDoPresenter() {
    }

    @Override
    public void getList(int page) {
        App.apiService(ApiService.class)
                .toDoList(page)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.updateList(responseBean.getData());
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void delete(int id) {
        App.apiService(ApiService.class)
                .toDoDelete(id)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.delete(responseBean);
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void done(int id, int status) {
        App.apiService(ApiService.class)
                .toDoDone(id, status)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.done(responseBean);
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }
}
