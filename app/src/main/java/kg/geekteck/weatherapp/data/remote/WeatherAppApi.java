package kg.geekteck.weatherapp.data.remote;

import kg.geekteck.weatherapp.data.models.MainResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherAppApi {

    //https://api.openweathermap.org/data/2.5/weather?q=Sokuluk&appid=8f2532bd1258017112c5514cef4a7b8b&units=metric&lang=ru
    @GET("/data/2.5/weather?q=Bishkek&appid=8f2532bd1258017112c5514cef4a7b8b&units=metric&lang=ru")
    Call<MainResponse> getWeatherInRussian();

    //Call<WeatherResponse> getCurrentWeatherData(@Query("lat") String lat, @Query("lon") String lon, @Query("APPID") String app_id);

  /*  @GET("/data/2.5/weather?")
    Call<MainResponse> getWeatherInRussian(@Query("city name") String city,
            @Query("APPID") String app_id,
            @Query("units") String units,
            @Query("lang") String lang);
*/

}
