package com.farmassist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.farmassist.R;
import com.farmassist.models.Crops;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class AddCrop extends AppCompatActivity {
    //private boolean[] availableSlots = {false, false, false, false, false, false, false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        populateCrops();
        //addClickListeners();
        //fillUsedAreas();

        Button btn = (Button) findViewById(R.id.activity_add_crop_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("pref", MODE_PRIVATE);
                String area = sp.getString("area_available", "0");
                EditText etCul = (EditText) findViewById(R.id.et_area_to_cultivate);
                String areaToCultivate = etCul.getText().toString();
                if (Integer.valueOf(area).compareTo(Integer.valueOf(areaToCultivate)) <= 0) {
                    Toast.makeText(getApplicationContext(), "Not enough area available", Toast.LENGTH_SHORT);
                }
                SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
                editor.putString("area_available", Integer.toString(Integer.valueOf(area).intValue() - Integer.valueOf(areaToCultivate).intValue()));
                editor.apply();
                Spinner spCrop = (Spinner)findViewById(R.id.crop_spinner);
                String crop = spCrop.getSelectedItem().toString();
                editor = getSharedPreferences("crops", MODE_PRIVATE).edit();
                editor.putFloat(crop, Float.valueOf(areaToCultivate));
                editor.apply();
                Intent intent = new Intent(AddCrop.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    /*private void addClickListeners() {
        for (int i=1; i <= 12; i++) {
            TextView tv = null;
            switch (i) {
                case 1:
                    tv = (TextView) findViewById(R.id.tb1);
                    break;
                case 2:
                    tv = (TextView) findViewById(R.id.tb2);
                    break;
                case 3:
                    tv = (TextView) findViewById(R.id.tb3);
                    break;
                case 4:
                    tv = (TextView) findViewById(R.id.tb4);
                    break;
                case 5:
                    tv = (TextView) findViewById(R.id.tb5);
                    break;
                case 6:
                    tv = (TextView) findViewById(R.id.tb6);
                    break;
                case 7:
                    tv = (TextView) findViewById(R.id.tb7);
                    break;
                case 8:
                    tv = (TextView) findViewById(R.id.tb8);
                    break;
                case 9:
                    tv = (TextView) findViewById(R.id.tb9);
                    break;
                case 10:
                    tv = (TextView) findViewById(R.id.tb10);
                    break;
                case 11:
                    tv = (TextView) findViewById(R.id.tb11);
                    break;
                case 12:
                    tv = (TextView) findViewById(R.id.tb12);
                    break;
            }
            tv.setOnClickListener(new View.OnClickListener() {
                private boolean available;
                public void onClick(View v) {
                    if(available) {
                        // reset background to default;
                        v.setBackgroundResource(R.drawable.border);
                        isAvailable(((TextView)v).getText(), true);
                    } else {
                        v.setBackgroundResource(R.color.colorPrimaryDark);
                        isAvailable(((TextView)v).getText(), false);
                    }
                    available = !available;
                }
            });
        }
    }

    private void isAvailable(CharSequence text, boolean b) {
        if (b) {
            availableSlots[Integer.valueOf(text.toString())] = true;
        } else {
            availableSlots[Integer.valueOf(text.toString())] = false;
        }
    }

    private void fillUsedAreas() {
        SharedPreferences sp = getSharedPreferences("area", MODE_PRIVATE);
        for (int i=1; i <= 12; i++) {
            boolean t = sp.getBoolean(Integer.toString(i), false);
            if (t) {
                TextView tv = null;
                switch (i) {
                    case 1:
                        tv = (TextView) findViewById(R.id.tb1);
                        break;
                    case 2:
                        tv = (TextView) findViewById(R.id.tb2);
                        break;
                    case 3:
                        tv = (TextView) findViewById(R.id.tb3);
                        break;
                    case 4:
                        tv = (TextView) findViewById(R.id.tb4);
                        break;
                    case 5:
                        tv = (TextView) findViewById(R.id.tb5);
                        break;
                    case 6:
                        tv = (TextView) findViewById(R.id.tb6);
                        break;
                    case 7:
                        tv = (TextView) findViewById(R.id.tb7);
                        break;
                    case 8:
                        tv = (TextView) findViewById(R.id.tb8);
                        break;
                    case 9:
                        tv = (TextView) findViewById(R.id.tb9);
                        break;
                    case 10:
                        tv = (TextView) findViewById(R.id.tb10);
                        break;
                    case 11:
                        tv = (TextView) findViewById(R.id.tb11);
                        break;
                    case 12:
                        tv = (TextView) findViewById(R.id.tb12);
                        break;
                }
                tv.setBackgroundResource(R.color.colorPrimaryDark);
                tv.setClickable(false);
            }
        }
    }*/

    private void populateCrops() {
        Spinner dropdown = (Spinner)findViewById(R.id.crop_spinner);
        InputStream raw =  getResources().openRawResource(R.raw.crop_data);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Crops[] crops = gson.fromJson(rd, Crops[].class);
        ArrayList<String> items = new ArrayList<>();
        for (int i=0; i < crops.length; i++) {
            items.add(crops[i].CROP_NAME);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
