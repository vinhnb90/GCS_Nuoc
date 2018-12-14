package freelancer.gcsnuoc.app;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.sharepref.entity.BookActivitySharePref;

public class GCSApplication extends Application {
    private static final String TAG = "GCSApplication";
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        try {
            //create shared pref
            ArrayList<Class<?>> setClassSharedPrefConfig = new ArrayList<Class<?>>();
            setClassSharedPrefConfig.add(BookActivitySharePref.class);
            SharePrefManager.getInstance(mContext, setClassSharedPrefConfig);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onCreate: create sharePref failer! " + e.getMessage());
        }
    }

    public static Context getContext() {
        return mContext;
    }

}
