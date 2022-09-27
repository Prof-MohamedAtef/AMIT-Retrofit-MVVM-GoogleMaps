package mo.ed.amit.dayten.network.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mo.ed.amit.dayten.network.room.model.Entries;


@androidx.room.Dao

public interface Dao {
    @Insert
    long insertEntries(Entries entry);

    @Query("SELECT * FROM Entries")
    LiveData<List<Entries>> getLiveEntries();

    @Query("SELECT * FROM Entries")
    List<Entries> getEntries();
}