package freelancer.gcsnuoc.app;

import android.app.Application;
import android.content.Context;

import freelancer.gcsnuoc.entities.SettingObject;

public class GCSApplication extends Application {
    private static final String TAG = "GCSApplication";
    private static Context mContext;
    private static SettingObject settingObject;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
    }


    public static SettingObject getSettingObject() {
        return settingObject;
    }

    public static void setSettingObject(SettingObject settingObject) {
        try {
            GCSApplication.settingObject = (SettingObject) settingObject.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
