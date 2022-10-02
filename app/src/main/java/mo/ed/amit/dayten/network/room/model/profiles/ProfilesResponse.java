
package mo.ed.amit.dayten.network.room.model.profiles;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ProfilesResponse {

    @SerializedName("data")
    private List<Profile> mData;

    public List<Profile> getData() {
        return mData;
    }

    public void setData(List<Profile> data) {
        mData = data;
    }

}
