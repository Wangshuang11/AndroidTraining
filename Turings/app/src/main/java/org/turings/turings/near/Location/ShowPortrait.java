package org.turings.turings.near.Location;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;


import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;


import org.turings.turings.R;
import org.turings.turings.near.entity.Position;

import java.util.List;

public class ShowPortrait {
    private List<Position> list;
    private MapView mapView;
    private Resources resources;
    //百度地图控制器
    private BaiduMap baiduMap;
    private Context context;

    public ShowPortrait(MapView mapView, BaiduMap baiduMap, Context context, List<Position> list,Resources resources){
        this.list = list;
        this.mapView = mapView;
        this.baiduMap = baiduMap;
        this.context = context;
        this.resources = resources;
    }

    public void showInfoWindowOp(final List<Position> list) {
        LatLng latLng = null;
        for (int i = 0;i<list.size();i++){
            //1.创建标注覆盖物显示位置的latlng对象
            latLng = new LatLng(list.get(i).getLat(),list.get(i).getLng());
            //2.创建标注覆盖物对象
//            int id = resources.getIdentifier(list.get(i).getPortrait(),"mipmap", context.getPackageName());
//            Bitmap icon = BitmapFactory.decodeResource(resources,id);
            Bitmap icon =BitmapFactory.decodeResource(resources, R.mipmap.a3);
            Bitmap bitmap = Bitmap.createBitmap(100,100,Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Matrix matrix = new Matrix();
            matrix.setScale(0.3f,0.3f);
            canvas.drawBitmap(icon,matrix,null);
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
            OverlayOptions markerOption = new MarkerOptions().alpha(0.8f).icon(bitmapDescriptor).position(latLng).perspective(true); // 是否支持近大远小
            //3.把标注覆盖物添加到地图上
            final Overlay myMarker = baiduMap.addOverlay(markerOption);
            //4.给覆盖物添加监听器
            baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Intent intent = new Intent(context, InformationActivity.class);
                    intent.putExtra("lat",marker.getPosition().latitude+"");
                    intent.putExtra("lng",marker.getPosition().longitude+"");
                    context.startActivity(intent);
                    return false;  //不能改为true
                }
            });
        }
    }

}
