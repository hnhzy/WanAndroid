package com.hzy.wanandroid.fragment.project_list;

import android.arch.lifecycle.LifecycleOwner;
import android.util.Log;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/25
 **/
public class ProjectListPresenter extends BasePAV<ProjectListContract.View> implements ProjectListContract.Presenter {

    private static final String TAG ="ProjectListPresenter";

    @Inject
    public ProjectListPresenter(){

    }

    @Override
    public void getProjectList(int page,int cid) {
        App.apiService(ApiService.class)
                .getProjectList(page,cid)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    Log.d(TAG, "ArticleList--" + responseBean.toString());
                    mView.updateProject(responseBean.getData());
                }, throwable -> {
                    Log.d(TAG, "ArticleList--" + throwable.toString());
                    mView.onFail();
                });
    }

    @Override
    public void collectArticle(String title, String author, String link, int position) {
        App.apiService(ApiService.class)
                .outsideCollect(title, author, link)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateCollect(responseBean, position);
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void unCollectArticle(int id, String title, String author, String link, int position) {
        App.apiService(ApiService.class)
                .articleListUncollect(id, title, author, link)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateUnCollect(responseBean, position);
                }, throwable -> {
                    mView.onFail();
                });

    }
}
