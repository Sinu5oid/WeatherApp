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

    private WeatherResponse weatherResponse;
    private WeatherCallback callback;

    public WeatherUtils(WeatherCallback callback) {
        this.callback = callback;
    }

    public void makeRequest(final Context context, RequestParameters requestParameters) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                buildUrl(context, requestParameters),
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
                        weatherResponse = new WeatherResponse(null);
                        Log.d("HTTP", "Response error: " + error);
                        weatherResponse.setErrorDescription(context.getString(R.string.error_connection));
                        callback.onResponseCallback(weatherResponse);
                    }
                });
        queue.add(jsonObjectRequest);
    }

    private String buildUrl(Context ctx, RequestParameters rp) {
        String units = rp.getUnits();
        String url;
        if (units.isEmpty()) {
            url = ctx.getString(R.string.base_url) +
                    "?q=" + rp.getCity() +
                    "&appid=" + ctx.getString(R.string.weather_app_key) +
                    "&lang=" + ctx.getString(R.string.language_code);
        } else {
            url = ctx.getString(R.string.base_url) +
                    "?q=" + rp.getCity() +
                    "&appid=" + ctx.getString(R.string.weather_app_key) +
                    "&lang=" + ctx.getString(R.string.language_code) +
                    "&units=" + rp.getUnits();
        }
        Log.d("HTTP", "URL builded: " + url);
        return url;
    }
}
