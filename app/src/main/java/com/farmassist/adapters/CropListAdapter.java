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

import java.util.ArrayList;

public class CropListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> cropName;
    private final ArrayList<Integer> cropType;
    public CropListAdapter(Activity context,
                           ArrayList<String> cropName, ArrayList<Integer> cropType) {
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
        txtTitle.setText(cropName.get(position));

        if (cropType.get(position) == Constants.CROP_TYPE.CASH_CROP) {
            imageView.setImageResource(R.drawable.ic_cash_crop);
        } else if (cropType.get(position) == Constants.CROP_TYPE.FOOD_CROP) {
            imageView.setImageResource(R.drawable.ic_food_crop);
        } else {
            imageView.setImageResource(R.drawable.ic_plantation_crop);
        }
        return rowView;
    }
}