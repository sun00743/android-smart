package com.example.administrator.smartbj.utils.bitmap;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2015/11/17.
 */
public class MemoryCacheUtils {

//    private HashMap<String, Bitmap> mMemoryCache = new HashMap<>();
    private LruCache<String,Bitmap> mLruCache;
    public MemoryCacheUtils(){
        // 获的当前运行内存的1/8来作为lrucache的最大内存
        long maxsize = Runtime.getRuntime().maxMemory()/8;
        //lrucache需要设定一个最大内存值
        mLruCache = new LruCache<String,Bitmap>((int) maxsize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int bytecount = value.getByteCount();
                return bytecount;
            }
        };
    }

    /**\
     * 从内存中读
     * @param url
     */
    public Bitmap getBitmapFromMemory(String url){
//        return mMemoryCache.get(url);
        return mLruCache.get(url);
    }

    /**
     * 写内存
     * @param url
     * @param bitmap
     */
    public void setBitmapToMemory(String url, Bitmap bitmap){
        mLruCache.put(url,bitmap);
    }

}
