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
    public LiveData<Resource<List<kg.geekteck.weatherapp.data.models.forecast.List>>> liveDataGetLocalForecast;


    private Repository repository;

    @Inject
    public WeatherViewModel(Repository repository) {
        this.repository = repository;
    }

    //region remote

    public void getWeatherByCityName(String lat, String lon){
        System.out.println("remote wvm _________by latlon______");
        liveData = repository.getWeatherInRussianByCityName(lat, lon);
    }

    public void getForecast(String lat, String lon){
        liveDataForecast = repository.getForecast(lat, lon);
    }
//endregion

    //region local

    // --------------Current --------------
    public void getLocalCurrentWeather(int id){
        System.out.println("666 WVM glcwID 6666666 "+id);
        liveDataGetLocalCurrent = repository.getLocalFilteredMainResponse(id);
        try {
            System.out.println("66   wvm glcwID 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalCurrent.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("666 wvm glcwID 6666666 "+repository.getLocalFilteredMainResponse(id));
    }

    public void getLocalLastMainResponse(){
        liveDataGetLocalCurrent = repository.getLocalSortedMainResponse();
        try {
            System.out.println("66   wvm glcwAll 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalCurrent.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------forecast ----------------
    public void getLocalForecastById(){
        System.out.println("666 WVM glfID 6666666 ");
        liveDataGetLocalForecast = repository.getLocalFilteredForecast();
        try {
            System.out.println("66   wvm glfID 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalForecast.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("666 wvm glfID 6666666 "+repository.getLocalFilteredForecast());
    }

    public void getLocalLastForecast(){
        liveDataGetLocalForecast = repository.getLocalSortedMForecast();
        try {
            System.out.println("66   wvm glfAll 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalForecast.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //endregion

}
