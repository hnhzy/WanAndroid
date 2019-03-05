package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.PubAddrListChild;
import com.hzy.wanandroid.fragment.PubList.PubListPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 **/
public class PubAddrAdapter extends CommonAdapter<PubAddrListChild> {
    private Context mContext;
    private PubListPresenter mPresenter;

    public PubAddrAdapter(Context context, List<PubAddrListChild> datas,
                          PubListPresenter mPresenter) {
        super(context, R.layout.item_pub_addr, datas);
        mContext = context;
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, PubAddrListChild bean, int position) {
        holder.setText(R.id.tv_project, bean.getTitle()
                .replaceAll("&ldquo;", "\"").replaceAll(
                        "&rdquo;", "\""))
                .setText(R.id.tv_time, "时间:" + bean.getNiceDate())
                .setImageResource(R.id.imv_like, bean.isCollect() ?
                        R.drawable.icon_like :
                        R.drawable.icon_unlike)
                .setOnClickListener(R.id.imv_like, v -> {
                    if (bean.isCollect()) {
                        mPresenter.unCollectArticle(bean.getId(), bean.getTitle(), bean.getAuthor(),
                                bean.getLink(), position);
                    } else {
                        mPresenter.collectArticle(bean.getTitle(), bean.getAuthor(),
                                bean.getLink(), position);
                    }
                });
    }
}
