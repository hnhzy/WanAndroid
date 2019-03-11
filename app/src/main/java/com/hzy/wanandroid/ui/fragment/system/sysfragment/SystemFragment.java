package com.hzy.wanandroid.ui.fragment.system.sysfragment;

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
import com.hzy.wanandroid.bean.SystemDataBean;
import com.hzy.wanandroid.ui.activity.PubActivity;
import com.hzy.wanandroid.ui.fragment.system.systemlist.SubSystemFragment;
import com.hzy.wanandroid.widget.TitleBarLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/1/22
 * 体系tab
 *
 * @author Administrator
 */
public class SystemFragment extends BaseMvpFragment<SystemPresenter> implements SystemContract.View {

    public static final String TAG = "SystemFragment";
    public static final int REQ_CODE = 0x12;
    private static SystemFragment instance = null;

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.vp_pub)
    ViewPager mViewPager;
    @BindView(R.id.tab_pub)
    TabLayout mTablayout;
    @BindView(R.id.imv_more)
    ImageView mImvMore;
    ViewPagerAdapter mPubAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    public static SystemFragment getInstance() {
        if (instance == null) {
            instance = new SystemFragment();
        }
        return instance;
    }

    public static SystemFragment getInstance(String title) {
        if (instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG, title);
            instance = new SystemFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_system;
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
            intent.putExtra("title", "体系列表");
            intent.putExtra("titleList", (Serializable) titleList);
            getActivity().startActivityForResult(intent, REQ_CODE);
        });
        mPresenter.getSystemData();
    }

    @Override
    public void updateView(List<SystemDataBean> list) {
        if (null != titleList) {
            titleList.clear();
        }
        for (SystemDataBean sdb : list) {
            Fragment fragment = null;
            titleList.add(sdb.getName());
            fragment = SubSystemFragment.newInstance(sdb.getChildren());
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
