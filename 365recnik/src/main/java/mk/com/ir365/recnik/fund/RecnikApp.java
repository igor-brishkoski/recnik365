package mk.com.ir365.recnik.fund;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class RecnikApp extends Application {


    private final String TAG = RecnikApp.class.getSimpleName();


    private static SharedPreferences sharedPreferences;
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;

    }

    /**
     * @return apps default SharedPrefrences
     */
    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    /**
     * @return the app Context
     */
    public static Context getContext() {
        return context;
    }


}
