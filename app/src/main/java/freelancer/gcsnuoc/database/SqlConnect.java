package freelancer.gcsnuoc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by VinhNB on 8/8/2017.
 */

@Deprecated
public class SqlConnect {

    private static SqlConnect sInstance;
    private int mOpenCounter;

    private SqlHelperExamp mSqlHelperExamp;
    private SQLiteDatabase mSqLiteDatabase;

    private SqlConnect(Context context) {
        mSqlHelperExamp = new SqlHelperExamp(context);
    }


    public static synchronized SqlConnect getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SqlConnect(context);
        }
        return sInstance;
    }

    public synchronized SQLiteDatabase open() {
        if (mOpenCounter == 0) {
            mSqLiteDatabase = mSqlHelperExamp.getWritableDatabase();
        }
        mOpenCounter++;
        return mSqLiteDatabase;
    }

    public synchronized void close() {
        if (mOpenCounter <= 0)
            return;
        if (mOpenCounter == 0)
            mSqLiteDatabase.close();
        mOpenCounter--;
    }

}

