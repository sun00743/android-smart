package com.example.administrator.smartbj.utils;

import android.content.Context;

/**
 * px和dp转换工具类
 * Created by Administrator on 2015/11/25.
 */
public class DensityUtils {

    public static int dp2px(Context ctx, float dp) {
        float density = ctx.getResources().getDisplayMetrics().density;
        int px = (int) ((dp * density) + 0.5f);
        return px;
    }

    public static float px2dp(Context ctx, int px){
        float density = ctx.getResources().getDisplayMetrics().density;
        float dp = px/density;
        return dp;
    }
}
