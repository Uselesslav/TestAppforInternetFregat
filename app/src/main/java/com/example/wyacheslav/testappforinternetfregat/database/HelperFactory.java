package com.example.wyacheslav.testappforinternetfregat.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

/**
 * Класс, создающий БД
 * Created by wyacheslav on 13.12.17.
 */
public class HelperFactory{

    private static DataBaseHelper mDataBaseHelper;

    public static DataBaseHelper getHelper(){
        return mDataBaseHelper;
    }
    public static void setHelper(Context context){
        mDataBaseHelper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
    }
    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        mDataBaseHelper = null;
    }
}
