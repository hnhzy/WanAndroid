package com.hzy.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.ArticleListBean;
import com.hzy.wanandroid.config.Constants;
import com.hzy.wanandroid.ui.activity.X5WebView;
import com.hzy.wanandroid.ui.activity.mycollect.MyCollectPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 *  收藏 Adapter
 * @author Administrator
 */
public class CollectAdapter extends CommonAdapter<ArticleListBean> {

    private Context mContext;
    private MyCollectPresenter mPresenter;

    public CollectAdapter(Context context, List<ArticleListBean> datas,
                          MyCollectPresenter mPresenter) {
        super(context, R.layout.item_article, datas);
        mContext = context;
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, ArticleListBean articleListBean, int position) {
        holder.setText(R.id.tv_author, "作者：" + articleListBean.getAuthor())
                .setVisible(R.id.tv_chapterName, false)
                .setText(R.id.tv_title, articleListBean.getTitle()
                        .replaceAll("&ldquo;", "\"")
                        .replaceAll("&rdquo;", "\"")
                        .replaceAll("&mdash;", "—"))
                .setVisible(R.id.tv_new, false)
                .setVisible(R.id.tv_project, false)
                .setText(R.id.tv_time, "收藏时间：" + articleListBean.getNiceDate())
                .setImageResource(R.id.imv_like, R.drawable.icon_like)
                //取消收藏
                .setOnClickListener(R.id.imv_like, v -> {
                    mPresenter.unCollectArticle(articleListBean.getId(),
                            TextUtils.isEmpty(articleListBean.getOrigin()) ? "-1" :
                                    articleListBean.getOrigin(), position);
                })
                .setOnClickListener(R.id.tv_project, v -> {
                    Intent intent = new Intent(mContext, X5WebView.class);
                    intent.putExtra("mUrl",
                            Constants.BASE_URL + articleListBean.getTags().get(0).getUrl());
                    intent.putExtra("mTitle", articleListBean.getTags().get(0).getName());
                    mContext.startActivity(intent);
                });
    }
}
