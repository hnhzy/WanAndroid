package com.hzy.wanandroid.ui.activity.collect.addcollect;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/12
 *
 * @author hzy
 */
public interface AddCollectContract {

    interface View extends BaseView {
        void updateView(ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View> {
        void addCollect(String title, String author, String link);
    }

}
