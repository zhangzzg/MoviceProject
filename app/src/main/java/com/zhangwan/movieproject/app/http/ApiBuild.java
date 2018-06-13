package com.zhangwan.movieproject.app.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.zhangwan.movieproject.app.bean.Constant;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiBuild {

    public static Retrofit retrofit;
    public static Retrofit baseRetrofit;

    public final static int CONNECT_TIMEOUT = 3000;//连接超时时间
    public final static int READ_TIMEOUT = 3000;//读取超时时间
    public final static int WRITE_TIMEOUT = 3000;//写的超时时间
    public static String Tag = "ApiBuild";

    private ApiBuild() {

    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (ApiBuild.class) {
                if (retrofit == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .addNetworkInterceptor(new StethoInterceptor())
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constant.Url.BASE_URL)
                            .client(client)
//                            .addConverterFactory(FastJsonConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }




    public static Retrofit getBaseRetrofit() {
        if (baseRetrofit == null) {
            baseRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.Url.BASE_URL)
                    .build();
        }
        return baseRetrofit;
    }

}
