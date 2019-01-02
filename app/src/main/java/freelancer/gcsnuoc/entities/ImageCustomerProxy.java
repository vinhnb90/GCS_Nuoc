package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.database.SqlQuery;

public class ImageCustomerProxy extends CursorItemProxy {
    private static final String TAG = "ImageCustomerProxy";
    private CustomerItem mCustomerItem;
    private ImageItem mImageItem;

    public ImageCustomerProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mImageItem = new ImageItem();
        mCustomerItem = new CustomerItem();
    }

    public String getLOCAL_URIOfTBL_IMAGE() {
        if (TextUtils.isEmpty(mImageItem.getLOCAL_URI())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String LOCAL_URI = SqlQuery.TBL_IMAGE.LOCAL_URI.getNameCollumn();
            mImageItem.setLOCAL_URI(cursor.getString(cursor.getColumnIndex(LOCAL_URI)));
        }
        return mImageItem.getLOCAL_URI();
    }

    public int getID() {
        if (mCustomerItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_CUSTOMER = SqlQuery.TBL_CUSTOMER.ID_TBL_CUSTOMER.getNameCollumn();
            mImageItem.setID(cursor.getInt(cursor.getColumnIndex(ID_TBL_CUSTOMER)));
        }
        return mImageItem.getID();
    }

}
