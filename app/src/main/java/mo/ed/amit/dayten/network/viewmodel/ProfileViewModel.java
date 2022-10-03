package mo.ed.amit.dayten.network.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mo.ed.amit.dayten.network.retrofit.repo.ProfilesApiRepository;
import mo.ed.amit.dayten.network.room.dao.Dao;
import mo.ed.amit.dayten.network.room.helper.AppDatabase;
import mo.ed.amit.dayten.network.room.helper.AppExecutors;
import mo.ed.amit.dayten.network.room.helper.BasicApp;
import mo.ed.amit.dayten.network.room.model.entries.Entries;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;
import mo.ed.amit.dayten.network.room.repo.EntriesDatabaseRepository;
import mo.ed.amit.dayten.network.room.repo.ProfilesDatabaseRepository;
import mo.ed.amit.dayten.network.util.Configs;

public class ProfileViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Profile>> mProfileMediatorLiveData;
    private final ProfilesApiRepository mProfileApiRepository;
    private AppDatabase mDatabase;
    private final AppExecutors mAppExecutors;
    private final ProfilesDatabaseRepository dbRepository;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        Configs.application=application;
        this.mProfileMediatorLiveData=new MediatorLiveData<>();
        this.mProfileMediatorLiveData.setValue(null);
        mProfileApiRepository=new ProfilesApiRepository(application);

        mDatabase =new AppDatabase() {
            @Override
            public Dao dao() {
                return null;
            }
        };
        mAppExecutors = new AppExecutors();

        mDatabase= AppDatabase.getAppDatabase(application.getApplicationContext(),mAppExecutors);
        dbRepository=new ProfilesDatabaseRepository(AppDatabase.getAppDatabase(application.getApplicationContext(),mAppExecutors));

//        dbRepository = ((BasicApp)application).getDatabaseInstance().dao().getLiveProfiles();
//        mObserverMediatorLiveDataOffersList.addSource(entriesDatabaseRepository, mObserverMediatorLiveDataOffersList::setValue);
    }

    public MutableLiveData<List<Profile>> getApiProfiles(){
        return mProfileApiRepository.callProfilesApi();
    }


    // TODO: 9/27/2022 use this mesthod to retrieve data onConnectionLost
    public LiveData<List<Profile>> getLiveDataList(){
        return dbRepository.getLiveProfiles();
    }

    public long insertData(Profile profile){
        return dbRepository.insertProfiles(profile);
    }

    public int deleteData(){
        return dbRepository.deleteData();
    }
}