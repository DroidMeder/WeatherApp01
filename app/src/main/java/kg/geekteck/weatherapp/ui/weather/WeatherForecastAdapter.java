package kg.geekteck.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kg.geekteck.weatherapp.databinding.ItemCitiesBinding;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ForecastHolder> {
    private List<kg.geekteck.weatherapp.data.models.forecast.List> forecastList = new ArrayList<>();
    private List<String> dt=new ArrayList<>();
    private ItemCitiesBinding binding;

    @SuppressLint("NotifyDataSetChanged")
    public void setLisOfCities(List<kg.geekteck.weatherapp.data.models.forecast.List> lisOfCities) {
        this.forecastList = lisOfCities;
        System.out.println("Adapter1 --------- "+lisOfCities.size());

        notifyDataSetChanged();
    }

    public void setDate(String d) {
        int i = Integer.parseInt(d);
        dt=new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            i++;
            String tmp="";
            if (i==24){
                i=0;
            }
            if (i<10) {
                tmp = "0" + i;
            }else {
                tmp = String.valueOf(i);
            }
            dt.add(tmp+":00:00");
        }
    }


    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCitiesBinding.inflate(LayoutInflater.from(
                parent.getContext()), parent, false);
        return new ForecastHolder(binding);
    }

    int size = 0;
    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        System.out.println("Adapter2 --------- "+(forecastList.size()-size));
        System.out.println("Adapter5 --------- "+dt);
        System.out.println("Adapter7 --------- "+size);
        for (int i = size; i < forecastList.size(); i++) {
            String [] strings = forecastList.get(i).getDtTxt().split(" ");
            size++;
            if (strings[1].equals(dt.get(0)) || strings[1].equals(dt.get(1))
                    || strings[1].equals(dt.get(2))){
                holder.bind(forecastList.get(i));
                System.out.println("Adapter4 --------- "+forecastList.get(i).toString());
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    protected static class ForecastHolder extends RecyclerView.ViewHolder{
        private final ItemCitiesBinding binding;

        public ForecastHolder(@NonNull ItemCitiesBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }

        @SuppressLint("SetTextI18n")
        public void bind(kg.geekteck.weatherapp.data.models.forecast.List forecastResponse) {
            String url = "http://openweathermap.org/img/wn/"+forecastResponse
                    .getWeather().get(0).getIcon()+"@2x.png";
            Glide.with(binding.getRoot())
                    .load(url)
                    .centerCrop()
                    .into(binding.ivForecast);

            //forecastResponse
            binding.tvForecastBold.setText(getDate(forecastResponse.getDt()*1000L,
                    "E, dd"));
            String s = String.valueOf(forecastResponse.getMain().getTempMax());
            binding.tvForecastHigh.setText(s+"\u00B0C");
            s =String.valueOf(forecastResponse.getMain().getTempMin());
            binding.tvForecastMin.setText(s+"\u00B0C");
        }

        private String getDate(long updatedAt, String dateFormat) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
            Calendar calendar = Calendar.getInstance();
            System.out.println("WF &&& getDate &&&& " + updatedAt);
            calendar.setTimeInMillis(updatedAt + calendar.getTimeZone().getRawOffset());
            System.out.println(format.format(calendar.getTime()) + "!!! getDate !!!!!!WF!!!!");
            return format.format(calendar.getTime());
        }
    }
}
