package org.turings.turings.near.Location;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    private RelativeLayout rl_lyh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);

        ivPortrait_lyh = findViewById(R.id.ivPortrait_lyh);
        tvFollowName_lyh = findViewById(R.id.tvFollowName_lyh);
        rl_lyh = findViewById(R.id.rl_lyh);

        Intent intent1 = getIntent();
        String lat1 = intent1.getStringExtra("lat");
        String lng1 = intent1.getStringExtra("lng");
        String portrait = intent1.getStringExtra("portrait");
        String name = intent1.getStringExtra("name");
        String top = intent1.getStringExtra("top");

        int id = getResources().getIdentifier(portrait, "mipmap", getApplicationContext().getPackageName());
        ivPortrait_lyh.setImageResource(id);
        tvFollowName_lyh.setText(name);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                600);
        WindowManager manager = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        int height2=dm.heightPixels;

        lp.setMargins(0, Integer.parseInt(top),0,height2-Integer.parseInt(top)-600);
        rl_lyh.setLayoutParams(lp);

        //动画效果
        ValueAnimator anim = ValueAnimator.ofInt(0,-Integer.parseInt(top)+150);
    // 设置动画运行的时长
        anim.setDuration(500);
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
                    }
                });
        anim.start(); // 启动动画


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MiddleActivity.this, InformationActivity.class);
                intent.setAction("lyhToInfo");
                intent.putExtra("lat",lat1);
                intent.putExtra("lng",lng1);
                startActivity(intent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 500);//3秒后执行TimeTask的run方法

    }
}
