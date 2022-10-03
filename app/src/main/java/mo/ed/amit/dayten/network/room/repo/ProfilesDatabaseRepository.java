package mo.ed.amit.dayten.network.room.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

import mo.ed.amit.dayten.network.room.helper.AppDatabase;
import mo.ed.amit.dayten.network.room.model.entries.Entries;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;

public class ProfilesDatabaseRepository {

    private static ProfilesDatabaseRepository profilesDatabaseRepository;
    private final AppDatabase mDatabase;
    private MediatorLiveData<List<Profile>> mObservableProfiles;

    public ProfilesDatabaseRepository(AppDatabase mDatabase) {
        this.mDatabase = mDatabase;
        mObservableProfiles = new MediatorLiveData<>();

        mObservableProfiles.addSource(mDatabase.dao().getLiveProfiles(),
                profiles -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProfiles.postValue(profiles);
                    }
                });
    }


    public long insertProfiles(Profile profile){
        return mDatabase.dao().insertProfiles(profile);
    }

    public LiveData<List<Profile>> getLiveProfiles(){
        return mDatabase.dao().getLiveProfiles();
    }

    public int deleteData() {
        return mDatabase.dao().deleteProfiles();
    }
}