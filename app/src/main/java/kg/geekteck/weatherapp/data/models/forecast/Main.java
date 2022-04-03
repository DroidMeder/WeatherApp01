
package kg.geekteck.weatherapp.data.models.forecast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    private double temp;
    @SerializedName("feels_like")
    @Expose
    private double feelsLike;
    @SerializedName("temp_min")
    @Expose
    private double tempMin;
    @SerializedName("temp_max")
    @Expose
    private double tempMax;
    @SerializedName("pressure")
    @Expose
    private long pressure;
    @SerializedName("sea_level")
    @Expose
    private long seaLevel;
    @SerializedName("grnd_level")
    @Expose
    private long grndLevel;
    @SerializedName("humidity")
    @Expose
    private long humidity;
    @SerializedName("temp_kf")
    @Expose
    private double tempKf;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getTempMin() {
        return (int) tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public int getTempMax() {
        return (int) tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public long getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public long getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(long seaLevel) {
        this.seaLevel = seaLevel;
    }

    public long getGrndLevel() {
        return grndLevel;
    }

    public void setGrndLevel(long grndLevel) {
        this.grndLevel = grndLevel;
    }

    public long getHumidity() {
        return humidity;
    }

    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    public double getTempKf() {
        return tempKf;
    }

    public void setTempKf(double tempKf) {
        this.tempKf = tempKf;
    }

}
