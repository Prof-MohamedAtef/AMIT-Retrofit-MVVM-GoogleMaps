package mo.ed.amit.dayten.network.retrofit;

import mo.ed.amit.dayten.network.room.model.entries.EntityModel;
import mo.ed.amit.dayten.network.room.model.profiles.ProfilesResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CallService {
    @GET("/entries")
    Call<EntityModel> getEntries();

    @GET("/GoogleApis/public/api/profiles")
    Call<ProfilesResponse> getProfiles();
}