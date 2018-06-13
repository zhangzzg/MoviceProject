package com.zhangwan.movieproject.app.ui.register;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.gxtc.commlibrary.BaseView;

import java.io.Serializable;

/**
 * Created by zzg on 2018/4/12.
 */

public class RegisterContract {
   public interface View extends BaseUiView<Presenter> {
      public void showRegisterDate(Object datas);
      public void showMobilecode(Object datas);
      public void showTempLoginResulte(Object datas);
    }

    public interface Presenter extends BasePresenter{
      //注册接口
      public void register(String phone,String passwd,String codetoken,String yzm);
      //获取验证码
      public void getMobilecode(String phone);
      //临时登录
      public void tempLogin();
    }
}
