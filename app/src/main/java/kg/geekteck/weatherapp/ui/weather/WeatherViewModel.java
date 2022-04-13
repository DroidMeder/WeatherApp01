package kg.geekteck.weatherapp.ui.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;

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
    public LiveData<Resource<List<MainResponse>>> liveDataGetLocalCurrent;


    private Repository repository;

    @Inject
    public WeatherViewModel(Repository repository) {
        this.repository = repository;
    }

    //region remote
    public void getWeatherByCityName(String lat, String lon){
        liveData = repository.getWeatherInRussianByCityName(lat, lon);
    }

    public void getForecast(String lat, String lon){
        liveDataForecast = repository.getForecast(lat, lon);
    }

    public void getLocalCurrentWeather(int id){
        System.out.println("666 WVM glcw 6666666 "+id);
        liveDataGetLocalCurrent = repository.getLocalFilteredMainResponse(id);
        try {
            System.out.println("66   wvm glcw 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalCurrent.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("666 wvm glcw 6666666 "+repository.getLocalFilteredMainResponse(id));
    }

    public void getLocalLastMainResponse(){
        liveDataGetLocalCurrent = repository.getLocalSortedMainResponse();
        try {
            System.out.println("66   wvm glcw 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalCurrent.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
