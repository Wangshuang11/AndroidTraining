package org.turings.turings.myself.sxn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;
import org.turings.turings.near.Location.InformationActivity;


public class MyAchieveActivity extends AppCompatActivity {

    private Button back;
    private ImageView avatar;
    private Bitmap bitmap;
    private TextView name;
    private MyUrl myUrl;
    private int id;
    private ImageView qq1;
    private ImageView qq2;
    private ImageView qq3;
    private ImageView qq4;
    private ImageView qq5;
    private ImageView qq6;
    private MyLinstener linstener;
    private int status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);

        back=findViewById(R.id.sxn_achieve_back);
        avatar=findViewById(R.id.sxn_achieve_avatar);
        name=findViewById(R.id.sxn_achieve_name);
        qq1 = findViewById(R.id.qq1);
        qq2 = findViewById(R.id.qq2);
        qq3 = findViewById(R.id.qq3);
        qq4 = findViewById(R.id.qq4);
        qq5 = findViewById(R.id.qq5);
        qq6 = findViewById(R.id.qq6);
        linstener = new MyLinstener();
        qq1.setOnClickListener(linstener);
//        qq2.setOnClickListener(linstener);
//        qq3.setOnClickListener(linstener);
//        qq4.setOnClickListener(linstener);
//        qq5.setOnClickListener(linstener);
//        qq6.setOnClickListener(linstener);
        startShakeByView(qq1,1,1.1f,10,1000);
        circleAvatar();

        status = getApplicationContext().
                getSharedPreferences("achStatus", MODE_PRIVATE).getInt("status",0);
        if (status==2){
            qq1.setImageResource(R.mipmap.qq1);
            qq1.setClickable(false);
            qq1.clearAnimation();
        }

        Intent intent1=getIntent();
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("achStatus", Context.MODE_PRIVATE);
        if (sharedPref.getInt("status",1)==2){
            qq1.setImageResource(R.mipmap.qq1);
            qq1.clearAnimation();
            qq1.setClickable(false);
        }

        myUrl=new MyUrl(this);
        id= Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        if(intent1!=null){
            myUrl.sendToServerMyAch(getResources().getString(R.string.connUrl)+"/ReFreshMyInfomation?uid="+id,
                    R.layout.activity_achieve,avatar,name);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setClass(MyAchieveActivity.this, MainActivity.class);
                        intent.setAction("loginBackMyself");
                        startActivityForResult(intent,0);
                    }
                });
            }
        });

    }
    private void circleAvatar(){
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.girl);
        RequestOptions requestOptions=new RequestOptions().circleCrop();
        Glide.with(this).load(new BitmapDrawable(bitmap)).apply(requestOptions).into(avatar);
    }

    /**
     * 动画效果
     * @param view
     * @param scaleSmall
     * @param scaleLarge
     * @param shakeDegrees
     * @param duration
     */
    private void startShakeByView(View view, float scaleSmall, float scaleLarge, float shakeDegrees, long duration) {
        //由小变大
        Animation scaleAnim = new ScaleAnimation(scaleSmall, scaleLarge, scaleSmall, scaleLarge);
        //从左向右
        Animation rotateAnim = new RotateAnimation(-shakeDegrees, shakeDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        scaleAnim.setDuration(duration);
        rotateAnim.setDuration(duration / 10);
        rotateAnim.setRepeatMode(Animation.REVERSE);
        rotateAnim.setRepeatCount(100);

        AnimationSet smallAnimationSet = new AnimationSet(false);
        smallAnimationSet.addAnimation(scaleAnim);
        smallAnimationSet.addAnimation(rotateAnim);

        view.startAnimation(smallAnimationSet);
    }

    class MyLinstener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.qq1:
                    Intent intent = new Intent(MyAchieveActivity.this, ShowAchActivity.class);
                    startActivity(intent);
                    break;
//                case R.id.qq2:
//                    Intent intent1 = new Intent(MyAchieveActivity.this,ShowAchActivity.class);
//                    startActivity(intent1);
//                    break;
//                case R.id.qq3:
//                    Intent intent2 = new Intent(MyAchieveActivity.this,ShowAchActivity.class);
//                    startActivity(intent2);
//                    break;
//                case R.id.qq4:
//                    Intent intent3 = new Intent(MyAchieveActivity.this,ShowAchActivity.class);
//                    startActivity(intent3);
//                    break;
//                case R.id.qq5:
//                    Intent intent4 = new Intent(MyAchieveActivity.this,ShowAchActivity.class);
//                    startActivity(intent4);
//                    break;
//                case R.id.qq6:
//                    Intent intent5 = new Intent(MyAchieveActivity.this,ShowAchActivity.class);
//                    startActivity(intent5);
//                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
<<<<<<< Updated upstream
        super.onBackPressed();
        Intent intent = new Intent(MyAchieveActivity.this, MainActivity.class);
        intent.setAction("loginBackMyself");
        startActivity(intent);
=======
        Intent intent=new Intent();
        intent.setClass(MyAchieveActivity.this, MainActivity.class);
        intent.setAction("loginBackMyself");
        startActivityForResult(intent,0);
>>>>>>> Stashed changes
    }
}
