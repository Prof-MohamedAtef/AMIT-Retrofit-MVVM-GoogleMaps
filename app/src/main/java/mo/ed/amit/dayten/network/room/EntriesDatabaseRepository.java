package mo.ed.amit.dayten.network.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import mo.ed.amit.dayten.network.room.Entries;


public class EntriesDatabaseRepository {
    private static EntriesDatabaseRepository entriesAPiRepository;
    private final AppDatabase mDatabase;
    private MediatorLiveData<List<Entries>> mObservableEntries;

    public EntriesDatabaseRepository(AppDatabase appDatabase) {
        this.mDatabase = appDatabase;
        mObservableEntries = new MediatorLiveData<>();

        mObservableEntries.addSource(mDatabase.dao().getLiveEntries(),
                entries -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableEntries.postValue(entries);
                    }
                });
    }

    public static EntriesDatabaseRepository getEntriesAPiRepoInstance(final AppDatabase database) {
        if (entriesAPiRepository == null) {
            synchronized (EntriesDatabaseRepository.class) {
                if (entriesAPiRepository == null) {
                    entriesAPiRepository = new EntriesDatabaseRepository(database);
                }
            }
        }
        return entriesAPiRepository;
    }

    public List<Entries> getEntries(){
        return mDatabase.dao().getEntries();
    }

    public LiveData<List<Entries>> getLiveEntries(){
        return mDatabase.dao().getLiveEntries();
    }

    public long insertEntries(Entries entry){
        return mDatabase.dao().insertEntries(entry);
    }
}