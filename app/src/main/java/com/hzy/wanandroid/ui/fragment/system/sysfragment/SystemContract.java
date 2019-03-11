package com.hzy.wanandroid.ui.fragment.system.sysfragment;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.SystemDataBean;

import java.util.List;

/**
 * Created by hzy on 2019/1/28
 *
 * @author Administrator
 * */
public interface SystemContract {

    interface View extends BaseView {
        void updateView(List<SystemDataBean> systemList);
    }

    interface Presenter extends BasePersenter<View> {
        void getSystemData();
    }
}
