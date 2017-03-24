package com.farmassist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.farmassist.Constants;
import com.farmassist.R;
import com.farmassist.adapters.CropListAdapter;
import com.farmassist.models.Crops;
import com.farmassist.models.Fertilizers;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        checkFarmExists();
        populateDetails();
        setupCropListView();
        setupFertilizerListView();
        setupFAB();
    }

    private void populateDetails() {
        TextView tvArea = (TextView) findViewById(R.id.tv_area);
        TextView tvSoil = (TextView) findViewById(R.id.tv_soil);
        TextView tvState = (TextView) findViewById(R.id.tv_location);
        SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
        String area = sp.getString("area", null);
        String soil = sp.getString("soil", null);
        String state = sp.getString("state", null);
        if (area != null && soil != null && state != null) {
            tvArea.setText(area + " acres");
            tvSoil.setText(soil);
            tvState.setText(state);
        }
        ProgressBar areaProgress = (ProgressBar) findViewById(R.id.progress_area);
        float available = Float.valueOf(sp.getString("area_available", "10"));
        float _area = Float.valueOf(area);
        float progress = (available/_area)*100;
        areaProgress.setProgress((int)progress);
    }

    private void checkFarmExists() {
        SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
        String area = sp.getString("area", null);
        if (area == null) {
            startActivity(new Intent(DashboardActivity.this, FarmSetup.class));
        }
    }

    private void setupFAB() {
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_crop);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_fertilizer);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddCrop.class));
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, AddFertilizer.class));
            }
        });
    }

    private void setupCropListView() {
        SharedPreferences sp = getSharedPreferences("crops", MODE_PRIVATE);
        InputStream raw =  getResources().openRawResource(R.raw.crop_data);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Crops[] cropList = gson.fromJson(rd, Crops[].class);
        ArrayList<String> crops = new ArrayList<>();
        ArrayList<Integer> type = new ArrayList<>();
        for (int i=0; i < cropList.length; i++) {
            if (sp.getFloat(cropList[i].CROP_NAME, -1) != -1) {
                crops.add(cropList[i].CROP_NAME);
                String t = cropList[i].TYPE_CROP;
                if (t.equals("cash")) {
                    type.add(Constants.CROP_TYPE.CASH_CROP);
                } else if (t.equals("plantation")) {
                    type.add(Constants.CROP_TYPE.PLANTATION_CROP);
                } else {
                    type.add(Constants.CROP_TYPE.FOOD_CROP);
                }
            }
        }
        //String[] crops = {"Rice", "Wheat", "Coffee"};
        //Integer[] type = {Constants.CROP_TYPE.FOOD_CROP, Constants.CROP_TYPE.PLANTATION_CROP, Constants.CROP_TYPE.CASH_CROP};
        CropListAdapter cropAdapter = new CropListAdapter(DashboardActivity.this, crops, type);
        final ListView list = (ListView) findViewById(R.id.crop_list);
        list.setAdapter(cropAdapter);

        // OnClickListener
        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.getItemAtPosition(position).toString();
                Intent i = new Intent(DashboardActivity.this, CropDetails.class);
                i.putExtra("item", item);
                startActivity(i);
            }
        });
    }

    private void setupFertilizerListView() {
        SharedPreferences sp = getSharedPreferences("fertilizers", MODE_PRIVATE);
        InputStream raw =  getResources().openRawResource(R.raw.fertilizer_data);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Fertilizers[] fertilizerList = gson.fromJson(rd, Fertilizers[].class);
        ArrayList<String> fertilizers = new ArrayList<>();
        for (int i=0; i < fertilizerList.length; i++) {
            if (sp.getFloat(fertilizerList[i].FERTILIZER_NAME, -1) != -1) {
                fertilizers.add(fertilizerList[i].FERTILIZER_NAME);
            }
        }
        ArrayAdapter fertilizerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, fertilizers);
        final ListView list = (ListView) findViewById(R.id.fertilizer_list);
        list.setAdapter(fertilizerAdapter);

        // OnClickListener
        list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = list.getItemAtPosition(position).toString();
                Intent i = new Intent(DashboardActivity.this, FertilizerDetails.class);
                i.putExtra("item", item);
                startActivity(i);
            }
        });
    }
}
