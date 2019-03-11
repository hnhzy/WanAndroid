package com.hzy.wanandroid.ui.activity.pubsearch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.SearchPubAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.bean.PubAddrListBean;
import com.hzy.wanandroid.bean.PubAddrListChild;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.activity.X5WebView;
import com.hzy.wanandroid.widget.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hzy on 2019/2/25
 * 在某个公众号中搜索历史文章
 *
 * @author Administrator
 */
public class SearchActivity extends BaseMvpActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.et_search)
    ClearEditText mEtSearch;
    @BindView(R.id.rc_list)
    RecyclerView mRcList;

    List<PubAddrListChild> mList = new ArrayList<>();
    SearchPubAdapter mAdapter;
    private int id;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page = 1;
    int pageCount = 0;
    public String searchStr;

    @Override
    protected int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);

        /**
         * 搜索点击
         */
        //发现执行了两次因为onkey事件包含了down和up事件，所以只需要加入其中一个即可。
        mEtSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                // 先隐藏键盘
                if (KeyboardUtils.isSoftInputVisible(this)) {
                    KeyboardUtils.hideSoftInput(this);
                }

                if (TextUtils.isEmpty(mEtSearch.getText().toString())) {
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    return false;
                }
                search();//搜索接口
            }
            return false;
        });

        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    search();
                }
            }
        });


        id = getIntent().getIntExtra("id", 408);
        mRcList.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mAdapter = new SearchPubAdapter(this, mList, mPresenter);
        mRcList.setAdapter(mAdapter);
        //禁用滑动事件
        mRcList.setNestedScrollingEnabled(false);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(SearchActivity.this, X5WebView.class);
                intent.putExtra("mUrl", mList.get(position).getLink());
                intent.putExtra("mTitle", mList.get(position).getTitle());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(SearchActivity.this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(SearchActivity.this));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (pageCount > page) {
                    page++;
                }
                mPresenter.getData(id, page, searchStr);
                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getData(id, page, searchStr);
                refreshLayout.finishRefresh();
            }
        });
        search();
    }

    @OnClick({R.id.imv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imv_back:
                finish();
                break;
            case R.id.tv_search:
                if (TextUtils.isEmpty(mEtSearch.getText().toString())) {
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                search();
                break;
        }
    }

    /**
     * 搜索接口
     */
    private void search() {
        page = 1;
        searchStr = mEtSearch.getText().toString().trim();
        mPresenter.getData(id, page, searchStr);
    }

    @Override
    public void updateView(PubAddrListBean bean) {
        page = bean.getCurPage();
        pageCount = bean.getPageCount();
        if (page == 1) {
            mList.clear();
        }
        mList.addAll(bean.getDatas());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        mList.get(position).setCollect(true);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        mList.get(position).setCollect(false);
        mAdapter.notifyItemChanged(position);
    }
}
