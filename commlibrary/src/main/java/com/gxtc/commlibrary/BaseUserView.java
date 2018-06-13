package com.gxtc.commlibrary;

/**
 * Created by Steven on 16/12/19.
 */

public interface BaseUserView<T> extends BaseUiView<T> {

    /**
     * token 过期回调
     */
    void tokenOverdue();
}
