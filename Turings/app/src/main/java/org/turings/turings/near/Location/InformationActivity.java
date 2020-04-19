package org.turings.turings.near.Location;


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

        getViews();
        registeLinstener();
        AnimationDrawable amDrawable = (AnimationDrawable) ivGuanzhu.getDrawable();
        amDrawable.start();
        AnimationDrawable amDrawable1 = (AnimationDrawable) ivHeye.getDrawable();
        amDrawable1.start();
        initView();
        gson = new Gson();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json =  msg.obj.toString();
                //反序列化
                information = gson.fromJson(json, Information.class);
                fid=information.getId();
                otherName = information.getUserName();
                tvFollowName_lyh.setText(information.getUserName());
                Glide.with(getApplicationContext()).load(information.getPortrait()).into(ivPortrait_lyh);
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
    }
    class CustomeLinstener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
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
                    aid=sharedPreferences.getInt("uId",0);
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
//                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/BrowseInformationServlet?lat=" + lat + "&lng=" + lng);
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
//                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/BrowseInfoByServlet?name="+name);
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

    public void sendToServer2(final int aid, final int fid) {

        new Thread() {
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/BrowseInformationServlet?lat=" + lat + "&lng=" + lng);
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

    public void sendToServer3(final int aid, final int fid) {

        new Thread() {
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/BrowseInformationServlet?lat=" + lat + "&lng=" + lng);
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

    class Provider implements DivergeView.DivergeViewProvider {

        @Override
        public Bitmap getBitmap(Object obj) {
            return mList == null ? null : mList.get((int) obj);
        }
    }
}
