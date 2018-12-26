package freelancer.gcsnuoc.database;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.entities.CursorItemProxy;
import freelancer.gcsnuoc.entities.CustomerItem;

public class CustomerItemProxy extends CursorItemProxy {
    private CustomerItem mCustomerItem;

    public CustomerItemProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mCustomerItem = new CustomerItem();
    }

    public int getID() {
        if (mCustomerItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_CUSTOMER.ID_TBL_CUSTOMER.getNameCollumn();
            mCustomerItem.setID(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mCustomerItem.getID();
    }

    public int getIDCustomer() {
        if (mCustomerItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_BOOK = SqlQuery.TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.getNameCollumn();
            mCustomerItem.setIDBook(cursor.getInt(cursor.getColumnIndex(ID_TBL_BOOK)));
        }
        return mCustomerItem.getID();
    }

    public String getCustomerName() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerName())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_CUSTOMER.NAME_CUSTOMER.getNameCollumn();
            mCustomerItem.setCustomerName(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mCustomerItem.getCustomerName();
    }

    public String getCustomerAddress() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerAddress())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String CustomerAddress = SqlQuery.TBL_CUSTOMER.ADDRESS_CUSTOMER.getNameCollumn();
            mCustomerItem.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerAddress)));
        }
        return mCustomerItem.getCustomerAddress();
    }

    public CustomerItem.STATUS_Customer getStatusCustomer() {
        if (mCustomerItem.getStatusCustomer() == null) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String STATUS = SqlQuery.TBL_CUSTOMER.STATUS_CUSTOMER.getNameCollumn();
            String data = cursor.getString(cursor.getColumnIndex(STATUS));
            mCustomerItem.setStatusCustomer(CustomerItem.STATUS_Customer.findNameBy(data));
        }
        return mCustomerItem.getStatusCustomer();
    }

    public boolean isFocus() {
        Cursor cursor = getmCursor();
        cursor.moveToPosition(getmIndex());
        String isFocus = SqlQuery.TBL_CUSTOMER.FOCUS_CUSTOMER.getNameCollumn();
        boolean data = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(isFocus)));
        mCustomerItem.setFocus(data);
        return data;
    }
}
