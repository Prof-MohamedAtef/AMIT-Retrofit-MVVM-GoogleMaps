package mo.ed.amit.dayten.network.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mo.ed.amit.dayten.network.retrofit.repo.ProfilesApiRepository;
import mo.ed.amit.dayten.network.room.helper.AppExecutors;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;
import mo.ed.amit.dayten.network.util.Configs;

public class ProfileViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Profile>> mProfileMediatorLiveData;
    private final ProfilesApiRepository mProfileApiRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        Configs.application=application;
        this.mProfileMediatorLiveData=new MediatorLiveData<>();
        this.mProfileMediatorLiveData.setValue(null);

        mProfileApiRepository=new ProfilesApiRepository(application);
    }

    public MutableLiveData<List<Profile>> getApiProfiles(){
        return mProfileApiRepository.callProfilesApi();
    }
}