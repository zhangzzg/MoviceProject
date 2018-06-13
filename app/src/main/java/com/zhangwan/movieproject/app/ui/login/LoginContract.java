package com.zhangwan.movieproject.app.ui.login;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;

import java.util.Map;

/**
 * Created by zzg on 2018/4/12.
 */

public class LoginContract {
    interface View extends BaseUiView<Presenter> {
      public void showLoginDate(Object datas);
      public void showWeChatDate(Object datas);
      public void showQQDate(Object datas);
      public void showTempLoginResulte(Object datas);
    }

    interface Presenter extends BasePresenter{
      //登录接口
      public void login(String phone, String passwd);
      //微信登录
      public void weChatLogin(String code);
      //QQ登录
      public void qqLogin(Map<String,String> map);
      //临时登录
      public void tempLogin();
    }
}
