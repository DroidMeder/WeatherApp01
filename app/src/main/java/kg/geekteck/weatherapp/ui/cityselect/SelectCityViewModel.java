package kg.geekteck.weatherapp.ui.cityselect;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;
import kg.geekteck.weatherapp.data.repositories.Repository;

@HiltViewModel
public class SelectCityViewModel extends ViewModel {
    public LiveData<Resource<MyResponse>> liveData;
    private Repository repository;

    public void getCitiesName(String city){
        liveData = repository.getCitiesName(city);
    }

    @Inject
    public SelectCityViewModel(Repository repository) {
        this.repository = repository;
    }

}
