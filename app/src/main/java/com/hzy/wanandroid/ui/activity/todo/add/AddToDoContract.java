package com.hzy.wanandroid.ui.activity.todo.add;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/7
 *
 * @author Administrator
 *
 * */
public interface AddToDoContract {

    interface View extends BaseView{
        void addView(ResponseBean responseBean);
        void updateView(ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View>{
        void addToDo(String title, String content, String date, int type);
        void updateToDo(int id, String title, String content, String date, int status, int type);
    }


}
