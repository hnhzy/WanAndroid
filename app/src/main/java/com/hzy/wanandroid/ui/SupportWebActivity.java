package com.hzy.wanandroid.ui;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvc.BaseActivity;
import com.hzy.wanandroid.widget.TitleBarLayout;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/4
 * 赞助本站
 **/
public class SupportWebActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @Override
    protected int getLayout() {
        return R.layout.activity_support_web;
    }

    @Override
    protected void initView() {
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle("赞助本站");
        mTitleBar.setLeftBack(v -> finish());
    }

    @Override
    protected void initData() {

    }
}
