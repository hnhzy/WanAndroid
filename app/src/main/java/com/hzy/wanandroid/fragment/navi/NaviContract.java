package com.hzy.wanandroid.fragment.navi;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.NaviBean;

import java.util.List;

/**
 * Created by hzy on 2019/1/28
 **/
public interface NaviContract {

    interface View extends BaseView {
        void updateView(List<NaviBean> naviList);
    }

    interface Presenter extends BasePersenter<View> {
        void getData();
    }

}
