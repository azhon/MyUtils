package com.azhong.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 * 文件名:    BaseRecyclerAdapter
 * 创建者:    ZSY
 * 创建时间:  2017/3/20 on 9:42
 * 描述:     TODO RecyclerView的封装适配器
 */
public abstract class BaseRecyclerAdapter<D, VH extends ViewHolder> extends RecyclerView.Adapter<VH>
        implements View.OnClickListener, View.OnLongClickListener {
    /**
     * 数据源
     */
    private List<D> data;
    /**
     * 布局资源id
     */
    private int layoutResId;
    /**
     * 头部布局id
     */
    private int headView = -1;
    /**
     * 底部部布局id
     */
    private int footView = -1;
    /**
     * 数据显示itemType
     */
    private final int ITEM_TYPE = 870371;
    /**
     * 头部itemType
     */
    private final int HEAD_TYPE = 870372;
    /**
     * 底部itemType
     */
    private final int FOOT_TYPE = 870373;
    /**
     * Item点击事件
     */
    private onItemClickListener clickListener;
    /**
     * Item长按事件
     */
    private onItemLongClickListener longListener;

    public BaseRecyclerAdapter(@LayoutRes int layoutResId, List<D> data) {
        this.data = data == null ? new ArrayList<D>() : data;
        if (layoutResId != 0) {
            this.layoutResId = layoutResId;
        } else {
            throw new NullPointerException("请设置Item资源id");
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case ITEM_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
                break;
            case HEAD_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(headView, parent, false);
                break;
            case FOOT_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(footView, parent, false);
                break;
        }
        return (VH) new BaseViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        int type = ITEM_TYPE;
        //如果添加了头部view
        if (position == 0 && headView != -1) {
            type = HEAD_TYPE;
        }
        //如果添加了底部view
        if (position == getItemCount() - 1 && footView != -1) {
            type = FOOT_TYPE;
        }
        return type;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_TYPE:
                //设置Item的点击事件
                holder.itemView.setOnClickListener(this);
                //设置Item的长按事件
                holder.itemView.setOnLongClickListener(this);
                if (headView == -1) {
                    holder.itemView.setTag(position);
                    bindTheData(holder, data.get(position), position);
                } else {
                    holder.itemView.setTag(position - 1);
                    bindTheData(holder, data.get(position - 1), position - 1);
                }
                break;
            case HEAD_TYPE:
                break;
            case FOOT_TYPE:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (headView != -1 && footView != -1) {
            return data.size() + 2;
        }
        if (headView != -1) {
            return data.size() + 1;
        }
        if (footView != -1) {
            return data.size() + 1;
        }
        return data.size();
    }

    /**
     * 清除数据
     */
    public void clearData() {
        if (data != null && data.size() > 0) {
            data.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     *
     * @param holder 视图管理者
     * @param data   数据源
     */
    protected abstract void bindTheData(VH holder, D data, int position);

    /**
     * 添加头部View
     */
    public void addHeadView(@LayoutRes int id) {
        this.headView = id;
    }

    /**
     * 添加底部View
     */
    public void addFootView(@LayoutRes int id) {
        this.footView = id;
    }

    @Override
    public void onClick(View v) {
        //点击回调
        if (clickListener != null) {
            clickListener.onItemClick((Integer) v.getTag(), v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        //长按回调
        return longListener != null && longListener.onItemLonClick((Integer) v.getTag(), v);
    }

    /*********************************************
     * 基础视图管理者
     *********************************************/
    public class BaseViewHolder extends ViewHolder {
        /**
         * 集合类，layout里包含的View,以view的id作为key，value是view对象
         */
        private SparseArray<View> mViews;

        public BaseViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        public <T extends View> T findViewById(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        /**
         * 设置文本资源
         *
         * @param viewId view id
         * @param s      字符
         */
        public TextView setText(int viewId, CharSequence s) {
            TextView view = findViewById(viewId);
            view.setText(s);
            return view;
        }

        /**
         * 设置图片资源
         *
         * @param viewId     view id
         * @param imageResId 图片资源id
         */
        public ImageView setImageResource(int viewId, @DrawableRes int imageResId) {
            ImageView view = findViewById(viewId);
            view.setImageResource(imageResId);
            return view;
        }


    }

    /**
     * 设置点击监听
     *
     * @param clickListener 监听器
     */
    public void setItemClickListener(onItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 设置长按监听
     *
     * @param longListener 监听器
     */
    public void setItemLongClickListener(onItemLongClickListener longListener) {
        this.longListener = longListener;
    }

    public interface onItemClickListener {
        void onItemClick(int position, View v);
    }

    public interface onItemLongClickListener {
        boolean onItemLonClick(int position, View v);
    }
}