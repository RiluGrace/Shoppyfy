package com.example.rilu_pc.shoppyfy;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by babur on 23-01-2018.
 */

public class StethoApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}