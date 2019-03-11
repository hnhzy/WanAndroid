package com.hzy.wanandroid.ui.fragment.project.profragment;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/22
 *
 * @author hzy
 *
 * */
public class ProjectPresenter extends BasePAV<ProjectContract.View> implements ProjectContract.Presenter {


    @Inject
    public ProjectPresenter() {

    }

    @Override
    public void getProjectList() {
        App.apiService(ApiService.class)
                .getProjectSubject()
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateProject(responseBean.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }
}
