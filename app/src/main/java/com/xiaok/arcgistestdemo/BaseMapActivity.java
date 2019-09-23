package com.xiaok.arcgistestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.esri.arcgisruntime.arcgisservices.TileInfo;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.xiaok.arcgistestdemo.tdt.LayerInfoFactory;
import com.xiaok.arcgistestdemo.tdt.TianDiTuLayer;
import com.xiaok.arcgistestdemo.tdt.TianDiTuLayerInfo;
import com.xiaok.arcgistestdemo.tdt.TianDiTuLayerTypes;

public class BaseMapActivity extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_map);
        mMapView = findViewById(R.id.mv_base);
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC,34.817196,113.537310,16);
        mMapView.setMap(map);
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
