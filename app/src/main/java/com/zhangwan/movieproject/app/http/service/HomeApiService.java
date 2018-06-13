package com.zhangwan.movieproject.app.http.service;




import com.zhangwan.movieproject.app.bean.ChannelBean;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.http.ApiBuild;
import com.zhangwan.movieproject.app.http.ApiResponseBean;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口
 * Created by zzg on 2018/4/11.
 */

public class HomeApiService {
    private static Service instance;

    public static Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(Service.class);
        }
        return instance;
    }


    public interface Service {


        //获首页
        @FormUrlEncoded
        @POST("app/Index/index")
        Observable<ApiResponseBean<HomeBean>> getHomeData(@Field("token") String token);

        //获发现
        @FormUrlEncoded
        @POST("app/Arctile/lists")
        Observable<ApiResponseBean<FindBean>> getData(@Field("token") String token,@Field("cid") String cid, @Field("word") String word, @Field("rem") String rem,@Field("page") int page);


        //获频道
        @FormUrlEncoded
        @POST("app/arctile/fanschannel")
        Observable<ApiResponseBean<List<ChannelBean>>> getChannelData(@Field("token") String token);

        //获取手机验证码
        @FormUrlEncoded
        @POST("app/start/getmobilecode")
        Observable<ApiResponseBean<Object>> getMobilecode(@Field("tel") String tel);

        //注册
        @FormUrlEncoded
        @POST("app/start/register")
        Observable<ApiResponseBean<Object>> register(@Field("tel") String tel,@Field("passwd") String passwd,@Field("codetoken") String codetoken,@Field("yzm")  String yzm);

        //登录
        @FormUrlEncoded
        @POST("app/start/login")
        Observable<ApiResponseBean<Object>> login(@Field("tel") String tel,@Field("passwd") String passwd);


        //微信
        @FormUrlEncoded
        @POST("app/start/wxlogin")
        Observable<ApiResponseBean<Object>> weiChatlogin(@Field("code") String code);


        //qq
        @FormUrlEncoded
        @POST("app/start/qqloginanzh")
        Observable<ApiResponseBean<Object>> qqlogin(@FieldMap Map<String,String> map);

        //重置密码
        @FormUrlEncoded
        @POST("app/start/reset")
        Observable<ApiResponseBean<Object>> resetPassWord(@Field("tel") String tel,@Field("passwd") String passwd,@Field("codetoken") String codetoken,@Field("yzm")  String yzm);

        //重置密码时所需的验证码
        @FormUrlEncoded
        @POST("app/start/resetmobilecode")
        Observable<ApiResponseBean<Object>> resetmobilecode(@Field("tel") String tel);

        //临时登录
        @POST("app/start/tmplogin")
        Observable<ApiResponseBean<Object>> tempLogin();

    }

}
