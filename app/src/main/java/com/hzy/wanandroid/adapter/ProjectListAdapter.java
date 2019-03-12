package com.hzy.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.ProjectListBean;
import com.hzy.wanandroid.config.Constants;
import com.hzy.wanandroid.ui.activity.login.LoginActivity;
import com.hzy.wanandroid.ui.fragment.project.projectlist.ProjectListPresenter;
import com.hzy.wanandroid.utils.ImageLoaderUtil;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 * ProjectListAdapter  项目列表
 * @author Administrator
 *
 * */
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
    protected void convert(ViewHolder holder, ProjectListBean.DatasBean bean, int position) {
        ImageLoaderUtil.LoadImage(mContext, bean.getEnvelopePic(),
                holder.getView(R.id.imv_project));
        holder.setText(R.id.tv_project_name, bean.getTitle())
                .setText(R.id.tv_project_content, bean.getDesc())
                .setText(R.id.tv_time, bean.getNiceDate())
                .setText(R.id.tv_author, bean.getAuthor())
                .setImageResource(R.id.imv_like, bean.isCollect() ? R.drawable.icon_like :
                        R.drawable.icon_unlike)
                .setOnClickListener(R.id.imv_like, v -> {
                    if (!(boolean)SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
                        ToastUtils.showShort("请先登录");
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        return;
                    }
                    if (bean.isCollect()) {
                        mPresenter.unCollectArticle(bean.getId(), position);
                    } else {
                        mPresenter.collectArticle(bean.getId(), position);
                    }
                });
    }
}
