package com.example.will.utils.sharepreferencehelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class SharePreferenceHelper {

    private static SharedPreferences sharedPreferences;

    public static String LOGIN_KEY = "LOGIN";

    public static String USER_KEY = "USER";

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences("music_pre", Context.MODE_PRIVATE);
    }

    public static void putString(String key, String content) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, content);
        editor.commit();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static void removeString(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void putSerailObj(String key, Serializable serializable) {
        putString(key, JSON.toJSONString(serializable));
    }

    public static void removeSerialObj(String key) {
        removeString(key);
    }


    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setLoginStatus(boolean status) {
        putBoolean(LOGIN_KEY, status);
    }

    public static boolean getLoginStatus() {
        return sharedPreferences.getBoolean(LOGIN_KEY, false);
    }

    public static void setUserInfo(Serializable serializable) {
        putSerailObj(USER_KEY, serializable);
    }

    public static String getUserInfoSerial() {
        return SharePreferenceHelper.getString(USER_KEY);
    }




}
