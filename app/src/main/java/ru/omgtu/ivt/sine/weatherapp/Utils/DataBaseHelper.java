package ru.omgtu.ivt.sine.weatherapp.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    private final static String LOG_TAG = "DataBaseHelper";

    public DataBaseHelper(Context context) {
        super(context, "weather_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "DBH_onCreate");
        db.execSQL(
                "create table weather_log ("
                + "id integer primary key autoincrement,"
                + "city_name text,"
                + "request_date datetime,"
                + "description text,"
                + "temperature integer,"
                + "humidity integer,"
                + "wind integer,"
                + "pressure real"
                + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
