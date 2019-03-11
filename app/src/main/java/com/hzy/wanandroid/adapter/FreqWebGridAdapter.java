package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.FreUsedWebBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 * FreqWebGridAdapter 常用网站Adapter
 *
 * @author hzy
 */
public class FreqWebGridAdapter extends CommonAdapter<FreUsedWebBean> {

    public FreqWebGridAdapter(Context context, List<FreUsedWebBean> datas) {
        super(context, R.layout.item_navi_grid, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FreUsedWebBean bean, int position) {
        holder.setText(R.id.tv_project, bean.getName());
    }
}
