package com.farmassist.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmassist.Constants;
import com.farmassist.R;

public class CropListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] cropName;
    private final Integer[] cropType;
    public CropListAdapter(Activity context,
                      String[] cropName, Integer[] cropType) {
        super(context, R.layout.crop_list_view_single, cropName);
        this.context = context;
        this.cropName = cropName;
        this.cropType = cropType;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.crop_list_view_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.crop_list_view_single_text);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.crop_list_view_single_ic);
        txtTitle.setText(cropName[position]);

        if (cropType[position] == Constants.CROP_TYPE.CASH_CROP) {
            imageView.setImageResource(R.drawable.ic_cash_crop);
        } else if (cropType[position] == Constants.CROP_TYPE.FOOD_CROP) {
            imageView.setImageResource(R.drawable.ic_food_crop);
        } else {
            imageView.setImageResource(R.drawable.ic_plantation_crop);
        }
        return rowView;
    }
}