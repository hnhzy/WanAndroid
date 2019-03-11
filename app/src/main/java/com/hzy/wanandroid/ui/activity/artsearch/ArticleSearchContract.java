package com.hzy.wanandroid.ui.activity.artsearch;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.bean.HotSearchBean;
import com.hzy.wanandroid.http.ResponseBean;

import java.util.List;

/**
 * Created by hzy on 2019/2/26
 *
 * @author Administrator
 * */
public interface ArticleSearchContract {

    interface View extends BaseView {
        void updateView(ArticleBean mArticleBean);
        void updateGridView(List<HotSearchBean> mGrid);
        void updateCollect(ResponseBean responseBean, int position);
        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter extends BasePersenter<View> {
        void getData(int page, String k);
        void getGridData();
        void collectArticle(String title, String author, String link, int position);
        void unCollectArticle(int id, String title, String author, String link, int position);
    }

}
