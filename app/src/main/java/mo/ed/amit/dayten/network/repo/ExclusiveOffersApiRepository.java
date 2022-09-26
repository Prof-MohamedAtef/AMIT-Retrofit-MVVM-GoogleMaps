package mo.ed.amit.dayten.network.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import mo.ed.amit.dayten.network.retrofit.CallService;
import mo.ed.amit.dayten.network.retrofit.Http;
import mo.ed.amit.dayten.network.model.EntriesModelResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExclusiveOffersApiRepository {
    private final MutableLiveData<ArrayList<EntriesModelResponse.Entries>> allEntries;

    public ExclusiveOffersApiRepository(Application application) { //application is subclass of context
        allEntries = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<EntriesModelResponse.Entries>> callAPI(){
        Call<EntriesModelResponse> call = Http.create(CallService.class).getEntries();
        call.enqueue(new Callback<EntriesModelResponse>() {
            @Override
            public void onResponse(Call<EntriesModelResponse> call, Response<EntriesModelResponse> response) {
                EntriesModelResponse data= response.body();
                if (data!=null){
                    allEntries.setValue(data.getEntries());
                }
            }

            @Override
            public void onFailure(Call<EntriesModelResponse> call, Throwable t) {
                Log.e("ConnectionFailure", t.toString());
            }
        });
        return allEntries;
    }
}
