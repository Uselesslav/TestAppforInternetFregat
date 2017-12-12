package com.example.wyacheslav.testappforinternetfregat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.wyacheslav.testappforinternetfregat.database.DAO.ManDAO;
import com.example.wyacheslav.testappforinternetfregat.models.Man;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by wyacheslav on 13.12.17.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DataBaseHelper.class.getSimpleName();

    // Имя файла базы данных
    private static final String DATABASE_NAME = "ormlitedb.db";

    // Считает обновления
    private static final int DATABASE_VERSION = 1;

    // Ссылка на DAO соответсвующие сущностям, хранимым в БД
    private ManDAO manDao = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Man.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, Man.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    //синглтон для человека
    public ManDAO getManDAO() throws SQLException {
        if (manDao == null) {
            manDao = new ManDAO(getConnectionSource(), Man.class);
        }
        return manDao;
    }

    //При закрытии приложения
    @Override
    public void close() {
        super.close();
        manDao = null;
    }
}