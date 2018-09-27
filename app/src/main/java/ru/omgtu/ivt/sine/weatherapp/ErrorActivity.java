package ru.omgtu.ivt.sine.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ErrorActivity extends AppCompatActivity {

    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        errorMessage = findViewById(R.id.error_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        errorMessage.setText(intent.getStringExtra("errorDescription"));
    }
}
