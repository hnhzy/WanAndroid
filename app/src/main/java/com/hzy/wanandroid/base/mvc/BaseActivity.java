package com.hzy.wanandroid.base.mvc;

import android.app.ActivityManager;
import android.arch.lifecycle.Lifecycle;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.afollestad.materialdialogs.color.CircleView;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvp.BaseView;
import com.hzy.wanandroid.config.Constants;
import com.hzy.wanandroid.utils.SettingUtil;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hzy on 2019/1/17
 * <p>
 * MVC BaseActivity 一些简单的页面依然使用mvc实现
 *
 * @author hzy
 *
 * */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    protected KProgressHUD mKProgressHUD;
    protected Unbinder unbinder;
    protected SlidrInterface slidrInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int color = SettingUtil.getInstance().getColor();
        int drawable = Constants.ICONS_DRAWABLES[SettingUtil.getInstance().getCustomIconValue()];
        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(CircleView.shiftColorDown(color));
            // 最近任务栏上色
            ActivityManager.TaskDescription tDesc = new ActivityManager.TaskDescription(
                    getString(R.string.app_name),
                    BitmapFactory.decodeResource(getResources(), drawable),
                    color);
            setTaskDescription(tDesc);
            if (SettingUtil.getInstance().getNavBar()) {
                getWindow().setNavigationBarColor(CircleView.shiftColorDown(color));
            } else {
                getWindow().setNavigationBarColor(Color.BLACK);
            }
        }
    }

    /**
     * 初始化 Toolbar
     */
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    /**
     * 初始化滑动返回
     */
    protected void initSlidable() {
        int isSlidable = SettingUtil.getInstance().getSlidable();
        if (isSlidable != Constants.SLIDABLE_DISABLE) {
            SlidrConfig config = new SlidrConfig.Builder()
                    .edge(isSlidable == Constants.SLIDABLE_EDGE)
                    .build();
            slidrInterface = Slidr.attach(this, config);
        }
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(this);
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void closeLoading() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {
        ToastUtils.showShort("获取数据失败");
    }

    @Override
    public void onNetError() {
        ToastUtils.showShort("请检查网络是否连接");
    }

    @Override
    public void onReLoad() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public <X> AutoDisposeConverter<X> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

}
