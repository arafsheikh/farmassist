package com.farmassist.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class GsonHelper {
    public static void toGson(Context context, Object myObj, String name) {
        Gson gson = new Gson();
        String json = gson.toJson(myObj);
        SharedPreferences.Editor editor = context.getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString(name, json);
        editor.apply();
    }

    public static Object getGson(Context context, Object myObj, String name) {
        SharedPreferences sp = context.getSharedPreferences("data", MODE_PRIVATE);
        String json = sp.getString(name, null);
        Gson gson = new Gson();
        return gson.fromJson(json, myObj.getClass());
    }
}
