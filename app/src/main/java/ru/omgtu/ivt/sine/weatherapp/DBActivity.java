package ru.omgtu.ivt.sine.weatherapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import ru.omgtu.ivt.sine.weatherapp.Utils.DataBaseHelper;
import ru.omgtu.ivt.sine.weatherapp.Utils.ToastHelper;

public class DBActivity extends AppCompatActivity {

    private final static String LOG_TAG = "DBActivity";
    private Button
            add,
            wipe,
            read;
    private TextView
            city,
            request_date,
            description,
            temp,
            humidity,
            pressure,
            wind;
    private ArrayList<TextView> components;
    private DataBaseHelper dataBaseHelper;

    private String toastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        components = new ArrayList<>();

        add = findViewById(R.id.add);
        wipe = findViewById(R.id.wipe);
        read = findViewById(R.id.read);

        city = findViewById(R.id.city);
        components.add(city);
        request_date = findViewById(R.id.request_date);
        components.add(request_date);
        description = findViewById(R.id.description);
        components.add(description);
        temp = findViewById(R.id.temperature);
        components.add(temp);
        humidity = findViewById(R.id.humidity);
        components.add(humidity);
        pressure = findViewById(R.id.pressure);
        components.add(pressure);
        wind = findViewById(R.id.wind);
        components.add(wind);

        dataBaseHelper = new DataBaseHelper(this);

        toastText = "";

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    protected void onClick(View view) {
        ContentValues contentValues = new ContentValues();
        SQLiteDatabase database = dataBaseHelper.getWritableDatabase();
        switch (view.getId()) {
            case R.id.add:
                if (!hasEmptyFields(components)) {
                    contentValues.put("city_name", city.getText().toString());
                    contentValues.put("request_date", request_date.getText().toString());
                    contentValues.put("description", description.getText().toString());
                    contentValues.put("temperature", temp.getText().toString());
                    contentValues.put("humidity", humidity.getText().toString());
                    contentValues.put("pressure", pressure.getText().toString());
                    contentValues.put("wind", wind.getText().toString());

                    long rowID = database.insert("weather_log", null, contentValues);
                    Log.d(LOG_TAG, "Record inserted @ ID = " + rowID);
                    toastText = getString(R.string.inserted_at) + rowID;
                    ToastHelper.showToast(getApplicationContext(), toastText);
                } else {
                    Log.d(LOG_TAG, "Some fields are empty. Abort inserting");
                    toastText = getString(R.string.all_fields_required);
                    ToastHelper.showToast(getApplicationContext(), toastText);
                }
                break;
            case R.id.read:
                Cursor c = database.query("weather_log", null, null, null, null, null, null);
                Log.d(LOG_TAG, "Records in 'weather_log' table: " + c.getCount());
                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("id");
                    int cityColIndex = c.getColumnIndex("city_name");
                    int request_dateColIndex = c.getColumnIndex("request_date");
                    int descriptionColIndex = c.getColumnIndex("description");
                    int temperatureColIndex = c.getColumnIndex("temperature");
                    int humidityColIndex = c.getColumnIndex("humidity");
                    int pressureColIndex = c.getColumnIndex("pressure");
                    int windColIndex = c.getColumnIndex("wind");
                    do {
                        // получаем значения по номерам столбцов и пишем все в лог
                        Log.d(LOG_TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", city = " + c.getString(cityColIndex) +
                                        ", request_date = " + c.getString(request_dateColIndex) +
                                        ", description = " + c.getString(descriptionColIndex) +
                                        ", temperature = " + c.getString(temperatureColIndex) +
                                        ", humidity = " + c.getString(humidityColIndex) +
                                        ", pressure = " + c.getString(pressureColIndex) +
                                        ", wind = " + c.getString(windColIndex) + ";"
                        );
                        // переход на следующую строку
                        // а если следующей нет (текущая - последняя), то false - выходим из цикла
                    } while (c.moveToNext());
                }
                toastText = getString(R.string.records_count_label) + c.getCount();
                ToastHelper.showToast(getApplicationContext(), toastText);
                c.close();
                break;
            case R.id.wipe:
                int clearCount = database.delete("weather_log", null, null);
                Log.d(LOG_TAG, "Deleted records: " + clearCount);
                break;
            default:
                break;
        }
    }

    private boolean hasEmptyFields(ArrayList<TextView> list) {
        for (TextView view : list) {
            if (view.getText().toString().isEmpty())
                return true;
        }
        return false;
    }
}
