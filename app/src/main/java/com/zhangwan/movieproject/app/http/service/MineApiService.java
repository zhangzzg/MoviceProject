package com.zhangwan.movieproject.app.http.service;




import com.zhangwan.movieproject.app.bean.TaskBean;
import com.zhangwan.movieproject.app.bean.UseInfoBean;
import com.zhangwan.movieproject.app.http.ApiBuild;
import com.zhangwan.movieproject.app.http.ApiResponseBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 接口
 * Created by zzg on 2018/4/11.
 */

public class MineApiService {
    private static Service instance;

    public static Service getInstance() {
        if (instance == null) {
            instance = ApiBuild.getRetrofit().create(Service.class);
        }
        return instance;
    }


    public interface Service {
        //获取用户信息
        @FormUrlEncoded
        @POST("app/Fans/getUserInfo")
        Observable<ApiResponseBean<UseInfoBean>> getUserInfo(@Field("token") String token);


        //获取任务列表
        @FormUrlEncoded
        @POST("app/Rask/lists")
        Observable<ApiResponseBean<TaskBean>> getTask(@Field("token") String token);

        //修改用户信息
        @FormUrlEncoded
        @POST("app/Fans/updateUserInfo")
        Observable<ApiResponseBean<Object>> updateUserInfo(@Field("token") String token,@Field("nickname") String nickname,@Field("face") String face);

    }

}
