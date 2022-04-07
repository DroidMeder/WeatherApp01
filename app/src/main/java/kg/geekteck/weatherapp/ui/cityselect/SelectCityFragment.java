package kg.geekteck.weatherapp.ui.cityselect;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import kg.geekteck.weatherapp.ConnectionDetector;
import kg.geekteck.weatherapp.base.BaseFragment;
import kg.geekteck.weatherapp.common.OnItemClick;
import kg.geekteck.weatherapp.common.Resource;
import kg.geekteck.weatherapp.data.models.citynames.MyResponse;
import kg.geekteck.weatherapp.data.models.room.CityName;
import kg.geekteck.weatherapp.databinding.FragmentSelectCityBinding;

@AndroidEntryPoint
public class SelectCityFragment extends BaseFragment<FragmentSelectCityBinding> implements
        OnItemClick<String> {
    private String city;
    private String s1;
    private ConnectionDetector cd;
    private boolean isNetwork = false;
    private SelectCityAdapter adapter;
    private SelectCityViewModel model;

    public SelectCityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cd = new ConnectionDetector(requireContext().getApplicationContext());
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
        isNetwork = cd.ConnectingToInternet();
        if (isNetwork) {
            System.out.println("iiieeess");
        } else {
            System.out.println("nnnnnooooou");
        }
        binding.rec.setAdapter(adapter);
    }

    @Override
    protected void setupListener() {
    }

    @Override
    protected void callRequests() {
        isNetwork = cd.ConnectingToInternet();
        System.out.println(".......SLF......" + city);
        if (isNetwork) {
            model.getCitiesName(city);
        } else {
            model.getLocalCitiesName(city);
        }
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
                city = editable.toString();
            }
        });
        binding.button.setOnClickListener(view -> {
            isNetwork = cd.ConnectingToInternet();
            s1 = binding.etCityName.getText().toString();
            if (s1 != null) {
                if (isNetwork) {
                    model.getCitiesName(s1);
                    setupObserver();
                } else {
                    model.getLocalCitiesName(s1);
                    model.liveData2.observe(getViewLifecycleOwner(), new Observer<Resource<List<CityName>>>() {
                        @Override
                        public void onChanged(Resource<List<CityName>> listResource) {
                            System.out.println("---SLF--------8888888888888 " + listResource.data);
                            switch (listResource.status) {
                                case SUCCESS: {
                                    if (listResource.data.size() > 0) {
                                        adapter.setListOfCity(listResource.data);
                                    }

                                    System.out.println("Success----SLF-------77766---"
                                            + listResource.data.size());
                                    System.out.println("[[[[" + listResource.msc + "888888SLF88888");
                                    break;
                                }
                                case ERROR: {
                                    System.out.println("==SLF==7666==" + listResource.msc);
                                    Snackbar.make(binding.getRoot(), listResource.msc,
                                            BaseTransientBottomBar.LENGTH_LONG)
                                            .show();

                                    break;
                                }
                                case LOADING: {
                                    System.out.println("loading==56655=SLF==" + listResource.msc);
                                    Snackbar.make(binding.getRoot(), "loading===SLF==",
                                            BaseTransientBottomBar.LENGTH_LONG)
                                            .show();
                                    break;
                                }
                            }
                        }
                    });
                }
                //model.getLocalCitiesName(s1);
                binding.button.setVisibility(View.GONE);
                binding.tvSimple.setFocusable(true);
                binding.tvSimple.setVisibility(View.VISIBLE);
            }
        });
        //city response
        if (model.liveData!=null) {
            model.liveData.observe(getViewLifecycleOwner(), new Observer<Resource<MyResponse>>() {
                @Override
                public void onChanged(Resource<MyResponse> mainResponseResource) {
                    switch (mainResponseResource.status) {
                        case SUCCESS: {
                            //model.getLocalCitiesName();
                            System.out.println("*********start2***********");

                            System.out.println("lat=="+mainResponseResource.data.getLat());
                            System.out.println("lon=="+ mainResponseResource.data.getLon());
                            System.out.println("***********end2***********");
                            CityName cityName = new CityName(mainResponseResource.data.getLat(),
                                    mainResponseResource.data.getLon(), mainResponseResource.data.getName(),
                                    s1,
                                    mainResponseResource.data.getCountry(), mainResponseResource.data.getState(),
                                    mainResponseResource.data.getLocalNames().getFeatureName(),
                                    mainResponseResource.data.getLocalNames().getEn(),
                                    mainResponseResource.data.getLocalNames().getKy(),
                                    mainResponseResource.data.getLocalNames().getRu());
                            System.out.println("city---SLF----" + cityName.getName());
                            if (isNetwork) {
                                model.setLocalCitiesName(cityName);
                            }
                            adapter.setLisOfCity(mainResponseResource.data);
                            System.out.println("Success------SLF--------");
                            break;
                        }
                        case ERROR: {
                            System.out.println("==SLF====" + mainResponseResource.msc);
                            isNetwork = cd.ConnectingToInternet();
                            if (!isNetwork) {
                                model.getLocalCitiesName(s1);
                                model.liveData2.observe(getViewLifecycleOwner(), listResource -> {
                                    System.out.println("---SLF--------8888888888888 " + listResource.data);
                                    switch (listResource.status) {
                                        case SUCCESS: {
                                            if (listResource.data.size() > 0) {
                                                adapter.setListOfCity(listResource.data);
                                            }

                                            System.out.println("Success----SLF-------77766---"
                                                    + listResource.data.size());
                                            System.out.println("[[[[" + listResource.msc + "888888SLF88888");
                                            break;
                                        }
                                        case ERROR: {
                                            System.out.println("==SLF==7666==" + listResource.msc);
                                            Snackbar.make(binding.getRoot(), listResource.msc,
                                                    BaseTransientBottomBar.LENGTH_LONG)
                                                    .show();

                                            break;
                                        }
                                        case LOADING: {
                                            System.out.println("loading==56655=SLF==" + listResource.msc);
                                            Snackbar.make(binding.getRoot(), "loading===SLF==",
                                                    BaseTransientBottomBar.LENGTH_LONG)
                                                    .show();
                                            break;
                                        }
                                    }
                                });
                            } else {
                                Snackbar.make(binding.getRoot(), mainResponseResource.msc,
                                        BaseTransientBottomBar.LENGTH_LONG)
                                        .show();
                            }
                            break;
                        }
                        case LOADING: {
                            System.out.println("loading==SLF===" + mainResponseResource.msc);
                            Snackbar.make(binding.getRoot(), "loading=====",
                                    BaseTransientBottomBar.LENGTH_LONG)
                                    .show();
                            break;
                        }
                    }
                }
            });
        } else {
            model.getLocalCitiesName(s1);
            model.liveData2.observe(getViewLifecycleOwner(), listResource -> {
                System.out.println("---SLF--------8888888888888 " + listResource.data);
                switch (listResource.status) {
                    case SUCCESS: {
                        if (listResource.data.size() > 0) {
                            adapter.setListOfCity(listResource.data);
                        }

                        System.out.println("Success----SLF-------77766---"
                                + listResource.data.size());
                        System.out.println("[[[[" + listResource.msc + "888888SLF88888");
                        break;
                    }
                    case ERROR: {
                        System.out.println("==SLF==7666==" + listResource.msc);
                        Snackbar.make(binding.getRoot(), listResource.msc,
                                BaseTransientBottomBar.LENGTH_LONG)
                                .show();

                        break;
                    }
                    case LOADING: {
                        System.out.println("loading==56655=SLF==" + listResource.msc);
                        Snackbar.make(binding.getRoot(), "loading===SLF==",
                                BaseTransientBottomBar.LENGTH_LONG)
                                .show();
                        break;
                    }
                }
            });
        }
    }

    @Override
    public void buttonClick(String data) {
        System.out.println("[[[[[SLF[[[[" + data);
        navController.navigate(SelectCityFragmentDirections
                .actionSelectCityFragmentToWeatherFragment(data));
    }
}