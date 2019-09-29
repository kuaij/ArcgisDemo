package com.xiaok.arcgistestdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.ArrayList;
import java.util.List;

public class MapViewListenerActivity extends AppCompatActivity {

    private MapView mMapView;
    public static SpatialReference mSR4326 = SpatialReference.create(4326);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view_listener);
        mMapView = findViewById(R.id.mapview_listener);
        new TianDiTuActivity().addTDT(mMapView); //添加天地图底图，函数最好单独封装到一个类中，这里为了演示方便就直接复用了
        Point centralPoint = new Point(116.41,39.902);
        mMapView.setViewpointCenterAsync(centralPoint,400000f); //设置地图中心点和初始放缩比
        mMapView.setAttributionTextVisible(false); //隐藏Esri logo

        mMapView.setOnTouchListener(new MyTouchListener(MapViewListenerActivity.this,mMapView)); //设置监听器
    }



    //室内地图用户触摸监听
    private class MyTouchListener extends DefaultMapViewOnTouchListener {

        public MyTouchListener(Context context, MapView mapView) {
            super(context, mapView);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Point currentPoint = screnToMapPoint(mMapView, (int) e.getX(), (int) e.getY());
            double currentLongitude = currentPoint.getX(); //当前用户点击的的经度
            double currentLatitude = currentPoint.getY(); //当前用户点击的纬度
            Toast.makeText(MapViewListenerActivity.this,"当前点击经度为:"+currentLongitude+"\n"
                    +"当前点击纬度为:"+currentLatitude+"\n"
                    +"点击方式为:单击",Toast.LENGTH_LONG).show();

            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Point currentPoint = screnToMapPoint(mMapView, (int) e.getX(), (int) e.getY());
            double currentLongitude = currentPoint.getX(); //当前用户点击的的经度
            double currentLatitude = currentPoint.getY(); //当前用户点击的纬度
            Toast.makeText(MapViewListenerActivity.this,"当前点击经度为:"+currentLongitude+"\n"
                    +"当前点击纬度为:"+currentLatitude+"\n"
                    +"点击方式为:双击",Toast.LENGTH_LONG).show();

            return false;  //return false时双击地图不会放大
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Point currentPoint = screnToMapPoint(mMapView, (int) e.getX(), (int) e.getY());
            double currentLongitude = currentPoint.getX(); //当前用户点击的的经度
            double currentLatitude = currentPoint.getY(); //当前用户点击的纬度
            Toast.makeText(MapViewListenerActivity.this,"当前点击经度为:"+currentLongitude+"\n"
                    +"当前点击纬度为:"+currentLatitude+"\n"
                    +"点击方式为:长按",Toast.LENGTH_LONG).show();
            super.onLongPress(e);
        }
    }


    //屏幕坐标转化为经纬度
    private Point screnToMapPoint(MapView mapView, float x, float y){
        //获取当前屏幕点击坐标
        android.graphics.Point point = new android.graphics.Point((int) x, (int) y);
        //转化为投影坐标
        Point sp = mapView.screenToLocation(point);
        //转化为经纬度，mSR4326为当前地图用的空间参考系
        Point resultPoint = (Point) GeometryEngine.project(sp, mSR4326);
        return resultPoint;
    }
}
