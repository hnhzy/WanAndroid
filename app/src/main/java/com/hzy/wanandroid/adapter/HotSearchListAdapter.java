package com.hzy.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.ArticleListBean;
import com.hzy.wanandroid.http.HttpManager;
import com.hzy.wanandroid.ui.X5WebView;
import com.hzy.wanandroid.ui.article_search.ArticleSearchPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 **/
public class HotSearchListAdapter extends CommonAdapter<ArticleListBean> {

    private Context mContext;
    private ArticleSearchPresenter mPresenter;

    public HotSearchListAdapter(Context context, List<ArticleListBean> datas,
                                ArticleSearchPresenter mPresenter) {
        super(context, R.layout.item_article, datas);
        mContext = context;
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, ArticleListBean bean, int position) {
        Boolean isNewest =
                bean.getNiceDate().contains("小时") || bean.getNiceDate().contains("分钟");
        String textStr =bean.getTitle()
                .replaceAll("&ldquo;", "\"")
                .replaceAll("&rdquo;", "\"")
                .replaceAll("&mdash;", "—")
                .replaceAll("<em class='highlight'>", "<font color='#ff0000'>")
                .replaceAll("</em>", "</font>");
        holder.setText(R.id.tv_author, "作者：" + bean.getAuthor())
                .setText(R.id.tv_chapterName,
                        "分类:" + bean.getChapterName())
                .setText(R.id.tv_title, String.valueOf(Html.fromHtml(textStr)))
                .setVisible(R.id.tv_new, isNewest)
                .setText(R.id.tv_project, bean.getSuperChapterName())
                .setText(R.id.tv_time, "时间：" + bean.getNiceDate())
                .setImageResource(R.id.imv_like, bean.isCollect() ?
                        R.drawable.icon_like :
                        R.drawable.icon_unlike)
                .setOnClickListener(R.id.imv_like, v -> {//收藏和取消收藏
                    if (bean.isCollect()) {
                        mPresenter.unCollectArticle(bean.getId(),
                                bean.getTitle(),
                                bean.getAuthor(),
                                bean.getLink(), position);
                    } else {
                        mPresenter.collectArticle(bean.getTitle(),
                                bean.getAuthor(),
                                bean.getLink(), position);
                    }
                })
                .setOnClickListener(R.id.tv_project, v -> {
                    Intent intent = new Intent(mContext, X5WebView.class);
                    intent.putExtra("mUrl",
                            HttpManager.BASE_URL + bean.getTags().get(0).getUrl());
                    intent.putExtra("mTitle", bean.getTags().get(0).getName());
                    mContext.startActivity(intent);
                });
    }
}
