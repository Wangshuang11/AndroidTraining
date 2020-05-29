package org.turings.turings.near.Location;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import org.turings.turings.R;

import java.util.List;

public class Location {
    private MapView mapView;
    //定位服务类
    private LocationClient locationClient;
    //定位选项类
    private LocationClientOption locationClientOption;
    //百度地图控制器
    private BaiduMap baiduMap;
    private Context context;

    public Location(MapView mapView, BaiduMap baiduMap,Context context) {
        this.mapView = mapView;
        this.baiduMap = baiduMap;
        this.context = context;
    }

    public void locationOption() {
        //1.创建定位服务客户端类的对象
        locationClient = new LocationClient(context);
        //2.创建定位客户端选项类的对象，并设置定位参数
        locationClientOption = new LocationClientOption();
        //设置定位参数
        //打开GPS
        locationClientOption.setOpenGps(true);
        //设置定位间隔时间
        locationClientOption.setScanSpan(1000);
        //设置定位坐标系
        locationClientOption.setCoorType("gcj02");
        //设置定位模式
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);   //高精度模式
        //需要定位的地址数据
        locationClientOption.setIsNeedAddress(true);
        //需要地址描述   可选
        locationClientOption.setIsNeedLocationDescribe(true);
        //需要周边POI信息
        locationClientOption.setIsNeedLocationPoiList(true);
        //3.将定位选项参数应用给定位服务客户端类的对象
        locationClient.setLocOption(locationClientOption);
        //4.开始定位
        locationClient.start();
        //5.给定位客户端类的对象注册定位监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获取定位详细数据
                //获取地址信息
                String addr = bdLocation.getAddrStr();
                //获取经纬度
                double lat = bdLocation.getLatitude();
                double lng  = bdLocation.getLongitude();
                //获取周边POI信息
                List<Poi> pois = bdLocation.getPoiList();
                for (Poi p:pois) {
                    String name = p.getName();
                    String pAddr = p.getAddr();
                }
                //获取时间
                String time = bdLocation.getTime();
                //将定位数据显示在地图上
                showLocOnMap(lat,lng);
            }
        });
    }

    public void showLocOnMap(double lat, double lng) {
        //获取定位图标
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.dw);
        //设置显示方式
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.COMPASS   //罗盘态
                ,false  //是否需要方向
                ,icon );  //定位图标m
        //应用显示方式
        baiduMap.setMyLocationConfiguration(config);
        //显示
        MyLocationData locData = new MyLocationData.Builder().latitude(lat).longitude(lng).build();
        baiduMap.setMyLocationData(locData);
        //移动到中心位置
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(lat,lng));    }
}
