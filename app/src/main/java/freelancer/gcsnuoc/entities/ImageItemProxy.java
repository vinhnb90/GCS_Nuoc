package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.database.SqlQuery;

public class ImageItemProxy extends CursorItemProxy {
    private ImageItem mImageItem;

    public ImageItemProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mImageItem = new ImageItem();
    }

    public int getID() {
        if (mImageItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_IMAGE.ID.getNameCollumn();
            mImageItem.setID(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mImageItem.getID();
    }


    public int getID_TBL_CUSTOMER() {
        if (mImageItem.getID_TBL_CUSTOMER() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_CUSTOMER = SqlQuery.TBL_IMAGE.ID_TBL_CUSTOMER.getNameCollumn();
            mImageItem.setID_TBL_CUSTOMER(cursor.getInt(cursor.getColumnIndex(ID_TBL_CUSTOMER)));
        }
        return mImageItem.getID_TBL_CUSTOMER();
    }

    public String getNAME() {
        if (TextUtils.isEmpty(mImageItem.getNAME())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_IMAGE.NAME.getNameCollumn();
            mImageItem.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mImageItem.getNAME();
    }

    public String getLOCAL_URI() {
        if (TextUtils.isEmpty(mImageItem.getLOCAL_URI())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String LOCAL_URI = SqlQuery.TBL_IMAGE.LOCAL_URI.getNameCollumn();
            mImageItem.setLOCAL_URI(cursor.getString(cursor.getColumnIndex(LOCAL_URI)));
        }
        return mImageItem.getLOCAL_URI();
    }

    public int getOLD_INDEX() {
        if (mImageItem.getOLD_INDEX() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String OLD_INDEX = SqlQuery.TBL_IMAGE.OLD_INDEX.getNameCollumn();
            mImageItem.setOLD_INDEX(cursor.getInt(cursor.getColumnIndex(OLD_INDEX)));
        }
        return mImageItem.getOLD_INDEX();
    }

    public int getNEW_INDEX() {
        if (mImageItem.getNEW_INDEX() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NEW_INDEX = SqlQuery.TBL_IMAGE.NEW_INDEX.getNameCollumn();
            mImageItem.setNEW_INDEX(cursor.getInt(cursor.getColumnIndex(NEW_INDEX)));
        }
        return mImageItem.getNEW_INDEX();
    }

    public String getCREATE_DAY() {
        if (TextUtils.isEmpty(mImageItem.getCREATE_DAY())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String CREATE_DAY = SqlQuery.TBL_IMAGE.CREATE_DAY.getNameCollumn();
            mImageItem.setCREATE_DAY(cursor.getString(cursor.getColumnIndex(CREATE_DAY)));
        }
        return mImageItem.getCREATE_DAY();
    }


}
