package com.hzy.wanandroid.ui.todo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.MyPagerAdapter;
import com.hzy.wanandroid.base.mvc.BaseActivity;
import com.hzy.wanandroid.ui.addtodo.AddToDoActivity;
import com.hzy.wanandroid.ui.todo.fragment.donefragment.DoneFragment;
import com.hzy.wanandroid.ui.todo.fragment.todofragment.ToDoFragment;
import com.hzy.wanandroid.widget.TitleBarLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/5
 *
 * @author hzy
 *
 * */
public class ToDoActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tl)
    TabLayout tabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {"待办清单", "已完成清单"};
    private MyPagerAdapter mAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_todo;
    }

    @Override
    protected void initView() {
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle("我的清单");
        mTitleBar.setLeftBack(v -> finish());
        mTitleBar.setRightImage(R.drawable.add_icon2, v -> {
            startActivity(new Intent(this, AddToDoActivity.class));
        });
    }

    @Override
    protected void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mFragments.add(i == 0 ? ToDoFragment.newInstance() : DoneFragment.newInstance());
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        mAdapter = new MyPagerAdapter(fragmentManager, mFragments, mTitles);
        vp.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(vp);
    }
}
