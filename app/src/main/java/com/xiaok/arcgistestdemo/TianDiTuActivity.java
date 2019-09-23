package com.xiaok.arcgistestdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.xiaok.arcgistestdemo.tdt.LayerInfoFactory;
import com.xiaok.arcgistestdemo.tdt.TianDiTuLayer;
import com.xiaok.arcgistestdemo.tdt.TianDiTuLayerInfo;
import com.xiaok.arcgistestdemo.tdt.TianDiTuLayerTypes;

public class TianDiTuActivity extends AppCompatActivity{

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tianditu);

        mMapView = findViewById(R.id.mv_tian_di_tu);
        addTDT(mMapView); //添加天地图底图
        Point centralPoint = new Point(116.41,39.902);
        mMapView.setViewpointCenterAsync(centralPoint,400000f); //设置地图中心点和初始放缩比
        mMapView.setAttributionTextVisible(false); //隐藏Esri logo

    }

    public void addTDT(MapView mapView){
        TianDiTuLayerInfo layerInfo=
                LayerInfoFactory.getLayerInfo(TianDiTuLayerTypes.TIANDITU_VECTOR_2000);
        TileInfo info=layerInfo.getTileInfo();
        Envelope fullExtent=layerInfo.getFullExtent();
        TianDiTuLayer layer=
                new TianDiTuLayer(info,fullExtent);
        layer.setLayerInfo(layerInfo);

        TianDiTuLayerInfo layerInfo_cva=
                LayerInfoFactory.getLayerInfo(TianDiTuLayerTypes.TIANDITU_VECTOR_ANNOTATION_CHINESE_2000);
        TileInfo info_cva =layerInfo_cva.getTileInfo();
        Envelope fullExtent_cva =layerInfo_cva.getFullExtent();
        TianDiTuLayer layer_cva =
                new TianDiTuLayer(info_cva,fullExtent_cva);
        layer_cva.setLayerInfo(layerInfo_cva);

        ArcGISMap map =new ArcGISMap();
        map.getBasemap().getBaseLayers().add(layer);
        map.getBasemap().getBaseLayers().add(layer_cva);
        mapView.setMap(map);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }
}
