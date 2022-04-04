package kg.geekteck.weatherapp.ui.weather;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kg.geekteck.weatherapp.data.models.forecast.City;
import kg.geekteck.weatherapp.databinding.ItemCitiesBinding;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.ForecastHolder> {
    private List<kg.geekteck.weatherapp.data.models.forecast.List> lisOfCities = new ArrayList<>();
    private static City city = new City();

    public void setCity(City city) {
        WeatherForecastAdapter.city = city;
    }

    @SuppressLint("NotifyDataSetChanged")
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
        try {
            if (0<getItemCount()-1){
                holder.bind(lisOfCities.get(o));
                o +=8;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            long date = (forecastResponse.getDt()
                    +city.getTimezone())*1000L;
            binding.tvForecastBold.setText(getDate(date));
            String s = String.valueOf(forecastResponse.getMain().getTempMax());
            binding.tvForecastHigh.setText(s+"\u00B0C");
            s =String.valueOf(forecastResponse.getMain().getTempMin());
            binding.tvForecastMin.setText(s+"\u00B0C");
        }

        private String getDate(long updatedAt){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format =
                    new SimpleDateFormat("E, dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(updatedAt);
            return format.format(calendar.getTime());
        }
    }
}
