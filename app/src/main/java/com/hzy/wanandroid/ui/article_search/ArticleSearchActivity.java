package com.hzy.wanandroid.ui.article_search;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.adapter.HotSearchAdapter;
import com.hzy.wanandroid.adapter.HotSearchListAdapter;
import com.hzy.wanandroid.base.mvp.BaseMvpActivity;
import com.hzy.wanandroid.bean.ArticleBean;
import com.hzy.wanandroid.bean.ArticleListBean;
import com.hzy.wanandroid.bean.HotSearchBean;
import com.hzy.wanandroid.http.ResponseBean;
import com.hzy.wanandroid.ui.X5WebView;
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
 * Created by hzy on 2019/2/26
 **/
public class ArticleSearchActivity extends BaseMvpActivity<ArticleSearchPresenter> implements ArticleSearchContract.View {

    @BindView(R.id.et_search)
    ClearEditText mEtSearch;
    @BindView(R.id.rc_list)
    RecyclerView mRcList;

    @BindView(R.id.rv_grid)
    RecyclerView mRvGrid;

    @BindView(R.id.tv_end)
    TextView mTvEnd;

    List<ArticleListBean> mList = new ArrayList<>();
    List<HotSearchBean> mGrid = new ArrayList<>();
    HotSearchListAdapter mListAdapter;
    HotSearchAdapter mGridAdapter;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    int page = 0;
    private boolean over;
    public String searchStr;


    @Override
    protected int getLayout() {
        return R.layout.activity_article_search;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initViewAndData() {
        mEtSearch.setText("面试");
        BarUtils.setStatusBarVisibility(this, true);
        BarUtils.setStatusBarColor(this, getResources().getColor(R.color.c_6c8cff), 1);

        /**
         * 搜索点击
         */
        //发现执行了两次因为onkey事件包含了down和up事件，所以只需要加入其中一个即可。
        mEtSearch.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                // 先隐藏键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(ArticleSearchActivity.this.getCurrentFocus()
                                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if (TextUtils.isEmpty(mEtSearch.getText().toString())) {
                    Toast.makeText(ArticleSearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
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


        /**
         * 大家都在搜
         */
        mRvGrid.setLayoutManager(new GridLayoutManager(this, 3));
        mGridAdapter = new HotSearchAdapter(this, mGrid);
        mRvGrid.setAdapter(mGridAdapter);
        mGridAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                for (HotSearchBean bean : mGrid) {
                    bean.setChecked(false);
                }
                mGrid.get(position).setChecked(true);
                mEtSearch.setText(mGrid.get(position).getName());
                mGridAdapter.notifyDataSetChanged();
                mList.clear();
                search();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,
                                           int position) {
                return false;
            }
        });

        /**
         * 搜索列表
         */
//        id = getIntent().getIntExtra("id", 408);
        mRcList.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        mListAdapter = new HotSearchListAdapter(this, mList, mPresenter);
        mRcList.setAdapter(mListAdapter);
        //禁用滑动事件
        mRcList.setNestedScrollingEnabled(false);
        mListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(ArticleSearchActivity.this, X5WebView.class);
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
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(ArticleSearchActivity.this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(ArticleSearchActivity.this));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (!over) {
                    page++;
                    mPresenter.getData(page, searchStr);
                } else {
                    ToastUtils.showShort("已经到底啦");
                }

                refreshLayout.finishLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                search();
                refreshLayout.finishRefresh();
            }
        });
        mPresenter.getGridData();
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
                    Toast.makeText(ArticleSearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
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
        page = 0;
        searchStr = mEtSearch.getText().toString().trim();
        mPresenter.getData(page, searchStr);
    }

    @Override
    public void updateView(ArticleBean bean) {
        //大于四条才显示这个
        if (bean.getDatas() != null && bean.getDatas().size() > 4) {
            over = bean.isOver();
            mTvEnd.setVisibility(over ? View.VISIBLE : View.GONE);
        }
        if (page == 0) {
            mList.clear();
        }
        mList.addAll(bean.getDatas());
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateGridView(List<HotSearchBean> gridList) {
        mGrid.clear();
        mGrid.addAll(gridList);
        for (HotSearchBean bean : mGrid) {
            bean.setChecked(false);
        }
        mGrid.get(0).setChecked(true);
        mGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("收藏成功");
        mList.get(position).setCollect(true);
        mListAdapter.notifyItemChanged(position);
    }

    @Override
    public void updateUnCollect(ResponseBean responseBean, int position) {
        ToastUtils.showShort("取消收藏成功");
        mList.get(position).setCollect(false);
        mListAdapter.notifyItemChanged(position);
    }
}
