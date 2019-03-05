package com.hzy.wanandroid.ui.register;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.login.LoginActivity;
import com.hzy.wanandroid.widget.TitleBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.View {

    private static final String TAG = "RegisterActivity";
    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.et_username)
    EditText mEtUsername;

    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.et_password2)
    EditText mEtPassword2;
    private String username;
    private String password;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle("注册");
    }

    @OnClick({R.id.bt_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_register://登录按钮
                register();
                break;
        }
    }

    private void register() {
        username = mEtUsername.getText().toString().trim();
        password = mEtPassword.getText().toString().trim();
        String password2 = mEtPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            ToastUtils.showShort("请输入用户名");
        } else if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort("请输入密码");
        } else if (password.length() < 6) {
            ToastUtils.showShort("密码的字符长度至少为6位");
        } else if (TextUtils.isEmpty(password2)) {
            ToastUtils.showShort("请再次输入密码");
        } else if (!password.equals(password2)) {
            ToastUtils.showShort("两次输入的密码不一样请重新输入");
            mEtPassword.setText("");
            mEtPassword2.setText("");
        } else {
            mPresenter.postRegister(username, password, password2);
        }
    }

    @Override
    public void updateView(ResponseBean responseBean) {
        Log.d(TAG, responseBean.toString());
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("注册成功");
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startActivity(intent);
            finish();
        } else {
            ToastUtils.showShort(responseBean.getErrorMsg());
        }

    }
}
