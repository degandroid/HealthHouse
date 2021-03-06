package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.enjoyor.healthhouse.R;

/**
 * Created by YuanYuan on 2016/5/17.
 */
public class MapActivity extends BaseActivity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private String positionLong;
    private String positionLat;
    double jindu;
    double weidu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        SDKInitializer.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_ac_layout);
        initData();
        initView();
        initEvent();

    }

    private void initEvent() {

        if (positionLong == null || positionLat == null) {
            Toast.makeText(this, "地图定位失败", Toast.LENGTH_SHORT).show();
            return;
        } else {
            jindu = Double.parseDouble(positionLong);
            weidu = Double.parseDouble(positionLat);
        }
        Log.i("----", jindu + "===========" + weidu);
        LatLng point = new LatLng(weidu, jindu);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_markg);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
        LatLng cenpt = new LatLng(weidu, jindu);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(12)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
    }

    private void initData() {
        positionLong = getIntent().getStringExtra("latitude");
        positionLong = getIntent().getStringExtra("longitude");
//        Log.d("aaaaaaaaaaa", positionLat);
//        Log.d("sssssssssssssssss", positionLong);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
