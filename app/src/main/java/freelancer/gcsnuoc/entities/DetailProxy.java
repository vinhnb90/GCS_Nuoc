package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.database.SqlQuery;

public class DetailProxy extends CursorItemProxy {
    private static final String TAG = "DetailProxy";
    private CustomerItem mCustomerItem;
    private ImageItem mImageItem;
    private BookItem mBookItem;
    private Bitmap mBitmap;

    public DetailProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mCustomerItem = new CustomerItem();
        mImageItem = new ImageItem();
        mBookItem = new BookItem();
    }


    //region BOOK
    public String getPeriodOfTBL_BOOK() {
        if (TextUtils.isEmpty(mBookItem.getPeriod())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String PERIOD = SqlQuery.TBL_BOOK.PERIOD.getNameCollumn();
            mBookItem.setPeriod(cursor.getString(cursor.getColumnIndex(PERIOD)));
        }
        return mBookItem.getPeriod();
    }
    //endregion


    //region IMAGE
    public int getIDOfTBL_IMAGE() {
        if (mImageItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_IMAGE.ID_TBL_IMAGE.getNameCollumn();
            mImageItem.setID(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mImageItem.getID();
    }

    public int getID_TBL_CUSTOMEROfTBL_IMAGE() {
        if (mImageItem.getID_TBL_CUSTOMER() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_CUSTOMER = SqlQuery.TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE.getNameCollumn();
            mImageItem.setID_TBL_CUSTOMER(cursor.getInt(cursor.getColumnIndex(ID_TBL_CUSTOMER)));
        }
        return mImageItem.getID_TBL_CUSTOMER();
    }

    public String getNAMEOfTBL_IMAGE() {
        if (TextUtils.isEmpty(mImageItem.getNAME())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_IMAGE.NAME_IMAGE.getNameCollumn();
            mImageItem.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mImageItem.getNAME();
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

    public Bitmap getBitmap() {
        if (TextUtils.isEmpty(getLOCAL_URIOfTBL_IMAGE())) {
            mBitmap = null;
            return mBitmap;
        }

        //get bitmap tu URI
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBitmap = BitmapFactory.decodeFile(getLOCAL_URIOfTBL_IMAGE(), options);
        return mBitmap;
    }

    public double getOLD_INDEXOfTBL_CUSTOMER() {
        if (mCustomerItem.getOldIndex() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String OLD_INDEX = SqlQuery.TBL_CUSTOMER.OLD_INDEX.getNameCollumn();
            mCustomerItem.setOldIndex(cursor.getInt(cursor.getColumnIndex(OLD_INDEX)));
        }
        return mCustomerItem.getOldIndex();
    }

    public double getNEW_INDEXOfTBL_CUSTOMER() {
        if (mCustomerItem.getNewIndex() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NEW_INDEX = SqlQuery.TBL_CUSTOMER.NEW_INDEX.getNameCollumn();
            mCustomerItem.setNewIndex(cursor.getInt(cursor.getColumnIndex(NEW_INDEX)));
        }
        return mCustomerItem.getNewIndex();
    }

    public String getCREATE_DAYOfTBL_IMAGE() {
        if (TextUtils.isEmpty(mImageItem.getCREATE_DAY())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String CREATE_DAY = SqlQuery.TBL_IMAGE.CREATE_DAY.getNameCollumn();
            mImageItem.setCREATE_DAY(cursor.getString(cursor.getColumnIndex(CREATE_DAY)));
        }
        return mImageItem.getCREATE_DAY();
    }
    //endregion


    //region CUSTOMER
    public int getIDOfTBL_CUSTOMER() {
        if (mCustomerItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_CUSTOMER_MAIN = SqlQuery.TBL_CUSTOMER.ID_TBL_CUSTOMER.getNameCollumn();
            mCustomerItem.setID(cursor.getInt(cursor.getColumnIndex(ID_TBL_CUSTOMER_MAIN)));
        }
        return mCustomerItem.getID();
    }

    public int getID_TBL_BOOKOfTBL_CUSTOMER() {
        if (mCustomerItem.getIDBook() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_BOOK = SqlQuery.TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.getNameCollumn();
            mCustomerItem.setIDBook(cursor.getInt(cursor.getColumnIndex(ID_TBL_BOOK)));
        }
        return mCustomerItem.getIDBook();
    }

    public String getCustomerNameOfTBL_CUSTOMER() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerName())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_CUSTOMER.NAME_CUSTOMER.getNameCollumn();
            mCustomerItem.setCustomerName(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mCustomerItem.getCustomerName();
    }

    public String getCustomerAddressOfTBL_CUSTOMER() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerAddress())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String CustomerAddress = SqlQuery.TBL_CUSTOMER.ADDRESS_CUSTOMER.getNameCollumn();
            mCustomerItem.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerAddress)));
        }
        return mCustomerItem.getCustomerAddress();
    }

    public CustomerItem.STATUS_Customer getStatusCustomerOfTBL_CUSTOMER() {
        if (mCustomerItem.getStatusCustomer() == null) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String STATUS = SqlQuery.TBL_CUSTOMER.STATUS_CUSTOMER.getNameCollumn();
            String data = cursor.getString(cursor.getColumnIndex(STATUS));
            mCustomerItem.setStatusCustomer(CustomerItem.STATUS_Customer.findNameBy(data));
        }
        return mCustomerItem.getStatusCustomer();
    }

    public boolean isFocusOfTBL_CUSTOMER() {
        Cursor cursor = getmCursor();
        cursor.moveToPosition(getmIndex());
        String isFocus = SqlQuery.TBL_CUSTOMER.FOCUS_CUSTOMER.getNameCollumn();
        boolean data = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(isFocus)));
        mCustomerItem.setFocus(data);
        return data;
    }

    public CustomerItem getCustomerItemClone() {
        CustomerItem customerItem = new CustomerItem();
        customerItem.setID(this.getIDOfTBL_CUSTOMER());
        customerItem.setIDBook(getID_TBL_BOOKOfTBL_CUSTOMER());
        customerItem.setCustomerName(getCustomerNameOfTBL_CUSTOMER());
        customerItem.setCustomerAddress(getCustomerAddressOfTBL_CUSTOMER());
        customerItem.setStatusCustomer(getStatusCustomerOfTBL_CUSTOMER());
        customerItem.setFocus(isFocusOfTBL_CUSTOMER());
        return customerItem;
    }

    public ImageItem getImageItemClone() {
        ImageItem customerItem = new ImageItem();

        customerItem.setID(this.getIDOfTBL_IMAGE());
        customerItem.setID_TBL_CUSTOMER(getIDOfTBL_CUSTOMER());
        customerItem.setLOCAL_URI(getLOCAL_URIOfTBL_IMAGE());
        customerItem.setCREATE_DAY(getCREATE_DAYOfTBL_IMAGE());
        return mImageItem;
    }

    public BookItem getBookItem() {
        return mBookItem;
    }

    public void resetAll() {
        mCustomerItem = new CustomerItem();
        mImageItem = new ImageItem();
        mBookItem = new BookItem();
        mBitmap = null;
    }

    //endregion
}
