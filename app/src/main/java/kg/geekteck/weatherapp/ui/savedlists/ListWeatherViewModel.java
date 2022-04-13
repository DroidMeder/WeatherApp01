package kg.geekteck.weatherapp.ui.savedlists;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.repositories.Repository;

@HiltViewModel
public class ListWeatherViewModel extends ViewModel {
    public LiveData<Resource<List<MainResponse>>> liveDataGetLocalCurrent;
    private Repository repository;

    @Inject
    public ListWeatherViewModel(Repository repository) {
        this.repository = repository;
    }

    public void getLocalCurrentWeather(){
        liveDataGetLocalCurrent = repository.getLocalSortedMainResponse();
    }
    public void cleanLocalCurrentWeather(){
        liveDataGetLocalCurrent = repository.deleteLocalMainResponse();
    }
}
