package com.hzy.wanandroid.ui.fragment.wxpulic.address;

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
import com.hzy.wanandroid.bean.PublicAddrBean;
import com.hzy.wanandroid.ui.activity.PubActivity;
import com.hzy.wanandroid.ui.fragment.wxpulic.addrlist.PubListFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by hzy on 2019/1/22
 *
 * @author hzy
 */
public class PublicAddrFragment extends BaseMvpFragment<PublicAddrPresenter> implements PublicAddrContract.View {

    public static final String TAG = "PublicAddrFragment";
    public static final int REQ_CODE = 0x14;
    private static PublicAddrFragment instance = null;

    @BindView(R.id.vp_pub)
    ViewPager mViewPager;
    @BindView(R.id.tab_pub)
    TabLayout mTablayout;
    @BindView(R.id.imv_more)
    ImageView mImvMore;
    ViewPagerAdapter mPubAdapter;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();
    List<PublicAddrBean> list = new ArrayList<>();
    private Map<Integer, Fragment> map = new HashMap<>();

    public static PublicAddrFragment getInstance() {
        if (instance == null) {
            instance = new PublicAddrFragment();
        }
        return instance;
    }

    public static PublicAddrFragment getInstance(String title) {
        if (instance == null) {
            Bundle bundle = new Bundle();
            bundle.putString(TAG, title);
            instance = new PublicAddrFragment();
            instance.setArguments(bundle);
        }
        return instance;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
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
            intent.putExtra("title", "公众号列表");
            intent.putExtra("titleList", (Serializable) titleList);
            getActivity().startActivityForResult(intent, REQ_CODE);
        });
        mPresenter.getData();
    }

    public int getAddrId() {
        int id = 408;
        id = list.get(mViewPager.getCurrentItem()).getId();
        return id;
    }

    @Override
    public void updateView(List<PublicAddrBean> list) {
        this.list = list;
        if (null != titleList) {
            titleList.clear();
        }
        fragmentList = new ArrayList<>();
        for (PublicAddrBean bean : list) {
            Fragment fragment = null;
            int id = bean.getId();
            if (map.containsKey(id)) {
                fragmentList.add(map.get(id));
            } else {
                fragment = PubListFragment.newInstance(id);
                fragmentList.add(fragment);
            }
            titleList.add(bean.getName());
            if (fragment != null) {
                map.put(id, fragment);
            }
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
