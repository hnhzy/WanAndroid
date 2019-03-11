package com.hzy.wanandroid.ui.activity.mycollect;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/5
 *
 * @author Administrator
 * */
public interface MyCollectContract {

    interface View extends BaseView {

        void updateView(ArticleBean bean);
        void updateUnCollect(ResponseBean responseBean, int position);

    }

    interface Presenter extends BasePersenter<View> {

        void getData(int page);
        void unCollectArticle(int id, String originId, int position);

    }
}
