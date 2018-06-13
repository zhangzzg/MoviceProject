package com.zhangwan.movieproject.app.ui.find;

import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiCallBack;
import com.zhangwan.movieproject.app.ui.home.HomeContract;
import com.zhangwan.movieproject.app.ui.home.HomeRespository;
import com.zhangwan.movieproject.app.ui.home.HomeSource;

import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public class FindPresenter implements FindContract.Presenter {
    FindContract.View mView;
    FindSource mSource;

    public FindPresenter(FindContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mSource = new FindRespository();
    }

    @Override
    public void getChannelData(String token) {
        mSource.getChannelData(token, new ApiCallBack<List<ChannelBean>>() {

            @Override
            public void onSuccess(List<ChannelBean> data) {
                mView.showLoadFinish();
                if(mView == null) return;
                if(data == null || data.size() == 0){
                    mView.showEmpty();
                    return;
                }
                mView.showChannelData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }

    @Override
    public void start() {}

    @Override
    public void destroy() {
        mSource.destroy();
    }

    @Override
    public void getData(String token,String pid, String word, String rem,int page) {
        mSource.getData(token,pid,word,rem,page, new ApiCallBack<FindBean>() {

            @Override
            public void onSuccess(FindBean data) {
                mView.showLoadFinish();
                if(mView == null) return;
                if(data == null){
                    mView.showEmpty();
                    return;
                }
                mView.showData(data);
            }

            @Override
            public void onError(String status, String message) {
                if(mView == null) return;
                mView.showError(status,message);
            }
        });
    }


}
