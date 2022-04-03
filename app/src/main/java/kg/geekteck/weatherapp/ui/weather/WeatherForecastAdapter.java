package kg.geekteck.weatherapp.ui.weather;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kg.geekteck.weatherapp.R;
import kg.geekteck.weatherapp.data.models.citynames.CityResponse;
import kg.geekteck.weatherapp.data.models.forecast.City;
import kg.geekteck.weatherapp.data.models.forecast.ForecastResponse;
import kg.geekteck.weatherapp.databinding.ItemCitiesBinding;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ForecastHolder> {
    private List<kg.geekteck.weatherapp.data.models.forecast.List> lisOfCities = new ArrayList<>();
    private static City city = new City();

    public void setCity(City city) {
        this.city = city;
    }

    public void setLisOfCities(List<kg.geekteck.weatherapp.data.models.forecast.List> lisOfCities) {
        this.lisOfCities = lisOfCities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ForecastHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCitiesBinding binding = ItemCitiesBinding.inflate(LayoutInflater.from(
                parent.getContext()), parent, false);
        return new ForecastHolder(binding);
    }

    int o = 0;
    @Override
    public void onBindViewHolder(@NonNull ForecastHolder holder, int position) {
        System.out.println("jgjhgk "+position);
        try {
            if (0<getItemCount()-1){
                holder.bind(lisOfCities.get(o));
                o +=8;
            }
        } catch (Exception e) {
            //onBindViewHolder(holder, getItemCount()-1);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    protected static class ForecastHolder extends RecyclerView.ViewHolder{
        private ItemCitiesBinding binding;

        public ForecastHolder(@NonNull ItemCitiesBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }

        public void bind(kg.geekteck.weatherapp.data.models.forecast.List forecastResponse) {
            String url = "http://openweathermap.org/img/wn/"+forecastResponse
                    .getWeather().get(0).getIcon()+"@2x.png";
            Glide.with(binding.getRoot())
                    .load(url)
                    .centerCrop()
                    .into(binding.ivForecast);

            //forecastResponse
            long date = (forecastResponse.getDt()
                    +city.getTimezone())*1000L;
            binding.tvForecastBold.setText(getDate(date, "E, dd"));
            String s = String.valueOf(forecastResponse.getMain().getTempMax());
            binding.tvForecastHigh.setText(s+"\u00B0C");
            s =String.valueOf(forecastResponse.getMain().getTempMin());
            binding.tvForecastMin.setText(s+"\u00B0C");
            System.out.println("ppppppppppppp");
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
    }
}
