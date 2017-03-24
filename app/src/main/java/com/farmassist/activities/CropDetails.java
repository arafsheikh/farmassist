package com.farmassist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farmassist.R;
import com.farmassist.models.Crops;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class CropDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        final String item = getIntent().getExtras().getString("item");
        InputStream raw =  getResources().openRawResource(R.raw.crop_data);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Crops[] cropList = gson.fromJson(rd, Crops[].class);
        int i;
        for (i=0; i < cropList.length; i++) {
            if (cropList[i].CROP_NAME.equals(item)) {
                break;
            }
        }

        ((TextView) findViewById(R.id.tv_name)).setText(cropList[i].CROP_NAME);
        ((TextView) findViewById(R.id.tv_tempmin)).setText(cropList[i].TEMP_MIN + "°C");
        ((TextView) findViewById(R.id.tv_temmax)).setText(cropList[i].TEMP_MAX + "°C");
        ((TextView) findViewById(R.id.tv_hummin)).setText(cropList[i].H_MIN);
        ((TextView) findViewById(R.id.tv_hummax)).setText(cropList[i].H_MAX);
        ((TextView) findViewById(R.id.tv_soil)).setText(cropList[i].SOIL_TYPE);
        ((TextView) findViewById(R.id.tv_fert)).setText(cropList[i].REQ_FERTILIZER);
        ((TextView) findViewById(R.id.tv_irri)).setText(cropList[i].TYPE_IRRG);
        ((TextView) findViewById(R.id.tv_rain)).setText(cropList[i].ANN_AVG_RF + " cm");
        ((TextView) findViewById(R.id.tv_type)).setText(cropList[i].TYPE_CROP);

        ((Button) findViewById(R.id.activity_remove_crop)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sp = getSharedPreferences("crops", MODE_PRIVATE).edit();
                sp.remove(item);
                sp.apply();
                Intent intent = new Intent(CropDetails.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}
