package com.farmassist.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.farmassist.R;

public class FarmSetup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_setup);

        populateSoilSpinner();
        populateStateSpinner();

        Button btn = (Button) findViewById(R.id.activity_farm_setup_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etArea = (EditText) findViewById(R.id.et_area);
                String area = etArea.getText().toString();
                Spinner spSoil = (Spinner)findViewById(R.id.soil_type_spinner);
                String soil = spSoil.getSelectedItem().toString();
                Spinner spState = (Spinner)findViewById(R.id.state_spinner);
                String state = spState.getSelectedItem().toString();
                if (!area.isEmpty() && soil != null && state != null) {
                    SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
                    editor.putString("area", area);
                    editor.putString("area_available", area);
                    editor.putString("soil", soil);
                    editor.putString("state", state);
                    editor.apply();
                    //finish();
                    Intent intent = new Intent(FarmSetup.this, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private void populateStateSpinner() {
        Spinner dropdown = (Spinner)findViewById(R.id.state_spinner);
        String[] items = new String[]{
                "Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chhattisgarh","Goa","Gujarat","Haryana","Himachal Pradesh","Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Meghalaya","Mizoram","Nagaland","Odisha(Orissa)","Punjab","Rajasthan","Sikkim","Tamil Nadu","Tripura","Uttar Pradesh","Uttarakhand","West Bengal"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    private void populateSoilSpinner() {
        Spinner dropdown = (Spinner)findViewById(R.id.soil_type_spinner);
        String[] items = new String[]{"Alluvial", "Loamy", "Black", "Red", "Laterite"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
