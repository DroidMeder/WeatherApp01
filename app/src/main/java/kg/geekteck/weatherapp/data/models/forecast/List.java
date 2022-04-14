
package kg.geekteck.weatherapp.data.models.forecast;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kg.geekteck.weatherapp.data.local.converters.WeatherForecastConverter;

@Entity
public class List {
    @PrimaryKey(autoGenerate = true)
    int id;
    @SerializedName("dt")
    @Expose
    private long dt;
    @SerializedName("main")
    @Expose
    @Embedded
    private Main main;
    @SerializedName("dt_txt")
    @Expose
    private String dtTxt;
    private long createdAt;

    @SerializedName("weather")
    @Expose
    @TypeConverters({WeatherForecastConverter.class})
    private java.util.List<Weather> weather = null;


    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @SerializedName("clouds")
    @Expose
    @Ignore
    private Clouds clouds;
    @Ignore
    @SerializedName("wind")
    @Expose
    private Wind wind;
    @Ignore
    @SerializedName("visibility")
    @Expose
    private long visibility;
    @Ignore
    @SerializedName("pop")
    @Expose
    private double pop;
    @Ignore
    @SerializedName("rain")
    @Expose
    private Rain rain;
    @Ignore
    @SerializedName("sys")
    @Expose
    private Sys sys;

    public List() {
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt+createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public java.util.List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(java.util.List<Weather> weather) {
        this.weather = weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public double getPop() {
        return pop;
    }

    public void setPop(double pop) {
        this.pop = pop;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDtTxt() {
        return dtTxt;
    }

    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }

}
