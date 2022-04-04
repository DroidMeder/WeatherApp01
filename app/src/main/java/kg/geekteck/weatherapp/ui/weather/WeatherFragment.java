package kg.geekteck.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geekteck.weatherapp.R;
import kg.geekteck.weatherapp.base.BaseFragment;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.databinding.FragmentWeatherBinding;

@AndroidEntryPoint
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding> {
    private WeatherViewModel viewModel;
    private WeatherForecastAdapter adapter;
    private WeatherFragmentArgs args;
    private int time;
    private int nightTime;
    private String city;

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new WeatherForecastAdapter();
        args=WeatherFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
    }

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        System.out.println("=-=-=-="+args);
        if (args!=null){
            city= args.getN();
        }else {
            city="42.8765615:74.6070079:Bishkek";
        }
        binding.recForecast.setAdapter(adapter);
    }

    @Override
    protected void callRequests() {
        String[] cord = city.split(":");
        viewModel.getWeatherByCityName(cord[0], cord[1]);
        viewModel.getForecast(cord[2]);
    }

    @Override
    protected void setupListener() {
        binding.tvLocation.setOnClickListener(view -> navController
                .navigate(R.id.action_weatherFragment_to_selectCityFragment));
    }

    @Override
    protected void setupObserver() {
        System.out.println("====== observer  ===");
        viewModel.liveData.observe(getViewLifecycleOwner(), mainResponseResource -> {
            switch (mainResponseResource.status){
                case SUCCESS:{
                    binding.clWeather.setVisibility(View.VISIBLE);
                    binding.progress.setVisibility(View.GONE);
                    time = mainResponseResource.data.getTimezone() +
                            mainResponseResource.data.getDt();
                    nightTime = mainResponseResource.data.getSys().getSunset();
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
                    System.out.println("======"+mainResponseResource.msc);
                    Snackbar.make(binding.getRoot(), mainResponseResource.msc,
                            BaseTransientBottomBar.LENGTH_LONG)
                            .show();
                    break;
                }
                case LOADING: {
                    binding.clWeather.setVisibility(View.GONE);
                    binding.progress.setVisibility(View.VISIBLE);
                    System.out.println("======Loading "+mainResponseResource.msc);
                    break;
                }
            }
        });

        viewModel.liveDataForecast.observe(getViewLifecycleOwner(), mainResponseResource -> {
            switch (mainResponseResource.status){
                case SUCCESS:{
                    adapter.setLisOfCities(mainResponseResource.data.getList());
                    adapter.setCity(mainResponseResource.data.getCity());
                    System.out.println("Success--------------");
                    break;
                }
                case ERROR: {
                    System.out.println("======"+mainResponseResource.msc);
                    Snackbar.make(binding.getRoot(), mainResponseResource.msc,
                            BaseTransientBottomBar.LENGTH_LONG)
                            .show();
                    break;
                }
                case LOADING: {
                    System.out.println("======Loading "+mainResponseResource.msc);
                    break;
                }
            }
        });
    }

    int i=0;
    private String getDate(long updatedAt, String dateFormat){

        System.out.println(++i +"--=-----------=-==- "+updatedAt);

        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        System.out.println("&&&&&&& "+calendar.getTimeZone().getRawOffset());
        calendar.setTimeInMillis(updatedAt+calendar.getTimeZone().getRawOffset());

        System.out.println(format.format(calendar.getTime())+"!!!!!!!!!!!!!");
        return format.format(calendar.getTime());
    }

    @SuppressLint("SetTextI18n")
    private void setViews(MainResponse mainResponse) {
        int updatedAt = mainResponse.getDt();
        int localTime = mainResponse.getTimezone();

        binding.tvDayDateTime.setText(getDate((updatedAt + localTime)* 1000L,
                "E, dd MMM yyyy | HH:mm a"));
        binding.tvLocation.setText(mainResponse.getName().toString()+", "
                +mainResponse.getSys().getCountry().toString());

        String url = "http://openweathermap.org/img/wn/"+mainResponse.getWeather().get(0)
                .getIcon()+"@2x.png";

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
                + localTime)*1000L, "hh:mm a"));

        binding.tvSunset.setText(getDate((mainResponse.getSys().getSunset()
                + localTime)*1000L, "hh:mm a"));


        long date3 = (mainResponse.getSys().getSunset()-mainResponse.getSys().getSunrise())*1000L;
        String rawFormat = getDate(date3, "HH m");
        String []hours =rawFormat.split(" ");
        binding.tvDaytime.setText(hours[0]+"h "+hours[1]+"m");
    }
}