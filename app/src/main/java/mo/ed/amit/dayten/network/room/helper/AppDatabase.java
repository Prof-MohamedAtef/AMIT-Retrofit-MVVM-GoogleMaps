package mo.ed.amit.dayten.network.room.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import mo.ed.amit.dayten.network.room.dao.Dao;
import mo.ed.amit.dayten.network.room.model.entries.Entries;


@Database(entities = Entries.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    private static String DATABASE_NAME = "AMIT-Database";
    public abstract Dao dao();
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }

    public static AppDatabase getAppDatabase(Context context, final AppExecutors executors) {
        if (INSTANCE == null) {
            try {
                /*
                block of code
                 */
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
                AppDatabase appDatabase=AppDatabase.getInstance(context,executors);
                appDatabase.setDatabaseCreated();
//                AppDatabase database=AppDatabase.ge
            } catch (Exception e) {
                return null;
            }
        }
        return INSTANCE;
    }


    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (INSTANCE==null){
            synchronized (AppDatabase.class){
                if (INSTANCE==null){
                    INSTANCE=getAppDatabase(context,executors);
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }
}