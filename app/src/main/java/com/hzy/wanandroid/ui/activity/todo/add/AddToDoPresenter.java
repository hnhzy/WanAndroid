package com.hzy.wanandroid.ui.activity.todo.add;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/3/7
 *
 * @author Administrator
 *
 * */
public class AddToDoPresenter extends BasePAV<AddToDoContract.View> implements AddToDoContract.Presenter {

    @Inject
    public AddToDoPresenter(){}

    @Override
    public void addToDo(String title,String content,String date,int type) {
        App.apiService(ApiService.class)
                .toDoAdd(title, content,date,type)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    if (responseBean != null) {
                        mView.addView(responseBean);
                    }
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void updateToDo(int id,String title,String content,String date,int status,int type) {
        App.apiService(ApiService.class)
                .toDoUpdate(id,title, content,date,status,type)
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
