package com.example.rilu_pc.shoppyfy;

import android.app.Activity;
import android.graphics.Typeface;

/**
 * Created by babur on 01-02-2018.
 */

public class FontTypeFace
{

    Activity activity;
    public FontTypeFace(Activity context) {
        this.activity=context;
    }


    public Typeface MontserratRegular() {
        Typeface montserratRegular = Typeface.createFromAsset(activity.getAssets(), "Montserrat-Regular.ttf");
        return montserratRegular;
    }

}
