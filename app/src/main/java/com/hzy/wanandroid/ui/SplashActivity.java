package com.hzy.wanandroid.ui;


import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;


import com.hzy.wanandroid.MainActivity;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvc.BaseActivity;
import com.hzy.wanandroid.ui.login.LoginActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import yanzhikai.textpath.PathAnimatorListener;
import yanzhikai.textpath.SyncTextPathView;
import yanzhikai.textpath.painter.ArrowPainter;
import yanzhikai.textpath.painter.PenPainter;

/**
 * Created by hzy on 2019/1/18
 * SplashActivity 启动界面
 **/
public class SplashActivity extends BaseActivity {

    /**
     * Wan Android
     */
    @BindView(R.id.stpv_wa)
    SyncTextPathView mStpvWa;

    /**
     * Created by hzy
     */
    @BindView(R.id.stpv_cbh)
    SyncTextPathView mStpvCbh;

    @Override
    protected int getLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mStpvCbh.setPathPainter(new ArrowPainter());
        mStpvCbh.startAnimation(0, 1);

        mStpvWa.setPathPainter(new PenPainter());
        mStpvWa.startAnimation(0, 1);
        //设置动画播放完后填充颜色
        mStpvWa.setAnimatorListener(new PathAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!isCancel) {
                    mStpvWa.showFillColorText();//填充文字颜色
                    mStpvCbh.stopAnimation();//关闭mStpvCbh动画
                    mStpvWa.stopAnimation();//关闭mStpvWa动画
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    protected void initData() {
        /**
         * rxPermissions
         */
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.WRITE_SETTINGS)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                    } else {
                        // Oups permission denied
                        rxPermissions.request(Manifest.permission.WRITE_SETTINGS);
                    }
                });
    }
}


