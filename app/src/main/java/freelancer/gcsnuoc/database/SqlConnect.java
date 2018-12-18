package freelancer.gcsnuoc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by VinhNB on 8/8/2017.
 */

public class SqlConnect {

    private static SqlConnect sInstance;
    private int mOpenCounter;

    private SqlHelper mSqlHelper;
    private SQLiteDatabase mSqLiteDatabase;

    private SqlConnect(Context context) {
        mSqlHelper = new SqlHelper(context);
    }


    public static synchronized SqlConnect getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SqlConnect(context);
        }
        return sInstance;
    }

    public synchronized SQLiteDatabase open() {
        if (mOpenCounter == 0) {
            mSqLiteDatabase = mSqlHelper.getWritableDatabase();
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

