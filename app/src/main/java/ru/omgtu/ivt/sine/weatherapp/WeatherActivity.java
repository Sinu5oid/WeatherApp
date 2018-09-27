package ru.omgtu.ivt.sine.weatherapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import ru.omgtu.ivt.sine.weatherapp.Utils.RequestParameters;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherCallback;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherResponse;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherUtils;

public class WeatherActivity extends AppCompatActivity implements WeatherCallback {

    private WeatherUtils utils;
    private TextView
            mainTemp,
            minTemp,
            maxTemp,
            humidity,
            city,
            description,
            wind,
            pressure;
    private String
            pendingStateLabel,
            notAvailableLabel,
            unitsDegreeMark,
            unitsSpeedMark;
    private RequestParameters
            requestParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        mainTemp = findViewById(R.id.temp);
        minTemp = findViewById(R.id.temp_min);
        maxTemp = findViewById(R.id.temp_max);
        humidity = findViewById(R.id.humidity);
        city = findViewById(R.id.city);
        description = findViewById(R.id.description);
        wind = findViewById(R.id.wind);
        pressure = findViewById(R.id.pressure);

        pendingStateLabel = getString(R.string.pending_state);
        notAvailableLabel = getString(R.string.not_available);

        Intent intent = getIntent();
        requestParameters = new RequestParameters(intent.getStringExtra("city"), intent.getIntExtra("units", RequestParameters.UNITS_DEFAULT));

        unitsDegreeMark = requestParameters.getUnitsDegreeMark();
        unitsSpeedMark = requestParameters.getUnitsSpeedMark();

        utils = new WeatherUtils(this);
        utils.makeRequest(this, requestParameters);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void onClick(View view) {
        mainTemp.setText(pendingStateLabel);
        minTemp.setText(pendingStateLabel);
        maxTemp.setText(pendingStateLabel);
        humidity.setText(pendingStateLabel);
        description.setText(pendingStateLabel);
        wind.setText(pendingStateLabel);
        pressure.setText(pendingStateLabel);
        utils.makeRequest(this, requestParameters);
    }

    @SuppressLint("DefaultLocale")
    public void onResponseCallback(WeatherResponse wr) {
        if (wr.getErrorFlag()) {
            city.setText(getString(R.string.city));
            mainTemp.setText(wr.getErrorDescription());
            description.setText(getString(R.string.description));
            minTemp.setText(notAvailableLabel);
            maxTemp.setText(notAvailableLabel);
            humidity.setText(notAvailableLabel);
            wind.setText(notAvailableLabel);
            pressure.setText(notAvailableLabel);
        } else {
            city.setText(wr.getName());
            mainTemp.setText(String.format("%d%s", wr.getTemp(), unitsDegreeMark));
            description.setText(wr.getDescription());
            minTemp.setText(String.format("%d%s", wr.getTemp_min(), unitsDegreeMark));
            maxTemp.setText(String.format("%d%s", wr.getTemp_max(), unitsDegreeMark));
            humidity.setText(String.format("%s%%", wr.getHumidity()));
            wind.setText(String.format("%d %s", wr.getWind(), unitsSpeedMark));
            pressure.setText(String.format("%.2f %s", wr.getPressure(), getString(R.string.pressure_units)));
        }
    }
}
