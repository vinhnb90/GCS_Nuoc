package freelancer.gcsnuoc.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.database.config.TBL_BOOK;
import freelancer.gcsnuoc.database.config.TBL_CUSTOMER;
import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.entities.CustomerItem;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.utils.Common;

import static android.content.ContentValues.TAG;

public class GCSH20DAO {
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;

    public GCSH20DAO(SQLiteDatabase mSqLiteDatabase, Context mContext) {
        this.mSqLiteDatabase = mSqLiteDatabase;
        this.mContext = mContext;
    }

    //region Common
    public int getIDLastRow(String table, String idNameCollumn) {
        String selectQuery = "SELECT * FROM " + table + " ORDER BY " + idNameCollumn + " DESC LIMIT 1";
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, null);
        int id = 0;
        if (cursor.moveToFirst())
            id = cursor.getInt(cursor.getColumnIndex(idNameCollumn));
        cursor.close();
        return id;
    }

    public static String[] build(Object... values) {
        String[] arr = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Boolean) {
                boolean value = (Boolean) values[i];
                arr[i] = String.valueOf(value ? 1 : 0);
            } else {
                arr[i] = String.valueOf(values[i]);
            }
        }
        return arr;
    }

    public void closeCursor(final Cursor cursor) {
        if (cursor != null)
            cursor.close();
    }
    //endregion


    //region TBL_BOOK
    public int getNumberRowTBL_BOOK() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build();

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_BOOK(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int insertTBL_BOOK(BookItem bookItem) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build(
                bookItem.getBookName(),
                bookItem.getStatusBook(),
                bookItem.getCustomerWrited(),
                bookItem.getCustomerNotWrite(),
                bookItem.isFocus(),
                bookItem.isChoose()
        );

        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_BOOK(), args);
        return this.getIDLastRow(TBL_BOOK.table.getName(), TBL_BOOK.table.ID.name());
    }

    public void updateChooseTBL_BOOK(int ID, boolean isChoosed) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build(
                isChoosed,
                ID
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateChooseTBL_BOOK(), args);
    }

    public void updateFocusTBL_BOOK(int ID, boolean isFocus) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build(
                isFocus,
                ID
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateFocusTBL_BOOK(), args);
    }


    public List<BookItemProxy> selectAllTBL_BOOK() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build();
        List<BookItemProxy> bookItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_BOOK(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return bookItemProxies;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            bookItemProxies.add(new BookItemProxy(cursor, cursor.getPosition()));
            cursor.moveToNext();
        }

        if (bookItemProxies.isEmpty())
            closeCursor(cursor);
        return bookItemProxies;
    }
    //endregion


    //region TBL_CUSTOMER
    public int getNumberRowTBL_CUSTOMER() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build();

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_CUSTOMER(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int insertTBL_CUSTOMER(CustomerItem CustomerItem) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build(
                CustomerItem.getIDBook(),
                CustomerItem.getCustomerName(),
                CustomerItem.getStatusCustomer(),
                CustomerItem.isFocus()
        );

        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_CUSTOMER(), args);
        return this.getIDLastRow(TBL_CUSTOMER.table.getName(), TBL_CUSTOMER.table.ID.name());
    }


    public void updateFocusTBL_CUSTOMER(int ID, boolean isFocus) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build(
                isFocus,
                ID
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateFocusTBL_CUSTOMER(), args);
    }


    public List<CustomerItemProxy> selectAllTBL_CUSTOMER() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build();
        List<CustomerItemProxy> CustomerItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_CUSTOMER(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return CustomerItemProxies;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CustomerItemProxies.add(new CustomerItemProxy(cursor, cursor.getPosition()));
            cursor.moveToNext();
        }

        if (CustomerItemProxies.isEmpty())
            closeCursor(cursor);
        return CustomerItemProxies;
    }
    //endregion

    //region DetailProxy
    public List<DetailProxy> getSelectAllDetailProxy() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = GCSH20DAO.build();
        List<DetailProxy> CustomerItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_CUSTOMER(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return CustomerItemProxies;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CustomerItemProxies.add(new DetailProxy(cursor, cursor.getPosition()));
            cursor.moveToNext();
        }

        if (CustomerItemProxies.isEmpty())
            closeCursor(cursor);
        return CustomerItemProxies;
    }
    //endregion
}
