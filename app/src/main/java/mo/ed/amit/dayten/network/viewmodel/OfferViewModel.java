package mo.ed.amit.dayten.network.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import mo.ed.amit.dayten.network.Configs;
import mo.ed.amit.dayten.network.model.EntriesModelResponse;
import mo.ed.amit.dayten.network.repo.ExclusiveOffersApiRepository;
import mo.ed.amit.dayten.network.room.AppDatabase;
import mo.ed.amit.dayten.network.room.AppExecutors;
import mo.ed.amit.dayten.network.room.BasicApp;
import mo.ed.amit.dayten.network.room.Dao;
import mo.ed.amit.dayten.network.room.Entries;
import mo.ed.amit.dayten.network.room.EntriesDatabaseRepository;

public class OfferViewModel extends AndroidViewModel {


    private final MediatorLiveData<List<Entries>> mObserverMediatorLiveDataOffersList;
    private final ExclusiveOffersApiRepository apiRepository;
    private final EntriesDatabaseRepository dbRepository;
//    private final LiveData<List<Entries>> entriesDatabaseRepository;
    private AppDatabase mDatabase;
    private final AppExecutors mAppExecutors;

    public OfferViewModel(@NonNull Application application) {
        super(application);
        Configs.application=application;
        mDatabase =new AppDatabase() {
            @Override
            public Dao dao() {
                return null;
            }
        };
        mAppExecutors = new AppExecutors();

        mDatabase= AppDatabase.getAppDatabase(application.getApplicationContext(),mAppExecutors);


        this.mObserverMediatorLiveDataOffersList = new MediatorLiveData<>();
        this.mObserverMediatorLiveDataOffersList.setValue(null);
        // api repository
        apiRepository = new ExclusiveOffersApiRepository(application);
        // db-liveData Entries
        dbRepository=new EntriesDatabaseRepository(AppDatabase.getAppDatabase(application.getApplicationContext(),mAppExecutors));
//        entriesDatabaseRepository = ((BasicApp)application).getDatabaseInstance().dao().getLiveEntries();
//        mObserverMediatorLiveDataOffersList.addSource(entriesDatabaseRepository, mObserverMediatorLiveDataOffersList::setValue);
    }

    public MutableLiveData<ArrayList<Entries>> getEntriesList(){
        return apiRepository.callAPI();
    }

    // TODO: 9/27/2022 use this mesthod to retrieve data onConnectionLost
    public LiveData<List<Entries>> getLiveDataList(){
        return dbRepository.getLiveEntries();
    }

    public long insertData(Entries entry){
        return dbRepository.insertEntries(entry);
    }
}