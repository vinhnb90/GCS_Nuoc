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
import freelancer.gcsnuoc.entities.ImageCustomerProxy;
import freelancer.gcsnuoc.entities.ImageItem;
import freelancer.gcsnuoc.entities.ImageItemProxy;
import freelancer.gcsnuoc.entities.SESSION;
import freelancer.gcsnuoc.entities.SessionProxy;
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
    public int insertTBL_BOOK(BookItem bookItem) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                bookItem.getBookName(),
                bookItem.getStatusBook().getStatus(),
                bookItem.getCustomerWrited(),
                bookItem.getCustomerNotWrite(),
//                bookItem.getPeriod(),
                bookItem.getTerm_book(),
                bookItem.getMonth_book(),
                bookItem.getYear_book(),
                bookItem.getBookCode(),
                bookItem.getFigureBookId(),
                String.valueOf(bookItem.isFocus()),
                String.valueOf(bookItem.isChoose()),
                bookItem.getMA_NVIEN(),
                bookItem.getCODE()
        );

        mSqLiteDatabase.execSQL(getInsertTBL_BOOK(), args);
        return this.getIDLastRow(TBL_BOOK.getName(), TBL_BOOK.ID_TBL_BOOK.name());
    }

    public void deleteAllTBL_SESSION(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllTBL_SESSION(), args);
    }

    public void deleteAllTBL_SESSIONByUSER_NAME(String USER_NAME) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                USER_NAME
        );

        mSqLiteDatabase.execSQL(SqlQuery.deleteAllTBL_SESSIONByUSER_NAME(), args);
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

    public int getNumberRowTBL_BOOK(String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build(MA_NVIEN);

        Cursor cursor = mSqLiteDatabase.rawQuery(getSelectTBL_BOOK(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public void updateChooseTBL_BOOK(int ID, boolean isChoosed, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                isChoosed,
                ID,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(getUpdateChooseTBL_BOOK(), args);
    }

    public void updateFocusTBL_BOOK(int ID, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                ID,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(getUpdateFocusTBL_BOOK(), args);
    }

    public List<BookItemProxy> selectAllTBL_BOOK(String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(MA_NVIEN);
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

    public List<BookItemProxy> selectAllTBL_BOOKHasWrited(String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(MA_NVIEN);
        List<BookItemProxy> bookItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.selectAllTBL_BOOKHasWrited(), args);

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


    public void updateResetFocusTBL_BOOK(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateResetFocusTBL_BOOK(), args);
    }

    public void deleteAllRowTBL_BOOK(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllRowTBL_BOOK(), args);
    }

    public void deleteAllRowUploadedTBL_BOOK(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllRowUploadedTBL_BOOK(), args);
    }

    public void deleteAllRowUploadedTBL_BOOKByStatus(String MA_NVIEN, BookItem.STATUS_BOOK statusBook) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN,
                statusBook.getStatus()
        );

        mSqLiteDatabase.execSQL(SqlQuery.deleteAllRowUploadedTBL_BOOKByStatus(), args);
    }

    public void deleteAllRowUploadedTBL_CUSTOMER(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllRowUploadedTBL_CUSTOMER(), args);
    }

    public void deleteAllRowUploadedTBL_CUSTOMERByStatus(String MA_NVIEN, CustomerItem.STATUS_Customer statusCustomer) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN,
                statusCustomer.getStatus()
        );

        mSqLiteDatabase.execSQL(SqlQuery.deleteAllRowUploadedTBL_CUSTOMERByStatus(), args);
    }


    public void updateCUS_WRITEDOfTBL_BOOK(int CUS_WRITED, int ID, String MA_NVIEN, boolean isCUS_WRITED) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                ID,
                CUS_WRITED,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateCUS_WRITEDOfTBL_BOOK(isCUS_WRITED), args);

    }
    //endregion

    //region TBL_CUSTOMER
    public int getNumberRowTBL_CUSTOMER(int ID_TBL_BOOK_OF_CUSTOMER, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
        String[] args = SqlDAO.build(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);

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
                String.valueOf(customerItem.isFocus()),
                customerItem.getOldIndex(),
                customerItem.getNewIndex(),
                customerItem.getMA_NVIEN(),

                customerItem.getIndexId(),
                customerItem.getDepartmentId(),
                customerItem.getPointId(),
                customerItem.getPointcode(),
                customerItem.getTimeOfUse(),
                customerItem.getCoefficient(),
                customerItem.getElectricityMeterId(),
                customerItem.getTerm(),
                customerItem.getMonth(),
                customerItem.getYear(),
                customerItem.getIndexType(),
                customerItem.getStartDate(),
                customerItem.getEndDate(),
                customerItem.getCustomerId(),
                customerItem.getFigureBookId_Customer(),
                customerItem.getCustomerCode()
        );

        mSqLiteDatabase.execSQL(getInsertTBL_CUSTOMER(), args);
        return this.getIDLastRow(TBL_CUSTOMER.getName(), TBL_CUSTOMER.ID_TBL_CUSTOMER.name());
    }


    public void updateFocusTBL_CUSTOMER(int ID_TBL_CUSTOMER, boolean isFocus, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                isFocus,
                ID_TBL_CUSTOMER,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(getUpdateFocusTBL_CUSTOMER(), args);
    }

    public void updateStatusTBL_CUSTOMER(int ID_TBL_CUSTOMER, CustomerItem.STATUS_Customer statusCustomer, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                statusCustomer.getStatus(),
                ID_TBL_CUSTOMER,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(getUpdateStatusTBL_CUSTOMER(), args);
    }

    public void updateStatusTBL_BOOK(int ID_TBL_BOOK, BookItem.STATUS_BOOK statusBook, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                statusBook.getStatus(),
                ID_TBL_BOOK,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(getUpdateStatusTBL_BOOK(), args);
    }

    public void updateNEW_INDEXOfTBL_CUSTOMER(int ID_TBL_CUSTOMER, double NEW_INDEX, String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                NEW_INDEX,
                ID_TBL_CUSTOMER,
                MA_NVIEN
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

    public List<ImageCustomerProxy> selectAllTBL_CUSTOMERByStatus(String MA_NVIEN, CustomerItem.STATUS_Customer statusCustomer) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(statusCustomer.getStatus(), MA_NVIEN);
        List<ImageCustomerProxy> imageCustomerProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(getJOINSelectAllTBL_IMAGEByStatus(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return imageCustomerProxies;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            imageCustomerProxies.add(new ImageCustomerProxy(cursor, cursor.getPosition()));
            cursor.moveToNext();
        }

        if (imageCustomerProxies.isEmpty())
            closeCursor(cursor);
        return imageCustomerProxies;
    }

    public int getNumberRowStatusTBL_CUSTOMERByBook(String MA_NVIEN, CustomerItem.STATUS_Customer statusCustomer, int ID_TBL_BOOK_OF_CUSTOMER) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN,
                statusCustomer.getStatus(),
                ID_TBL_BOOK_OF_CUSTOMER
        );

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getNumberRowStatusTBL_CUSTOMERByBook(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int getNumberRowStatusTBL_CUSTOMER(String MA_NVIEN, CustomerItem.STATUS_Customer statusCustomer) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN,
                statusCustomer.getStatus()
        );

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getNumberRowStatusTBL_CUSTOMER(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public void updateResetFocusTBL_CUSTOMER(String MA_NVIEN, int ID_TBL_BOOK_OF_CUSTOMER) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN,
                ID_TBL_BOOK_OF_CUSTOMER
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

    public void deleteAllRowTBL_CUSTOMER(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllRowTBL_CUSTOMER(), args);
    }


    public int countAllByStatusTBL_CUSTOMER(int ID_TBL_BOOK_OF_CUSTOMER, String MA_NVIEN, CustomerItem.STATUS_Customer statusCustomer) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build(MA_NVIEN, statusCustomer.getStatus(), ID_TBL_BOOK_OF_CUSTOMER);

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_CUSTOMERbyStatus(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public int checkExistCustomer(String pointId, int term, int month, int year, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build(pointId, term, month, year, MA_NVIEN);

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getCheckExistCustomer(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    //endregion

    //region IMAGE
    public void deleteIMAGE(int ID_TBL_IMAGE, String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                ID_TBL_IMAGE,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteIMAGE(), args);
    }

    public int insertTBL_IMAGE(ImageItem imageItem, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                imageItem.getID_TBL_CUSTOMER(),
                imageItem.getID_TBL_BOOK_OF_IMAGE(),
                imageItem.getNAME(),
                imageItem.getLOCAL_URI(),
                MA_NVIEN,
                imageItem.getCREATE_DAY()
        );

        mSqLiteDatabase.execSQL(getInsertTBL_IMAGE(), args);
        return this.getIDLastRow(TBL_IMAGE.getName(), TBL_IMAGE.ID_TBL_IMAGE.name());
    }

    public List<ImageItemProxy> selectAllTBL_IMAGE(String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(MA_NVIEN);
        List<ImageItemProxy> imageItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectAllTBL_IMAGE(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return imageItemProxies;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            imageItemProxies.add(new ImageItemProxy(cursor, cursor.getPosition()));
            cursor.moveToNext();
        }

        if (imageItemProxies.isEmpty())
            closeCursor(cursor);
        return imageItemProxies;
    }

    public void deleteAllRowTBL_IMAGE(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteAllRowTBL_IMAGE(), args);
    }

    public void deleteRowTBL_IMAGE(int ID_TBL_CUSTOMER_OF_IMAGE, String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                ID_TBL_CUSTOMER_OF_IMAGE,
                MA_NVIEN
        );

        mSqLiteDatabase.execSQL(SqlQuery.getDeleteRowTBL_IMAGE(), args);
    }
    //endregion

    //region DetailProxy
    public int checkExistTBL_BOOK(String code, int term_book, int month_book, int year_book, String BookCode, String ma_nvien) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = build(code, term_book, month_book, year_book, BookCode, ma_nvien);

        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.checkExistTBL_BOOK(), args);
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    public List<DetailProxy> getSelectAllDetailProxy(int ID_TBL_BOOK_OF_CUSTOMER, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
        String[] args = SqlDAO.build(
                MA_NVIEN,
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

    public List<DetailProxy> getSelectAllDetailProxyNOTWrite(int ID_TBL_BOOK_OF_CUSTOMER, String MA_NVIEN) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
        String[] args = SqlDAO.build(
                MA_NVIEN,
                ID_TBL_BOOK_OF_CUSTOMER
        );

        List<DetailProxy> CustomerItemProxies = new ArrayList<>(ID_TBL_BOOK_OF_CUSTOMER);

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectAllDetailProxyNotWrite(), args);
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

    public SessionProxy getInfoPassTBL_SESSION(String MA_NVIEN, String USER_NAME) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(MA_NVIEN, USER_NAME);
        SessionProxy sessionProxy = null;

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectPassTBL_SESSION(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return sessionProxy;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sessionProxy = new SessionProxy(cursor, cursor.getPosition());
            cursor.moveToNext();
        }

        if (sessionProxy == null)
            closeCursor(cursor);
        return sessionProxy;
    }


    public SessionProxy checkAccountTBL_SESSION(String USER_NAME, String PASS) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(USER_NAME, PASS);
        SessionProxy sessionProxy = null;

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(SqlQuery.checkAccountTBL_SESSION(), args);

        if (cursor == null) {
            Log.d(TAG, "getAllCongTo: null cursor");
            return sessionProxy;
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            sessionProxy = new SessionProxy(cursor, cursor.getPosition());
            cursor.moveToNext();
        }

        if (sessionProxy == null)
            closeCursor(cursor);
        return sessionProxy;
    }

    public List<BookItemProxy> selectAllTBL_BOOKByChoose(String MA_NVIEN) throws FileNotFoundException {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(MA_NVIEN);
        List<BookItemProxy> bookItemProxies = new ArrayList<>();

        Cursor cursor = null;
        cursor = mSqLiteDatabase.rawQuery(getsSelectAllTBL_BOOKByChoose(), args);

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
}
