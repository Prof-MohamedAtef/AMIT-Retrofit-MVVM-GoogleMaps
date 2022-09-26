package mo.ed.amit.dayten.network.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import mo.ed.amit.dayten.network.Configs;
import mo.ed.amit.dayten.network.model.EntriesModelResponse;
import mo.ed.amit.dayten.network.repo.ExclusiveOffersApiRepository;

public class OfferViewModel extends AndroidViewModel {


    private final MediatorLiveData<List<EntriesModelResponse.Entries>> mObserverMediatorLiveDataOffersList;
    private final ExclusiveOffersApiRepository apiRepository;

    public OfferViewModel(@NonNull Application application) {
        super(application);
        Configs.application=application;
        this.mObserverMediatorLiveDataOffersList = new MediatorLiveData<>();
        this.mObserverMediatorLiveDataOffersList.setValue(null);
        apiRepository = new ExclusiveOffersApiRepository(application);
    }

    public MutableLiveData<ArrayList<EntriesModelResponse.Entries>> getEntriesList(){
        return apiRepository.callAPI();
    }
}