package mo.ed.amit.dayten.network.room;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(getBaseContext());
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabaseInstance(){
        return AppDatabase.getInstance(this, mAppExecutors);
    }

//    public EntriesAPiRepository getDatabaseRepo(){
//        return EntriesAPiRepository
//    }
}
