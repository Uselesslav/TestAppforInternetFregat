package com.example.wyacheslav.testappforinternetfregat;

import android.app.Application;

import com.example.wyacheslav.testappforinternetfregat.database.HelperFactory;

/**
 * Created by wyacheslav on 13.12.17.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}