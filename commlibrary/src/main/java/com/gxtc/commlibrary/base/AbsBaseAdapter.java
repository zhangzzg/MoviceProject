package com.gxtc.commlibrary.base;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 把适配器进行封装，体现一种面向对象的思想,要弄懂弄透
 *
 * @param <T> 每次重写适配器都要重写四个方法，而且三个方法是没用的，都是重复的动作，所以可以把这个重复的动作进行封装,把三个没用的方法封装在一起，
 *            然后下车写适配器的时候就直接继承这个方法然后就重写一个方法就可以了
 *            <p/>
 *            所有操作数据源的操作都放到适配器来操作
 * @author Administrator
 */
public abstract class AbsBaseAdapter<T> extends BaseAdapter {

    private Context context;
    private int itemLayoutId;
    private List<T> datas;	//数据源

    public AbsBaseAdapter(Context context, List<T> datas, int itemLayoutId){
        this.context = context;
        this.datas = datas;
        this.itemLayoutId = itemLayoutId;
    }

    /**
     * 填充数据
     * @param holder 装有View控件的holder
     * @param t	对应position的数据对象
     */
    public abstract void bindData(ViewHolder holder, T t, int position);


    /**
     * 更新数据源
     */
    public void notifyChangeData(List<T> datas){
        if(datas == null)	return ;

        this.datas = datas;
        notifyDataSetChanged();
    }


    /**
     * 加载更多数据
     */
    public void changeData(List<T> data){
        if(data == null)  return ;

        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public void insetData(T t){
        if(t == null)	return ;

        datas.add(t);
        notifyDataSetChanged();
    }

    public List<T> getDatas(){
        return datas;
    }


    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas == null ? 0 :datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(itemLayoutId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder,datas.get(position),position);
        return convertView;
    }



    public class ViewHolder{

        private ArrayMap<Integer, View> viewMap;
        private View                    itemView;

        public ViewHolder(View rootView){
            viewMap = new ArrayMap<Integer, View>();
            this.itemView = rootView;
        }

        /*
         * 从集合中获取view，没有则重新findId
         */
        public View getView(int viewId){
            View view = viewMap.get(viewId);
            if(view == null){
                view = itemView.findViewById(viewId);
                viewMap.put(viewId, view);
            }
            return view;
        }

        //返回所有的Item
        public List<View> getAllView(){
            ArrayList<View> views = new ArrayList<View>();
            Iterator<Integer> it = viewMap.keySet().iterator();
            while(it.hasNext()){
                int id = it.next();
                views.add(viewMap.get(id));
            }
            return views;
        }

        public View getItemView(){
            return itemView;
        }


    }


    /**
     * 局部更新数据，调用一次getView()方法；Google推荐的做法
     *
     * @param listView 要更新的listview
     * @param position 要更新的位置
     */
    public void refrshItemView(ListView listView, int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = listView.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = listView.getChildAt(position - firstVisiblePosition);
            getView(position, view, listView);
        }

    }

}
