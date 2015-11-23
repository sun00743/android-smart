package com.example.administrator.smartbj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.administrator.smartbj.utils.SharepreferencesUtil;

public class SplashActiivity extends Activity {

    //    private RelativeLayout rlRoot;
    private ImageView imgview;
    private boolean isfist = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        imgview = (ImageView) findViewById(R.id.imageView);
        startAnim();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                SharedPreferences preferences = getSharedPreferences("smartBJ", MODE_PRIVATE);
//                isfist = preferences.getBoolean("isfirst",true);

                isfist = SharepreferencesUtil.getBoolean(SplashActiivity.this, "isfirst", true);

                if (isfist) {
                    Intent intent = new Intent(SplashActiivity.this, GudieActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActiivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 2000);

    }

    private void startAnim() {

        //动画集合
        AnimationSet animationSet = new AnimationSet(false);

        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setFillAfter(true);  //保持动画结束时的状态

        //缩放动画
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setFillAfter(true);

        //透明动画
        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setFillAfter(true);

        animationSet.setDuration(500); //动画时间
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(alpha);

        imgview.startAnimation(animationSet);
    }
}
