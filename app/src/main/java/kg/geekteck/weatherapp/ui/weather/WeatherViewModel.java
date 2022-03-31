package kg.geekteck.weatherapp.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import kg.geekteck.weatherapp.App;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;

public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<MainResponse>> liveData;

    public void getWeather(){
        liveData = App.repository.getWeatherInRussian();
    }

}
