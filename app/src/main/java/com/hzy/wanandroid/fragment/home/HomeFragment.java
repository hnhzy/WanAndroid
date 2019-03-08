package com.hzy.wanandroid.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.ArticleAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.bean.ArticleListBean;
import com.hzy.wanandroid.bean.HomeBanner;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.X5WebView;
import com.hzy.wanandroid.widget.GlideImageLoader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/1/22
 * HomeFragment 首页fragment
 *
 * @author Administrator
 *
 * */
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    public static final String TAG = "HomeFragment";

    private static HomeFragment instance = null;

    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.rv_article)
    RecyclerView mRvArticle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    int page = 0;
    int pageCount = 0;
    ArticleAdapter mAdapter;
    List<ArticleListBean> articleList = new ArrayList<>();

    public static HomeFragment getInstance() {
        if (instance == null) {
            instance = new HomeFragment();
        }
        return instance;
    }


    public static HomeFragment getInstance(String title) {
        if (instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG, title);
            instance = new HomeFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        BarUtils.setStatusBarVisibility(getActivity(), true);
        BarUtils.setStatusBarColor(getActivity(), getResources().getColor(R.color.c_6c8cff), 1);

        mAdapter = new ArticleAdapter(getActivity(), articleList, mPresenter);
        mRvArticle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvArticle.setAdapter(mAdapter);
        //禁用滑动事件
        mRvArticle.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), X5WebView.class);
                intent.putExtra("mUrl", articleList.get(position).getLink());
                intent.putExtra("mTitle", articleList.get(position).getTitle());
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
                mPresenter.getArticleList(page);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 0;
                mPresenter.getArticleList(page);
                refreshLayout.finishRefresh();
            }
        });
        page = 0;
        mPresenter.getBannerList();
        mPresenter.getArticleList(page);

    }


    @Override
    public void updateBanner(List<HomeBanner> homeBannerList) {
        List<String> listBanner = new ArrayList<>();
        List<String> listTitle = new ArrayList<>();
        List<String> listUrl = new ArrayList<>();
        for (HomeBanner homeBanner : homeBannerList) {
            listBanner.add(homeBanner.getImagePath());
            listTitle.add(homeBanner.getTitle());
            listUrl.add(homeBanner.getUrl());
        }
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(listBanner);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        mBanner.setBannerTitles(listTitle);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.setOnBannerClickListener(position -> {
            Intent intent = new Intent(getActivity(), X5WebView.class);
            intent.putExtra("mUrl", homeBannerList.get(position - 1).getUrl());
            intent.putExtra("mTitle", homeBannerList.get(position - 1).getTitle());
            startActivity(intent);
        });
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        articleList.get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        articleList.get(position).setCollect(false);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateArticle(ArticleBean articleBean) {
        page = articleBean.getCurPage();
        pageCount = articleBean.getPageCount();
        if (page == 0) {
            articleList.clear();
        }
        articleList.addAll(articleBean.getDatas());
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 如果你需要考虑更好的体验，可以这么操作
     */
    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mBanner.stopAutoPlay();
    }
}
