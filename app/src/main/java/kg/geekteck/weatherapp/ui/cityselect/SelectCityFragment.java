package kg.geekteck.weatherapp.ui.cityselect;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geekteck.weatherapp.base.BaseFragment;
import kg.geekteck.weatherapp.common.OnItemClick;
import kg.geekteck.weatherapp.databinding.FragmentSelectCityBinding;

@AndroidEntryPoint
public class SelectCityFragment extends BaseFragment<FragmentSelectCityBinding> implements
        OnItemClick<String> {
    private String city;
    private SelectCityAdapter adapter;
    private SelectCityViewModel model;

    public SelectCityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SelectCityAdapter();
        adapter.setItemClick(this);
        model = new ViewModelProvider(requireActivity()).get(SelectCityViewModel.class);
    }

    @Override
    protected FragmentSelectCityBinding bind() {
        return FragmentSelectCityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void setupViews() {
        binding.rec.setAdapter(adapter);
    }

    @Override
    protected void setupListener() {
    }

    @Override
    protected void callRequests() {
        System.out.println("............."+city);
        model.getCitiesName(city);
    }


    @Override
    protected void setupObserver() {
        binding.etCityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.button.setVisibility(View.VISIBLE);
                binding.tvSimple.setVisibility(View.GONE);
                adapter.clearList();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                city=editable.toString();
            }
        });
        binding.button.setOnClickListener(view -> {
            String s1 = binding.etCityName.getText().toString();
            if (s1 !=null) {
                model.getCitiesName(s1);
                setupObserver();
                binding.button.setVisibility(View.GONE);
                binding.tvSimple.setFocusable(true);
                binding.tvSimple.setVisibility(View.VISIBLE);
            }
        });
        model.liveData.observe(getViewLifecycleOwner(), mainResponseResource -> {
            switch (mainResponseResource.status){
                case SUCCESS:{
                    adapter.setLisOfCity(mainResponseResource.data);
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
                    System.out.println("loading====="+mainResponseResource.msc);
                    Snackbar.make(binding.getRoot(), "loading=====",
                            BaseTransientBottomBar.LENGTH_LONG)
                            .show();
                    break;
                }
            }
        });
    }

    @Override
    public void buttonClick(String data) {
        System.out.println("[[[[[[[[["+data);
        navController.navigate(SelectCityFragmentDirections
                .actionSelectCityFragmentToWeatherFragment(data));
    }
}