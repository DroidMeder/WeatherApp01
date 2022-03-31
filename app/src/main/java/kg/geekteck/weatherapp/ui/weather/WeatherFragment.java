package kg.geekteck.weatherapp.ui.weather;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import kg.geekteck.weatherapp.R;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private WeatherViewModel viewModel;
    private int time= 0;
    private int dayTime;
    private int nightTime = 0;

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        if (time>=nightTime){
            binding.ivBackground.setImageResource(R.drawable.ic_graphic_city_night);
        }else {
            binding.ivBackground.setImageResource(R.drawable.ic_graphic_city_day);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // binding.rvWeather.setAdapter(adapter);
        viewModel.getWeather();
        viewModel.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<MainResponse>>() {
            @Override
            public void onChanged(Resource<MainResponse> mainResponseResource) {
                switch (mainResponseResource.status){
                    case SUCCESS:{
                        binding.clWeather.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);
                        time = mainResponseResource.data.getTimezone() +
                                mainResponseResource.data.getDt();
                        nightTime = mainResponseResource.data.getSys().getSunset();
                        dayTime = mainResponseResource.data.getSys().getSunrise();
                        if (time>=nightTime){
                            binding.ivBackground.setImageResource(R.drawable.ic_graphic_city_night);
                        }else {
                            binding.ivBackground.setImageResource(R.drawable.ic_graphic_city_day);
                        }
                        setViews(mainResponseResource.data);
                        //adapter.setMainResponseList(mainResponseResource.data.g);
                        System.out.println("Success--------------");
                        break;
                    }
                    case ERROR: {
                        binding.clWeather.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.GONE);
                        Snackbar.make(binding.getRoot(), mainResponseResource.msc,
                                BaseTransientBottomBar.LENGTH_LONG)
                                .show();
                        break;
                    }
                    case LOADING: {
                        binding.clWeather.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }
        });
    }

    private void setViews(MainResponse mainResponse) {
        int updatedAt = mainResponse.getDt(); //jsonObj.getLong(“dt”);
        int localTime = mainResponse.getTimezone();

        String updatedAtText = new SimpleDateFormat("E, dd MMM yyyy | HH:mm", Locale.ENGLISH).format(
                new Date((updatedAt +localTime)*1000l));

        binding.tvDayDateTime.setText(updatedAtText);
        binding.tvLocation.setText(mainResponse.getName().toString()+", "
                +mainResponse.getSys().getCountry().toString());

        String url = "http://openweathermap.org/img/wn/"+mainResponse.getWeather().get(0).getIcon()+"@2x.png";
        Uri uri = Uri.parse(url);
        Glide.with(binding.getRoot())
                .load(uri)
                .centerCrop()
                .into(binding.ivIsSkyClear);
        binding.tvIsSkyClear.setText(mainResponse.getWeather().get(0).getDescription());

        binding.tvTemperature1.setText(String.format("%s", mainResponse.getMain().getTemp()));

        binding.tvMaxTemp.setText(String.format("%s", mainResponse.getMain().getTempMax()));
        binding.tvMinTemp.setText(String.format("%s", mainResponse.getMain().getTempMin()));

        String str = String.format("%s", mainResponse.getMain().getHumidity());
        binding.tvHumidity.setText(str+"%");

        binding.tvBarometer.setText(String.format("%s mBar", mainResponse.getMain().getPressure()));

        binding.tvWind.setText(String.format("%s km/h", mainResponse.getWind().getSpeed()));

        Integer date =  mainResponse.getSys().getSunrise();
        String updatedAtText1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                new Date(date * 1000));
        binding.tvSunrise.setText(updatedAtText1);

        date =  mainResponse.getSys().getSunset();
        updatedAtText1 = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                new Date(date * 1000));
        binding.tvSunset.setText(updatedAtText1);


        date = mainResponse.getSys().getSunset()-mainResponse.getSys().getSunrise();
        updatedAtText1 = new SimpleDateFormat("hh m", Locale.ENGLISH).format(
                new Date(date * 1000));
        String[] hours = updatedAtText1.split(" ");
        binding.tvDaytime.setText(hours[0]+"h "+hours[1]+"m");
    }
}