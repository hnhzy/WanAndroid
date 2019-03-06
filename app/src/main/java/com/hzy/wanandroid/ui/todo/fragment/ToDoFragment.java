package com.hzy.wanandroid.ui.todo.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.ToDoAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.ToDoBean;
import com.hzy.wanandroid.bean.ToDoPageBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/5
 **/
public class ToDoFragment extends BaseMvpFragment<ToDoPresenter> implements ToDoContract.View {

    public static final String TAG = "ToDoFragment";
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    List<ToDoBean> mList = new ArrayList<>();
    ToDoAdapter mAdapter;
    private int status;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page = 1;
    int pageCount = 0;

    public static ToDoFragment newInstance(int status) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, status);
        ToDoFragment fragment = new ToDoFragment();
        fragment.setArguments(bundle);
        return fragment;
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
        mAdapter = new ToDoAdapter(getActivity(), mList, mPresenter);
        mRvList.setAdapter(mAdapter);
        //禁用滑动事件
        mRvList.setNestedScrollingEnabled(false);
        status = getArguments().getInt(TAG);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                }
                mPresenter.getList(page);
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
    }

    @Override
    public void updateList(ToDoPageBean bean) {
        List<ToDoBean> list = new ArrayList<>();
        list.addAll(bean.getDatas());
        if (page == 1) {
            mList.clear();
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getStatus() == status) {
                mList.add(list.get(i));
            }

        }
        page = bean.getCurPage();
        pageCount = bean.getPageCount();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void done(ResponseBean responseBean) {
        Log.e(TAG,"done"+responseBean.toString());
    }

    @Override
    public void delete(ResponseBean responseBean) {
        Log.e(TAG,"delete"+responseBean.toString());
    }
}
