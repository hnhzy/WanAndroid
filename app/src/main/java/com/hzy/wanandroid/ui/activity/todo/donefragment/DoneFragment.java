package com.hzy.wanandroid.ui.activity.todo.donefragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.DoneAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.ToDoBean;
import com.hzy.wanandroid.bean.ToDoPageBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/5
 * 已完成清单
 *
 * @author hzy
 */
public class DoneFragment extends BaseMvpFragment<DonePresenter> implements DoneContract.View {

    public static final String TAG = "DoneFragment";
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    DoneAdapter mAdapter;
    private int status = 1;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page = 1;
    int pageCount = 0;
    /**
     * 全部的数据
     */
    private List<ToDoBean> list = new ArrayList<>();

    /**
     * status = 0的list
     */
    List<ToDoBean> mList = new ArrayList<>();

    /**
     * 根据时间嵌套的list
     */
    List<List<ToDoBean>> mListToDo = new ArrayList<>();

    public static DoneFragment newInstance() {
        return new DoneFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new DoneAdapter(getActivity(), mListToDo, mPresenter);
        mRvList.setAdapter(mAdapter);
        //禁用滑动事件
        mRvList.setNestedScrollingEnabled(false);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                    mPresenter.getList(page);
                } else {
                    ToastUtils.showShort("已经没有数据了");
                }

                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getList(page);
                refreshLayout.finishRefresh();
            }
        });
        page = 1;
        mPresenter.getList(page);
        EventBus.getDefault().register(this);
    }

    @Override
    public void updateList(ToDoPageBean bean) {
        if (page == 1) {
            list.clear();
            mList.clear();
            mListToDo.clear();
        }
        list.addAll(bean.getDatas());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStatus() == status) {
                mList.add(list.get(i));
            }

        }
        if (mList == null) {
            return;
        }

        /**
         * 1、使用set去重
         * Hashset 它不保证集合的迭代顺序；特别是它不保证该顺序恒久不变。
         * LinkedHashSet定义了迭代顺序，即按照将元素插入到集合中的顺序（插入顺序）进行迭代。
         */
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (int i = 0; i < mList.size(); i++) {
            set.add(mList.get(i).getDateStr());
        }

        /**
         * 2、遍历set
         */
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            List<ToDoBean> dateList = new ArrayList<>();
            String date = it.next();
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getDateStr().equals(date)) {
                    dateList.add(mList.get(i));
                }
            }
            mListToDo.add(dateList);
        }

        pageCount = bean.getPageCount();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void done(int position, int subPos, int status, ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            mListToDo.get(position).get(subPos).setWhere("DoneFragment");
            EventBus.getDefault().post(mListToDo.get(position).get(subPos));
            mListToDo.get(position).remove(subPos);
            mAdapter.notifyDataSetChanged();
            ToastUtils.showShort("更新成功");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToDoBean bean) {
        Log.d(TAG, "onMessageEvent" + bean.getStatus());
        if (bean.getWhere().equals("AddToDoActivity")) {
            if (bean.getStatus() == 1) {

                onGetData(bean);
            }
        } else if (bean.getWhere().equals("ToDoFragment")) {
            bean.setStatus(1);
            onGetData(bean);
        }
    }

    private void onGetData(ToDoBean bean) {
        String date = bean.getDateStr();
        boolean isContain = false;
        for (int i = 0; i < mListToDo.size(); i++) {
            if (mListToDo.get(i).get(0).getDateStr().equals(date)) {
                mListToDo.get(i).add(0, bean);
                isContain = true;
            }
        }
        if (!isContain) {
            List<ToDoBean> doBeanList = new ArrayList<>();
            doBeanList.add(bean);
            mListToDo.add(doBeanList);
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void delete(int position, int subPos, ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("删除成功");
            mListToDo.get(position).remove(subPos);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
