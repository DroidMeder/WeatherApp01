package kg.geekteck.weatherapp.ui.savedlists;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geekteck.weatherapp.base.BaseFragment;
import kg.geekteck.weatherapp.common.OnItemClick;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.databinding.FragmentListWeatherBinding;

@AndroidEntryPoint
public class ListWeatherFragment extends BaseFragment<FragmentListWeatherBinding> implements OnItemClick {
    private ListWeatherViewModel viewModel;
    private ListWeatherAdapter adapter;


    public ListWeatherFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ListWeatherAdapter(this);
        viewModel=new ViewModelProvider(requireActivity()).get(ListWeatherViewModel.class);
        //viewModel = new ViewModelProvider(requireActivity()).get(ListWeatherViewModel.class);
    }

    @Override
    protected FragmentListWeatherBinding bind() {
        return FragmentListWeatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        binding.recList.setAdapter(adapter);
    }

    @Override
    protected void callRequests() {
        viewModel.getLocalCurrentWeather();
    }

    @Override
    protected void setupListener() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.cleanLocalCurrentWeather();
                setupObserver();
            }
        });
    }

    @Override
    protected void setupObserver() {
        viewModel.getLocalCurrentWeather();
        viewModel.liveDataGetLocalCurrent.observe(getViewLifecycleOwner(),
                cR -> {
                    switch (cR.status) {
                        case SUCCESS: {
                            adapter.setList(cR.data);
                            System.out.println("Success--LWF-----LCW-------");
                            break;
                        }
                        case ERROR: {
                            System.out.println("Error==LWF==LCW==" + cR.msc);
                            break;
                        }
                        case LOADING: {
                            System.out.println("===LWF==LCW==Loading " + cR.msc);
                            break;
                        }
                    }
                });
    }

    @Override
    public void buttonClick(Object data) {
    }

    @Override
    public void click(MainResponse currentWeather) {
        String data = currentWeather.getCoord().getLat() +":" +currentWeather.getCoord().getLon()
                +":list:"+currentWeather.getId();
        navController.navigate(ListWeatherFragmentDirections
                .actionListWeatherFragmentToWeatherFragment(data));
    }
}