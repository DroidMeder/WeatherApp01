
package kg.geekteck.weatherapp.data.models.forecast;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City {
    @Ignore
    @SerializedName("id")
    @Expose
    private long id;
    @Ignore
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("coord")
    @Expose
    @Ignore
    private Coord coord;
    @SerializedName("country")
    @Expose
    @Ignore
    private String country;
    @SerializedName("population")
    @Expose
    @Ignore
    private long population;
    @SerializedName("timezone")
    @Expose
    private long timezone;
    @Ignore
    @SerializedName("sunrise")
    @Expose
    private long sunrise;
    @SerializedName("sunset")
    @Expose
    @Ignore
    private long sunset;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getTimezone() {
        return timezone;
    }

    public void setTimezone(long timezone) {
        this.timezone = timezone;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

}
