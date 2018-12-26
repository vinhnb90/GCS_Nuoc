package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.database.SqlQuery;

public class BookItemProxy extends CursorItemProxy {
    private BookItem mBookItem;

    public BookItemProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mBookItem = new BookItem();
    }

    public int getID() {
        if (mBookItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_BOOK.ID_TBL_BOOK.getNameCollumn();
            mBookItem.setID(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mBookItem.getID();
    }

    public String getBookName() {
        if (TextUtils.isEmpty(mBookItem.getBookName())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_BOOK.NAME.getNameCollumn();
            mBookItem.setBookName(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mBookItem.getBookName();
    }

    public BookItem.STATUS_BOOK getStatusBook() {
        if (mBookItem.getStatusBook() == null) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String STATUS = SqlQuery.TBL_BOOK.STATUS.getNameCollumn();
            String data = cursor.getString(cursor.getColumnIndex(STATUS));
            mBookItem.setStatusBook(BookItem.STATUS_BOOK.findNameBy(data));
        }
        return mBookItem.getStatusBook();
    }

    public int getCustomerWrited() {
        if (mBookItem.getCustomerWrited() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String getCustomerWrited = SqlQuery.TBL_BOOK.CUS_WRITED.getNameCollumn();
            mBookItem.setCustomerWrited(cursor.getInt(cursor.getColumnIndex(getCustomerWrited)));
        }
        return mBookItem.getCustomerWrited();
    }


    public int getCustomerNotWrite() {
        if (mBookItem.getCustomerNotWrite() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String getCustomerNotWrite = SqlQuery.TBL_BOOK.CUS_NOT_WRITED.getNameCollumn();
            mBookItem.setCustomerNotWrite(cursor.getInt(cursor.getColumnIndex(getCustomerNotWrite)));
        }
        return mBookItem.getCustomerNotWrite();
    }

    public boolean isFocus() {
        Cursor cursor = getmCursor();
        cursor.moveToPosition(getmIndex());
        String isFocus = SqlQuery.TBL_BOOK.FOCUS.getNameCollumn();
        boolean data = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(isFocus)));
        mBookItem.setFocus(data);
        return data;
    }


    public boolean isChoose() {
        Cursor cursor = getmCursor();
        cursor.moveToPosition(getmIndex());
        String isChoose = SqlQuery.TBL_BOOK.CHOOSE.getNameCollumn();
        boolean data = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(isChoose)));
        mBookItem.setChoose(data);
        return data;
    }

}
