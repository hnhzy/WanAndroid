package com.hzy.wanandroid.ui.mycollect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.CollectAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.bean.ArticleListBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.X5WebView;
import com.hzy.wanandroid.widget.TitleBarLayout;
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
 * Created by hzy on 2019/3/5
 * 我的收藏
 **/
public class MyCollectActivity extends BaseMvpActivity<MyCollectPresenter> implements MyCollectContract.View {


    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.rv_list)
    RecyclerView mRvCollet;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    int page = 0;
    int pageCount = 0;
    CollectAdapter mAdapter;
    List<ArticleListBean> mColletList = new ArrayList<>();
    boolean isOver;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle("我的收藏");
        mTitleBar.setLeftBack(v -> finish());

        
        mAdapter = new CollectAdapter(MyCollectActivity.this, mColletList, mPresenter);
        mRvCollet.setLayoutManager(new LinearLayoutManager(this));
        mRvCollet.setAdapter(mAdapter);
        //禁用滑动事件
        mRvCollet.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(MyCollectActivity.this, X5WebView.class);
                intent.putExtra("mUrl", mColletList.get(position).getLink());
                intent.putExtra("mTitle", mColletList.get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                }
                mPresenter.getData(page);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPresenter.getData(page);
                refreshLayout.finishRefresh();
            }
        });
        page = 0;
        mPresenter.getData(page);
    }

    @Override
    public void updateView(ArticleBean bean) {
        page = bean.getCurPage();
        pageCount = bean.getPageCount();
        if (page == 0) {
            mColletList.clear();
        }
        mColletList.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        mColletList.get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        mColletList.get(position).setCollect(false);
        mAdapter.notifyItemChanged(position);
    }
}
