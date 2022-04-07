package kg.geekteck.weatherapp.data.repositories;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.local.WeatherDao;
import kg.geekteck.weatherapp.data.models.room.CityName;
import kg.geekteck.weatherapp.data.models.room.CurrentWeather;
import kg.geekteck.weatherapp.data.models.room.ForecastWeather;

public class DaoRepository {
    private WeatherDao dao;

    @Inject
    public DaoRepository(WeatherDao dao) {
        this.dao = dao;
    }

    //region local save
    public MutableLiveData<Resource<CityName>> setLocalCityName(CityName cityName){
        MutableLiveData<Resource<CityName>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        System.out.println(cityName.getName()+"222222 DRep 2222222");
        dao.insertCityName(cityName);
        System.out.println("DRep ----- "+dao.getAllNews());
        return liveData;
    }
    public MutableLiveData<Resource<CurrentWeather>> setLocalCurrentWeather(CurrentWeather weather){
        MutableLiveData<Resource<CurrentWeather>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        dao.insertCurrentWeather(weather);
        System.out.println("DRep1 ----- "+dao.getAllCurrent().size());
        return liveData;
    }
    public MutableLiveData<Resource<ForecastWeather>> setLocalForecastWeather(ForecastWeather weather){
        MutableLiveData<Resource<ForecastWeather>> liveData = new MutableLiveData<>();
        dao.insertForecastWeather(weather);
        System.out.println("DRep2 ----- "+dao.getAllForecast().size());
        return liveData;
    }
    //endregion

    //region local load
    public MutableLiveData<Resource<List<CityName>>> getLocalCityName(String hintName){
        MutableLiveData<Resource<List<CityName>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getCities(hintName)));
        System.out.println("555DRep55555"+hintName);
        System.out.println("DRep01 ----- "+dao.getAllNews().size());

        System.out.println("$$$$$$$$ DRep "+dao.getCities(hintName).size());
        return liveData;
    }
    public MutableLiveData<Resource<List<CurrentWeather>>> getLocalCurrentWeather(String lat, String lon){
        MutableLiveData<Resource<List<CurrentWeather>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getCurrentWeather(lat, lon)));
        System.out.println("DRep02 ----- "+dao.getAllCurrent().size());

        System.out.println("$$$$$$$$ DRep "+dao.getCurrentWeather(lat, lon).size());
        return liveData;
    }
    public MutableLiveData<Resource<List<ForecastWeather>>> getLocalForecastWeather(String lat, String lon){
        MutableLiveData<Resource<List<ForecastWeather>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getForecastWeather(lat, lon)));
        System.out.println("DRep02 ----- "+dao.getAllForecast().size());

        System.out.println("$$$$$$$$ DRep "+dao.getForecastWeather(lat, lon).size());
        return liveData;
    }
    //endregion
}
