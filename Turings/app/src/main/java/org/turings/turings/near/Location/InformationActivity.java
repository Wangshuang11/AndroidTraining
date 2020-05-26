package org.turings.turings.near.Location;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.near.entity.DivergeView;
import org.turings.turings.near.entity.Information;
import org.turings.turings.near.entity.RoundImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {
    private ImageView btnBack_lyh;
    private RoundImageView ivPortrait_lyh;//头像
    private TextView tvFollowName_lyh;  //昵称
    private TextView tvTime_lyh;  //学习总时长
    private TextView tvUniversity_lyh;  //目标大学
    private TextView tvMotto_lyh;  //座右铭
    private TextView tvCurrentTime_lyh;  //本次时长
    private ImageView ivGuanzhu;
    private TextView tvShare;
    private ImageView ivHeye;
    private ImageView lyh_qqimg;
    private TextView lyh_qqtext;
    private CustomeLinstener linstener;
    private ImageView btnFollow_lyh;
    private Handler handler;
    private Gson gson;
    private DivergeView mDivergeView;
    private ArrayList<Bitmap> mList;
    private int mIndex = 0;
    private Information information;
    private Resources resources;
    private double lat;
    private double lng;
    private String otherName;
    private Button btnGuanzhu_lyh;
    private int aid;
    private int fid;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        resources = getResources();
        Intent intent = getIntent();

//        String portrait = intent.getStringExtra("touxiang");
//        String name = intent.getStringExtra("name");
//        int id = getResources().getIdentifier(portrait, "mipmap", getApplicationContext().getPackageName());

        getViews();
        if (intent.getAction()!=null){
            btnGuanzhu_lyh.setText("已关注");
            count++;
        }
//        ivPortrait_lyh.setImageResource(id);
//        tvFollowName_lyh.setText(name);
        registeLinstener();
        AnimationDrawable amDrawable = (AnimationDrawable) ivGuanzhu.getDrawable();
        amDrawable.start();
        AnimationDrawable amDrawable1 = (AnimationDrawable) ivHeye.getDrawable();
        amDrawable1.start();
        initView();
//        signup();
        spContext();
        signin();
        gson = new Gson();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json =  msg.obj.toString();
                //反序列化
                information = gson.fromJson(json, Information.class);
                otherName = information.getUserName();
                fid=information.getId();
                tvFollowName_lyh.setText(information.getUserName());
                int id = getResources().getIdentifier(information.getPortrait(), "mipmap", getApplicationContext().getPackageName());
                ivPortrait_lyh.setImageResource(id);
//                Glide.with(getApplicationContext()).load(information.getPortrait()).into(ivPortrait_lyh);
                tvTime_lyh.setText(String.valueOf(information.getTotalTime()));
                tvCurrentTime_lyh.setText(String.valueOf(information.getCurrentTime()));
                tvUniversity_lyh.setText(information.getUniversity());
                tvMotto_lyh.setText(information.getMotto());
            }
        };

        if (intent.getAction().equals("sxnToInfo")){
            sendToServer1(intent.getStringExtra("name"));
        }else{
            String lat1 = intent.getStringExtra("lat");
            String lng1 = intent.getStringExtra("lng");
            lat = Double.parseDouble(lat1);
            lng = Double.parseDouble(lng1);
            sendToServer(lat,lng);
        }
    }

    private void registeLinstener() {
        linstener = new CustomeLinstener();
        btnBack_lyh.setOnClickListener(linstener);
        tvShare.setOnClickListener(linstener);
        btnGuanzhu_lyh.setOnClickListener(linstener);
        lyh_qqimg.setOnClickListener(linstener);
        lyh_qqtext.setOnClickListener(linstener);
    }

    private void getViews() {
        btnBack_lyh = findViewById(R.id.btnBack_lyh);
        ivGuanzhu = findViewById(R.id.ivGuanzhu);
        ivHeye = findViewById(R.id.ivHeye);
        tvShare = findViewById(R.id.tvShare);
        ivPortrait_lyh = findViewById(R.id.ivPortrait_lyh);
        tvFollowName_lyh = findViewById(R.id.tvFollowName_lyh);
        tvTime_lyh = findViewById(R.id.tvTime_lyh);
        tvUniversity_lyh = findViewById(R.id.tvUniversity_lyh);
        tvMotto_lyh = findViewById(R.id.tvMotto_lyh);
        tvCurrentTime_lyh = findViewById(R.id.tvCurrentTime_lyh);
        btnGuanzhu_lyh = findViewById(R.id.btnGuanzhu_lyh);
        lyh_qqtext = findViewById(R.id.lyh_qqtext);
        lyh_qqimg = findViewById(R.id.lyh_qqimg);
    }
    class CustomeLinstener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lyh_qqimg:
                    Intent intent2 = new Intent(InformationActivity.this,QQActivity.class);
//                    intent2.putExtra(EaseConstant.EXTRA_USER_ID,fid+"");
                    intent2.putExtra(EaseConstant.EXTRA_USER_ID,"user1");
//                    intent2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                    intent2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    startActivity(intent2);
                    break;
                case R.id.lyh_qqtext:
                    Intent intent3 = new Intent(InformationActivity.this,QQActivity.class);
//                    intent3.putExtra(EaseConstant.EXTRA_USER_ID,fid+"");
                    intent3.putExtra(EaseConstant.EXTRA_USER_ID,"user1");
//                    intent3.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                    intent3.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                    startActivity(intent3);
                    break;
                case R.id.btnBack_lyh:
                    Intent intent = new Intent(InformationActivity.this, MainActivity.class);
                    intent.setAction("backTonear");
                    startActivity(intent);
                    break;
                case R.id.tvShare:
                    Intent intent1 = new Intent(InformationActivity.this, ShareTitleActivity.class);
                    intent1.putExtra("otherName",otherName);
                    startActivity(intent1);
                    break;
                case R.id.btnGuanzhu_lyh:
                    count++;
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
                    aid= Integer.parseInt(sharedPreferences.getString("uId",""));
                    if (count%2 == 0){
                        btnGuanzhu_lyh.setText("+ 关 注");
                        sendToServer3(aid,fid);
                    }else {
                        btnGuanzhu_lyh.setText("已关注");
                        sendToServer2(aid,fid);
                    }
                    break;
            }
        }
    }

    private void spContext(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("achStatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("status",1);
        editor.commit();
    }

//    初始化布局
    private void initView() {
        mDivergeView = findViewById(R.id.divergeView);
        btnFollow_lyh = findViewById(R.id.btnFollow_lyh);

        mList = new ArrayList<>();
        // 添加点赞图标
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_praise_sm1, null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_praise_sm2, null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_praise_sm3, null)).getBitmap());
        mList.add(((BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.mipmap.ic_praise_sm4, null)).getBitmap());

        btnFollow_lyh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIndex == mList.size()) {
                    mIndex = 0;
                }
                mDivergeView.startDiverges(mIndex);
                mIndex++;
            }
        });


        mDivergeView.post(new Runnable() {
            @Override
            public void run() {
                mDivergeView.setEndPoint(new PointF(mDivergeView.getMeasuredWidth() / 2, 0));
                mDivergeView.setDivergeViewProvider(new Provider());
            }
        });
    }

    public void sendToServer(final double lat, final double lng) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/lyh/findOne?lat=" + lat + "&lng=" + lng);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public void sendToServer1(final String name) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/lyh/findOneByName?name="+name);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendToServer2(final int aid,final int fid) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/SetAt?aid="+aid+"&fid="+fid);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void sendToServer3(final int aid,final int fid) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/DelAt?aid="+aid+"&fid="+fid);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mList != null) {
            mList.clear();
            mList = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(InformationActivity.this, MainActivity.class);
        intent.setAction("backTonear");
        startActivity(intent);
    }

    class Provider implements DivergeView.DivergeViewProvider {

        @Override
        public Bitmap getBitmap(Object obj) {
            return mList == null ? null : mList.get((int) obj);
        }
    }

    //    登录
    private void signin(){
        EMClient.getInstance().login("王大爽", "wangshaung", new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }
    //    注册
    private void signup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("userInfo",MODE_PRIVATE);
//                    int aid = Integer.parseInt(sharedPreferences.getString("uId",""));
                    EMClient.getInstance().createAccount(5+"","111111");
                    Log.e("lyh","注册成功");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    Log.e("lyh","注册失败"+e.getErrorCode()+","+e.getMessage());
                }
            }
        }).start();
    }
}
