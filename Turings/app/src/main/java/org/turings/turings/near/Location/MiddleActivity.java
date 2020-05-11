package org.turings.turings.near.Location;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Fade;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.near.entity.RoundImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MiddleActivity extends AppCompatActivity {

    private RoundImageView ivPortrait_lyh;
    private TextView tvFollowName_lyh;
    private RoundImageView ivPortrait_lyh1;
    private TextView tvFollowName_lyh1;
    private RelativeLayout rl_lyh;
    private RelativeLayout r2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Fade fade = new Fade();
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        getWindow().setReenterTransition(fade);
        getWindow().setReturnTransition(fade);

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(1000);
        ChangeClipBounds changeClipBounds = new ChangeClipBounds();
        changeClipBounds.setDuration(1000);
        ChangeImageTransform changeImageTransform = new ChangeImageTransform();
        changeImageTransform.setDuration(1000);
        ChangeTransform changeTransform = new ChangeTransform();
        changeTransform.setDuration(1000);
        getWindow().setSharedElementEnterTransition(changeBounds);
        getWindow().setSharedElementExitTransition(changeClipBounds);
        getWindow().setSharedElementReenterTransition(changeImageTransform);
        getWindow().setSharedElementReturnTransition(changeTransform);
        getWindow().setSharedElementsUseOverlay(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);

        ivPortrait_lyh = findViewById(R.id.ivPortrait_lyh);
        tvFollowName_lyh = findViewById(R.id.tvFollowName_lyh);
        ivPortrait_lyh1 = findViewById(R.id.ivPortrait_lyh1);
        tvFollowName_lyh1 = findViewById(R.id.tvFollowName_lyh1);
        rl_lyh = findViewById(R.id.rl_lyh);
        r2 = findViewById(R.id.r2);

        Intent intent1 = getIntent();
        String lat1 = intent1.getStringExtra("lat");
        String lng1 = intent1.getStringExtra("lng");
        String portrait = intent1.getStringExtra("portrait");
        String name = intent1.getStringExtra("name");
        String top = intent1.getStringExtra("top");

        int id = getResources().getIdentifier(portrait, "mipmap", getApplicationContext().getPackageName());
        ivPortrait_lyh.setImageResource(id);
        tvFollowName_lyh.setText(name);
        ivPortrait_lyh1.setImageResource(id);
        tvFollowName_lyh1.setText(name);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                800);
        WindowManager manager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int height2=dm.heightPixels;

        lp.setMargins(0, Integer.parseInt(top),0,height2-Integer.parseInt(top)-800);
        rl_lyh.setLayoutParams(lp);
        r2.setLayoutParams(lp);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);//休眠
                    Intent intent = new Intent(MiddleActivity.this, InformationActivity.class);
                    intent.setAction("lyhToInfo");
                    intent.putExtra("lat",lat1);
                    intent.putExtra("lng",lng1);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //动画效果
        ValueAnimator anim = ValueAnimator.ofInt(0,-Integer.parseInt(top)+150);
    // 设置动画运行的时长
        anim.setDuration(900);
    // 设置动画延迟播放时间
        anim.setStartDelay(10);
    // 设置动画重复播放次数 = 重放次数 + 1
    // 动画播放次数 = ValueAnimator.INFINITE时，动画无限重复
        anim.setRepeatCount(0);
    // 设置重复播放动画模式
    // ValueAnimator.RESTART(默认):正序重放
    // ValueAnimator.REVERSE:倒序回放
        anim.setRepeatMode(ValueAnimator.RESTART);

        anim.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(
                            ValueAnimator animation) {
                        // 获得改变后的值，并对相应控件进行设置
                        int currentValue = (Integer) animation
                                .getAnimatedValue();
                        rl_lyh.setTranslationY(currentValue);
                        r2.setTranslationY(currentValue);
                    }
                });
        anim.start(); // 启动动画

        rl_lyh.post(new Runnable() {
            @Override
            public void run() {
                final int width = rl_lyh.getMeasuredWidth();
                final int height = rl_lyh.getMeasuredHeight();
                final float radius = (float)Math.sqrt(width*width + height*height) / 2;//半径
                Animator animator;
                if (rl_lyh.getVisibility() == View.VISIBLE){
                    animator = ViewAnimationUtils.createCircularReveal(rl_lyh, width/2, height/2, radius, 0);
                    animator.setDuration(900);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            rl_lyh.setVisibility(View.GONE);
                        }
                    });
                }else {
                    animator = ViewAnimationUtils.createCircularReveal(rl_lyh, width / 2, height / 2, 0, radius);
                    animator.setDuration(900);
                    animator.setInterpolator(new AccelerateDecelerateInterpolator());
                    rl_lyh.setVisibility(View.VISIBLE);
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            rl_lyh.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });



//        ObjectAnimator animator = ObjectAnimator
//                .ofFloat(rl_lyh, "alpha",
//                        1f, 0);
//        animator.setDuration(900);
//        animator.start();





    }
}
