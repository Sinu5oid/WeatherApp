package ru.omgtu.ivt.sine.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import ru.omgtu.ivt.sine.weatherapp.Utils.CredentialsHelper;

public class LoginActivity extends AppCompatActivity {
    Intent weatherActivityIntent;
    Toast credentialsToast;
    CredentialsHelper helper;

    TextView login;
    TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        helper = new CredentialsHelper();
        login = findViewById(R.id.email);
        password = findViewById(R.id.password);

        weatherActivityIntent = new Intent(this, WeatherActivity.class);
    }

    protected void onClick(View view) {
        if (helper.isValidCredentials(login, password)) {
            credentialsToast = Toast.makeText(getApplicationContext(),
                    getString(R.string.login_success), Toast.LENGTH_SHORT);
            credentialsToast.show();
            startActivity(weatherActivityIntent);
        } else {
            credentialsToast = Toast.makeText(getApplicationContext(),
                    getString(R.string.login_fail), Toast.LENGTH_SHORT);
            credentialsToast.show();
        }
    }
}
