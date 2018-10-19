package ru.omgtu.ivt.sine.weatherapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Objects;

import ru.omgtu.ivt.sine.weatherapp.Utils.DataBaseHelper;
import ru.omgtu.ivt.sine.weatherapp.Utils.ToastHelper;

public class DBActivity extends AppCompatActivity {

    private final static String LOG_TAG = "DBActivity";
    private DataBaseHelper dataBaseHelper;

    private String toastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        dataBaseHelper = new DataBaseHelper(this);

        toastText = "";

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.read:
                toastText = getString(R.string.how_many_records) + dataBaseHelper.logRecords(getApplicationContext());
                ToastHelper.showToast(getApplicationContext(), toastText);
                break;
            case R.id.wipe:
                int deleted_records = dataBaseHelper.wipeDB();
                Log.d(LOG_TAG, "Deleted records: " + deleted_records);
                toastText = getString(R.string.how_many_records_deleted) + deleted_records;
                ToastHelper.showToast(getApplicationContext(), toastText);
                break;
            default:
                break;
        }
    }
}
