package com.example.administrator.smartbj.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/11/15.
 */
public class NetCacheUtils {
    private LocalCacheUtils mlocalCacheUtils;
    private MemoryCacheUtils mMemoryCacheUtils;

    public NetCacheUtils(LocalCacheUtils localCacheUtils, MemoryCacheUtils memoryCacheUtils) {
        mlocalCacheUtils = localCacheUtils;
        mMemoryCacheUtils = memoryCacheUtils;
    }

    public void getBitmapFromNet(ImageView img, String url) {
        new BitmapTask().execute(img, url);
    }

    public void getImage(Bitmap bitmap, String url) {
        new GetBitmapTask().execute(bitmap, url);
    }

    class BitmapTask extends AsyncTask<Object, Void, Bitmap> {
        private ImageView img;
        private String url;

        @Override
        protected Bitmap doInBackground(Object... params) {
            img = (ImageView) params[0];
            url = (String) params[1];

            Bitmap downloadBitmap = getdownloadBitmap(url);
            return downloadBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                img.setImageBitmap(result);

//                if (img != null) {
//                    img.setImageBitmap(bitmap);
//                } else if (mbitmap != null) {
//                    mbitmap = bitmap;
//                    Log.i("cccccc", "从网络读取图片.......");
//                }

                mMemoryCacheUtils.setBitmapToMemory(url, result);
                mlocalCacheUtils.setBitmapeToLoacl(url, result);
                Log.i("cccccc", "从网络读取图片.......");
            }
        }
    }

    private Bitmap getdownloadBitmap(String url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();
            if (conn.getResponseCode() == 200) {
                InputStream ins = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(ins);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return null;
    }

    class GetBitmapTask extends AsyncTask<Object, Void, Bitmap> {
        private String url;
        private Bitmap mbitmap;

        @Override
        protected Bitmap doInBackground(Object... params) {
            mbitmap = (Bitmap) params[0];
            url = (String) params[1];
            Bitmap downloadBitmap = getdownloadBitmap(url);
            return downloadBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
//                img.setImageBitmap(bitmap);
//                Log.i("cccccc","从网络读取图片.......");
                mbitmap = bitmap;
                Log.i("cccccc", "从网络读取图片.......");

                mlocalCacheUtils.setBitmapeToLoacl(url, bitmap);
            }
        }
    }

}
