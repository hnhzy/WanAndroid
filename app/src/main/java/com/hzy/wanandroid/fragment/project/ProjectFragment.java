package com.hzy.wanandroid.fragment.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.ViewPagerAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpFragment;
import com.hzy.wanandroid.bean.ProjectBean;
import com.hzy.wanandroid.fragment.home.HomeFragment;
import com.hzy.wanandroid.fragment.project_list.ProjectListFragment;
import com.hzy.wanandroid.ui.PubActivity;
import com.hzy.wanandroid.widget.TitleBarLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/1/22
 * 项目tab
 **/
public class ProjectFragment extends BaseMvpFragment<ProjectPresenter> implements ProjectContract.View {

    public static final String TAG = "ProjectFragment";

    private static ProjectFragment instance = null;

    public static final int REQ_CODE = 0x11;

    @BindView(R.id.vp_pub)
    ViewPager mViewPager;
    @BindView(R.id.tab_pub)
    TabLayout mTablayout;
    @BindView(R.id.imv_more)
    ImageView mImvMore;
    ViewPagerAdapter mPubAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();


    public static ProjectFragment getInstance() {
        if (instance == null) {
            instance = new ProjectFragment();
        }
        return instance;
    }

    public static ProjectFragment getInstance(String title) {
        if (instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG, title);
            instance = new ProjectFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initEventAndData() {
        BarUtils.setStatusBarVisibility(getActivity(), true);
        BarUtils.setStatusBarColor(getActivity(), getResources().getColor(R.color.c_6c8cff), 1);

        mImvMore.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PubActivity.class);
            intent.putExtra("title", "项目列表");
            intent.putExtra("titleList", (Serializable) titleList);
            getActivity().startActivityForResult(intent, REQ_CODE);
        });
        mPresenter.getProjectList();
    }

    @Override
    public void updateProject(List<ProjectBean> projectList) {
        if (null != titleList) titleList.clear();
        for (ProjectBean pb : projectList) {
            Fragment fragment = null;
            titleList.add(pb.getName());
            fragment = ProjectListFragment.newInstance(pb.getId());
            fragmentList.add(fragment);
        }
        mTablayout.setupWithViewPager(mViewPager);
        mTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mPubAdapter = new ViewPagerAdapter(getChildFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(mPubAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int position = data.getIntExtra("position", 0);
        mViewPager.setCurrentItem(position);
    }

}
