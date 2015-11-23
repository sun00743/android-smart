package com.example.administrator.smartbj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.smartbj.utils.SetStatusBar;
import com.example.administrator.smartbj.utils.bitmap.MyBitmapUtil;

public class ImageGroupActivity extends Activity {
    private MyBitmapUtil myBitmapUtil;
    private String IMGURL = "http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201511/14133801zqhdirwt.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_group);
        new SetStatusBar().setStatusBar(this);

        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        myBitmapUtil = new MyBitmapUtil();
        myBitmapUtil.display(imageView, IMGURL);
//        imageView.setImageBitmap(myBitmapUtil.getImage(IMGURL,this));

    }
}
