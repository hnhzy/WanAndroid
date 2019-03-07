package com.hzy.wanandroid.ui.addtodo;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/7
 **/
public interface AddToDoContract {

    interface View extends BaseView{
        void updateView(ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View>{
        void addToDo(String title,String content,String date,int type);
    }


}
