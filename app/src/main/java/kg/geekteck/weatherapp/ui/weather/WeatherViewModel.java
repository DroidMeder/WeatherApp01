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
import kg.geekteck.weatherapp.data.models.room.CityName;
import kg.geekteck.weatherapp.data.models.room.CurrentWeather;
import kg.geekteck.weatherapp.data.models.room.ForecastWeather;
import kg.geekteck.weatherapp.data.repositories.DaoRepository;
import kg.geekteck.weatherapp.data.repositories.Repository;

@HiltViewModel
public class WeatherViewModel extends ViewModel {

    public LiveData<Resource<MainResponse>> liveData;
    public LiveData<Resource<ForecastResponse>> liveDataForecast;
    public LiveData<Resource<ForecastWeather>> liveDataSetLocalForecast;
    public LiveData<Resource<CurrentWeather>> liveDataSetLocalCurrent;
    public LiveData<Resource<List<ForecastWeather>>> liveDataGetLocalForecast;
    public LiveData<Resource<List<CurrentWeather>>> liveDataGetLocalCurrent;


    private Repository repository;
    private DaoRepository daoRepository;

    @Inject
    public WeatherViewModel(Repository repository, DaoRepository daoRepository) {
        this.repository = repository;
        this.daoRepository = daoRepository;
    }

    //region remote
    public void getWeatherByCityName(String lat, String lon){
        liveData = repository.getWeatherInRussianByCityName(lat, lon);
    }

    public void getForecast(String lat, String lon){
        liveDataForecast = repository.getForecast(lat, lon);
    }
    //endregion
    //region local
    //region set
    public void setLocalCurrentWeather(CurrentWeather citiesWeather){
        System.out.println(citiesWeather.getLat()+" 11 WVM--CW 11111111");
        liveDataSetLocalCurrent = daoRepository.setLocalCurrentWeather(citiesWeather);
        //System.out.println(daoRepository.+"11 WVM--CW 11111111");

    }

    public void setLocalForecastWeather(ForecastWeather forecastWeather){
        System.out.println(forecastWeather.getLat()+"11 WVM -- FW 11111111");
        liveDataSetLocalForecast = daoRepository.setLocalForecastWeather(forecastWeather);
    }
    //endregion
    //region get
    public void getLocalCurrentWeather(String lat, String lon){
        System.out.println("666 WVM glcw 6666666"+lat+lon);
        liveDataGetLocalCurrent = daoRepository.getLocalCurrentWeather(lat, lon);
        try {
            System.out.println("66   wvm glcw 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalCurrent.getValue()).data.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("666 wvm glcw 6666666 "+daoRepository.getLocalCurrentWeather(lat,lon));
    }

    public void getLocalForecastWeather(String lat, String lon){
        System.out.println("666 wvm glfw 6666666 "+lat+lon);
        liveDataGetLocalForecast = daoRepository.getLocalForecastWeather(lat, lon);
        try {
            System.out.println("66   wvm glfw 66 ======"+
                    Objects.requireNonNull(liveDataGetLocalForecast.getValue()).data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("666 wvm glfw 6666666 "+daoRepository.getLocalForecastWeather(lat,lon).toString());
    }
    //endregion
    //endregion

}
