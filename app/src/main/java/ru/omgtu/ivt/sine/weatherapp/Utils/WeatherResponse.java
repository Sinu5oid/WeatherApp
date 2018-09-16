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

    public long getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public long getHumidity() {
        return humidity;
    }

    public long getTemp_min() {
        return temp_min;
    }

    public long getTemp_max() {
        return temp_max;
    }

    public long getWind() {
        return wind;
    }

    public boolean getErrorFlag() {
        return errorFlag;
    }

    private String
            description,
            name;
    private long
            temp,
            humidity,
            temp_min,
            temp_max,
            wind;
    private double pressure;
    private boolean errorFlag;

    public WeatherResponse(JSONObject jsonObject) {
        if (jsonObject == null) {
            resetFieldsByDefault();
            errorFlag = true;
            return;
        }

        try {
            JSONArray weather_array = jsonObject.getJSONArray("weather");
            JSONObject weather = weather_array.getJSONObject(0);
            this.description = weather.getString("description");

            JSONObject main = jsonObject.getJSONObject("main");

            this.temp = Math.round(main.getDouble("temp"));
            this.pressure = main.getDouble("pressure") * 0.75006375541921;
            this.humidity = Math.round(main.getDouble("humidity"));
            this.temp_min = Math.round(main.getDouble("temp_min"));
            this.temp_max = Math.round(main.getDouble("temp_max"));

            JSONObject wind = jsonObject.getJSONObject("wind");
            this.wind = Math.round(wind.getDouble("speed"));

            this.name = jsonObject.getString("name");
        } catch (JSONException je) {
            resetFieldsByDefault();
            Log.d("PARSING", "Error while parsing JSON: " + je.getMessage());
        }
    }

    private void resetFieldsByDefault(){
        description = "";
        name = "";
        temp = 0;
        humidity = 0;
        temp_min = 0;
        temp_max = 0;
        wind = 0;
        pressure = 0;
    }
}
