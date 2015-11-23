package com.example.administrator.smartbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2015/10/26.
 */
public class SharepreferencesUtil {

    private static String FIRE_NAME = "smartBJ";

    public static void putBoolean(Context context, String key, boolean val) {
        SharedPreferences preferences = context.getSharedPreferences(FIRE_NAME,Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key,val).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean val){
        SharedPreferences preferences = context.getSharedPreferences(FIRE_NAME,context.MODE_PRIVATE);
        return preferences.getBoolean(key,val);
    }
}
