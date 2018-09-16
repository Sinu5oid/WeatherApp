package ru.omgtu.ivt.sine.weatherapp.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** FORMAT EXAMPLE
 *
 * {
     "coord": {
         "lon":73.4,
         "lat":55
     },
     "weather": [
         {
             "id": 803,
             "main": "Clouds",
             "description": "пасмурно",
             "icon": "04d"
         }
     ],
     "base": "stations",
     "main": {
         "temp": 12,
         "pressure": 1020,
         "humidity": 54,
         "temp_min": 12,
         "temp_max": 12
     },
     "visibility": 10000,
     "wind":{
         "speed": 3,
         "deg": 330
     },
     "clouds": {
         "all": 75
     },
     "dt":1537097400,
     "sys": {
         "type": 1,
         "id": 7289,
         "message": 0.0044,
         "country": "RU",
         "sunrise": 1537058457,
         "sunset": 1537104013
     },
     "id": 1496153,
     "name": "Omsk",
     "cod": 200
 }
 *
 **/

public class WeatherResponse {


    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getWind() {
        return wind;
    }

    private String
            description,
            name;
    private double
            temp,
            pressure,
            humidity,
            temp_min,
            temp_max,
            wind;

    public WeatherResponse(JSONObject o) {
        try {
            JSONArray weather_array = o.getJSONArray("weather");
            JSONObject weather = weather_array.getJSONObject(0);
            this.description = weather.getString("description");

            JSONObject main = o.getJSONObject("main");
            this.temp = main.getDouble("temp");
            this.pressure = main.getDouble("pressure");
            this.humidity = main.getDouble("humidity");
            this.temp_min = main.getDouble("temp_min");
            this.temp_max = main.getDouble("temp_max");

            JSONObject wind = o.getJSONObject("wind");
            this.wind = wind.getDouble("speed");

            this.name = o.getString("name");
        } catch (JSONException je) {
            Log.d("PARSING", "Error while parsing JSON: " + je.getMessage());
        }
    }
}
