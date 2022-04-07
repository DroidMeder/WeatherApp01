package kg.geekteck.weatherapp.data.models.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import kg.geekteck.weatherapp.data.models.citynames.LocalNames;

@Entity
public class CityName implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "lat")
    double lat;
    @ColumnInfo(name = "lon")
    double lon;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "hintName")
    String hintName;
    @ColumnInfo(name = "country")
    String country;
    @ColumnInfo(name = "state")
    String state;
    @ColumnInfo(name = "featureName")
    String featureName;
    @ColumnInfo(name = "nameEn")
    String nameEn;
    @ColumnInfo(name = "nameKg")
    String nameKg;
    @ColumnInfo(name = "nameRu")
    String nameRu;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Ignore
    public CityName() {
    }

    public CityName(double lat, double lon, String name, String hintName, String country, String state,
                    String featureName, String nameEn, String nameKg, String nameRu) {
        this.lat = lat;
        this.lon = lon;
        this.name = name;
        this.hintName = hintName;
        this.country = country;
        this.state = state;
        this.featureName = featureName;
        this.nameEn = nameEn;
        this.nameKg = nameKg;
        this.nameRu = nameRu;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
    public String getHintName() {
        return hintName;
    }

    public void setHintName(String hintName) {
        this.hintName = hintName;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getFeatureName() {
        return featureName;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameKg() {
        return nameKg;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameKg(String nameKg) {
        this.nameKg = nameKg;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }
}