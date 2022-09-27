package mo.ed.amit.dayten.network.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@androidx.room.Dao

public interface Dao {
    @Insert
    long insertEntries(Entries entry);

    @Query("SELECT * FROM Entries")
    LiveData<List<Entries>> getLiveEntries();

    @Query("SELECT * FROM Entries")
    List<Entries> getEntries();
}