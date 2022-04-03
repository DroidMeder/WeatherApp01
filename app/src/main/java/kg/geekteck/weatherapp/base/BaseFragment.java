package kg.geekteck.weatherapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import kg.geekteck.weatherapp.R;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    // region values
    protected T binding;
    protected abstract T bind();
    protected NavController navController;
    //endregion


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = bind();
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        callRequests();
        setupListener();
        setupObserver();

    }

    protected abstract void setupViews();

    protected abstract void callRequests();

    protected abstract void setupListener();

    protected abstract void setupObserver();
}
