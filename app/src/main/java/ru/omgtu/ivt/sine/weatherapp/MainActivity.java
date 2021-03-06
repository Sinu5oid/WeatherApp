package ru.omgtu.ivt.sine.weatherapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import ru.omgtu.ivt.sine.weatherapp.Utils.DataBaseHelper;
import ru.omgtu.ivt.sine.weatherapp.Utils.RequestParameters;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherCallback;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherResponse;
import ru.omgtu.ivt.sine.weatherapp.Utils.WeatherUtils;

public class MainActivity extends Activity implements WeatherCallback {

    private Spinner citySpinner;
    private EditText cityInput;
    private Button makeRequestButton;
    private ProgressBar progressBar;
    private WeatherUtils weatherUtils;
    private RequestParameters requestParameters;
    private final static String LOG_TAG = "MainActivity";
    private DataBaseHelper dataBaseHelper;

    @Override
    public void onResponseCallback(WeatherResponse wr) {
        makeRequestButton.setEnabled(true);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        if (wr == null) return;
        dataBaseHelper.logRecords(getApplicationContext());
        Intent intent;
        boolean haveCachedData = !dataBaseHelper.getRecord(requestParameters).getErrorFlag();

        Log.d(LOG_TAG, "Launch error activity? Got error flag: " + wr.getErrorFlag() + "; Got haveCachedData: " + haveCachedData);
        if (wr.getErrorFlag() && !haveCachedData) {
            intent = new Intent(this, ErrorActivity.class);
            intent.putExtra("errorDescription", wr.getErrorDescription());
        } else {
            intent = new Intent(this, WeatherActivity.class);
            intent.putExtra("city", requestParameters.getCity());
            intent.putExtra("units", requestParameters.getUnitsCode());
            if (wr.getErrorFlag() && haveCachedData) {
                intent.putExtra("useCache", true);
            } else {
                intent.putExtra("useCache", false);
            }
        }
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");
        setContentView(R.layout.activity_main);

        citySpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(spinnerAdapter);

        cityInput = findViewById(R.id.city_input);

        cityInput.setEnabled(false);

        makeRequestButton = findViewById(R.id.make_request_button);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        requestParameters = new RequestParameters(this);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
    }

    protected void onModeGroupClick(View view) {
        switch (view.getId()) {
            case R.id.mode_manual:
                cityInput.setEnabled(true);
                citySpinner.setEnabled(false);
                break;
            case R.id.mode_predef:
            default:
                cityInput.setEnabled(false);
                citySpinner.setEnabled(true);
                break;
        }
    }

    protected void onUnitsGroupClick(View view) {
        switch (view.getId()) {
            case R.id.radio_kelvin:
                requestParameters.setUnits(RequestParameters.UNITS_KELVIN);
                break;
            case R.id.radio_farenheit:
                requestParameters.setUnits(RequestParameters.UNITS_FARENHEIT);
                break;
            case R.id.radio_celsium:
            default:
                requestParameters.setUnits(RequestParameters.UNITS_CELSIUM);
                break;
        }
    }

    protected void onMakeRequestClick(View view) {
        makeRequestButton.setEnabled(false);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        weatherUtils = new WeatherUtils(getApplicationContext(), this);
        if (citySpinner.isEnabled()) {
            requestParameters.setCity(citySpinner.getSelectedItem().toString());
        } else {
            requestParameters.setCity(cityInput.getText().toString());
        }

        weatherUtils.makeRequest(requestParameters);
    }

    protected void openDBActivity(View view) {
        Intent intent = new Intent(this, DBActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }
}
