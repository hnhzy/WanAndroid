package com.hzy.wanandroid.ui.activity.pubsearch;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.PubAddrListBean;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/2/25
 *
 * @author Administrator
 * */
public interface SearchContract {

    interface View extends BaseView {
        void updateView(PubAddrListBean mPubAddrListBean);
        void updateCollect(ResponseBean responseBean, int position);
        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter extends BasePersenter<View> {
        void getData(int id, int page, String k);
        void collectArticle(String title, String author, String link, int position);
        void unCollectArticle(int id, String title, String author, String link, int position);
    }

}
