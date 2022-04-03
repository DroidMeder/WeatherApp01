package kg.geekteck.weatherapp.ui.cityselect;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

//import javax.inject.Inject;

//import dagger.hilt.android.lifecycle.HiltViewModel;
//import javax.inject.Inject;

//import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geekteck.weatherapp.App;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.citynames.CityResponse;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;

//@HiltViewModel
public class SelectCityViewModel extends ViewModel {
    public LiveData<Resource<MyResponse>> liveData;
    //private Repository repository;

    public void getCitiesName(String city){
        liveData = App.repository.getCitiesName(city);
    }

  /*  @Inject
    public SelectCityViewModel(LiveData<Resource<CityArrayResponse>> liveData) {
        this.liveData = liveData;
    }*/

   /* @Inject
    public void getWeatherOfCity(String city){
        liveData= repository.getCityName(city);
    }*/
}
