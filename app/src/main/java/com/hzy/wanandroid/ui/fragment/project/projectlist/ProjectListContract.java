package com.hzy.wanandroid.ui.fragment.project.projectlist;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ProjectListBean;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/1/25
 *
 * @author Administrator
 *
 * */
public interface ProjectListContract {

    interface View extends BaseView {
        void updateProject(ProjectListBean projectListBean);
        void updateCollect(ResponseBean responseBean, int position);
        void updateUnCollect(ResponseBean responseBean, int position);
    }

    interface Presenter extends BasePersenter<View> {
        void getProjectList(int page, int cid);
        void collectArticle(String title, String author, String link, int position);
        void unCollectArticle(int id, String title, String author, String link, int position);
    }
}
