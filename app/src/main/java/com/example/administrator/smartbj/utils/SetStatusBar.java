package com.example.administrator.smartbj.utils;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.example.administrator.smartbj.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 修改顶部通知栏工具类
 * Created by Administrator on 2015/11/16.
 */
public class SetStatusBar {


    /**
     * 修改顶部通知栏颜色
     */
    public void setStatusBar(Activity activity){

        //设置填充窗口(不充窗口)
        //在布局文件中 设置fitsSystemWindows = true;
//        activity.getWindow().getDecorView().findViewById(android.R.id.content)
//                .setFitsSystemWindows(true);
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
    }

    protected void setTranslucentStatus(Activity activity) {
        Window window = activity.getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
