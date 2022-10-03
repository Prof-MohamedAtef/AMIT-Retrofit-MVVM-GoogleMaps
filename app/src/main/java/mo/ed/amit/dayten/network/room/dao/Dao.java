package mo.ed.amit.dayten.network.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mo.ed.amit.dayten.network.room.model.entries.Entries;
import mo.ed.amit.dayten.network.room.model.profiles.Profile;


@androidx.room.Dao

public interface Dao {
    @Insert
    long insertEntries(Entries entry);

    @Query("SELECT * FROM Entries")
    LiveData<List<Entries>> getLiveEntries();

    @Query("SELECT * FROM Entries")
    List<Entries> getEntries();


    /*

    insert profiles
     */

    @Insert
    long insertProfiles(Profile profile);

    @Query("SELECT * FROM Profile")
    LiveData<List<Profile>> getLiveProfiles();

    @Query("DELETE FROM Profile")
    int deleteProfiles();
}