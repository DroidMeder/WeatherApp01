package kg.geekteck.weatherapp.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import javax.inject.Inject;

import kg.geekteck.weatherapp.data.models.room.CityName;
import kg.geekteck.weatherapp.data.models.room.CurrentWeather;
import kg.geekteck.weatherapp.data.models.room.ForecastWeather;

@Database(entities = {CityName.class, CurrentWeather.class, ForecastWeather.class}, version = 10
        , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract WeatherDao newsDao();
}
