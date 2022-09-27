package mo.ed.amit.dayten.network.retrofit;
import mo.ed.amit.dayten.network.model.EntriesModelResponse;
import mo.ed.amit.dayten.network.room.model.EntityModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CallService {
    @GET("/entries")
    Call<EntityModel> getEntries();
}