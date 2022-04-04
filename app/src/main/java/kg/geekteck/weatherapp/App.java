package kg.geekteck.weatherapp;

import android.app.Application;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
/*
    private RetrofitClient client;
    private WeatherAppApi api;
    public static Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        client = new RetrofitClient();
        api=client.provideApi();
        repository = new Repository(api);//, "8f2532bd1258017112c5514cef4a7b8b",
        // "metric", "ru");
    }*/
}
