package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.ProjectListBean;
import com.hzy.wanandroid.fragment.project_list.ProjectListPresenter;
import com.hzy.wanandroid.utils.ImageLoaderUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 **/
public class ProjectListAdapter extends CommonAdapter<ProjectListBean.DatasBean> {

    private Context mContext;
    private ProjectListPresenter mPresenter;

    public ProjectListAdapter(Context context,
                              List<ProjectListBean.DatasBean> datas,
                              ProjectListPresenter mPresenter) {
        super(context, R.layout.item_project_list, datas);
        mContext = context;
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, ProjectListBean.DatasBean datasBean, int position) {
        ImageLoaderUtil.LoadImage(mContext, datasBean.getEnvelopePic(),
                holder.getView(R.id.imv_project));
        holder.setText(R.id.tv_project_name, datasBean.getTitle())
                .setText(R.id.tv_project_content, datasBean.getDesc())
                .setText(R.id.tv_time, datasBean.getNiceDate())
                .setText(R.id.tv_author, datasBean.getAuthor())
                .setImageResource(R.id.imv_like, datasBean.isCollect() ? R.drawable.icon_like :
                        R.drawable.icon_unlike)
                .setOnClickListener(R.id.imv_like, v -> {
                    if (datasBean.isCollect()) {
                        mPresenter.unCollectArticle(datasBean.getId(), datasBean.getTitle(),
                                datasBean.getAuthor(),
                                datasBean.getLink(), position);
                    } else {
                        mPresenter.collectArticle(datasBean.getTitle(), datasBean.getAuthor(),
                                datasBean.getLink(), position);
                    }
                });
    }
}
