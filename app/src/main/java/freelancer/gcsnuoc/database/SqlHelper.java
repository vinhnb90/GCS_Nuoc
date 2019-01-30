package freelancer.gcsnuoc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

import freelancer.gcsnuoc.utils.Common;

/**
 * Created by VinhNB on 8/8/2017.
 */

public class SqlHelper extends SQLiteOpenHelper {

    public static final String PATH_FOLDER_DB = Environment.getExternalStorageDirectory() + File.separator + "GCSh2o" + File.separator + "DB" + File.separator;
    public static final String DB_NAME = "GCSh2o.s3db";
    private static final int DB_VER = 104;

    public SqlHelper(Context context) {
        super(context, PATH_FOLDER_DB + DB_NAME, null, DB_VER);
        Common.isExistDB();
        SQLiteDatabase.openOrCreateDatabase(PATH_FOLDER_DB + DB_NAME, null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_SESSION());
        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_BOOK());
        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_CUSTOMER());
        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_IMAGE());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        if (newVer > oldVer) {
            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_SESSION());
            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_BOOK());
            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_CUSTOMER());
            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_IMAGE());
            onCreate(sqLiteDatabase);
        }
    }
}
