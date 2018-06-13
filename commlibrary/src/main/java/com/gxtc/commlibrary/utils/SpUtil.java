package com.gxtc.commlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by 宋家任 on 2016/9/23.
 * SharedPreferences工具类
 */

public class SpUtil {
    private static final String spFileName = "app";


    public static String getString(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, "");
        return result;
    }

    public static String getString(Context context, String strKey,
                                   String strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        String result = setPreferences.getString(strKey, strDefault);
        return result;
    }

    public static void putString(Context context, String strKey, String strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putString(strKey, strData);
        editor.commit();
    }

    public static Boolean getBoolean(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, true);
        return result;
    }

    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }


    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

    public static int getInt(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, -1);
        return result;
    }

    public static int getInt(Context context, String strKey, int strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        int result = setPreferences.getInt(strKey, strDefault);
        return result;
    }

    public static void putInt(Context context, String strKey, int strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putInt(strKey, strData);
        editor.commit();
    }

    public static long getLong(Context context, String strKey) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, -1);
        return result;
    }

    public static long getLong(Context context, String strKey, long strDefault) {
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        long result = setPreferences.getLong(strKey, strDefault);
        return result;
    }

    public static void putLong(Context context, String strKey, long strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putLong(strKey, strData);
        editor.commit();
    }

    public static void putSet(Context context, String strKey, Set<String> setData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putStringSet(strKey, setData);
        editor.commit();
    }


    public static void setToken(Context context, String token) {

        SpUtil.putString(context, "token", token);
    }

    public static String getToken(Context context) {
        return SpUtil.getString(context, "token");
    }

    public static void setGrade(Context context, String token) {

        SpUtil.putString(context, "grade", token);
    }

    public static String getGrade(Context context) {
        return SpUtil.getString(context, "grade");
    }

    public static void setLoginId(Context context, String loginId) {
        SpUtil.putString(context, "loginId", loginId);

    }

    public static String getLoginId(Context context) {
        return SpUtil.getString(context, "loginId");
    }

    public static void setUserName(Context context, String userName) {
        SpUtil.putString(context, "userName", userName);
    }

    public static String getUserName(Context context) {
        return SpUtil.getString(context, "userName");
    }

    public static void setUserPic(Context context, String url) {
        SpUtil.putString(context, "userPic", url);
    }

    public static String getUserPic(Context context) {
        return SpUtil.getString(context, "userPic");
    }

    public static void setUserVip(Context context, boolean isVip) {
        SpUtil.putBoolean(context, "userVip", isVip);
    }

    public static boolean getUserVip(Context context) {
        return SpUtil.getBoolean(context, "userVip");
    }

    public static void setUserBalacne(Context context, int userBalacne) {
        SpUtil.putInt(context, "userBalacne", userBalacne);
    }

    public static int getUserBalacne(Context context) {
        return SpUtil.getInt(context, "userBalacne");
    }

    public static void setIsLogin(Context context, boolean isLogin) {
        SpUtil.putBoolean(context, "isLogin", isLogin);
    }

    public static boolean getIsLogin(Context context) {
        return SpUtil.getBoolean(context, "isLogin", false);
    }

    public static void setUserId(Context context, String userId) {
        SpUtil.putString(context, "userId", userId);
    }

    public static String getUserId(Context context) {
        return SpUtil.getString(context, "userId");
    }

    public static void setUserSex(Context context, int userId) {
        SpUtil.putInt(context, "userSex", userId);
    }

    public static int getUserSex(Context context) {
        return SpUtil.getInt(context, "userSex", 0);
    }

    public static void setCity(Context context, String data) {
        SpUtil.putString(context, "city", data);
    }

    public static String getCity(Context context) {
        return SpUtil.getString(context, "city", "");
    }

    public static void setBirthDat(Context context, String data) {
        SpUtil.putString(context, "BirthDat", data);
    }

    public static String getBirthDat(Context context) {
        return SpUtil.getString(context, "BirthDat", "");
    }

    public static void setHeadPic(Context context, String data) {
        SpUtil.putString(context, "HeadPic", data);
    }

    public static String getHeadPic(Context context) {
        return SpUtil.getString(context, "HeadPic", "");
    }

    public static void setSearchKey(Context context, String str) {
        SpUtil.putString(context, "searchKey", str);
    }

    public static String getSearchKey(Context context) {
        return SpUtil.getString(context, "searchKey");
    }

    /**
     * 是否自动解锁下一章
     *
     * @param context
     * @param isCheck
     */
    public static void setIsCheckAutoNextChatper(Context context, boolean isCheck) {
        SpUtil.putBoolean(context, "isCheck", isCheck);
    }

    /**
     * 是否自动解锁下一章 默认先勾选
     *
     * @param context
     * @return
     */
    public static boolean getIsCheckAutoNextChatper(Context context) {
        return SpUtil.getBoolean(context, "isCheck");
    }

    public static void login(Context context, String loginId, String token, String userName, String userId) {
        SpUtil.setLoginId(context, loginId);
        SpUtil.setToken(context, token);
        SpUtil.setUserName(context, userName);
        SpUtil.setUserId(context, userId);


    }

    public static void exitSystemt(Context context) {
//        SpUtil.setLoginId(context, "");
        SpUtil.setToken(context, "");
//        SpUtil.setUserName(context, "");
//        SpUtil.setUserId(context, "");
//        SpUtil.setUserPic(context, "");
//        SpUtil.setUserVip(context, false);
//        SpUtil.setUserBalacne(context, 0);
//        SpUtil.setIsLogin(context, false);
//        SpUtil.putBoolean(context,"isOpen",false);
    }

    public static boolean remove(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(spFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return editor.commit();
    }
}
