package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.NaviChildBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 * NaviGridAdapter 导航右边grid
 *
 * @author Administrator
 */
public class NaviGridAdapter extends CommonAdapter<NaviChildBean> {

    public NaviGridAdapter(Context context, List<NaviChildBean> datas) {
        super(context, R.layout.item_navi_grid, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NaviChildBean naviBean, int position) {
        holder.setText(R.id.tv_project, naviBean.getTitle());
    }
}
