
package kg.geekteck.weatherapp.data.models.forecast;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("icon")
    @Expose
    private String icon;

    @Ignore
    @SerializedName("id")
    @Expose
    private long id;
    @Ignore
    @SerializedName("main")
    @Expose
    private String main;
    @Ignore
    @SerializedName("description")
    @Expose
    private String description;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
