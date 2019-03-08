package com.hzy.wanandroid.fragment.home;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.bean.HomeBanner;
import com.hzy.wanandroid.http.ResponseBean;

import java.util.List;

/**
 * Created by hzy on 2019/1/22
 *
 * @author hzy
 *
 * */
public interface HomeContract {

    interface View extends BaseView {
        void updateBanner(List<HomeBanner> homeBannerList);
        void updateArticle(ArticleBean articleBean);
        void updateCollect(ResponseBean responseBean, int position);
        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter extends BasePersenter<View> {
        void getBannerList();
        void getArticleList(int page);
        void collectArticle(String title, String author, String link, int position);
        void unCollectArticle(int id, String title, String author, String link, int position);
    }
}
