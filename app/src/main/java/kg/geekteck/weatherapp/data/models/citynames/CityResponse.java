
package kg.geekteck.weatherapp.data.models.citynames;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityResponse {

    @SerializedName("my_response")
    @Expose
    private List<MyResponse> myResponse = null;

    public List<MyResponse> getMyResponse() {
        return myResponse;
    }

    public void setMyResponse(List<MyResponse> myResponse) {
        this.myResponse = myResponse;
    }

}
