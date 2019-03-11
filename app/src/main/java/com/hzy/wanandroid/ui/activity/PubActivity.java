package com.hzy.wanandroid.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.BarUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.PubAdapter;
import com.hzy.wanandroid.base.mvc.BaseActivity;
import com.hzy.wanandroid.widget.TitleBarLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hzy on 2019/2/20
 * <p>
 * tab 选择页面
 *
 * @author Administrator
 */
public class PubActivity extends BaseActivity {

    @BindView(R.id.title_bar)
    TitleBarLayout mTitleBar;

    @BindView(R.id.rv_pub)
    RecyclerView mRvPub;

    PubAdapter mAdapter;
    List<String> titleList = new ArrayList<>();
    String title;


    @Override
    protected int getLayout() {
        return R.layout.activity_pub;
    }

    @Override
    protected void initView() {
        title = getIntent().getStringExtra("title");
        titleList = getIntent().getStringArrayListExtra("titleList");
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);
        mTitleBar.setTitleBarBgColor(getResources().getColor(R.color.c_6c8cff));
        mTitleBar.setTitleColor(getResources().getColor(R.color.c_ffffff));
        mTitleBar.setTitle(title);
        mTitleBar.setLeftBack(v -> finish());
    }

    @Override
    protected void initData() {
        mRvPub.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapter = new PubAdapter(this, titleList);
        mRvPub.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent();
                intent.putExtra("position", position);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });
    }

}
