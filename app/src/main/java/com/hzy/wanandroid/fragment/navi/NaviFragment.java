package com.hzy.wanandroid.fragment.navi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.NaviAdapter;
import com.hzy.wanandroid.adapter.NaviGridAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.NaviBean;
import com.hzy.wanandroid.bean.NaviChildBean;
import com.hzy.wanandroid.fragment.system.SystemFragment;
import com.hzy.wanandroid.ui.X5WebView;
import com.hzy.wanandroid.widget.TitleBarLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/1/22
 * 导航tab
 **/
public class NaviFragment extends BaseMvpFragment<NaviPresenter> implements NaviContract.View {

    public static final String TAG = "NaviFragment";
    private static NaviFragment instance = null;


    @BindView(R.id.rv_list)
    RecyclerView mRvList;
    @BindView(R.id.rv_grid)
    RecyclerView mRvGrid;

    List<NaviBean> naviList = new ArrayList<>();
    NaviAdapter mAdapter;

    List<NaviChildBean> naviGridList = new ArrayList<>();
    NaviGridAdapter mGridAdapter;


    public static NaviFragment getInstance() {
        if (instance == null) {
            instance = new NaviFragment();
        }
        return instance;
    }

    public static NaviFragment getInstance(String title) {
        if (instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG, title);
            instance = new NaviFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navi;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        BarUtils.setStatusBarVisibility(getActivity(), true);
        BarUtils.setStatusBarColor(getActivity(), getResources().getColor(R.color.c_6c8cff), 1);
        mPresenter.getData();
    }

    @Override
    public void updateView(List<NaviBean> naviBeanList) {
        Log.e("NaviFragment", naviBeanList.toString());
        Log.d("updateProject", naviBeanList.toString());
        naviList.addAll(naviBeanList);
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        naviList.get(0).setChecked(true);
        mAdapter = new NaviAdapter(getActivity(), naviList);
        mRvList.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                for (NaviBean bean : naviList) {
                    bean.setChecked(false);
                }
                naviList.get(position).setChecked(true);
                mAdapter.notifyDataSetChanged();

                naviGridList.clear();
                naviGridList.addAll(naviBeanList.get(position).getArticles());
                mGridAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });

        if (null != naviBeanList && naviBeanList.size() > 0) {
            naviGridList.addAll(naviBeanList.get(0).getArticles());
            mRvGrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            mGridAdapter = new NaviGridAdapter(getActivity(), naviGridList);
            mRvGrid.setAdapter(mGridAdapter);
            mGridAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    Intent intent = new Intent(getActivity(), X5WebView.class);
                    intent.putExtra("mUrl", naviGridList.get(position).getLink());
                    intent.putExtra("mTitle", naviGridList.get(position).getTitle());
                    startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                               int position) {
                    return false;
                }
            });
        }


    }
}
