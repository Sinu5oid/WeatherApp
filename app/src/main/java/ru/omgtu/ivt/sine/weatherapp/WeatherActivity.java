package ru.omgtu.ivt.sine.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherCallback;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherResponse;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherUtils;

public class WeatherActivity extends AppCompatActivity implements WeatherCallback{

    TextView mainTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mainTemp = (TextView) findViewById(R.id.result_celsium);

        WeatherUtils utils = new WeatherUtils(this);

        utils.makeRequest(this);
    }

    public void onResponseCallback(WeatherResponse wr) {
        mainTemp.setText(String.format("%s%s %s", wr.getTemp(), getString(R.string.degree_symbol), getString(R.string.celsium)));
        Log.d("WEATHER_ACTIVITY", "onResponseCallBack: " + wr);
    }
}
