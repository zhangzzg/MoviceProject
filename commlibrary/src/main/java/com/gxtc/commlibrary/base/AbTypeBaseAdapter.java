package com.gxtc.commlibrary.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 多布局自定义适配器封装 -- 要求用这个适配器的实体类（T）必须有一个type的字段
 */
public abstract class AbTypeBaseAdapter<T> extends BaseAdapter {

    private List<T> datas;
    private Context context;
    private int[] itemLayoutId;

    public AbTypeBaseAdapter(Context context, List<T> datas, int... itemLayoutId) {
        this.context = context;
        this.datas = new ArrayList<T>();
        this.datas.addAll(datas);
        this.itemLayoutId = itemLayoutId;
    }

    /*
     * 返回itemType
     */
    public abstract int getItemType(int position);

    /*
     * 填充数据
     */
    public abstract void bindData(ViewHolder holder, int type, T t);


    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? null : datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getViewTypeCount() {
        return itemLayoutId.length;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int type = getItemViewType(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(itemLayoutId[type], parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bindData(holder, type, datas.get(position));
        return convertView;
    }


    protected class ViewHolder {
        private SparseArray<View> viewMap;
        private View itemView;

        public ViewHolder(View view) {
            this.itemView = view;
            viewMap = new SparseArray<View>();
        }

        public View getView(int viewId) {
            View view = viewMap.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                viewMap.put(viewId, view);
            }
            return view;
        }

        public ViewHolder setText(int viewId, String text) {
            TextView tv = (TextView) getView(viewId);
            tv.setText(text);
            return this;
        }


        public ImageView getImageView(int viewId) {
            ImageView tv = (ImageView) getView(viewId);

            return tv;
        }

        public View getItemView() {
            return itemView;
        }

    }

    /**
     * 刷新数据源
     *
     * @param datas
     */
    public void notifyDataSetChanged(List<T> datas) {
        if (datas == null) return;

        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 加载更多数据源
     */
    public void changeDatas(List<T> datas) {
        if (datas == null) return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }


    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
