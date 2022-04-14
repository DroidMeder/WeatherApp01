package kg.geekteck.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import kg.geekteck.weatherapp.MainActivity2;
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
    private long nightTime;
    private String city;
    private int updatedAt;
    private int localTime;
    String[] cord;

    public WeatherFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new WeatherForecastAdapter();
        args = WeatherFragmentArgs.fromBundle(getArguments());
        viewModel = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
    }

    @Override
    protected FragmentWeatherBinding bind() {
        return FragmentWeatherBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        System.out.println("=setupViews WF-=-=-=" + args);
        if (args != null) {
            city = args.getN();
        } else {
            System.out.println("nuuulll0000========");
            city = "42.8766:74.607:Bishkek";
        }
        binding.recForecast.setAdapter(adapter);
    }

    @Override
    protected void callRequests() {
        if (city.equals("423")){
            if (MainActivity2.userLocation!=null){
                city=MainActivity2.userLocation;
            }
        }
        System.out.println("___WF__*split*_______"+city);
        if (city!=null) {
            cord = city.split(":");
            if (cord.length>1) {
                viewModel.getWeatherByCityName(cord[0], cord[1]);
            }
        }

    }

    @Override
    protected void setupListener() {
        binding.tvLocation.setOnClickListener(view -> navController
                .navigate(R.id.action_weatherFragment_to_selectCityFragment));
        binding.tvDayDateTime.setOnClickListener(view -> navController
                .navigate(R.id.action_weatherFragment_to_listWeatherFragment));
    }

    @Override
    protected void setupObserver() {
        System.out.println("====== observer WF  ===");
        //region main response or current weather
        if (viewModel.liveData!=null) {
            System.out.println("Get Main response -------WF-----");
            viewModel.liveData.observe(getViewLifecycleOwner(), mR -> {
                switch (mR.status) {
                    case SUCCESS: {
                        System.out.println("Success------WF--GMR------");
                        getForecast(getDate((mR.data.getDt()+mR.data.getTimezone())*1000L,
                                "HH"));
                        binding.clWeather.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);
                        time = mR.data.getTimezone() +
                                mR.data.getDt();
                        nightTime = mR.data.getSys().getSunset();
                        if (time <= nightTime) {
                            binding.ivBackground.setImageResource(R.drawable.ic_graphic_city_night);
                        } else {
                            binding.ivBackground.setImageResource(R.drawable.ic_graphic_city_day);
                        }
                        try {
                            setViews(mR.data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println(String.format("Success--WF--CW----------"
                                + mR.data.toString()));
                        break;
                    }
                    case ERROR: {
                        binding.clWeather.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.GONE);
                        System.out.println("Error==WF==CW==" + mR.msc);
                        if (!checkInternetConnection()) {
                            System.out.println("Error==WF==CW=loadLocal=");
                            try {
                                getCurrentWeatherFromRoom();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Snackbar.make(binding.getRoot(), mR.msc,
                                BaseTransientBottomBar.LENGTH_LONG)
                                .show();
                        break;
                    }
                    case LOADING: {
                        binding.clWeather.setVisibility(View.GONE);
                        binding.progress.setVisibility(View.VISIBLE);
                        System.out.println("===WF==CW==Loading " + mR.msc);
                        break;
                    }
                }
            });
        } else {
            System.out.println("---***Weather view model is empty***---");
            getCurrentWeatherFromRoom();
        }
        //endregion
    }

    private void getForecast(String dt) {
        if (cord.length>1) {
            viewModel.getForecast(cord[0], cord[1]);
        }
        //region forecast
        if (viewModel.liveDataForecast!=null) {
            System.out.println("Get Forecast response -------WF-----");
            viewModel.liveDataForecast.observe(
                    getViewLifecycleOwner(), fR ->
                    {
                        switch (fR.status) {
                            case SUCCESS: {
                                adapter.setLisOfCities(fR.data.getList());
                                adapter.setDate(dt);
                                System.out.println("Success--WF1------------");
                                break;
                            }
                            case ERROR: {
                                System.out.println("===WF1===" + fR.msc);
                                if (!checkInternetConnection()) {
                                    System.out.println("Error==WF==CW=loadLocal=");
                                    try {
                                        getLocalForecastFromRoom(dt);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                Snackbar.make(binding.getRoot(), fR.msc,
                                        BaseTransientBottomBar.LENGTH_LONG)
                                        .show();
                                break;
                            }
                            case LOADING: {
                                System.out.println("===WF1====Loading " + fR.msc);
                                break;
                            }
                        }
                    });
        }  else{
            System.out.println("----***Forecast view model is empty***----");
            getLocalForecastFromRoom(dt);
        }
        //endregion
    }

    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected());
    }

    private void getCurrentWeatherFromRoom() {
        if (cord.length>3){
            System.out.println("_________Yeas");
            viewModel.getLocalCurrentWeather(Integer.parseInt(cord[3]));
        }else {
            System.out.println("_________Neeuuu");
            viewModel.getLocalLastMainResponse();
        }
        viewModel.liveDataGetLocalCurrent.observe(getViewLifecycleOwner(),
                cR -> {
                    switch (cR.status) {
                        case SUCCESS: {
                            System.out.println("Success--WF-----LCW-------");
                            try {
                                getForecast(getDate((cR.data.get(0).getDt()
                                                +cR.data.get(0).getTimezone())*1000L,
                                        "HH"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            binding.clWeather.setVisibility(View.VISIBLE);
                            binding.progress.setVisibility(View.GONE);
                            if (cR.data.size() > 0 && cR.data!=null) {
                                setViews(cR.data.get(0));
                            }
                            break;
                        }
                        case ERROR: {
                            binding.clWeather.setVisibility(View.GONE);
                            binding.progress.setVisibility(View.GONE);
                            System.out.println("Error==WF==LCW==" + cR.msc);
                            Snackbar.make(binding.getRoot(), cR.msc,
                                    BaseTransientBottomBar.LENGTH_LONG)
                                    .show();
                            break;
                        }
                        case LOADING: {
                            System.out.println("===WF==LCW==Loading " + cR.msc);
                            binding.clWeather.setVisibility(View.GONE);
                            binding.progress.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                });
    }
 private void getLocalForecastFromRoom(String dt) {
        if (cord.length>3){
            System.out.println("____wf__lff___Yeas");
            viewModel.getLocalForecastById();
        }else {
            System.out.println("__wf__llf_____Neeuuu");
            viewModel.getLocalLastForecast();
        }
        viewModel.liveDataGetLocalForecast.observe(getViewLifecycleOwner(),
                fR -> {
                    switch (fR.status) {
                        case SUCCESS: {
                            System.out.println("Success--WF-----Lf-------"+fR.data.size());
                            try {
                                adapter.setLisOfCities(fR.data);
                                adapter.setDate(dt);
                            } catch (Exception e) {
                                System.out.println("----S wf--- lf"+e);
                            }
                            break;
                        }
                        case ERROR: {
                            System.out.println("Error==WF==Lf==" + fR.msc);
                            break;
                        }
                        case LOADING: {
                            System.out.println("===WF==Lf==Loading " + fR.msc);
                            break;
                        }
                    }
                });
    }

    int i = 0;

    private String getDate(long updatedAt, String dateFormat) {

        System.out.println(++i + "--=---WF----getDate---=-==- " + updatedAt);

        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        System.out.println("WF &&& getDate &&&& " + calendar.getTimeZone().getRawOffset());
        calendar.setTimeInMillis(updatedAt + calendar.getTimeZone().getRawOffset());

        System.out.println(format.format(calendar.getTime()) + "!!! getDate !!!!!!WF!!!!");
        return format.format(calendar.getTime());
    }

    @SuppressLint("SetTextI18n")
    private void setViews(MainResponse mainResponse) {
        System.out.println(mainResponse.getSys().getCountry()+" --*start setting views*--");
        updatedAt = mainResponse.getDt();
        localTime = mainResponse.getTimezone();

        binding.tvDayDateTime.setText(getDate((updatedAt + localTime) * 1000L,
                "E, dd MMM yyyy | HH:mm a"));
        binding.tvLocation.setText(mainResponse.getName().toString() + ", "
                + mainResponse.getSys().getCountry().toString());

        String url = "http://openweathermap.org/img/wn/" + mainResponse.getWeather().get(0)
                .getIcon() + "@2x.png";

        Glide.with(binding.getRoot())
                .load(url)
                .centerCrop()
                .into(binding.ivIsSkyClear);
        binding.tvIsSkyClear.setText(mainResponse.getWeather().get(0).getMain());

        int temp= (int) mainResponse.getMain().getTemp();
        int maxTemp = (int) mainResponse.getMain().getTempMax();
        int minTemp = (int) mainResponse.getMain().getTempMin();

        binding.tvTemperature1.setText(String.valueOf(temp));
        binding.tvMaxTemp.setText(String.valueOf(maxTemp));
        binding.tvMinTemp.setText(String.valueOf(minTemp));

        String str = String.format("%s", mainResponse.getMain().getHumidity());
        binding.tvHumidity.setText(str + "%");

        binding.tvBarometer.setText(String.format("%s mBar", mainResponse.getMain().getPressure() / 1000f));

        binding.tvWind.setText(String.format("%s km/h", mainResponse.getWind().getSpeed()));

        binding.tvSunrise.setText(getDate((mainResponse.getSys().getSunrise()
                + localTime) * 1000L, "hh:mm a"));

        binding.tvSunset.setText(getDate((mainResponse.getSys().getSunset()
                + localTime) * 1000L, "hh:mm a"));

        long date3 = (mainResponse.getSys().getSunset() - mainResponse.getSys().getSunrise()) * 1000L;
        String rawFormat = getDate(date3, "HH m");
        String[] hours = rawFormat.split(" ");
        binding.tvDaytime.setText(hours[0] + "h " + hours[1] + "m");
        System.out.println(mainResponse.getMain().getTemp()+"-- *end setting views*--");
    }
}