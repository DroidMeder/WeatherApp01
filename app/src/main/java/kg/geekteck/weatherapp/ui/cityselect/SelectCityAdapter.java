package kg.geekteck.weatherapp.ui.cityselect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geekteck.weatherapp.common.OnItemClick;
import kg.geekteck.weatherapp.data.models.MainResponse;
import kg.geekteck.weatherapp.data.models.citynames.CityResponse;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;
import kg.geekteck.weatherapp.databinding.ItemCitiesBinding;
import kg.geekteck.weatherapp.databinding.ItemCitiesNamesBinding;

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.CityHolder> {
    private List<MyResponse> lisOfCities = new ArrayList<>();
    private OnItemClick<String> itemClick;

    public void setItemClick(OnItemClick<String> itemClick) {
        this.itemClick = itemClick;
    }

    public void setLisOfCities(List<MyResponse> lisOfCities) {
        this.lisOfCities = lisOfCities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCitiesNamesBinding binding = ItemCitiesNamesBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CityHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        System.out.println("uuuuuuuuuuuuu "+lisOfCities.size());
        holder.onBind(lisOfCities.get(position), position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClick.buttonClick(lisOfCities.get(holder.getAdapterPosition()).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lisOfCities.size();
    }

    public void setLisOfCity(MyResponse data) {
        lisOfCities.add(data);
        notifyDataSetChanged();
    }

    protected static class CityHolder extends RecyclerView.ViewHolder{
        private ItemCitiesNamesBinding binding;

        public CityHolder(@NonNull ItemCitiesNamesBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }

        public void onBind(MyResponse cityResponse, int position) {
            try {
                binding.tvCityName.setText("Название города: "+cityResponse.getName());
                binding.tvCountry.setText("Название страны: "+cityResponse.getCountry());
                binding.tvState.setText("Название штата: "+cityResponse.getState());
                binding.tvLocalName.setText("Оригинальное название города: "+cityResponse
                        .getLocalNames().getFeatureName());
                binding.tvNames.setText("Назание города на на кыргызском: "+cityResponse
                        .getLocalNames().getEn()
                        +"\n на англиском: "+cityResponse.getLocalNames().getEn()
                        +"\n на русском: "+cityResponse.getLocalNames().getRu());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
