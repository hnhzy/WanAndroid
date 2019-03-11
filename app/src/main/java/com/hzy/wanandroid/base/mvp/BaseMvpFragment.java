package com.hzy.wanandroid.base.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.App;
import com.hzy.wanandroid.dragger.component.DaggerFragmentComponent;
import com.hzy.wanandroid.dragger.component.FragmentComponent;
import com.hzy.wanandroid.dragger.module.FragmentModule;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;

/**
 * Created by hzy on 2019/1/17
 * <p>
 * MVP BaseMvpFragment
 *
 * @author Administrator
 *
 * */
public abstract class BaseMvpFragment<T extends BasePersenter> extends Fragment implements BaseView {
    @Inject
    @Nullable
    protected T mPresenter;

    protected Unbinder unbinder;
    protected View mRootView, mErrorView, mEmptyView;
    protected KProgressHUD mKProgressHUD;

    protected abstract int getLayoutId();

    protected abstract void initInject();

    protected abstract void initEventAndData();

    @Override
    public void onResume() {
        super.onResume();
        if (!NetworkUtils.isConnected()) {
            onNetError();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), container, false);
        }
        unbinder = ButterKnife.bind(this, mRootView);
        initInject();
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
        unbinder = ButterKnife.bind(this, view);
        initEventAndData();
    }

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(getActivity());
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
}
