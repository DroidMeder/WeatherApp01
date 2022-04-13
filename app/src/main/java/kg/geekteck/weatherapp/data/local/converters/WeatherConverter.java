package kg.geekteck.weatherapp.data.local.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import kg.geekteck.weatherapp.data.models.Weather;

public class WeatherConverter implements Serializable {
    @TypeConverter // note this annotation
    public String fromWeatherList(List<Weather> optionValues) {
        if (optionValues == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Weather>>() {
        }.getType();
        String json = gson.toJson(optionValues, type);
        return json;
    }

    @TypeConverter // note this annotation
    public List<Weather> toOptionValuesList(String optionValuesString) {
        if (optionValuesString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Weather>>() {
        }.getType();
        List<Weather> productCategoriesList = gson.fromJson(optionValuesString, type);
        return productCategoriesList;
    }
}

