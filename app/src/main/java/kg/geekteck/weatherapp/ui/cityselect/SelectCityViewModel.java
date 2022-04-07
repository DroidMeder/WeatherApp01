package kg.geekteck.weatherapp.ui.cityselect;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;
import kg.geekteck.weatherapp.data.models.room.CityName;
import kg.geekteck.weatherapp.data.repositories.DaoRepository;
import kg.geekteck.weatherapp.data.repositories.Repository;

@HiltViewModel
public class SelectCityViewModel extends ViewModel {
    public LiveData<Resource<MyResponse>> liveData;
    public LiveData<Resource<CityName>> liveData1;
    public LiveData<Resource<List<CityName>>> liveData2;

    private Repository repository;
    private DaoRepository daoRepository;

    @Inject
    public SelectCityViewModel(Repository repository, DaoRepository daoRepository) {
        System.out.println(repository.toString()+" 4444 SC 4444444 "+daoRepository.toString());
        this.repository = repository;
        this.daoRepository=daoRepository;
    }

    public void getCitiesName(String city){
        liveData = repository.getCitiesName(city);
    }

    public void getLocalCitiesName(String city){
        System.out.println("666 SC 6666666"+city);
        liveData2 = daoRepository.getLocalCityName(city);
        try {
            System.out.println("66   sc 66 ======"+liveData2.getValue().data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("666 SC 6666666"+daoRepository.getLocalCityName(city));
    }

    public void setLocalCitiesName(CityName name){
        System.out.println(name.getName()+"11 SC 11111111");
        liveData1 = daoRepository.setLocalCityName(name);
    }
}
