package com.hzy.wanandroid.ui.fragment.system.systemlist;


import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.KnowledgeSystem;

/**
 * Created by hzy on 2019/2/22
 *
 * @author Administrator
 * */
public interface SubSysContract {

    interface View extends BaseView {
        void updateView(KnowledgeSystem mKnowledgeSystem);
    }

    interface Presenter extends BasePersenter<View> {
        void getData(int cid, int page);
    }

}
