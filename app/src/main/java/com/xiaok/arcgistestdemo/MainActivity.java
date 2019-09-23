package com.xiaok.arcgistestdemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //消除水印
        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud4449636536,none,NKMFA0PL4S0DRJE15166");

        new RelaseDateTask().execute();//释放数据

        //基本地图
        Button btn_base_map = findViewById(R.id.btn_base_map);
        btn_base_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,BaseMapActivity.class));
            }
        });

        //天地图作为底图的地图
        Button btn_tian_di_tu = findViewById(R.id.btn_tian_di_tu);
        btn_tian_di_tu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TianDiTuActivity.class));
            }
        });

        //加载本地Geodatabase图层
        Button btn_local_database = findViewById(R.id.btn_local_database);
        btn_local_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocalDatabaseActivity.class));
            }
        });
    }


    private class RelaseDateTask extends AsyncTask<Void, String, Boolean> {


        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                relaseDate(String.valueOf(getFilesDir())+"/WaterClub1.geodatabase");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }


    private void relaseDate(String strOutFileName) throws IOException {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = getAssets().open("WaterClub1.geodatabase");
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }

}
