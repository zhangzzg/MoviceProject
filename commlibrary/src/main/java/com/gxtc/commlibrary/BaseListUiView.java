package com.gxtc.commlibrary;

import java.util.List;

/**
 * Created by Steven on 17/5/5.
 */

public interface BaseListUiView<T> {

    void showRefreshData(List<T> datas);
    void showLoadMoreData(List<T> datas);
    void showNoLoadMore();
}
