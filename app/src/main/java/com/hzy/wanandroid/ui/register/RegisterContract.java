package com.hzy.wanandroid.ui.register;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/2/23
 **/
public interface RegisterContract {

    interface View extends BaseView {
        void updateView(ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View> {
        void postRegister(String username, String password, String password2);
    }

}
