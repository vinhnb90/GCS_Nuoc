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

public class SqlHelperExamp extends SQLiteOpenHelper {

    public static final String PATH_FOLDER_DB = Environment.getExternalStorageDirectory() + File.separator + "BARCODE_HY" + File.separator;
    public static final String DB_NAME = "BARCODE_HUNGYEN_PC.s3db";
    private static final int DB_VER = 80;

    public SqlHelperExamp(Context context) {
        super(context, PATH_FOLDER_DB + DB_NAME, null, DB_VER);
        Common.isExistDB();
        SQLiteDatabase.openOrCreateDatabase(PATH_FOLDER_DB + DB_NAME, null);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_CTO_PB());
//        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_CTO_GUI_KD());
//        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_DIENLUC());
//        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_HISTORY());
//        sqLiteDatabase.execSQL(SqlQuery.getCreateTBL_DIENLUC_PB());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        if (newVer > oldVer) {
//            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_CTO_PB());
//            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_CTO_GUI_KD());
//            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_DIENLUC());
//            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_HISTORY());
//            sqLiteDatabase.execSQL(SqlQuery.getDropTBL_DIENLUC_PB());
            onCreate(sqLiteDatabase);
        }
    }
}
