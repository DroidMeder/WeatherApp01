package kg.geekteck.weatherapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import kg.geekteck.weatherapp.data.remote.WeatherAppApi;
import kg.geekteck.weatherapp.data.repositories.Repository;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Provides
    @Singleton
    public static OkHttpClient provideOkHttpClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    @Provides
    @Singleton
    public static Retrofit provideRetrofit(OkHttpClient client){
        return  new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    public static WeatherAppApi provideApi(Retrofit retrofit){
        return retrofit.create(WeatherAppApi.class);
    }

    @Provides
    @Singleton
    public static Repository provideRepository(WeatherAppApi api){
        return new Repository(api);
    }

}
