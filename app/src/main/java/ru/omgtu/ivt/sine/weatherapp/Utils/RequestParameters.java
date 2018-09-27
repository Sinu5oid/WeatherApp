package ru.omgtu.ivt.sine.weatherapp.Utils;

import android.content.Context;

import ru.omgtu.ivt.sine.weatherapp.R;

public class RequestParameters {

    public static final int UNITS_DEFAULT = 0;
    public static final int UNITS_CELSIUM = 1;
    public static final int UNITS_FARENHEIT = 2;
    public static final int UNITS_KELVIN = 3;
    private String units;
    private String city;
    private String unitsDegreeMark;
    private String unitsSpeedMark;
    private Context ctx;

    public RequestParameters(final Context ctx) {
        this.city = "";
        setUnits(UNITS_DEFAULT);
        this.ctx = ctx;
    }

    public RequestParameters(String city) {
        this.city = city;
        setUnits(UNITS_DEFAULT);
    }

    public RequestParameters(String city, int units) {
        this.city = city;
        setUnits(units);
    }

    public String getUnitsSpeedMark() {
        return unitsSpeedMark;
    }

    public String getUnitsDegreeMark() {
        return unitsDegreeMark;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(int units) {
        switch (units) {
            case UNITS_CELSIUM:
            case UNITS_DEFAULT:
            default:
                this.units = "metric";
                unitsDegreeMark = "°C";
                if (ctx != null) {
                    unitsSpeedMark = ctx.getString(R.string.speed_units_metersph);
                }
                break;
            case UNITS_FARENHEIT:
                this.units = "imperial";
                unitsDegreeMark = "°F";
                if (ctx != null) {
                    unitsSpeedMark = ctx.getString(R.string.speed_units_milesph);
                }
                break;
            case UNITS_KELVIN:
                this.units = "";
                unitsDegreeMark = "K";
                if (ctx != null) {
                    unitsSpeedMark = ctx.getString(R.string.speed_units_metersph);
                }
                break;
        }
    }

    public int getUnitsCode() {
        switch (units) {
            case "metric":
            default:
                return UNITS_DEFAULT;
            case "imperial":
                return UNITS_FARENHEIT;
            case "":
                return UNITS_KELVIN;
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
