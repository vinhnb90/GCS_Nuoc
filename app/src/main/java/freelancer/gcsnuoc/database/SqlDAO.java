package freelancer.gcsnuoc.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.entities.CustomerItem;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.entities.ImageItem;
import freelancer.gcsnuoc.entities.SESSION;
import freelancer.gcsnuoc.utils.Common;

import static android.content.ContentValues.TAG;
import static freelancer.gcsnuoc.database.SqlQuery.*;

public class SqlDAO {
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;

    public SqlDAO(SQLiteDatabase mSqLiteDatabase, Context mContext) {
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
                arr[i] = String.valueOf(value ? "true" : "false");
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

    //region TBL_SESSION
    public void deleteAllTBL_SESSION(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllTBL_SESSION(), args);
    }

    public int insertTBL_SESSION(SESSION tblSession) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
        String[] args = SqlDAO.build(
                tblSession.getUSERNAME(),
                tblSession.getPASSWORD(),
                tblSession.getDATE_LOGIN(),
                tblSession.getMA_NVIEN()
        );

        mSqLiteDatabase.execSQL(getInsertTBL_SESSION(), args);
        return this.getIDLastRow(TBL_BOOK.getName(), TBL_BOOK.ID_TBL_BOOK.name());
    }
    //endregion

    //region TBL_BOOK
    public int getNumberRowTBL_BOOK() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build();

        Cursor cursor = mSqLiteDatabase.rawQuery(getSelectTBL_BOOK(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int insertTBL_BOOK(BookItem bookItem) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                bookItem.getBookName(),
                bookItem.getStatusBook().getStatus(),
                bookItem.getCustomerWrited(),
                bookItem.getCustomerNotWrite(),
                bookItem.getPeriod(),
                String.valueOf(bookItem.isFocus()),
                String.valueOf(bookItem.isChoose())
        );

        mSqLiteDatabase.execSQL(getInsertTBL_BOOK(), args);
        return this.getIDLastRow(TBL_BOOK.getName(), TBL_BOOK.ID_TBL_BOOK.name());
    }

    public void updateChooseTBL_BOOK(int ID, boolean isChoosed) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                isChoosed,
                ID
        );

        mSqLiteDatabase.execSQL(getUpdateChooseTBL_BOOK(), args);
    }

    public void updateFocusTBL_BOOK(int ID) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                ID
        );

        mSqLiteDatabase.execSQL(getUpdateFocusTBL_BOOK(), args);
    }


    public List<BookItemProxy> selectAllTBL_BOOK() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build();
        List<BookItemProxy> bookItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(getSelectTBL_BOOK(), args);

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
    public int getNumberRowTBL_CUSTOMER(int ID_TBL_BOOK_OF_CUSTOMER) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
        String[] args = SqlDAO.build(ID_TBL_BOOK_OF_CUSTOMER);

        Cursor cursor = mSqLiteDatabase.rawQuery(getSelectTBL_CUSTOMER(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int insertTBL_CUSTOMER(CustomerItem customerItem) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                customerItem.getIDBook(),
                customerItem.getCustomerName(),
                customerItem.getCustomerAddress(),
                customerItem.getStatusCustomer().getStatus(),
                String.valueOf(customerItem.isFocus())
        );
        mSqLiteDatabase.execSQL(getInsertTBL_CUSTOMER(), args);
        return this.getIDLastRow(TBL_CUSTOMER.getName(), TBL_CUSTOMER.ID_TBL_CUSTOMER.name());
    }


    public void updateFocusTBL_CUSTOMER(int ID_TBL_CUSTOMER, boolean isFocus) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                isFocus,
                ID_TBL_CUSTOMER
        );

        mSqLiteDatabase.execSQL(getUpdateFocusTBL_CUSTOMER(), args);
    }

    public void updateStatusTBL_CUSTOMER(int ID_TBL_CUSTOMER, CustomerItem.STATUS_Customer statusCustomer) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                statusCustomer.getStatus(),
                ID_TBL_CUSTOMER
        );

        mSqLiteDatabase.execSQL(getUpdateStatusTBL_CUSTOMER(), args);
    }

    public void updateNEW_INDEXOfTBL_CUSTOMER(int ID_TBL_CUSTOMER, int NEW_INDEX) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                NEW_INDEX,
                ID_TBL_CUSTOMER
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateNEW_INDEXOfTBL_CUSTOMER(), args);

    }


    public List<CustomerItemProxy> selectAllTBL_CUSTOMER() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build();
        List<CustomerItemProxy> CustomerItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(getSelectTBL_CUSTOMER(), args);

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

    //region IMAGE
    public void deleteIMAGE(int ID_TBL_IMAGE) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                ID_TBL_IMAGE
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteIMAGE(), args);
    }

    public int insertTBL_IMAGE(ImageItem imageItem) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                imageItem.getID_TBL_CUSTOMER(),
                imageItem.getNAME(),
                imageItem.getLOCAL_URI(),
                String.valueOf(imageItem.getOLD_INDEX()),
                String.valueOf(imageItem.getNEW_INDEX()),
                imageItem.getCREATE_DAY()
        );

        mSqLiteDatabase.execSQL(getInsertTBL_IMAGE(), args);
        return this.getIDLastRow(TBL_IMAGE.getName(), TBL_IMAGE.ID_TBL_IMAGE.name());
    }
    //endregion

    //region DetailProxy
    public List<DetailProxy> getSelectAllDetailProxy(int ID_TBL_BOOK_OF_CUSTOMER) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
        String[] args = SqlDAO.build(
                ID_TBL_BOOK_OF_CUSTOMER
        );

        List<DetailProxy> CustomerItemProxies = new ArrayList<>(ID_TBL_BOOK_OF_CUSTOMER);

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectAllDetailProxy(), args);
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

    public void updateResetFocusTBL_BOOK() throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateResetFocusTBL_BOOK(), args);
    }

    public void updateResetFocusTBL_CUSTOMER() throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateResetFocusTBL_CUSTOMER(), args);
    }

    public int getIDByFocusTBL_CUSTOMER() throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
        );

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getIDByFocusTBL_CUSTOMER(), args);
        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return 0;
        }

        if (cursor.getCount() == 0) {
            closeCursor(cursor);
            return 0;
        }

        cursor.moveToFirst();
        int ID_TBL_CUSTOMER = cursor.getInt(cursor.getColumnIndex(TBL_CUSTOMER.ID_TBL_CUSTOMER.name()));
        closeCursor(cursor);
        return ID_TBL_CUSTOMER;
    }


    //endregion
}
