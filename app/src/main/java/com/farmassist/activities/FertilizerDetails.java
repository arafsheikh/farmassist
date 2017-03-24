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
import com.farmassist.models.Fertilizers;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class FertilizerDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_details);

        final String item = getIntent().getExtras().getString("item");
        InputStream raw =  getResources().openRawResource(R.raw.fertilizer_data);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Fertilizers[] cropList = gson.fromJson(rd, Fertilizers[].class);
        int i;
        for (i=0; i < cropList.length; i++) {
            if (cropList[i].FERTILIZER_NAME.equals(item)) {
                break;
            }
        }

        ((TextView) findViewById(R.id.tv_fert_name)).setText(cropList[i].FERTILIZER_NAME);

        ((Button) findViewById(R.id.activity_remove_fert)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor sp = getSharedPreferences("fertilizers", MODE_PRIVATE).edit();
                sp.remove(item);
                sp.apply();
                Intent intent = new Intent(FertilizerDetails.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
