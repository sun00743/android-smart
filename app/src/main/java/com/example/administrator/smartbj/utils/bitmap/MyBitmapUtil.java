package com.example.administrator.smartbj.utils.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.example.administrator.smartbj.R;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MyBitmapUtil {
    NetCacheUtils netCacheUtils;
    LocalCacheUtils localCacheUtils;
    MemoryCacheUtils memoryCacheUtils;

    public MyBitmapUtil(){
        memoryCacheUtils = new MemoryCacheUtils();
        localCacheUtils = new LocalCacheUtils();
        netCacheUtils = new NetCacheUtils(localCacheUtils,memoryCacheUtils);
    }

    public void display(ImageView img,String url){
        Bitmap bitmap = null;
        img.setImageResource(R.drawable.defult);

        bitmap = memoryCacheUtils.getBitmapFromMemory(url);
        if(bitmap != null){
            img.setImageBitmap(bitmap);
            Log.i("ccccccc","从内存读取图片.......");
            return;
        }

        bitmap =  localCacheUtils.getBitmapFromLocal(url);
        if(bitmap != null){
            img.setImageBitmap(bitmap);
            Log.i("cccccc", "从本地读取图片.......");
            memoryCacheUtils.setBitmapToMemory(url, bitmap); //将图片保存在内存
            return;
        }
        netCacheUtils.getBitmapFromNet(img,url);
    }

    public Bitmap getImage(String url,Context context){
        Bitmap bitmap = null;
        netCacheUtils.getImage(bitmap, url);
        return bitmap;
    }
}

