package kg.geekteck.weatherapp.ui.cityselect;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kg.geekteck.weatherapp.common.OnItemClick;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;
import kg.geekteck.weatherapp.databinding.ItemCitiesNamesBinding;

public class SelectCityAdapter extends RecyclerView.Adapter<SelectCityAdapter.CityHolder> {
    private List<MyResponse> lisOfCities = new ArrayList<>();
    private OnItemClick<String> itemClick;

    public void setItemClick(OnItemClick<String> itemClick) {
        this.itemClick = itemClick;
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
        holder.onBind(lisOfCities.get(position));
        holder.itemView.setOnClickListener(view ->
                itemClick.buttonClick(lisOfCities.get(holder.getAdapterPosition()).getLat() + ":"
                        + lisOfCities.get(holder.getAdapterPosition()).getLon() + ":"
                        + lisOfCities.get(holder.getAdapterPosition()).getName()));
    }

    @Override
    public int getItemCount() {
        return lisOfCities.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setLisOfCity(MyResponse data) {
        lisOfCities.add(data);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearList() {
        lisOfCities = new ArrayList<>();
        notifyDataSetChanged();
    }

    protected static class CityHolder extends RecyclerView.ViewHolder {
        private final ItemCitiesNamesBinding binding;

        public CityHolder(@NonNull ItemCitiesNamesBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        @SuppressLint("SetTextI18n")
        public void onBind(MyResponse cityResponse) {
            try {
                binding.tvCityName.setText("Название города: " + cityResponse.getName());
                binding.tvCountry.setText("Название страны: " + cityResponse.getCountry());
                binding.tvState.setText("Название штата: " + cityResponse.getState());
                binding.tvLocalName.setText("Оригинальное название города: " + cityResponse
                        .getLocalNames().getFeatureName());
                binding.tvNames.setText("Назание города на на кыргызском: " + cityResponse
                        .getLocalNames().getEn()
                        + "\n на англиском: " + cityResponse.getLocalNames().getEn()
                        + "\n на русском: " + cityResponse.getLocalNames().getRu());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
