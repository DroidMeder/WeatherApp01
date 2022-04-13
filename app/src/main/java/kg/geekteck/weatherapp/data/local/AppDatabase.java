package kg.geekteck.weatherapp.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kg.geekteck.weatherapp.data.models.MainResponse;

@Database(entities = {MainResponse.class}, version = 12
        , exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract WeatherDao newsDao();
}
