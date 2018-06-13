package com.zhangwan.movieproject.app.ui.login.change;

import com.gxtc.commlibrary.BasePresenter;
import com.gxtc.commlibrary.BaseUiView;
import com.zhangwan.movieproject.app.http.ApiCallBack;

import java.util.Map;

/**
 * Created by zzg on 2018/4/12.
 */

public class ChangeContract {
    public interface View extends BaseUiView<Presenter> {
      public void showResult(Object datas);
      public void showMobilecode(Object datas);
    }

    public interface Presenter extends BasePresenter{
      //重置密码
     public void resetPassWord(String phone,String passwd,String codetoken,String yzm);
     //获取验证码(跟注册时获取验证码的接口不一样)
     public void getMobilecode(String phone);
    }
}
