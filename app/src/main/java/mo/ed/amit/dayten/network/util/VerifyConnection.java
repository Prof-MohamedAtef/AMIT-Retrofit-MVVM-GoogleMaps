package mo.ed.amit.dayten.network.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class VerifyConnection {

    public static Context mContext;
    public  VerifyConnection(Context context){
        this.mContext=context;
    }

    public boolean isInternetConnected;

    public boolean checkConnection() {
        return isInternetConnected=isConnected();
    }

    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork!=null){
            return isInternetConnected= activeNetwork.isConnected();
        }else
            return isInternetConnected=false;
    }
}