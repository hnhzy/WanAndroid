package com.hzy.wanandroid.ui.activity.todo.donefragment;

import com.hzy.wanandroid.base.mvp.BasePersenter;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.bean.ToDoPageBean;
import com.hzy.wanandroid.http.ResponseBean;

/**
 * Created by hzy on 2019/3/5
 *
 * @author hzy
 *
 * */
public interface DoneContract {

    interface View extends BaseView{

        void updateList(ToDoPageBean responseBean);

        void delete(int position, int subPos, ResponseBean responseBean);

        void done(int position, int subPos, int status, ResponseBean responseBean);
    }

    interface Presenter extends BasePersenter<View>{

        void getList(int page);

        void delete(int position, int subPos, int id);

        void done(int position, int subPos, int id, int status);

    }
}
