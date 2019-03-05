package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.NaviChildBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 **/
public class NaviGridAdapter extends CommonAdapter<NaviChildBean> {
    private Context mContext;
    private List<NaviChildBean> datas;

    public NaviGridAdapter(Context context, List<NaviChildBean> datas) {
        super(context, R.layout.item_navi_grid, datas);
        mContext = context;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, NaviChildBean naviBean, int position) {
        holder.setText(R.id.tv_project, naviBean.getTitle());
//                .setOnClickListener(R.id.tv_project, v -> {
//                    ToastUtils.showShort(naviBean.getTitle());
//                });
    }
}
