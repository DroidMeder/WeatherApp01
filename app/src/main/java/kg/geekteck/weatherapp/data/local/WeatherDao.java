package kg.geekteck.weatherapp.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrent(MainResponse mainResponse);

    @Delete
    void deleteMainResponse(List<MainResponse> currentWeather);

    @Query("SELECT * FROM mainresponse")
    List<MainResponse> getAllMainResponse();

    @Query("SELECT * FROM mainresponse  ORDER BY createdAt DESC")
    List<MainResponse> getAllCurrentSorted();

    @Query("SELECT * FROM mainresponse WHERE id=:id")
    List<MainResponse> getCurrentById(int id);

    //forecast
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertForecast(kg.geekteck.weatherapp.data.models.forecast.List list);

    @Delete
    void deleteForecast(List<kg.geekteck.weatherapp.data.models.forecast.List> forecastWeather);

    @Query("SELECT * FROM list")
    List<kg.geekteck.weatherapp.data.models.forecast.List> getAllForecast();

    @Query("SELECT * FROM list WHERE createdAt = :dt")
    List<kg.geekteck.weatherapp.data.models.forecast.List> getForecastByDt(long dt);

    @Query("SELECT * FROM list  ORDER BY createdAt DESC")
    List<kg.geekteck.weatherapp.data.models.forecast.List> getAllForecastSorted();

}
