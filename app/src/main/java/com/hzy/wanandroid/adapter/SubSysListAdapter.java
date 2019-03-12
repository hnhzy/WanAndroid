package com.hzy.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.ToastUtils;
import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.KnowledgeSystemChildBean;
import com.hzy.wanandroid.config.Constants;
import com.hzy.wanandroid.ui.activity.login.LoginActivity;
import com.hzy.wanandroid.ui.fragment.system.systemlist.SubSysPresenter;
import com.hzy.wanandroid.utils.SharedPreferencesUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 *
 * @author Administrator
 */
public class SubSysListAdapter extends CommonAdapter<KnowledgeSystemChildBean> {

    private SubSysPresenter mPresenter;

    public SubSysListAdapter(Context context, List<KnowledgeSystemChildBean> datas,
                             SubSysPresenter mPresenter) {
        super(context, R.layout.item_pub_addr, datas);
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, KnowledgeSystemChildBean bean, int position) {
        holder.setText(R.id.tv_project, bean.getTitle()
                .replaceAll("&ldquo;", "\"").replaceAll(
                        "&rdquo;", "\""))
                .setText(R.id.tv_time, "时间:" + bean.getNiceDate())
                .setImageResource(R.id.imv_like, bean.isCollect() ?
                        R.drawable.icon_like :
                        R.drawable.icon_unlike)
                .setOnClickListener(R.id.imv_like, v -> {
                    if (!(boolean) SharedPreferencesUtil.getData(Constants.ISLOGIN, false)) {
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
