package com.example.will.sharelight.application;

import android.app.Application;

import com.example.will.utils.sharepreferencehelper.SharePreferenceHelper;

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        SharePreferenceHelper.init(getApplicationContext());
    }
}
