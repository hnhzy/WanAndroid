package com.hzy.wanandroid.adapter;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
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

    public ToDoAdapter(Context context, List<ToDoBean> datas,
                       ToDoPresenter mPresenter) {
        super(context, R.layout.item_todo, datas);
        mContext = context;
        this.mPresenter = mPresenter;
    }

    @Override
    protected void convert(ViewHolder holder, ToDoBean bean, int position) {
        holder.setText(R.id.tv_name, bean.getTitle())
                .setText(R.id.tv_content, bean.getContent())
                .setText(R.id.tv_date, bean.getDateStr());
        holder.getView(R.id.imv_up).setOnClickListener(v -> {
            ToastUtils.showShort("imv_up");
        });
        holder.getView(R.id.imv_delete).setOnClickListener(v -> {
            mPresenter.delete(bean.getId());
        });
        holder.getView(R.id.imv_undone).setOnClickListener(v -> {
            if (bean.getStatus() == 0) {
                mPresenter.done(bean.getId(), 1);
            } else {
                mPresenter.done(bean.getId(), 0);
            }
        });
    }
}
