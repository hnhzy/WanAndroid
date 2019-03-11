package com.hzy.wanandroid.ui.activity.freqweb;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.FreqWebGridAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.bean.FreUsedWebBean;
import com.hzy.wanandroid.ui.activity.X5WebView;
import com.hzy.wanandroid.widget.TitleBarLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/4
 * 常用网站页面
 *
 * @author Administrator
 *
 * */
public class FreqWebActivity extends BaseMvpActivity<FreqWebPresenter> implements FreqWebContract.View {

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.rv_grid)
    RecyclerView mRvGrid;

    List<FreUsedWebBean> naviGridList = new ArrayList<>();
    FreqWebGridAdapter mGridAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_freq_web;
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
        mTitleBar.setTitle("常用网站");
        mTitleBar.setLeftBack(v -> finish());

        mRvGrid.setLayoutManager(new GridLayoutManager(this, 2));
        mGridAdapter = new FreqWebGridAdapter(this, naviGridList);
        mRvGrid.setAdapter(mGridAdapter);
        mGridAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(FreqWebActivity.this, X5WebView.class);
                intent.putExtra("mUrl", naviGridList.get(position).getLink());
                intent.putExtra("mTitle", naviGridList.get(position).getName());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });
        mPresenter.getData();
    }

    @Override
    public void updateView(List<FreUsedWebBean> list) {
        naviGridList.clear();
        naviGridList.addAll(list);
        mGridAdapter.notifyDataSetChanged();
    }

}
