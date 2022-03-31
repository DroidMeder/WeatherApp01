package kg.geekteck.weatherapp.data.repositories;

import androidx.lifecycle.MutableLiveData;

import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.remote.WeatherAppApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private WeatherAppApi api;
    private String cityName;
    private String appIdKey;
    private String units;
    private String lang;

    public Repository(WeatherAppApi api, String cityName, String appId, String units, String lang) {
        this.api = api;
        this.cityName = cityName;
        appIdKey = appId;
        this.units = units;
        this.lang = lang;
    }

    public Repository(WeatherAppApi api, String cityName) {
        this.api = api;
        this.cityName = cityName;
    }
public Repository(WeatherAppApi api) {
        this.api = api;
        this.cityName = cityName;
    }

    public MutableLiveData<Resource<MainResponse>> getWeatherInRussian(){
        MutableLiveData<Resource<MainResponse>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading());
        api.getWeatherInRussian().enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
                }else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }
            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });

      /*  api.getWeatherInRussian(cityName, appIdKey, units, lang).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                if (response.isSuccessful() && response.body()!= null){
                    liveData.setValue(Resource.success(response.body()));
                }else {
                    liveData.setValue(Resource.error(response.message(), null));
                }
            }
            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                liveData.setValue(Resource.error(t.getLocalizedMessage(), null));
            }
        });*/
        return liveData;
    }
}
