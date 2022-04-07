package kg.geekteck.weatherapp.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import kg.geekteck.weatherapp.data.local.AppDatabase;
import kg.geekteck.weatherapp.data.local.WeatherDao;
import kg.geekteck.weatherapp.data.repositories.DaoRepository;
import kg.geekteck.weatherapp.data.repositories.Repository;

@Module
@InstallIn(SingletonComponent.class)
public abstract class DaoModule {
    @Provides
    @Singleton
    public static AppDatabase provideDatabase(Application context){
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "weather")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static WeatherDao provideDoa(AppDatabase database){
        System.out.println("3333 DM 3333"+database.newsDao().toString());
        return database.newsDao();
    }

    @Provides
    @Singleton
    public static DaoRepository provideDaoRepository(WeatherDao dao){
        System.out.println("4433 DM 3334"+dao);
        return new DaoRepository(dao);
    }

}
