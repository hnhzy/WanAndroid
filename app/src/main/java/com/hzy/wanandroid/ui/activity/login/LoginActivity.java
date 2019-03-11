package com.hzy.wanandroid.ui.activity.login;


import android.content.Intent;
import android.text.TextUtils;
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
import com.hzy.wanandroid.ui.activity.register.RegisterActivity;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.hzy.wanandroid.widget.TitleBarLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hzy on 2019/1/18
 * LoginActivity  登录界面
 *
 * @author hzy
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
        /**
         * //从注册界面跳转过来直接记录账号密码
         */
        if (getIntent().getExtras() != null) {
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
            case R.id.bt_login:
                login();//登录按钮
                break;
            case R.id.bt_reset_password:
                //重置密码
                Toast.makeText(this, "点击了重置密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_register:
                //注册
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            default:
                break;
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
            mPresenter.postLogin(username, password);
        }
    }

    @Override
    public void updateView(ResponseBean responseBean) {
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
