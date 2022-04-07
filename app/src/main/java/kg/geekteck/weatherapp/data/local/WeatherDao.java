package kg.geekteck.weatherapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kg.geekteck.weatherapp.data.models.room.CityName;
import kg.geekteck.weatherapp.data.models.room.CurrentWeather;
import kg.geekteck.weatherapp.data.models.room.ForecastWeather;

@Dao
public interface WeatherDao {

    //region insert block
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentWeather(CurrentWeather currentWeather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecastWeather(ForecastWeather forecastWeather);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCityName(CityName cityName);
    //endregion

    //region get block
    @Query("SELECT * FROM cityname")
    List<CityName> getAllNews();

    @Query("SELECT * FROM forecastweather WHERE  lat = :lat AND lon = :lon")
    List<ForecastWeather> getForecastWeather(String lat, String lon);

    @Query("SELECT * FROM forecastweather")
    List<CityName> getAllForecast();

    @Query("SELECT * FROM cityname WHERE hintName LIKE '%'|| :search || '%'")
    List<CityName> getCities(String search);

    @Query("SELECT * FROM currentweather WHERE lat = :lat AND lon = :lon ")
    List<CurrentWeather> getCurrentWeather(String lat, String lon);

    @Query("SELECT * FROM currentweather")
    List<CityName> getAllCurrent();
    //endregion

}
