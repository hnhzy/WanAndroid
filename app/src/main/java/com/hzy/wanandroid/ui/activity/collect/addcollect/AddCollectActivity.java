package com.hzy.wanandroid.ui.activity.collect.addcollect;

import android.text.TextUtils;
import android.widget.Button;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.widget.ClearEditText;
import com.hzy.wanandroid.widget.TitleBarLayout;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/12
 * 添加收藏
 *
 * @author Administrator
 */
public class AddCollectActivity extends BaseMvpActivity<AddCollectPresenter> implements AddCollectContract.View {

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.et_title)
    ClearEditText mEtTitle;

    @BindView(R.id.et_author)
    ClearEditText mEtAuthor;

    @BindView(R.id.et_link)
    ClearEditText mEtLink;

    @BindView(R.id.bt_submit)
    Button mBtSubmit;


    @Override
    protected int getLayout() {
        return R.layout.activity_add_collect;
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
        mTitleBar.setTitle("添加收藏");
        mTitleBar.setLeftBack(v -> finish());

        mBtSubmit.setOnClickListener(v -> {
            String title = mEtTitle.getText().toString().trim();
            String author = mEtAuthor.getText().toString().trim();
            String link = mEtLink.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastUtils.showShort("请输入标题");
            } else if (TextUtils.isEmpty(link)) {
                ToastUtils.showShort("请输入链接地址");
            } else {
                mPresenter.addCollect(title, author, link);
            }
        });
    }

    @Override
    public void updateView(ResponseBean bean) {
        if (bean.getErrorCode() == 0) {
            ToastUtils.showShort("添加收藏成功");
            finish();
        }
    }
}
