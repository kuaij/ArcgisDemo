package com.xiaok.arcgistestdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.esri.arcgisruntime.data.Geodatabase;
import com.esri.arcgisruntime.data.GeodatabaseFeatureTable;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.FeatureLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.view.MapView;

import java.util.List;

public class LocalDatabaseActivity extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_database);
        mMapView = findViewById(R.id.local_mapview);

        new TianDiTuActivity().addTDT(mMapView); //添加天地图底图，函数最好单独封装到一个类中，这里为了演示方便就直接复用了

        Point centralPoint = new Point(116.384216, 39.991298);
        mMapView.setViewpointCenterAsync(centralPoint, 3140f); //设置地图中心点
        loadLocalLayer(String.valueOf(getFilesDir())+"/WaterClub1.geodatabase");
        mMapView.setAttributionTextVisible(false); //隐藏Esri logo
    }


    private void loadLocalLayer(String geodatabasePath){

        final Geodatabase geodatabase = new Geodatabase(geodatabasePath);
        geodatabase.loadAsync();
        // 当geodatabase读取成功后将geodatabase加载到数据库
        geodatabase.addDoneLoadingListener(() -> {
            if (geodatabase.getLoadStatus() == LoadStatus.LOADED) {

                List<GeodatabaseFeatureTable> geodatabaseFeatureTables = geodatabase.getGeodatabaseFeatureTables();
                for (int i=geodatabaseFeatureTables.size()-1;i>=0;i--){
                    GeodatabaseFeatureTable geodatabaseFeatureTable = geodatabaseFeatureTables.get(i);
                    geodatabaseFeatureTable.loadAsync();
                    //创建要素图层
                    final FeatureLayer featureLayer = new FeatureLayer(geodatabaseFeatureTable);
                    // 添加到地图
                    mMapView.getMap().getOperationalLayers().add(featureLayer);
                }


            } else {
                Toast.makeText(LocalDatabaseActivity.this, "Geodatabase failed to load!", Toast.LENGTH_LONG).show();
                Log.e("Tag","Geodatabase failed to load!");
            }
        });
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
