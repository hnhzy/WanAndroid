package com.hzy.wanandroid.ui.mycollect;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/5
 **/
public interface MyCollectContract {

    interface View extends BaseView{
        void updateView(ArticleBean bean);
        void updateCollect(ResponseBean responseBean, int position);
        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter extends BasePersenter<View>{
        void getData(int page);
        void collectArticle(String title, String author, String link, int position);
        void unCollectArticle(int id, String title, String author, String link, int position);
    }
}
