package com.hzy.wanandroid.ui.freq_web;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.FreUsedWebBean;

import java.util.List;

/**
 * Created by hzy on 2019/3/4
 **/
public interface FreqWebContract {

    interface View extends BaseView{
        void updateView(List<FreUsedWebBean>list);
    }

    interface Presenter extends BasePersenter<View>{
        void getData();
    }

}
