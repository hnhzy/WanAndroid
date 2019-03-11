package com.hzy.wanandroid.ui.fragment.project.projectlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.ProjectListAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.ProjectListBean;
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
 * Created by hzy on 2019/2/20
 * 项目列表
 *
 * @author hzy
 *
 * */

public class ProjectListFragment extends BaseMvpFragment<ProjectListPresenter> implements ProjectListContract.View {

    private static final String TAG = "ProjectListFragment";

    @BindView(R.id.rv_list)
    RecyclerView mRvProject;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    int page = 1;
    int cid = 0;
    int pageCount = 0;
    ProjectListAdapter mAdapter;
    List<ProjectListBean.DatasBean> projectList = new ArrayList<>();

    public static ProjectListFragment newInstance(int cid) {
        Bundle bundle = new Bundle();
        bundle.putInt(TAG, cid);
        ProjectListFragment fragment = new ProjectListFragment();
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

        cid = getArguments().getInt(TAG);

        mAdapter = new ProjectListAdapter(getActivity(), projectList,mPresenter);
        mRvProject.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvProject.setAdapter(mAdapter);
        //禁用滑动事件
        mRvProject.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(getActivity(), X5WebView.class);
                intent.putExtra("mUrl", projectList.get(position).getLink());
                intent.putExtra("mTitle", projectList.get(position).getTitle());
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
                    mPresenter.getProjectList(page, cid);
                } else {
                    ToastUtils.showShort("已经没有数据了");
                }
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getProjectList(page, cid);
                refreshLayout.finishRefresh();
            }
        });
        page = 1;
        mPresenter.getProjectList(page, cid);

    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        projectList.get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        projectList.get(position).setCollect(false);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateProject(ProjectListBean projectListBean) {
        pageCount = projectListBean.getPageCount();
        page = projectListBean.getCurPage();
        if (page == 1) {
            projectList.clear();
        }
        projectList.addAll(projectListBean.getDatas());
        mAdapter.notifyDataSetChanged();
    }


}
