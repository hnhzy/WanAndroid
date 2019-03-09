package com.hzy.wanandroid.ui.addtodo;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.hzy.wanandroid.bean.ToDoBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.widget.TitleBarLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * Created by hzy on 2019/3/7
 * 添加待办清单
 *
 * @author Administrator
 */
public class AddToDoActivity extends BaseMvpActivity<AddToDoPresenter> implements AddToDoContract.View {

    private static final String TAG = "AddToDoActivity";

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.et_title)
    EditText mEtTitle;

    @BindView(R.id.et_content)
    EditText mEtContent;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.bt_submit)
    Button mBtSubmit;

    private TimePickerView pvTime;

    private int type = 0;
    private ToDoBean mToDoBean;

    private boolean isAdd;

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

        mTitleBar.setLeftBack(v -> finish());
        mBtSubmit.setOnClickListener(v -> {
            String title = mEtTitle.getText().toString().trim();
            String content = mEtContent.getText().toString().trim();
            String date = mTvTime.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                ToastUtils.showShort("请输入标题");
            } else if (TextUtils.isEmpty(date)) {
                ToastUtils.showShort("请选择时间");
            } else {
                if (isAdd) {
                    mToDoBean=new ToDoBean();
                    mToDoBean.setStatus(0);
                    mToDoBean.setArrowUp(true);
                    mToDoBean.setType(0);
                    mToDoBean.setTitle(title);
                    mToDoBean.setContent(content);
                    mToDoBean.setDateStr(date);
                    mPresenter.addToDo(title, content, date, type);
                } else {
                    mToDoBean.setTitle(title);
                    mToDoBean.setContent(content);
                    mToDoBean.setDateStr(date);
                    mPresenter.updateToDo(mToDoBean.getId(), title, content, date,
                            mToDoBean.getStatus(), type);
                }


            }
        });
        mTvTime.setOnClickListener(v -> {
            if (KeyboardUtils.isSoftInputVisible(this)) {
                KeyboardUtils.hideSoftInput(this);
            }
            //时间选择器
            pvTime = new TimePickerBuilder(AddToDoActivity.this,
                    (date, v1) -> mTvTime.setText(getTime(date))).build();
            pvTime.show();
        });

        if (getIntent().getExtras() != null) {
            isAdd = false;
            mToDoBean = (ToDoBean) getIntent().getSerializableExtra("mToDoBean");
            mTitleBar.setTitle("修改待办清单");
            mTvTime.setText(mToDoBean.getDateStr());
            mEtTitle.setText(mToDoBean.getTitle());
            mEtContent.setText(mToDoBean.getContent());
            mBtSubmit.setText("确认修改");
        } else {
            isAdd = true;
            mTitleBar.setTitle("添加待办清单");
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ToDoBean bean) {
        Log.e(TAG, "onMessageEvent" + bean.getStatus());
    }

    @Override
    public void addView(ResponseBean responseBean) {
        ToastUtils.showShort("添加清单成功");
        mToDoBean.setWhere("AddToDoActivity");
        EventBus.getDefault().post(mToDoBean);
        finish();
    }

    @Override
    public void updateView(ResponseBean responseBean) {
        if (responseBean.getErrorCode() == 0) {
            ToastUtils.showShort("修改清单成功");
            mToDoBean.setWhere("AddToDoActivity");
            EventBus.getDefault().post(mToDoBean);
            finish();
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
