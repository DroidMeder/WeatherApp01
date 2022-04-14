package kg.geekteck.weatherapp.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.local.WeatherDao;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;
import kg.geekteck.weatherapp.data.remote.WeatherAppApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private WeatherAppApi api;
    private WeatherDao dao;
    private final String appIdKey = "8f2532bd1258017112c5514cef4a7b8b";
    private final String units = "metric";
    private final String lang = "ru";
    private long createdAt=0L;
    private long sort=0L;


    @Inject
    public Repository(WeatherAppApi api, WeatherDao dao) {
        this.api = api;
        this.dao = dao;
    }


    //region remote
    public MutableLiveData<Resource<MainResponse>> getWeatherInRussianByCityName(String lat, String lon){
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getWeatherInRussianByCityName(lat, lon, appIdKey, units,lang).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(@NonNull Call<MainResponse> call,
                                   @NonNull Response<MainResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
                    MainResponse mainResponse = response.body();
                    mainResponse.setCreatedAt(System.currentTimeMillis());
                    createdAt=mainResponse.getCreatedAt();
                    dao.insertCurrent(response.body());
                    System.out.println("**REp***"+dao.getAllMainResponse().size());
                    System.out.println("**REp***"+dao.getAllMainResponse().get(0).getCreatedAt());
                }else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }
            @Override
            public void onFailure(@NonNull Call<MainResponse> call, @NonNull Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }

    public MutableLiveData<Resource<ForecastResponse>> getForecast(String lat, String lon){
        MutableLiveData<Resource<ForecastResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getForecast(lat, lon, appIdKey, units, lang).enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResponse> call,
                                   @NonNull Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
                    for (int i = 0; i < response.body().getList().size(); i++) {
                        kg.geekteck.weatherapp.data.models.forecast.List list =
                                response.body().getList().get(i);
                        list.setCreatedAt(createdAt);
                        dao.insertForecast(list);
                    }
                    System.out.println("-----------------------DRep set forecast---" +
                            "-----------"+dao.getAllForecast().size());
                }else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }
            @Override
            public void onFailure(@NonNull Call<ForecastResponse> call, @NonNull Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }
    //endregion

    //region local load
    //     For Current Weather
    public MutableLiveData<Resource<List<MainResponse>>> getLocalSortedMainResponse(){
        MutableLiveData<Resource<List<MainResponse>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getAllCurrentSorted()));
        System.out.println("DRep02 ----- "+dao.getAllMainResponse().size());
        return liveData;
    }
    public MutableLiveData<Resource<List<MainResponse>>> getLocalFilteredMainResponse(
            int id){
        MutableLiveData<Resource<List<MainResponse>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getCurrentById(id)));
        sort = dao.getCurrentById(id).get(0).getCreatedAt();
        System.out.println("$$$$$$$$ DRep "+dao.getCurrentById(id).size());
        return liveData;
    }
    public MutableLiveData<Resource<List<MainResponse>>> deleteLocalMainResponse(){
        MutableLiveData<Resource<List<MainResponse>>> liveData = new MutableLiveData<>();
        dao.deleteMainResponse(dao.getAllMainResponse());
        deleteLocalForecast();
        System.out.println("$$$$$$$$ DRep "+dao.getAllMainResponse().size());
        return liveData;
    }

    // for Forecast
    public MutableLiveData<Resource<List<kg.geekteck.weatherapp.data.models.forecast.List>>
            > getLocalSortedMForecast(){
        MutableLiveData<Resource<List<kg.geekteck.weatherapp.data.models.forecast.List>>
                > liveData = new MutableLiveData<>();
        List<kg.geekteck.weatherapp.data.models.forecast.List> sorted = new ArrayList<>();
        for (int i =0; i< 40; i++) {
            sorted.add(dao.getAllForecastSorted().get(i));
        }
        liveData.setValue(Resource.success(sorted));
        System.out.println("DRep002 ----- "+sorted.size());
        System.out.println(sort+"$$$$$$$$ DRep001 "+dao.getAllForecast().size());
        return liveData;
    }
    public MutableLiveData<Resource<List
            <kg.geekteck.weatherapp.data.models.forecast.List>>> getLocalFilteredForecast(){
        MutableLiveData<Resource<List<kg.geekteck.weatherapp.data.models.forecast.List>>> liveData =
                new MutableLiveData<>();
        liveData.setValue(Resource.success(dao.getForecastByDt(sort)));
        System.out.println(sort+"$$$$$$$$ DRep00 "+dao.getForecastByDt(sort).size());
        System.out.println(sort+"$$$$$$$$ DRep001 "+dao.getAllForecast().size());
        return liveData;
    }

    public MutableLiveData<Resource<List<kg.geekteck.weatherapp.data.models.forecast.List>>> deleteLocalForecast(){
        MutableLiveData<Resource<List<kg.geekteck.weatherapp.data.models.forecast.List>>> liveData =
                new MutableLiveData<>();
        dao.deleteForecast(dao.getAllForecast());
        System.out.println("$$$$$$$$ DRep00 "+dao.getAllForecast().size());
        return liveData;
    }
    //endregion

}
