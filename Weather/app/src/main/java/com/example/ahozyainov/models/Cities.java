package com.example.ahozyainov.models;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.example.ahozyainov.activities.R;


public class Cities {

    public String name;
    public @StringRes
    int descriptionId;
    public @DrawableRes
    int imageId;

    private Cities(String name, int descriptionId, int imageId) {
        this.name = name;
        this.descriptionId = descriptionId;
        this.imageId = imageId;
    }

    public static Cities[] getAllCities(Context context) {
        return new Cities[]{
                new Cities(context.getString(R.string.moscow_city), R.string.sunny, R.drawable.sunny),
                new Cities(context.getString(R.string.spb_city), R.string.rainy, R.drawable.rainy),
                new Cities(context.getString(R.string.paris_city), R.string.rainy, R.drawable.rainy),
                new Cities(context.getString(R.string.madrid_city), R.string.sunny, R.drawable.sunny),
        };
    }

}


