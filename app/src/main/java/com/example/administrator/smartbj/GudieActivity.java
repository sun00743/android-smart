package com.example.administrator.smartbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.smartbj.utils.SharepreferencesUtil;

import java.util.ArrayList;

public class GudieActivity extends Activity {

    private final int[] imageids = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3
    };

    private int pointdis;
    private int btnleft;

    private ImageView pointOverlay;
    private RelativeLayout btnlay;
    private Button button;
    private ViewPager guidePager;
    private LinearLayout pointGroup;
    private ArrayList<View> imageList;
    private GuidePageAdapter adapter = new GuidePageAdapter();
    private int lastPoint;
    private RelativeLayout lastimage;
    private Button guideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudie);

        initView();
        addImage();
        initGuide();

    }

    private void initGuide() {

        pointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        pointGroup.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        pointdis = pointGroup.getChildAt(1).getLeft()
                                - pointGroup.getChildAt(0).getLeft();
                    }
                });

//        button.getViewTreeObserver().addOnGlobalLayoutListener(
//                new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        button.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        btnleft = btnlay.getLeft();
//                        Log.i("btnleft", "" + btnleft);
//                    }
//                });


        guidePager.setAdapter(adapter);

        guidePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.i("rrrssss",
                        "当前位置：" + position + "位移比例：" + positionOffset + "位移参数（像素）：" + positionOffsetPixels);

                RelativeLayout.LayoutParams params =
                        (RelativeLayout.LayoutParams) pointOverlay.getLayoutParams();
                params.leftMargin = (int) (pointdis * positionOffset) + pointdis * position;
                pointOverlay.setLayoutParams(params);

//                RelativeLayout.LayoutParams params1 =
//                        (RelativeLayout.LayoutParams) button.getLayoutParams();
//                if (position == 1) {
//                    params1.leftMargin = ((int) (1080 * (1 - positionOffset) - 1080 * (position - 1))) * 2;
//                    Log.i("rrrrrr", "" + params1.leftMargin);
//                    button.setLayoutParams(params1);
//                }
            }

            @Override
            public void onPageSelected(int position) {
                pointGroup.getChildAt(position).setEnabled(true);
                pointGroup.getChildAt(lastPoint).setEnabled(false);
                lastPoint = position;

//                if (position == 2) {
//                    button.setVisibility(View.INVISIBLE);
//                } else {
//                    button.setVisibility(View.INVISIBLE);
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        guideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SharedPreferences preferences = getSharedPreferences("smartBJ",MODE_PRIVATE);
//                preferences.edit().putBoolean("isfirst",false).commit();
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putBoolean("isfirst",false);
//                editor.commit();   //commit 方法不要忘

                SharepreferencesUtil.putBoolean(GudieActivity.this,"isfirst",false);

                Intent intent = new Intent(GudieActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        btnlay = (RelativeLayout) findViewById(R.id.btn_layout);
        guidePager = (ViewPager) findViewById(R.id.guide);
        pointGroup = (LinearLayout) findViewById(R.id.point);
        pointOverlay = (ImageView) findViewById(R.id.point_overlay);
        lastimage = (RelativeLayout) findViewById(R.id.guide_3);
    }

    public ArrayList<View> addImage() {
        imageList = new ArrayList<View>();
        for (int i = 0; i < imageids.length; i++) {

            View image = new View(GudieActivity.this);
            if(i == 2){
                View last = getLayoutInflater().inflate(R.layout.guide_3,null);
                guideButton = (Button) last.findViewById(R.id.gudiebutton);
                imageList.add(last);
            }else {
                image.setBackgroundResource(imageids[i]);
                imageList.add(image);
            }

            ImageView point = new ImageView(GudieActivity.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.rightMargin = 20;
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            pointGroup.addView(point);
        }
        return imageList;
    }

    class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageids.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageList.get(position));
            return imageList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

}
