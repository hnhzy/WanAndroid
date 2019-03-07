package com.hzy.wanandroid.adapter;

import android.content.Context;
import android.view.View;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.ToDoBean;
import com.hzy.wanandroid.ui.todo.fragment.ToDoPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by hzy on 2019/1/24
 **/
public class ToDoAdapter extends CommonAdapter<ToDoBean> {

    private Context mContext;
    private ToDoPresenter mPresenter;
    private List<ToDoBean> datas;

    public ToDoAdapter(Context context, List<ToDoBean> datas,
                       ToDoPresenter mPresenter) {
        super(context, R.layout.item_todo, datas);
        mContext = context;
        this.mPresenter = mPresenter;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, ToDoBean bean, int position) {
        holder.setText(R.id.tv_name, bean.getTitle())
                .setText(R.id.tv_content, bean.getContent())
                .setText(R.id.tv_date, bean.getDateStr())
                .setImageResource(R.id.imv_up, bean.isArrowUp() ? R.drawable.up_icon :
                        R.drawable.down_icon)
                .getView(R.id.rl_top).setVisibility(bean.isTopVisible() ? View.VISIBLE : View.GONE);
        holder.getView(R.id.rl_bottom).setVisibility(bean.isBottomVisible() ? View.VISIBLE :
                View.GONE);
        holder.getView(R.id.imv_delete).setOnClickListener(v -> {
            mPresenter.delete(position, bean.getId());
        });
        holder.getView(R.id.imv_undone).setOnClickListener(v -> {
            mPresenter.done(position, bean.getId(), bean.getStatus() == 0 ? 1 : 0);
        });
        holder.getView(R.id.rl_top).setOnClickListener(v -> {
            String date = bean.getDateStr();
            if (bean.isArrowUp()) {
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getDateStr().equals(date)) {
                        datas.get(i).setBottomVisible(false);
                        datas.get(i).setArrowUp(false);
                    }
                }
            } else {
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).getDateStr().equals(date)) {
                        datas.get(i).setBottomVisible(true);
                        datas.get(i).setArrowUp(true);
                    }
                }
            }

            this.notifyDataSetChanged();
        });
    }
}
