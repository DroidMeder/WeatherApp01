
package kg.geekteck.weatherapp.data.models;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import kg.geekteck.weatherapp.data.local.converters.WeatherConverter;

@Entity
public class MainResponse {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    private int id;
    private long createdAt;
    @SerializedName("dt")
    @Expose
    private int dt;
    @SerializedName("timezone")
    @Expose
    private int timezone;
    @SerializedName("name")
    @Expose
    private String name;


    @SerializedName("coord")
    @Expose
    @Embedded
    private Coord coord;
    @SerializedName("main")
    @Expose
    @Embedded
    private Main main;
    @SerializedName("wind")
    @Expose
    @Embedded
    private Wind wind;
    @SerializedName("sys")
    @Expose
    @Embedded
    private Sys sys;

    @SerializedName("weather")
    @Expose
    @TypeConverters({WeatherConverter.class})
    private List<Weather> weather = null;


    @SerializedName("clouds")
    @Expose
    @Ignore
    private Clouds clouds;
    @SerializedName("base")
    @Expose
    @Ignore
    private String base;
    @SerializedName("visibility")
    @Expose
    @Ignore
    private Integer visibility;
    @SerializedName("cod")
    @Expose
    @Ignore
    private Integer cod;


    public MainResponse() {
    }

    public Coord getCoord() {
        return coord;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCod() {
        return cod;
    }

    public void setCod(Integer cod) {
        this.cod = cod;
    }

}
