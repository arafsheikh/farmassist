package com.farmassist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.farmassist.Constants;
import com.farmassist.R;
import com.farmassist.adapters.CropListAdapter;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupCropListView();
    }

    private void setupCropListView() {
        String[] crops = {"Rice", "Wheat", "Coffee"};
        Integer[] type = {Constants.CROP_TYPE.FOOD_CROP, Constants.CROP_TYPE.PLANTATION_CROP, Constants.CROP_TYPE.CASH_CROP};
        CropListAdapter cropAdapter = new CropListAdapter(DashboardActivity.this, crops, type);
        ListView cropList = (ListView) findViewById(R.id.crop_list);
        cropList.setAdapter(cropAdapter);
    }
}
