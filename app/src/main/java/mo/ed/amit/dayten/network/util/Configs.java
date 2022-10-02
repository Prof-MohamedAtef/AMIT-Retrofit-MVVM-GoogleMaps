package mo.ed.amit.dayten.network.util;

import android.app.Application;
import android.content.Context;

import mo.ed.amit.dayten.network.view.MainActivity;

public class Configs {
    public static Application application;
    public static Context mContext;
    public static MainActivity activity;
    public static String development_baseUrl="https://api.publicapis.org";
    public static String talab_baseUrl="https://talabstation.org";
    public static final String MULTIPART = "multipart/form-data";

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1001;
}
