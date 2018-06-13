package com.zhangwan.movieproject.app.ui.find;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiCallBack;

import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public interface FindSource extends BaseSource {
    public void getData(String token,String pid, String word, String rem,int page,ApiCallBack<FindBean> callBack);
    public void getChannelData (String token,ApiCallBack<List<ChannelBean>> callBack);
}
