package com.example.administrator.smartbj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.smartbj.utils.AddSildingMenu;
import com.example.administrator.smartbj.utils.SetStatusBar;
import com.example.administrator.smartbj.utils.bitmap.MyBitmapUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageGroupActivity extends AppCompatActivity {

    @Bind(R.id.ig_toolbar)
    Toolbar toolbar;
    @Bind(R.id.rec_ig)
    RecyclerView recIg;

    private MyBitmapUtil myBitmapUtil;
    private String IMGURL[] = {"http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201511/14133801zqhdirwt.jpg"};
    private IGAdapter recAdapter = new IGAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_group);
        ButterKnife.bind(this);
        new SetStatusBar().setStatusBar(this);
        setSupportActionBar(toolbar);
        AddSildingMenu.add(ImageGroupActivity.this, toolbar);

        toolbar.setTitle("组图");
        recIg.setItemAnimator(new DefaultItemAnimator());
        recIg.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recIg.setAdapter(recAdapter);

        ImageView imageView = (ImageView) findViewById(R.id.imageView4);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        myBitmapUtil = new MyBitmapUtil();
        myBitmapUtil.display(imageView, IMGURL[0]);
//        imageView.setImageBitmap(myBitmapUtil.getImage(IMGURL,this));

    }

    class IGAdapter extends RecyclerView.Adapter<IGAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ImageGroupActivity.this).inflate(R.layout.item_imagegroup
                    , parent, false);
            view.setPadding(0,0,0,50);
            parent.removeView(view);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            myBitmapUtil.display(holder.image, IMGURL[0]);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView img_text;
            TextView img_title;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image_item_imagegroup);
            }
        }
    }

}
