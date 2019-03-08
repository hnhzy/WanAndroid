package com.hzy.wanandroid.ui.todo.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.blankj.utilcode.util.ToastUtils;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/5
 * 待办清单
 *
 * @author hzy
 *
 * */
public class ToDoFragment extends BaseMvpFragment<ToDoPresenter> implements ToDoContract.View {

    public static final String TAG = "ToDoFragment";
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    List<ToDoBean> mList = new ArrayList<>();
    ToDoAdapter mAdapter;
    private int status = 0;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page = 1;
    int pageCount = 0;
    /**
     * 全部的数据
     */
    private List<ToDoBean> list = new ArrayList<>();

    public static ToDoFragment newInstance() {
        return new ToDoFragment();
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
        for (int i = 0; i < mList.size(); i++) {
            if (TextUtils.isEmpty(mList.get(i).getDateStr())) {
                continue;
            }
            String date = mList.get(i).getDateStr();
            /**
             * //判断date是否已经存在，默认不存在
             */
            Boolean isContain = false;
            for (int j = 0; j < i; j++) {
                if (TextUtils.isEmpty(mList.get(i).getDateStr())) {
                    continue;
                }
                if (mList.get(j).getDateStr().contains(date)) {
                    isContain = true;
                } else {
                    isContain = false;
                }
            }
            if (isContain) {
                /**
                 * //date存在设置为top布局隐藏
                 */
                mList.get(i).setTopVisible(false);
            } else {
                mList.get(i).setTopVisible(true);
            }
            mList.get(i).setBottomVisible(true);
            mList.get(i).setArrowUp(true);
        }
        pageCount = bean.getPageCount();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void done(int position, int status, ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            EventBus.getDefault().post(mList.get(position));
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
            ToastUtils.showShort("更新成功");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToDoBean bean) {
        if (bean.getStatus() == 1) {
            bean.setStatus(0);
            mList.add(bean);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void delete(int position, ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("删除成功");
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
