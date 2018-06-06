package app.test.com.washtestapp.models;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import app.test.com.washtestapp.R;

public class Wash {
    public String name;
    public @StringRes int descriptionId;
    public @DrawableRes int imageId;

    private Wash(String name, int descriptionId, int imageId) {
        this.name = name;
        this.descriptionId = descriptionId;
        this.imageId = imageId;
    }

    public static Wash[] getAllWashes(Context cntx)
    {
        return new Wash[]
                {
                    new Wash(cntx.getString(R.string.sedans), R.string.sedans_description, R.drawable.ic_sedan),
                    new Wash(cntx.getString(R.string.suvs), R.string.suvs_description, R.drawable.ic_suv),
                    new Wash(cntx.getString(R.string.buses), R.string.buses_description, R.drawable.ic_bus),
                };
    }
}
