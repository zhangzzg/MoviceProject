package com.zhangwan.movieproject.app.ui.register;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiCallBack;

/**
 * Created by zzg on 2018/4/12.
 */

public interface RegsterSource extends BaseSource {
    public void register(String phone,String passwd,String codetoken,String yzm,ApiCallBack<Object> callBack);
    public void getMobilecode(String phone,ApiCallBack<Object> callBack);
}
