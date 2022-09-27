package mo.ed.amit.dayten.network.retrofit.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import mo.ed.amit.dayten.network.retrofit.CallService;
import mo.ed.amit.dayten.network.retrofit.Http;
import mo.ed.amit.dayten.network.room.model.EntityModel;
import mo.ed.amit.dayten.network.room.model.Entries;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExclusiveOffersApiRepository {
    private final MutableLiveData<ArrayList<Entries>> allEntries;

    public ExclusiveOffersApiRepository(Application application) { //application is subclass of context
        allEntries = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<Entries>> callAPI(){
        Call<EntityModel> call = Http.create(CallService.class).getEntries();
        call.enqueue(new Callback<EntityModel>() {
            @Override
            public void onResponse(Call<EntityModel> call, Response<EntityModel> response) {
                EntityModel data= response.body();
                if (data!=null){
                    allEntries.setValue(data.getEntries());
                }
            }

            @Override
            public void onFailure(Call<EntityModel> call, Throwable t) {
                Log.e("ConnectionFailure", t.toString());
            }
        });
        return allEntries;
    }
}
