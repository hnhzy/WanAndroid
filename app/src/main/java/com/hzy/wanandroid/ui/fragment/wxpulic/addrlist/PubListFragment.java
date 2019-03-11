package com.hzy.wanandroid.ui.fragment.wxpulic.addrlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.PubAddrAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.PubAddrListBean;
import com.hzy.wanandroid.bean.PubAddrListChild;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.activity.X5WebView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/2/18
 * 公众号文章列表
 *
 * @author hzy
 *
 * */
public class PubListFragment extends BaseMvpFragment<PubListPresenter> implements PubListContract.View {

    @BindView(R.id.rv_list)
    RecyclerView mRvList;

    List<PubAddrListChild> mList = new ArrayList<>();
    PubAddrAdapter mAdapter;

    private static final String TAG = "PubListFragment";
    private int id;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page = 1;
    int pageCount = 0;

    public static PubListFragment newInstance(int id) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, id);
        PubListFragment fragment = new PubListFragment();
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
        mAdapter = new PubAddrAdapter(getActivity(), mList, mPresenter);
        mRvList.setAdapter(mAdapter);
        //禁用滑动事件
        mRvList.setNestedScrollingEnabled(false);
        id = getArguments().getInt(TAG);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), X5WebView.class);
                intent.putExtra("mUrl", mList.get(position).getLink());
                intent.putExtra("mTitle", mList.get(position).getTitle());
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
                }
                mPresenter.getList(id, page);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getList(id, page);
                refreshLayout.finishRefresh();
            }
        });
        page = 1;
        mPresenter.getList(id, page);
    }

    @Override
    public void updateList(PubAddrListBean bean) {
        page = bean.getCurPage();
        pageCount = bean.getPageCount();
        if (page == 1) {
            mList.clear();
        }
        mList.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        mList.get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        mList.get(position).setCollect(false);
        mAdapter.notifyItemChanged(position);
    }
}
