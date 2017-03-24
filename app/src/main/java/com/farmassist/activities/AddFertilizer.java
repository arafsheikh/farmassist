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
import android.widget.Toast;

import com.farmassist.R;
import com.farmassist.models.Crops;
import com.farmassist.models.Fertilizers;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class AddFertilizer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fertilizer);

        populateFertilizers();
        Button btn = (Button) findViewById(R.id.activity_add_fertilizer_save);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etFert = (EditText) findViewById(R.id.et_fertilizers_added);
                String areaToCultivate = etFert.getText().toString();
                SharedPreferences.Editor editor = getSharedPreferences("fertilizers", MODE_PRIVATE).edit();
                Spinner spCrop = (Spinner)findViewById(R.id.fertilizer_spinner);
                String crop = spCrop.getSelectedItem().toString();
                editor.putFloat(crop, Float.valueOf(areaToCultivate));
                editor.apply();
                Intent intent = new Intent(AddFertilizer.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private void populateFertilizers() {
        Spinner dropdown = (Spinner)findViewById(R.id.fertilizer_spinner);
        InputStream raw =  getResources().openRawResource(R.raw.fertilizer_data);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Gson gson = new Gson();
        Fertilizers[] fertilizers = gson.fromJson(rd, Fertilizers[].class);
        ArrayList<String> items = new ArrayList<>();
        for (int i=0; i < fertilizers.length; i++) {
            items.add(fertilizers[i].FERTILIZER_NAME);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }
}
