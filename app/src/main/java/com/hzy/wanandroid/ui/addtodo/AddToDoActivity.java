package com.hzy.wanandroid.ui.addtodo;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.widget.TitleBarLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/7
 * 添加待办清单
 **/
public class AddToDoActivity extends BaseMvpActivity<AddToDoPresenter> implements AddToDoContract.View {

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.et_title)
    EditText mEtTitle;

    @BindView(R.id.et_content)
    EditText mEtContent;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    private TimePickerView pvTime;

    private int type = 0;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_todo;
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
        mTitleBar.setTitle("添加待办清单");
        mTitleBar.setLeftBack(v -> finish());
        findViewById(R.id.bt_submit).setOnClickListener(v -> {
            String title = mEtTitle.getText().toString().trim();
            String content = mEtContent.getText().toString().trim();
            String date = mTvTime.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastUtils.showShort("请输入标题");
            } else if (TextUtils.isEmpty(date)) {
                ToastUtils.showShort("请选择时间");
            } else {
                mPresenter.addToDo(title, content, date, type);
            }
        });
        mTvTime.setOnClickListener(v -> {
            if (KeyboardUtils.isSoftInputVisible(this)) {
                KeyboardUtils.hideSoftInput(this);
            }
            //时间选择器
            pvTime = new TimePickerBuilder(AddToDoActivity.this, new OnTimeSelectListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    mTvTime.setText(getTime(date));
                }
            }).build();
            pvTime.show();
        });

    }

    @Override
    public void updateView(ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("添加清单成功");
            finish();
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
