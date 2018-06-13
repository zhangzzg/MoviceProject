package com.zhangwan.movieproject.app.ui.login;

import com.gxtc.commlibrary.data.BaseSource;
import com.zhangwan.movieproject.app.http.ApiCallBack;

import java.util.Map;

/**
 * Created by zzg on 2018/4/12.
 */

public interface LoginSource extends BaseSource {
    public void login(String phone, String passwd, ApiCallBack<Object> callBack);
    //微信登录
    public void weChatLogin(String code,ApiCallBack<Object> callBack);
    //QQ登录
    public void qqLogin(Map<String,String> map,ApiCallBack<Object> callBack);
    //重置密码
    public void resetPassWord(String phone,String passwd,String codetoken,String yzm,ApiCallBack<Object> callBack);
    //获取验证码（仅适合忘记密码）
    public void getMobilecode(String phone,ApiCallBack<Object> callBack);
    //临时登录
    public void tempLogin(ApiCallBack<Object> callBack);
}
