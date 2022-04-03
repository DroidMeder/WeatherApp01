package kg.geekteck.weatherapp.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kg.geekteck.weatherapp.App;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;

public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<MainResponse>> liveData;
    public LiveData<Resource<ForecastResponse>> liveDataForecast;

    public void getWeatherByCityName(String city){
        liveData = App.repository.getWeatherInRussianByCityName(city);
    }

    public void getForecast(String city){
        liveDataForecast = App.repository.getForecast(city);
    }

}
