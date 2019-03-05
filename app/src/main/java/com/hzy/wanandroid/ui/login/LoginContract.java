package com.hzy.wanandroid.ui.login;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/1/18
 * LoginContract
 **/
public interface LoginContract {


    interface View extends BaseView {
        void updateView(ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View> {
        void postLogin(String username, String password);
    }

}
