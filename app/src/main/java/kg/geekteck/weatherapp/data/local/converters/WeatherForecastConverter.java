package kg.geekteck.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import kg.geekteck.weatherapp.data.models.forecast.Weather;

public class WeatherForecastConverter implements Serializable {
    @TypeConverter // note this annotation
    public String fromWeatherList(List<kg.geekteck.weatherapp.data.models.forecast.Weather> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<kg.geekteck.weatherapp.data.models.forecast.Weather>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<kg.geekteck.weatherapp.data.models.forecast.Weather> toOptionValuesList(
            String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<kg.geekteck.weatherapp.data.models.forecast.Weather>>() {
        }.getType();
        List<Weather> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}

