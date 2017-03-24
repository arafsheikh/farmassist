package com.farmassist.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.farmassist.Constants;
import com.farmassist.R;
import com.farmassist.adapters.CropListAdapter;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class DashboardActivity extends AppCompatActivity {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        setupCropListView();
        setupFAB();
    }

    private void setupFAB() {
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_crop);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_fertilizer);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }

    private void setupCropListView() {
        String[] crops = {"Rice", "Wheat", "Coffee"};
        Integer[] type = {Constants.CROP_TYPE.FOOD_CROP, Constants.CROP_TYPE.PLANTATION_CROP, Constants.CROP_TYPE.CASH_CROP};
        CropListAdapter cropAdapter = new CropListAdapter(DashboardActivity.this, crops, type);
        ListView cropList = (ListView) findViewById(R.id.crop_list);
        cropList.setAdapter(cropAdapter);
    }
}
