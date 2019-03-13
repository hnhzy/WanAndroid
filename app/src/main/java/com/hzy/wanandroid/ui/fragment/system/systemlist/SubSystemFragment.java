package com.hzy.wanandroid.ui.fragment.system.systemlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.SubSysAdapter;
import com.hzy.wanandroid.adapter.SubSysListAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.KnowledgeSystem;
import com.hzy.wanandroid.bean.KnowledgeSystemChildBean;
import com.hzy.wanandroid.bean.SystemDataChildBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.activity.X5WebView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/2/22
 *
 * @author hzy
 */
    public class SubSystemFragment extends BaseMvpFragment<SubSysPresenter> implements SubSysContract.View {

    private static final String TAG = "SubSystemFragment";

    private static SubSystemFragment instance = null;


    @BindView(R.id.rv_grid)
    RecyclerView mRvGrid;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    SubSysAdapter mGridAdapter;
    SubSysListAdapter mListAdapter;
    List<SystemDataChildBean> mGridlist = new ArrayList<>();
    List<SystemDataChildBean> mGridlist2 = new ArrayList<>();
    List<KnowledgeSystemChildBean> mListlist = new ArrayList<>();

    int page = 0;
    int cid = 0;
    int pageCount = 0;

    public static SubSystemFragment newInstance(List<SystemDataChildBean> list) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, (Serializable) list);
        SubSystemFragment fragment = new SubSystemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_system;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        mGridlist = (List<SystemDataChildBean>) getArguments().getSerializable(TAG);
        for (SystemDataChildBean bean : mGridlist) {
            bean.setChecked(false);
        }
        mGridlist.get(0).setChecked(true);
        if (mGridlist.size() > 5) {
            SystemDataChildBean sys = new SystemDataChildBean("查看更多", false);
            mGridlist.add(5, sys);
            mGridlist2.clear();
            mGridlist2.addAll(mGridlist.subList(0, 6));
            mGridAdapter = new SubSysAdapter(getActivity(), mGridlist2);
        } else {
            mGridlist2.clear();
            mGridlist2.addAll(mGridlist);
            mGridAdapter = new SubSysAdapter(getActivity(), mGridlist2);
        }
        mRvGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        mRvGrid.setAdapter(mGridAdapter);
        mGridAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == 5 && mGridlist2.get(position).getName().equals("查看更多")) {
                    mGridlist2.clear();
                    mGridlist.remove(5);
                    SystemDataChildBean sys = new SystemDataChildBean("收回列表", false);
                    mGridlist.add(sys);
                    mGridlist2.addAll(mGridlist);
                    mGridAdapter.notifyDataSetChanged();
                } else if (position == mGridlist2.size() - 1 && mGridlist2.get(position).getName().equals(
                        "收回列表")) {
                    mGridlist2.clear();
                    SystemDataChildBean sys = new SystemDataChildBean("查看更多", false);
                    mGridlist.remove(mGridlist.size() - 1);
                    mGridlist.add(5, sys);
                    mGridlist2.addAll(mGridlist.subList(0, 6));
                    mGridAdapter.notifyDataSetChanged();
                } else {
                    for (SystemDataChildBean bean : mGridlist2) {
                        bean.setChecked(false);
                    }
                    mGridlist2.get(position).setChecked(true);
                    mGridAdapter.notifyDataSetChanged();

                    page = 0;
                    cid = mGridlist.get(position).getId();
                    mPresenter.getData(cid, page);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });

        mListAdapter = new SubSysListAdapter(getActivity(), mListlist, mPresenter);
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvList.setAdapter(mListAdapter);
        //禁用滑动事件
        mRvList.setNestedScrollingEnabled(false);
        mListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), X5WebView.class);
                intent.putExtra("mUrl", mListlist.get(position).getLink());
                intent.putExtra("mTitle", mListlist.get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });

        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                    mPresenter.getData(cid, page);
                } else {
                    ToastUtils.showShort("已经没有数据了");
                }
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPresenter.getData(cid, page);
                refreshLayout.finishRefresh();
            }
        });
        cid = mGridlist.get(0).getId();
        mPresenter.getData(cid, page);
    }

    @Override
    public void updateView(KnowledgeSystem mKnowledgeSystem) {
        pageCount = mKnowledgeSystem.getPageCount();
        if (page == 0) {mListlist.clear();}
        mListlist.addAll(mKnowledgeSystem.getDatas());
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        mListlist.get(position).setCollect(true);
        mListAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        mListlist.get(position).setCollect(false);
        mListAdapter.notifyItemChanged(position);
    }


}
