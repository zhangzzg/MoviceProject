package com.zhangwan.movieproject.app.http;

import com.google.gson.Gson;
import com.gxtc.commlibrary.utils.GsonUtil;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by laoshiren on 2018/3/21.
 * 解决服务器同一字段返回不同类型数据问题
 */

public class CustomGsonResponseBodyConverter <T> implements Converter<ResponseBody,T>{
    private Gson gson;
    private Type type;

    public CustomGsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {
            ApiResponseBean baseBean = gson.fromJson(response,ApiResponseBean.class);
            if (!"200".equals(baseBean.getCode())) {
                throw new DataResultException(baseBean.getMsg(),baseBean.getCode());
            }
            return gson.fromJson(GsonUtil.objectToJson(response),type);
        }finally {
            value.close();
        }
    }


}
