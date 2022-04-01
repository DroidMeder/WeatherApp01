package kg.geekteck.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import kg.geekteck.weatherapp.R;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.databinding.FragmentWeatherBinding;

public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private WeatherViewModel viewModel;
    private int time;
    private int dayTime =1;
    private int nightTime;
    private int updatedAt;
    private int localTime;

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
        if (time<=nightTime){
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
                        if (time<=nightTime){
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

    int i=0;
    private String getDate(long updatedAt, String dateFormat){

        System.out.println(++i +"--=-----------=-==- "+updatedAt);

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(updatedAt);

        System.out.println(format.format(calendar.getTime())+"!!!!!!!!!!!!!");
        return format.format(calendar.getTime());
    }

    @SuppressLint("SetTextI18n")
    private void setViews(MainResponse mainResponse) {
        updatedAt = mainResponse.getDt();
        localTime = mainResponse.getTimezone();

        binding.tvDayDateTime.setText(getDate((updatedAt+localTime)* 1000L, "E, dd MMM yyyy | HH:mm a"));
        binding.tvLocation.setText(mainResponse.getName().toString()+", "
                +mainResponse.getSys().getCountry().toString());

        String url = "http://openweathermap.org/img/wn/"+mainResponse.getWeather().get(0).getIcon()+"@2x.png";

        Glide.with(binding.getRoot())
                .load(url)
                .centerCrop()
                .into(binding.ivIsSkyClear);
        binding.tvIsSkyClear.setText(mainResponse.getWeather().get(0).getMain());

        binding.tvTemperature1.setText(String.format("%s", mainResponse.getMain().getTemp()));

        binding.tvMaxTemp.setText(String.format("%s", mainResponse.getMain().getTempMax()));
        binding.tvMinTemp.setText(String.format("%s", mainResponse.getMain().getTempMin()));

        String str = String.format("%s", mainResponse.getMain().getHumidity());
        binding.tvHumidity.setText(str+"%");

        binding.tvBarometer.setText(String.format("%s mBar", mainResponse.getMain().getPressure()/1000f));

        binding.tvWind.setText(String.format("%s km/h", mainResponse.getWind().getSpeed()));

        binding.tvSunrise.setText(getDate((mainResponse.getSys().getSunrise()
                +localTime)*1000L, "hh:mm a"));

        binding.tvSunset.setText(getDate((mainResponse.getSys().getSunset()
                +localTime)*1000L, "hh:mm a"));


        long date3 = (mainResponse.getSys().getSunset()-mainResponse.getSys().getSunrise())*1000L;
        String rawFormat = getDate(date3, "HH m");
        String []hours =rawFormat.split(" ");
        binding.tvDaytime.setText(hours[0]+"h "+hours[1]+"m");
    }
}