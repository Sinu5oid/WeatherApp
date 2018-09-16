package ru.omgtu.ivt.sine.weatherapp.Utils;

import android.util.Log;

import java.util.HashMap;

public class CredentialsHelper {
    HashMap<String, String> credentials;

    public CredentialsHelper() {
        credentials = new HashMap<>();
        credentials.put("root", "root");
    }

    public HashMap<String, String> getCredentials() {
        return credentials;
    }
}
