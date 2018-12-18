package freelancer.gcsnuoc.app;

import android.app.Application;
import android.content.Context;

public class GCSApplication extends Application {
    private static final String TAG = "GCSApplication";
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }

}
