package mo.ed.amit.dayten.network.retrofit.repo;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import mo.ed.amit.dayten.network.retrofit.CallService;
import mo.ed.amit.dayten.network.retrofit.Http;
import mo.ed.amit.dayten.network.room.model.entries.Entries;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;
import mo.ed.amit.dayten.network.room.model.profiles.ProfilesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilesApiRepository {

    private final MutableLiveData<List<Profile>> allProfiles;


    public ProfilesApiRepository(Application application) {
        allProfiles = new MutableLiveData<>();
    }


    public MutableLiveData<List<Profile>> callProfilesApi(){
        Call<ProfilesResponse> call= Http.create(CallService.class).getProfiles();
        call.enqueue(new Callback<ProfilesResponse>() {
            @Override
            public void onResponse(Call<ProfilesResponse> call, Response<ProfilesResponse> response) {
                 ProfilesResponse profilesResponse= response.body();
                 if (profilesResponse!=null){
                     allProfiles.setValue(profilesResponse.getData());
                 }
            }

            @Override
            public void onFailure(Call<ProfilesResponse> call, Throwable t) {
                Log.e("ConnectionFailure", t.toString());
            }
        });
        return allProfiles;
    }
}