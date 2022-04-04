package kg.geekteck.weatherapp.data.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;
import kg.geekteck.weatherapp.data.remote.WeatherAppApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private final WeatherAppApi api;
    private final String appIdKey = "8f2532bd1258017112c5514cef4a7b8b";
    private final String units = "metric";
    private final String lang = "ru";

    @Inject
    public Repository(WeatherAppApi api) {
        this.api = api;
    }

    public MutableLiveData<Resource<MainResponse>> getWeatherInRussianByCityName(String lat, String lon){
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getWeatherInRussianByCityName(lat, lon, appIdKey, units,lang).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(@NonNull Call<MainResponse> call,
                                   @NonNull Response<MainResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
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

    public MutableLiveData<Resource<MyResponse>> getCitiesName(String city){
        MutableLiveData<Resource<MyResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getCitiesName(city,"10", appIdKey,lang).enqueue(new Callback<List<MyResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<MyResponse>> call,
                                   @NonNull Response<List<MyResponse>> response) {
                System.out.println(response.body());
                if (response.isSuccessful() && response.body()!=null){
                    int i = response.body().size();
                    System.out.println(i+"--success---"+response.message());
                    for (int j = 0; j < i; j++) {
                        System.out.println("index ---"+j);
                        liveData.setValue(Resource.success(response.body().get(j)));
                    }
                }else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MyResponse>> call, @NonNull Throwable t) {
                System.out.println("error "+t.getLocalizedMessage());
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });
        return liveData;
    }

    public MutableLiveData<Resource<ForecastResponse>> getForecast(String city){
        MutableLiveData<Resource<ForecastResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getForecast(city, appIdKey, units, lang).enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForecastResponse> call,
                                   @NonNull Response<ForecastResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
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
}
