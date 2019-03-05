package com.hzy.wanandroid.base.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.App;
import com.hzy.wanandroid.dragger.component.ActivityComponent;
import com.hzy.wanandroid.dragger.component.DaggerActivityComponent;
import com.hzy.wanandroid.dragger.module.ActivityModule;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

/**
 * Created by hzy on 2019/1/17
 * <p>
 * MVP BaseMvpActivity
 **/
public abstract class BaseMvpActivity<T extends BasePersenter> extends AppCompatActivity implements BaseView {


    @Inject //drager
    @Nullable
    public T mPresenter;

    protected KProgressHUD mKProgressHUD;
    protected Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unbinder = ButterKnife.bind(this);
        ButterKnife.bind(this);
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
        initViewAndData();
    }

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(App.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    protected abstract int getLayout();

    protected abstract void initInject();

    protected abstract void initViewAndData();

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
        if (mPresenter != null)
            mPresenter.detachView();
        unbinder.unbind();
    }
}
