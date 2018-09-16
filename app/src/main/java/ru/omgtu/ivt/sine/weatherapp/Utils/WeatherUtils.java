package ru.omgtu.ivt.sine.weatherapp.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import ru.omgtu.ivt.sine.weatherapp.R;

public class WeatherUtils {

    public WeatherUtils(WeatherCallback callback){
        this.callback = callback;
    }

    public void makeRequest(Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                buildUrl(context),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        weatherResponse = new WeatherResponse(response);
                        Log.d("HTTP", "Response success: " + response.toString());
                        callback.onResponseCallback(weatherResponse);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTP", "Response error: " + error);
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private String buildUrl(Context ctx) {
        String url = ctx.getString(R.string.base_url) +
                "?id=" + ctx.getString(R.string.city_id) +
                "&appid=" + ctx.getString(R.string.weather_app_key) +
                "&lang=" + ctx.getString(R.string.language_code) +
                "&units=" + ctx.getString(R.string.units_code);
        Log.d("HTTP", "URL builded: " + url);
        return url;
    }

    private WeatherResponse weatherResponse;
    private WeatherCallback callback;
}
