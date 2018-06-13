package com.gxtc.commlibrary.recyclerview.base;

import java.util.List;

/**
 * Created by Gubr on 2017/6/5.
 */

public interface IRecyclerViewAdapter<T> {
    void changeData(List<T> datas);
    void changeData(List<T> datas, int position);
    void notifyChangeData(List<T> datas);
    void removeData(int position);
}
