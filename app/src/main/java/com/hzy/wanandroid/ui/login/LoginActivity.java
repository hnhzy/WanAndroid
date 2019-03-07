package com.hzy.wanandroid.ui.login;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.MainActivity;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.bean.LoginBean;
import com.hzy.wanandroid.config.Constants;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.register.RegisterActivity;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.hzy.wanandroid.widget.TitleBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hzy on 2019/1/18
 * LoginActivity  登录界面
 */
public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    private static final String TAG = "LoginActivity";

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    private String username;
    private String password;

    Boolean isOtherToLogin = false;

    LoginViewModel model;

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {
        if (getIntent().getExtras() != null) {//从注册界面跳转过来直接记录账号密码
            isOtherToLogin = true;
            username = getIntent().getStringExtra("username");
            password = getIntent().getStringExtra("password");
            mEtUsername.setText(username);
            mEtPassword.setText(password);
        }

        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle("登录");

        model = ViewModelProviders.of(this).get(LoginViewModel.class);
        model.loginResult().observe(this, new Observer<ResponseBean<LoginBean>>() {
            @Override
            public void onChanged(@Nullable ResponseBean<LoginBean> s) {
                Log.d(TAG, "onChanged: " + s);
                closeLoading();
                if (s != null) {
                    updateView(s);
                } else {
                    onFail();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOtherToLogin) {
            mEtUsername.setText("362070860@qq.com");
            mEtPassword.setText("123456");
        }
    }

    @OnClick({R.id.bt_login, R.id.bt_reset_password, R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login://登录按钮
                login();
                break;
            case R.id.bt_reset_password://重置密码
                Toast.makeText(this, "点击了重置密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_register://注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
        }
    }

    private void login() {
        username = mEtUsername.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入登录密码");
        } else {
//            mPresenter.postLogin(username, password);
            showLoading();
            model.postLogin(username, password);
        }
    }

    @Override
    public void updateView(ResponseBean responseBean) {
        Log.d(TAG, responseBean.toString());
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("登录成功");
            LoginBean loginBean = (LoginBean) responseBean.getData();
            SharedPreferencesUtil.putData(Constants.USERNAME, loginBean.getUsername());
            SharedPreferencesUtil.putData(Constants.ISLOGIN, true);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            ToastUtils.showShort(responseBean.getErrorMsg());
        }

    }
}
