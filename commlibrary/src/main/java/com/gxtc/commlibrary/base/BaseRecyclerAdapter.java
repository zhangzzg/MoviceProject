package com.gxtc.commlibrary.base;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.recyclerview.base.IRecyclerViewAdapter;

import java.util.List;


/**
 * 抽象recyclerView 适配器基类
 *
 * @param <T>
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        IRecyclerViewAdapter<T> {

    protected Context                     context;
    private   int                         itemLayoutId;
    private   List<T>                     list;
    private   OnItemClickLisntener        mItemClickLisntener;
    private   OnReItemOnClickListener     itemListener;
    private   OnReItemOnLongClickListener longListener;
    private   RecyclerView                mRecyclerView;

    public BaseRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.itemLayoutId = itemLayoutId;
    }

    /**
     * 将数据与view视图绑定
     */
    public abstract void bindData(ViewHolder holder, int position, T t);

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewholder = (BaseRecyclerAdapter.ViewHolder) holder;
        viewholder.getItemView().setTag(position);
        bindData(viewholder, position, list.get(position));

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup arg0, int viewType) {
        View view = LayoutInflater.from(context).inflate(itemLayoutId, arg0, false);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longListener != null && mRecyclerView != null) {
                    longListener.onItemLongClick(v, (Integer) v.getTag());
                }
                return true;
            }
        });
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemListener != null && mRecyclerView != null) {
                    itemListener.onItemClick(v, (Integer) v.getTag());
                }

                if (mItemClickLisntener != null && mRecyclerView != null) {
                    mItemClickLisntener.onItemClick(mRecyclerView, v, (Integer) v.getTag());
                }
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public void changeDatas(List<T> datas) {
        if (list != null && list.size() > 0) {
            list.clear();
            list.addAll(datas);
        } else {
            list = datas;
        }
        notifyDataSetChanged();
    }

    /**
     * 普通视图的viewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ArrayMap<Integer, View> viewMap;
        private View                    itemView;
        private Object                  mObject;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            viewMap = new ArrayMap<Integer, View>();
        }

        public View getView(int viewId) {
            View view = viewMap.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                ConfigView(view, viewId);       //对view进行配置
                viewMap.put(viewId, view);
            }

            return view;
        }

        public ViewHolder setDataTag(Object tag) {
            mObject = tag;
            return this;
        }

        public Object getDataTag() {
            return mObject;
        }


        public ImageView getImageView(int viewid) {
            return (ImageView) getView(viewid);
        }

        /**
         * 查找view  用这个方法不需要强转  内部已经帮转好了。
         *
         * @param viewId
         * @param <T>    view的子类
         * @return 查找到的view
         */
        public <T extends View> T getViewV2(int viewId) {
            View view = viewMap.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                viewMap.put(viewId, view);
            }
            return (T) view;
        }


        /**
         * 设置文字
         *
         * @param viewId 要设置文字的 ImageView ID
         * @param str    文字的资源ID
         * @return
         */
        public ViewHolder setText(int viewId, String str) {
            ((TextView) getViewV2(viewId)).setText(str);
            return this;
        }

        /**
         * 设置图片
         *
         * @param viewId 要设置图片的 ImageView ID
         * @param resId  图片的资源ID
         * @return
         */
        public ViewHolder setImage(int viewId, int resId) {
            ((ImageView) getViewV2(viewId)).setImageResource(resId);
            return this;
        }

        /**
         * 设置点击事件
         *
         * @param viewId 要设置的控件的id
         * @param l      点击事件
         * @return
         */
        public ViewHolder setOnClick(int viewId, OnClickListener l) {
            getView(viewId).setOnClickListener(l);
            return this;
        }


        public ViewHolder setOnLongClick(int viewId,View.OnLongClickListener l){
            getView(viewId).setOnLongClickListener(l);
            return this;
        }

        public View getItemView() {
            return itemView;
        }

    }


    /**
     * listener
     */
    public interface OnReItemOnClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnReItemOnLongClickListener {
        void onItemLongClick(View v, int position);
    }

    public interface OnItemClickLisntener{
        void onItemClick(RecyclerView parentView, View v, int position);
    }


    public List<T> getList() {
        return list;
    }

    public void setOnReItemOnClickListener(OnReItemOnClickListener listener) {
        this.itemListener = listener;
    }

    public void setOnReItemOnLongClickListener(OnReItemOnLongClickListener longListener) {
        this.longListener = longListener;
    }

    public void setOnItemClickLisntener(OnItemClickLisntener itemClickLisntener) {
        mItemClickLisntener = itemClickLisntener;
    }

    public Context getContext() {
        return context;
    }

    public void addData(int position, T t) {
        list.add(position, t);
        notifyItemInserted(position);
    }

    public void addData(T t) {
        list.add(t);
        notifyItemInserted(list.size() - 1);
    }


    //如果需要对控件修改的话，在这个方法进行，在bindView里面进行影响效率
    public void ConfigView(View view, int resId){

    }


    /**
     * 带动画的删除
     *
     * @param position
     */
    @Override
    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, list.size() - position);
    }



    /**
     * 加载更多数据使用这个方法
     */
    @Override
    public void changeData(List<T> t) {
        if (t != null) {
            list.addAll(t);
            notifyDataSetChanged();
        }
    }


    /**
     * 下拉刷新使用这个方法
     */

    @Override
    public void notifyChangeData(List<T> t) {
        if (t != null) {
            list.clear();
            list.addAll(t);
            notifyDataSetChanged();
        }
    }

    @Override
    public void changeData(List<T> t, int position) {
        if (t != null && list != null && position <= list.size() && position != -1) {
            list.addAll(position, t);
            notifyDataSetChanged();
        }
    }
}
