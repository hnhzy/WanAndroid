package com.hzy.wanandroid.fragment.public_address;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.PublicAddrBean;

import java.util.List;

/**
 * Created by hzy on 2019/1/29
 **/
public interface PublicAddrContract {

    interface View extends BaseView {
        void updateView(List<PublicAddrBean> list);
    }

    interface Presenter extends BasePersenter<View> {
        void getData();
    }

}
