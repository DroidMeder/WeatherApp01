package kg.geekteck.weatherapp.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;
import kg.geekteck.weatherapp.data.repositories.Repository;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<MainResponse>> liveData;
    public LiveData<Resource<ForecastResponse>> liveDataForecast;
    private Repository repository;

    @Inject
    public WeatherViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getWeatherByCityName(String lat, String lon){
        liveData = repository.getWeatherInRussianByCityName(lat, lon);
    }

    public void getForecast(String city){
        liveDataForecast = repository.getForecast(city);
    }

}
