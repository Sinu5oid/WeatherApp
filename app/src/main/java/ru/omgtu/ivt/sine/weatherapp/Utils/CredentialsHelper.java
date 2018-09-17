package ru.omgtu.ivt.sine.weatherapp.Utils;

import android.widget.TextView;

import java.util.HashMap;

public class CredentialsHelper {
    private HashMap<String, String> credentials;
    private String enteredLogin, enteredPassword;

    public CredentialsHelper() {
        credentials = new HashMap<>();
        credentials.put("root", "root");
    }

    public boolean isValidCredentials(TextView login_field, TextView password_field) {
        enteredLogin = login_field.getText().toString();
        enteredPassword = password_field.getText().toString();
        return
                credentials.containsKey(enteredLogin) &&
                credentials.get(enteredLogin).equals(enteredPassword);
    }
}
