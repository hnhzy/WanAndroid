package com.hzy.wanandroid.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hzy.wanandroid.R;
import com.hzy.wanandroid.bean.ToDoBean;
import com.hzy.wanandroid.ui.addtodo.AddToDoActivity;
import com.hzy.wanandroid.ui.todo.fragment.donefragment.DonePresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/1/24
 *
 * @author hzy
 */
public class DoneAdapter extends CommonAdapter<List<ToDoBean>> {

    private Context mContext;
    private DonePresenter mPresenter;
    private List<List<ToDoBean>> datas;

    public DoneAdapter(Context context, List<List<ToDoBean>> datas,
                       DonePresenter mPresenter) {
        super(context, R.layout.item_todo, datas);
        mContext = context;
        this.mPresenter = mPresenter;
        this.datas = datas;
    }

    @Override
    protected void convert(ViewHolder holder, List<ToDoBean> beanList, int position) {
        if (null == beanList || beanList.size() == 0) {
            return;
        }
        holder.setText(R.id.tv_date, beanList.get(0).getDateStr())
                .setImageResource(R.id.imv_up, beanList.get(0).isArrowUp() ? R.drawable.up_icon :
                        R.drawable.down_icon);
        RelativeLayout rlTop = holder.getView(R.id.rl_top);

        LinearLayout llBottom = holder.getView(R.id.ll_bottom);
        rlTop.setOnClickListener(v -> {
            if (beanList.get(0).isArrowUp()) {
                beanList.get(0).setArrowUp(false);
                llBottom.setVisibility(View.GONE);
            } else {
                beanList.get(0).setArrowUp(true);
                llBottom.setVisibility(View.VISIBLE);
            }
            notifyDataSetChanged();
        });
        if (beanList.size() == 0) {
            rlTop.setVisibility(View.GONE);
        } else {
            rlTop.setVisibility(View.VISIBLE);
            llBottom.removeAllViews();
            for (int i = 0; i < beanList.size(); i++) {
                View view = mInflater.inflate(R.layout.item_todo_bottom, null);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView tv_content = view.findViewById(R.id.tv_content);
                ImageView imv_delete = view.findViewById(R.id.imv_delete);
                ImageView imv_undone = view.findViewById(R.id.imv_undone);
                RelativeLayout rl_item = view.findViewById(R.id.rl_item);
                imv_delete.setTag(i);
                imv_delete.setOnClickListener(v -> {
                    int subPos = (int) imv_delete.getTag();
                    mPresenter.delete(position, subPos, beanList.get(subPos).getId());
                });
                imv_undone.setTag(i);
                imv_undone.setOnClickListener(v -> {
                    int subPos = (int) imv_undone.getTag();
                    mPresenter.done(position, subPos, beanList.get(subPos).getId(),
                            0);
                });
                rl_item.setTag(i);
                rl_item.setOnClickListener(v -> {
                    int subPos = (int) rl_item.getTag();
                    Intent intent = new Intent(mContext, AddToDoActivity.class);
                    intent.putExtra("mToDoBean", beanList.get(subPos));
                    mContext.startActivity(intent);
                });
                tv_name.setText(beanList.get(i).getTitle());
                tv_content.setText(beanList.get(i).getContent());
                llBottom.addView(view);
            }
        }
    }
}
