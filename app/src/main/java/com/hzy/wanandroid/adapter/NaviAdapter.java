package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.NaviBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 * NaviAdapter 导航Adapter
 * @author Administrator
 */
public class NaviAdapter extends CommonAdapter<NaviBean> {

    public NaviAdapter(Context context, List<NaviBean> datas) {
        super(context, R.layout.item_navi, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NaviBean naviBean, int position) {
        holder.setText(R.id.tv_project, naviBean.getName())
                .setBackgroundRes(R.id.tv_project, naviBean.getChecked() ?
                        R.drawable.shape_solid_blue_corner : R.drawable.shape_solid_gray_corner);
    }
}
