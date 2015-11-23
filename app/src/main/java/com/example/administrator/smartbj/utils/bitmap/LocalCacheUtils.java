package com.example.administrator.smartbj.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.smartbj.utils.MD5Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2015/11/16.
 */
public class LocalCacheUtils {

    public LocalCacheUtils(){

    }

    public static final String CACHEPATH = Environment.getExternalStorageDirectory()
            .getPath() + "/Smart_Cache";

    public Bitmap getBitmapFromLocal(String url){

        try {
            String fileName = MD5Encoder.encode(url);
            File file = new File(CACHEPATH,fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setBitmapeToLoacl(String url, Bitmap bitmap) {

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                String fileName = MD5Encoder.encode(url);
                File file = new File(CACHEPATH, fileName);
                File fileParent = file.getParentFile(); // 获取文件的父路径
                //判断，如果父路径不存在，那么创建一个路径
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                    Log.i("cccccc", "" + CACHEPATH);
                }

                Log.i("cccccc", "" + fileParent);
                // 调用compress方法将bitmap保存到本地
                /**
                 * CompressFormat:bitmap保存的图片格式
                 * quality:图片的压缩程度 0-100
                 * FileOutputStream:文件输出流
                 */
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));

                Log.i("cccccc", "图片保存到SD卡.......");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
