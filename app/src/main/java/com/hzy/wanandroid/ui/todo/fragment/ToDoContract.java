package com.hzy.wanandroid.ui.todo.fragment;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ToDoPageBean;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/5
 **/
public interface ToDoContract {

    interface View extends BaseView{
        void updateList(ToDoPageBean responseBean);
        void done(ResponseBean responseBean);
        void delete(ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View>{
        void getList(int page);
        void delete(int id);
        void done(int id,int status);
    }
}
