package ru.omgtu.ivt.sine.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherCallback;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherResponse;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherUtils;

public class WeatherActivity extends AppCompatActivity implements WeatherCallback{

    WeatherUtils utils;
    TextView mainTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView humidity;
    TextView city;
    TextView description;
    TextView wind;
    TextView pressure;
    String pendingStateLabel;
    String notAvailableLabel;

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

        utils = new WeatherUtils(this);
        utils.makeRequest(this);
    }

    protected void onClick(View view){
        mainTemp.setText(pendingStateLabel);
        minTemp.setText(pendingStateLabel);
        maxTemp.setText(pendingStateLabel);
        humidity.setText(pendingStateLabel);
        description.setText(pendingStateLabel);
        wind.setText(pendingStateLabel);
        pressure.setText(pendingStateLabel);
        utils.makeRequest(this);
    }

    public void onResponseCallback(WeatherResponse wr) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        if (wr.getErrorFlag()) {
            city.setText(getString(R.string.city));
            mainTemp.setText(getString(R.string.no_connection));
            description.setText(getString(R.string.description));
            minTemp.setText(notAvailableLabel);
            maxTemp.setText(notAvailableLabel);
            humidity.setText(notAvailableLabel);
            wind.setText(notAvailableLabel);
            pressure.setText(notAvailableLabel);
            return;
        }
        city.setText(wr.getName());
        mainTemp.setText(String.format("%d%s%s", wr.getTemp(), getString(R.string.degree_symbol), getString(R.string.celsium)));
        description.setText(wr.getDescription());
        minTemp.setText(String.format("%d%s%s", wr.getTemp_min(), getString(R.string.degree_symbol), getString(R.string.celsium)));
        maxTemp.setText(String.format("%d%s%s", wr.getTemp_max(), getString(R.string.degree_symbol), getString(R.string.celsium)));
        humidity.setText(String.format("%s%%", wr.getHumidity()));
        wind.setText(String.format("%d %s", wr.getWind(), getString(R.string.speed_units)));
        pressure.setText(String.format("%.2f %s", wr.getPressure(), getString(R.string.pressure_units)));
    }
}
