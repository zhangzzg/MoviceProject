package com.zhangwan.movieproject.app.ui.find;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.bean.HomeBean;

import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public class FindContract {
    interface View extends BaseUiView<Presenter>{
        public void showChannelData(List<ChannelBean> data);
        public void showData( FindBean data);
    }
    interface Presenter extends BasePresenter {
        public void getData(String token,String pid, String word, String rem,int page);
        public void getChannelData(String token);
    }
}
