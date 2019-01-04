package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.database.SqlQuery;

public class SessionProxy extends CursorItemProxy {
    private SESSION mSESSION;

    public SessionProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mSESSION = new SESSION();
    }

    public int getID() {
        if (mSESSION.getID_TABLE_SESSION() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_SESSION.ID_TABLE_SESSION.getNameCollumn();
            mSESSION.setID_TABLE_SESSION(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mSESSION.getID_TABLE_SESSION();
    }

    public String getPASSWORD() {
        if (TextUtils.isEmpty(mSESSION.getPASSWORD())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_SESSION.PASSWORD.getNameCollumn();
            mSESSION.setPASSWORD(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mSESSION.getPASSWORD();
    }

    public String getNAME_NVIEN() {
        if (TextUtils.isEmpty(mSESSION.getNAME_NVIEN())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME_NVIEN = SqlQuery.TBL_SESSION.NAME_NVIEN.getNameCollumn();
            mSESSION.setNAME_NVIEN(cursor.getString(cursor.getColumnIndex(NAME_NVIEN)));
        }
        return mSESSION.getNAME_NVIEN();
    }


    public String getMA_NVIEN() {
        if (TextUtils.isEmpty(mSESSION.getMA_NVIEN())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_SESSION.MA_NVIEN.getNameCollumn();
            mSESSION.setMA_NVIEN(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mSESSION.getMA_NVIEN();
    }

}
