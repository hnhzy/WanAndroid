package com.hzy.wanandroid.base.mvp;

/**
 * Created by hzy on 2019/1/23
 * BasePersenter
 **/
public interface BasePersenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
