package ru.omgtu.ivt.sine.weatherapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import ru.omgtu.ivt.sine.weatherapp.Utils.RequestParameters;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherCallback;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherResponse;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherUtils;

public class WeatherService extends Service implements WeatherCallback {

    WeatherUtils weatherUtils;
    RequestParameters requestParameters;
    SharedPreferences sharedPreferences;
    private final String LOG_TAG = "WeatherService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "Service Started");

        weatherUtils = new WeatherUtils(this, this);
        sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);

        RequestParameters requestParameters = new RequestParameters(this, sharedPreferences.getString("city", "Omsk"), sharedPreferences.getInt("units", 0));

        weatherUtils.makeRequest(requestParameters);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onResponseCallback(WeatherResponse wr) {
        Log.d(LOG_TAG, "onResponseCallback: made request, status: " + !wr.getErrorFlag());
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "Service Destroyed");
        super.onDestroy();
    }
}
