package com.xiaok.arcgistestdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.esri.arcgisruntime.layers.ArcGISSceneLayer;
import com.esri.arcgisruntime.mapping.ArcGISScene;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.view.Camera;
import com.esri.arcgisruntime.mapping.view.SceneView;

public class SLPKActivity extends AppCompatActivity {
    private SceneView mSceneView;
    private ArcGISScene scene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slpk);

        scene = new ArcGISScene(Basemap.createImagery()); //用遥感影像作为三维场景底图
        mSceneView = findViewById(R.id.scene_slpk);
        mSceneView.setScene(scene);
        mSceneView.setAttributionTextVisible(false);  //去掉Esri logo

        String filepath = "/data/data/com.xiaok.arcgisscenedemo/files/scene.slpk";  //三维场景文件路径
        if (!filepath.isEmpty()) {
            ArcGISSceneLayer sceneLayer = new ArcGISSceneLayer(filepath); // 实例化图层对象
            scene.getOperationalLayers().add(sceneLayer);  //添加三维场景图层

            // 设置场景视角镜头（camera），参数依次为纬度、经度、X轴偏转角度、Y轴偏转角度、Z轴偏转角度、相机高度
            Camera camera = new Camera(39.991616, 116.3842271,200, 345, 65, 0);
            mSceneView.setViewpointCamera(camera);
        }

    }



    @Override
    protected void onPause() {
        mSceneView.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSceneView.resume();
    }

    @Override
    protected void onDestroy() {
        mSceneView.dispose();
        super.onDestroy();
    }

}
