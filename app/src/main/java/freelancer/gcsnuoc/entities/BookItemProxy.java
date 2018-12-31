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

    public void setFocus(boolean isFocus) {
        mBookItem.setFocus(isFocus);
    }

    public String getBookCode() {
        if (TextUtils.isEmpty(mBookItem.getBookCode())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_BOOK.BookCode.getNameCollumn();
            mBookItem.setBookCode(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mBookItem.getBookCode();
    }

    public int getFigureBookId() {
        if (mBookItem.getFigureBookId() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_BOOK.FigureBookId.getNameCollumn();
            mBookItem.setFigureBookId(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mBookItem.getFigureBookId();
    }

    public String getCODE() {
        if (TextUtils.isEmpty(mBookItem.getCODE())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_BOOK.CODE.getNameCollumn();
            mBookItem.setCODE(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mBookItem.getCODE();
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
        return mBookItem.isFocus();
    }


    public boolean isChoose() {
        Cursor cursor = getmCursor();
        cursor.moveToPosition(getmIndex());
        String isChoose = SqlQuery.TBL_BOOK.CHOOSE.getNameCollumn();
        boolean data = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(isChoose)));
        mBookItem.setChoose(data);
        return data;
    }

    public int getTerm_book() {
        if (mBookItem.getTerm_book() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_BOOK.term_book.getNameCollumn();
            mBookItem.setTerm_book(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mBookItem.getTerm_book();
    }


    public int getMonth_book() {
        if (mBookItem.getMonth_book() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String month_book = SqlQuery.TBL_BOOK.month_book.getNameCollumn();
            mBookItem.setMonth_book(cursor.getInt(cursor.getColumnIndex(month_book)));
        }
        return mBookItem.getMonth_book();
    }


    public int getYear_book() {
        if (mBookItem.getYear_book() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_BOOK.year_book.getNameCollumn();
            mBookItem.setYear_book(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mBookItem.getYear_book();
    }

}
