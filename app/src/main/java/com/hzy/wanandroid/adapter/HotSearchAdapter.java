package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.HotSearchBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 * HotSearchAdapter  热门搜索
 *
 * @author Administrator
 */
public class HotSearchAdapter extends CommonAdapter<HotSearchBean> {

    public HotSearchAdapter(Context context, List<HotSearchBean> datas) {
        super(context, R.layout.item_navi_grid, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HotSearchBean bean, int position) {
        holder.setText(R.id.tv_project, bean.getName())
                .setBackgroundRes(R.id.tv_project, bean.getChecked() ?
                        R.drawable.shape_solid_blue_corner10 : R.drawable.shape_blue_corner10_bg)
                .setTextColorRes(R.id.tv_project, bean.getChecked() ? R.color.white :
                        R.color.c_4285F4);
    }
}
