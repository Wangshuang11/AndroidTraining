package org.turings.turings.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.UiSettings;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.R;
import org.turings.turings.login.LoginActivity;
import org.turings.turings.login.RegisterNewUserActivity;
import org.turings.turings.near.Location.Location;
import org.turings.turings.near.Location.ShowPortrait;
import org.turings.turings.near.entity.Position;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class NearFragment extends Fragment {
    private View view;
    private TextView tvJumpLogin_ws;//未登录界面的登录按钮
    private ImageView ivUnLogin_ws;//未登录界面的上方图片
    private TextView tvJumpRegister_ws;//未登录界面的注册按钮

    private MapView mapView;
    private BaiduMap baiduMap;
    //ui控制器
    private UiSettings uiSettings;
    private Context context;
    private Handler handler;
    private Gson gson;
    private List<Position> posList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //用户是否登录
        if(!checkUserIsLogin()){//未登录
            view = inflater.inflate(R.layout.sxn_activity_unlogged, container,false);
            //加载上方图片
            loadTopImg();
            //点击跳转按钮，跳到登录或注册界面
            jumpToLogin();
            return view;
        }

        SDKInitializer.initialize(getContext().getApplicationContext());
        view = inflater.inflate(R.layout.near, container,false);
        mapView = view.findViewById(R.id.mapView);
        gson = new Gson();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json =  msg.obj.toString();
                //得到集合对应的具体类型
                Type type = new TypeToken<List<Position>>(){}.getType();
                //反序列化
                posList = gson.fromJson(json, type);
                ShowPortrait sp = new ShowPortrait(mapView,baiduMap,context,posList,getResources());
                sp.showInfoWindowOp(posList);
            }
        };

        //初始化地图相关图像
        initializeMap();
        hideLogo();
        //设置显示指南针
        baiduMap.setCompassEnable(true);
        uiSettings.setCompassEnabled(true);
        //设置自定义指南针图标
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.znz);
        baiduMap.setCompassIcon(bitmap);
        //设置指南针显示位置
        baiduMap.setCompassPosition(new Point(100,100));
        //比例尺操作
        zoomLevelOp();
        //设置图层定位
        baiduMap.setMyLocationEnabled(true);
        context = getActivity();
        Location location = new Location(mapView,baiduMap,context);
        location.locationOption();
        //设置图层覆盖物
        sendToServer("王大爽");
        return view;
    }

    //加载上方图片
    private void loadTopImg() {
        ivUnLogin_ws=view.findViewById(R.id.ivUnLogin_ws);
        RequestOptions requestOptions=new RequestOptions().circleCrop();
        Glide.with(getContext()).asGif().load(R.mipmap.myselfthinkingtwo).apply(requestOptions).into(ivUnLogin_ws);
    }

    //点击跳转按钮，跳到登录或注册界面
    private void jumpToLogin() {
        tvJumpLogin_ws= view.findViewById(R.id.tvJumpLogin_ws);
        tvJumpRegister_ws=view.findViewById(R.id.tvJumpRegister_ws);
        tvJumpLogin_ws.setOnClickListener(new View.OnClickListener() {//跳到登录界面
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tvJumpRegister_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RegisterNewUserActivity.class);
                startActivity(intent);
            }
        });
    }

    //用户是否登录
    private boolean checkUserIsLogin() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        String uName=sharedPreferences.getString("name","");
        String uTel=sharedPreferences.getString("phone","");
        if (uName.equals("") && uTel.equals("")){//用户名或者密码两个都为空，就是用户没登录
            return false;
        }else{//只要用户名或者密码有一个不为空，就是用户登录了
            return true;
        }
    }

    //比例尺
    private void zoomLevelOp() {
        //设置允许放大和缩小的比例
        baiduMap.setMaxAndMinZoomLevel(21,5);
        //设置默认比例为100米
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(19);
        baiduMap.setMapStatus(msu);
    }

    //初始地图
    private void initializeMap() {
        baiduMap = mapView.getMap();
        uiSettings = baiduMap.getUiSettings();

//        //设置地图类型
//        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);//卫星地图
        //设置百度logo显示位置
        mapView.setLogoPosition(LogoPosition.logoPostionCenterBottom);
    }

    //隐藏logo
    private void hideLogo() {
        View child = mapView.getChildAt(1);
        if (null!=child && (child instanceof ImageView || child instanceof ZoomControls)){
            child.setVisibility(View.INVISIBLE);
        }
    }

    public void sendToServer(final String userName) {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/LocationServlet?userName="+userName);
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

}
