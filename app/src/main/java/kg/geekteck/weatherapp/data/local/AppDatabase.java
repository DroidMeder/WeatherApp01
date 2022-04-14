package kg.geekteck.weatherapp.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;
import kg.geekteck.weatherapp.data.models.forecast.List;

@Database(entities = {MainResponse.class, List.class}, version = 27
        , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract WeatherDao newsDao();
}
