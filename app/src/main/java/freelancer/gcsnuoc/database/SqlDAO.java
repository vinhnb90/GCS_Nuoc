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
import freelancer.gcsnuoc.utils.Common;

import static android.content.ContentValues.TAG;

public class SqlDAO {
    private SQLiteDatabase mSqLiteDatabase;
    private Context mContext;

    public SqlDAO(SQLiteDatabase mSqLiteDatabase, Context mContext) {
        this.mSqLiteDatabase = mSqLiteDatabase;
        this.mContext = mContext;
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

        String[] args = SqlDAO.build(
                bookItem.getBookName(),
                bookItem.getStatusBook(),
                bookItem.getCustomerWrited(),
                bookItem.getCustomerNotWrite(),
                bookItem.isFocus(),
                bookItem.isChoose()
        );

        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_BOOK(), args);
        return this.getIDLastRow(SqlQuery.TBL_BOOK.getNameTable(), SqlQuery.TBL_BOOK.ID.getNameCollumn());
    }

    public void updateChooseTBL_BOOK(int ID, boolean isChoosed) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                isChoosed,
                ID
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateChooseTBL_BOOK(), args);
    }

    public void updateFocusTBL_BOOK(int ID, boolean isFocus) throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build(
                isFocus,
                ID
        );

        mSqLiteDatabase.execSQL(SqlQuery.getUpdateFocusTBL_BOOK(), args);
    }


    public List<BookItemProxy> selectAllTBL_BOOK() throws Exception {
        if (!Common.isExistDB())
            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());

        String[] args = SqlDAO.build();
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

    /*Common*/

    public int getIDLastRow(String table, String idNameCollumn) {
        String selectQuery = "SELECT * FROM " + table + " ORDER BY " + idNameCollumn + " DESC LIMIT 1";
        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, null);
        int id = 0;
        if (cursor.moveToFirst())
            id = cursor.getInt(cursor.getColumnIndex(idNameCollumn));
        cursor.close();
        return id;
    }

//    //region Tbl_CongTo
////    public List<CongToGuiKDProxy> getAllCongTo(Common.KIEU_CHUONG_TRINH mKieuChuongTrinh) throws Exception {
////        if (!Common.isExistDB())
////            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
////
////        String[] args = SqlDAO.build(
////                MainActivity.sTaiKhoan
////        );
////        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
////
////        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectTBL_CTO_PB(), args);
////
////        if (cursor == null) {
////            Log.d(TAG, "getAllCongTo: null cursor");
////            return listCongToProxies;
////        }
////
////        cursor.moveToFirst();
////        while (!cursor.isAfterLast()) {
////            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
////            cursor.moveToNext();
////        }
////
////        if (listCongToProxies.isEmpty())
////            closeCursor(cursor);
////        return listCongToProxies;
////    }
//
//    public List<CongToPBProxy> getByDateAllCongToPB(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getAllCongToByDatePB(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public List<CongToPBProxy> getByDateAllCongToPBNoSuccess(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToPBNoSuccess(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//
//    public List<CongToGuiKDProxy> getByDateAllCongToKD(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getAllCongToByDateKD(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public List<CongToGuiKDProxy> getByDateAllCongToKDNoSuccess(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getAllCongToByDateKDNoSuccess(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public int insertTBL_CTO_GUI_KD(CongToGuiKD congToGuiKD) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                congToGuiKD.getCHON(),
//                congToGuiKD.getSTT(),
//                congToGuiKD.getMA_CTO(),
//                congToGuiKD.getSO_CTO(),
//                congToGuiKD.getMA_DVIQLY(),
//
//                congToGuiKD.getMA_CLOAI(),
//                congToGuiKD.getNGAY_NHAP_HT(),
////                Common.convertDateUIToDateSQL(congToGuiKD.getNGAY_NHAP_HT()),
//                congToGuiKD.getNAM_SX(),
//                congToGuiKD.getLOAI_SOHUU(),
//                congToGuiKD.getTEN_SOHUU(),
//
//                congToGuiKD.getMA_BDONG(),
//                congToGuiKD.getNGAY_BDONG(),
////                Common.convertDateUIToDateSQL(congToGuiKD.getNGAY_BDONG()),
//                congToGuiKD.getNGAY_BDONG_HTAI(),
////                Common.convertDateUIToDateSQL(congToGuiKD.getNGAY_BDONG_HTAI()),
//                congToGuiKD.getSO_PHA(),
//                congToGuiKD.getSO_DAY(),
//
//                congToGuiKD.getDONG_DIEN(),
//                congToGuiKD.getVH_CONG(),
//                congToGuiKD.getDIEN_AP(),
//                congToGuiKD.getHS_NHAN(),
//                congToGuiKD.getNGAY_KDINH(),
//
//                congToGuiKD.getSO_GKDCT_MTB(),
//
//                congToGuiKD.getCHISO_THAO(),
//                congToGuiKD.getHSN(),
//                congToGuiKD.getNGAY_NHAP(),
////                Common.convertDateUIToDateSQL(congToGuiKD.getNGAY_NHAP()),
//                congToGuiKD.getNGAY_NHAP_MTB(),
//                congToGuiKD.getTRANG_THAI_GHIM(),
//                congToGuiKD.getTRANG_THAI_CHON()
//        );
//
////        ContentValues initialValues = new ContentValues();
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.MA_CTO.getNameCollumn(), Common.MA_CTO.CHUA_GUI.getCode());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.MA_CTO.getNameCollumn(), congToGuiKD.getMA_CTO());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.SO_CTO.getNameCollumn(), congToGuiKD.getSO_CTO());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.MA_DVIQLY.getNameCollumn(), congToGuiKD.getMA_DVIQLY());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.MA_CLOAI.getNameCollumn(), congToGuiKD.getMA_CLOAI());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NGAY_NHAP_HT.getNameCollumn(), congToGuiKD.getNGAY_NHAP_HT());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NAM_SX.getNameCollumn(), congToGuiKD.getNAM_SX());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.LOAI_SOHUU.getNameCollumn(), congToGuiKD.getLOAI_SOHUU());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.TEN_SOHUU.getNameCollumn(), congToGuiKD.getTEN_SOHUU());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.MA_BDONG.getNameCollumn(), congToGuiKD.getMA_BDONG());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NGAY_BDONG.getNameCollumn(), congToGuiKD.getNGAY_BDONG());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NGAY_BDONG_HTAI.getNameCollumn(), congToGuiKD.getNGAY_BDONG_HTAI());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.SO_PHA.getNameCollumn(), congToGuiKD.getSO_PHA());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.SO_DAY.getNameCollumn(), congToGuiKD.getSO_DAY());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.DONG_DIEN.getNameCollumn(), congToGuiKD.getDONG_DIEN());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.VH_CONG.getNameCollumn(), congToGuiKD.getVH_CONG());
////
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.DIEN_AP.getNameCollumn(), congToGuiKD.getDIEN_AP());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.HS_NHAN.getNameCollumn(), congToGuiKD.getHS_NHAN());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NGAY_KDINH.getNameCollumn(), congToGuiKD.getNGAY_KDINH());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.CHISO_THAO.getNameCollumn(), congToGuiKD.getCHISO_THAO());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.HSN.getNameCollumn(), congToGuiKD.getHSN());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NGAY_NHAP.getNameCollumn(), congToGuiKD.getNGAY_NHAP());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn(), congToGuiKD.getNGAY_NHAP_MTB());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn(), congToGuiKD.getTRANG_THAI_GHIM());
////        initialValues.put(SqlQuery.TBL_CTO_GUI_KD.TRANG_THAI_CHON.getNameCollumn(), congToGuiKD.getTRANG_THAI_CHON());
////
////
////        int rowAffect = -1;
////        try {
////            rowAffect = (int) mSqLiteDatabase.insertOrThrow(SqlQuery.TBL_CTO_GUI_KD.getNameTable(), null, initialValues);
////        } catch (Exception e) {
////            Log.w(TAG, "insertTBL_CTO_GUI_KD: " + " has row not insert because same id" );
////        }
////
////        if(rowAffect == -1)
////            return 0;
//
//        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_CTO_GUI_KD(), args);
//        return this.getIDLastRow(SqlQuery.TBL_CTO_GUI_KD.getNameTable(), SqlQuery.TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn());
//    }
//
//    public int getIDLastRow(String table, String idNameCollumn) {
//        String selectQuery = "SELECT * FROM " + table + " ORDER BY " + idNameCollumn + " DESC LIMIT 1";
////        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = mSqLiteDatabase.rawQuery(selectQuery, null);
//        int id = 0;
//        if (cursor.moveToFirst())
//            id = cursor.getInt(cursor.getColumnIndex(idNameCollumn));
//        cursor.close();
//        return id;
//    }
//
//    public int insertTBL_CTO_PB(CongToPB congToPB) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                congToPB.getCHON(),
//                congToPB.getMA_CTO(),
//                congToPB.getSO_CTO(),
//                congToPB.getMA_DVIQLY(),
//                congToPB.getMA_CLOAI(),
//
////                Common.convertDateUIToDateSQL(congToPB.getNGAY_NHAP_HTHONG()),
//                congToPB.getNAM_SX(),
//                congToPB.getLOAI_SOHUU(),
//                congToPB.getMA_BDONG(),
//                congToPB.getNGAY_BDONG(),
//
//                congToPB.getSO_PHA(),
//                congToPB.getSO_DAY(),
//                congToPB.getDONG_DIEN(),
//                congToPB.getVH_CONG(),
//                congToPB.getDIEN_AP(),
//
//                congToPB.getNGAY_KDINH(),
//                congToPB.getNGAY_NHAP(),
//                congToPB.getNGAY_NHAP_MTB(),
//                congToPB.getTRANG_THAI_GHIM(),
//
//                congToPB.getTRANG_THAI_CHON(),
//
//                //mới
//                congToPB.getID_BBAN_KHO(),
//                congToPB.getMA_NVIEN(),
//                congToPB.getSO_BBAN(),
//                congToPB.getID_BBAN_KDINH(),
//
//                congToPB.getNGAY_GUIKD(),
//                congToPB.getLOAI_CTO(),
//                congToPB.getSO_CSO(),
//                congToPB.getMA_HANG(),
//
//                congToPB.getCAP_CXAC(),
//                congToPB.getMA_NUOC(),
//
//                //them
//                congToPB.getLOAISOHUU(),
//                congToPB.getNGAY_NHAP_HTHI(),
//                congToPB.getSO_BBAN_KDINH(),
//                congToPB.getMA_NVIENKDINH(),
//                congToPB.getNGAY_KDINH_HTHI(),
//                congToPB.getSO_PBCT_MTB(),
//                congToPB.getMA_DVIQLY_CAPDUOI()
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_CTO_PB(), args);
//        return this.getIDLastRow(SqlQuery.TBL_CTO_PB.getNameTable(), SqlQuery.TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn());
//    }
//
//
//    public void insertTBL_HISTORY(History history) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                history.getID_TBL_CTO(),
//                history.getTYPE_TBL_CTO(),
//                history.getTYPE_SESSION(),
//                history.getDATE_SESSION(),
//                history.getTYPE_RESULT(),
//                history.getINFO_SEARCH(),
//                history.getINFO_RESULT()
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_HISTORY(), args);
//    }
//
//    public void deleteAllCongTo() throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
////                MainActivity.sTaiKhoan
//        );
//        mSqLiteDatabase.rawQuery(SqlQuery.getDeleteAllTBL_CTO_PB(), args);
//    }
//
//    public void updateGhimCtoPB(int idCto, int statusGhimCto) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                statusGhimCto,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getUpdateGhimCtoPB(), args);
//    }
//
//    public void updateGhimCtoKD(int idCto, int statusGhimCto) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                statusGhimCto,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getUpdateGhimCtoKD(), args);
//    }
//
////    public void updateGhimCtoPB(int idCto, int statusGhimCto) throws Exception {
////        if (!Common.isExistDB())
////            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
////
////        String[] args = SqlDAO.build(
////                statusGhimCto,
////                idCto
////        );
////
////        mSqLiteDatabase.execSQL(SqlQuery.updateGhimCtoPB(), args);
////    }
//
//
//    public void getUpdateGhimCtoAllKD(String dateSQL, int TRANG_THAI_GHIM) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                TRANG_THAI_GHIM,
//                dateSQL
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.updateGhimCtoAllKD(), args);
//    }
//
//    public void updateChonCtoPB(int idCto, int chon) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                chon,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getUpdateChonCtoPB(), args);
//    }
//
//    public void updateChonCtoKD(int idCto, int chon) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                chon,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getUpdateChonCtoKD(), args);
//    }
//
//    public void updateSO_GKDCT_MTBCtoKD(int idCto, String SO_GKDCT_MTB) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                SO_GKDCT_MTB,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getupdateSO_GKDCT_MTBCtoKD(), args);
//    }
//
//    public void updateTRANG_THAI_CHONCto(int idCto, int TRANG_THAI_CHON, Common.KIEU_CHUONG_TRINH kieuChuongTrinh) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                TRANG_THAI_CHON,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL((kieuChuongTrinh == Common.KIEU_CHUONG_TRINH.KIEM_DINH) ? SqlQuery.getUpdateTRANG_THAI_CHONCtoKD() : SqlQuery.getUpdateTRANG_THAI_CHONCtoPB(), args);
//    }
//
//    public String selectDienLuc(String maDienLuc) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        if (StringUtils.isEmpty(maDienLuc))
//            return "";
//
//        return "";
//    }
//
//    public List<CongToPBProxy> getByDateAllCongToGhimPB(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateSelectCongToGhimPB(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public List<CongToPBProxy> getByDateAllCongToGhimPBNoSuccess(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToGhimPBNoSuccess(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public int countByDateAllCongToGhimKD(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.countByDateAllCongToGhimKD(), args);
//        int count = 0;
//        if (cursor != null) {
//            cursor.moveToFirst();
//            count = cursor.getInt(0);
//        }
//
//        return count;
//
//    }
//
//    public int countByDateAllCongToGhimPB(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.countByDateAllCongToGhimPB(), args);
//        int count = 0;
//        if (cursor != null) {
//            cursor.moveToFirst();
//            count = cursor.getInt(0);
//        }
//
//        return count;
//    }
//
//
//    public List<CongToGuiKDProxy> getByDateAllCongToGhimKD(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateSelectCongToGhimKD(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public List<CongToGuiKDProxy> getByDateAllCongToGhimKDNoSuccess(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateSelectCongToGhimKDNoSuccess(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//
//    public int deleteCongToPB(int idCongTo) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                idCongTo
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getDeletePB(), args);
//
//        return getIDLastRow(SqlQuery.TBL_CTO_PB.getNameTable(), SqlQuery.TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn());
//    }
//
//    public int deleteCongToKD(int idCongTo) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                idCongTo
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getDeleteKD(), args);
//
//        return getIDLastRow(SqlQuery.TBL_CTO_GUI_KD.getNameTable(), SqlQuery.TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn());
//    }
//
//
//    public boolean checkExistTBL_CTO_GUI_KD(String MA_CTO) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                MA_CTO
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getCheckExistTBL_CTO_GUI_KD(), args);
//        if (cursor == null) {
//            throw new Exception(Common.MESSAGE.ex02.getContent());
//        }
//
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
//
//        return (count > 0) ? true : false;
//    }
//
//    public boolean checkExistTBL_DVI_PB_CAPDUOI(String MA_DVIQLY_CAPTREN, String MA_DVIQLY_CAPDUOI) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                MA_DVIQLY_CAPTREN,
//                MA_DVIQLY_CAPDUOI
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getcheckExistTBL_DVI_PB_CAPDUOI(), args);
//        if (cursor == null) {
//            throw new Exception(Common.MESSAGE.ex02.getContent());
//        }
//
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
//
//        return (count > 0) ? true : false;
//    }
//
//    public int checkExistByDateTBL_CTO_GUI_KDreturnID(String MA_CTO, String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                MA_CTO,
//                dateSQL
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getCheckExistByDateTBL_CTO_GUI_KD(), args);
//
//        if (cursor != null) {
//            if (cursor.getCount() == 0)
//                return 0;
//            else {
//                cursor.moveToFirst();
//                return cursor.getInt(cursor.getColumnIndex(SqlQuery.TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn()));
//            }
//        }
//        return 0;
//    }
//
//
//    public boolean checkExistTBL_CTO_PB(String MA_CTO) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                MA_CTO
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getCheckExistTBL_CTO_PB(), args);
//        if (cursor == null) {
//            throw new Exception(Common.MESSAGE.ex02.getContent());
//        }
//
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
//
//        return (count > 0) ? true : false;
//    }
//
//    //endregion

//    //region TBL_DIENLUC
//    public List<DienLucProxy> getAllTBL_DIENLUC() throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//        );
//        List<DienLucProxy> dienLucProxies = new ArrayList<>();
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getSelectDienLuc(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return dienLucProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            dienLucProxies.add(new DienLucProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (dienLucProxies.isEmpty())
//            closeCursor(cursor);
//        return dienLucProxies;
//    }
//
//    public boolean checkExistTBL_DIENLUC(String MA_DVIQLY) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                MA_DVIQLY
//        );
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getCheckExistTBL_DIENLUC(), args);
//        if (cursor == null) {
//            throw new Exception(Common.MESSAGE.ex02.getContent());
//        }
//
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
//
//        return (count > 0) ? true : false;
//    }
//
//    public void insertTBL_DIENLUC(DienLuc dienLuc) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                dienLuc.getMA_DVIQLY()
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_DIENLUC(), args);
//    }
//
//    public List<CongToGuiKDProxy> getByDateAllCongToGhimAndChonKD(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToGhimAndChonKD(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public List<CongToPBProxy> getByDateAllCongToGhimAndChonPB(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
//
//        String[] args = SqlDAO.build(
//                dateSQL
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToGhimAndChonPB(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//
//    public void deleteByDateAllCongToKD(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                dateSQL
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.deleteByDateAllCongToKD(), args);
//    }
//
//    public int countByDateALLHistoryCto(String dateSQL, String TYPE_TBL_CTO, Common.DATE_TIME_TYPE typeDefault) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, typeDefault);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//        String[] args = SqlDAO.build(
//                TYPE_TBL_CTO,
//                beginDayTime,
//                endDayTime
//
//        );
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.countByDateALLHistoryCto(), args);
//
//        int count = 0;
//        if (cursor != null) {
//            count = cursor.getCount();
//        }
//        return count;
//    }
//
//    public List<HistoryProxy> getBydateALLHistoryCto(String dateSQL, String TYPE_TBL_CTO, Common.DATE_TIME_TYPE typeDefault, Common.KIEU_CHUONG_TRINH kieuChuongTrinh) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, typeDefault);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//        List<HistoryProxy> historyProxyList = new ArrayList<>();
//
//        String[] args = SqlDAO.build(
//                TYPE_TBL_CTO,
//                beginDayTime,
//                endDayTime
//
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getBydateALLHistoryCto(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return historyProxyList;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            historyProxyList.add(new HistoryProxy(cursor, cursor.getPosition(), kieuChuongTrinh));
//            cursor.moveToNext();
//        }
//
//        if (historyProxyList.isEmpty())
//            closeCursor(cursor);
//        return historyProxyList;
//    }
//
//    public List<DviPBCapDuoiProxy> getDviPBCapDuoi(String MA_DVIQLY_CAPTREN) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        List<DviPBCapDuoiProxy> dviPBCapDuoiProxies = new ArrayList<>();
//
//        String[] args = SqlDAO.build(
//                MA_DVIQLY_CAPTREN
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getDviCapDuoiPB(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return dviPBCapDuoiProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            dviPBCapDuoiProxies.add(new DviPBCapDuoiProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (dviPBCapDuoiProxies.isEmpty())
//            closeCursor(cursor);
//        return dviPBCapDuoiProxies;
//    }
//
//
//    public List<ThongKe> getByDateAllThongKeKD(String dateSQL, Common.DATE_TIME_TYPE typeDefault) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        List<ThongKe> result = new ArrayList<>();
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, typeDefault);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//        String[] args = SqlDAO.build(
//                beginDayTime,
//                endDayTime
//
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllThongKeKD(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return result;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            String SO_GKDCT_MTB = cursor.getString(cursor.getColumnIndex(SqlQuery.TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn()));
//            int count = cursor.getInt(cursor.getColumnIndex("COUNT"));
//
//            ThongKe thongKe = new ThongKe(SO_GKDCT_MTB, count);
//            result.add(thongKe);
//            cursor.moveToNext();
//        }
//
//        closeCursor(cursor);
//        return result;
//    }
//
//    public int countByDateAllBbanTamThoiThongKeKD(String dateSQL, Common.DATE_TIME_TYPE typeDefault) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        List<ThongKe> result = new ArrayList<>();
//
//        result = getByDateAllThongKeKD(dateSQL, typeDefault);
//
//        return result.size();
//    }
//
//    public int countByDateAllBbanTamThoiThongKePB(String dateSQL, Common.DATE_TIME_TYPE typeDefault) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        List<ThongKe> result = new ArrayList<>();
//
//        result = getByDateAllThongKePB(dateSQL, typeDefault);
//
//        return result.size();
//    }
//
//
//    public List<ThongKe> getByDateAllThongKePB(String dateSQL, Common.DATE_TIME_TYPE typeDefault) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        List<ThongKe> result = new ArrayList<>();
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, typeDefault);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//        String[] args = SqlDAO.build(
//                beginDayTime,
//                endDayTime
//
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllThongKePB(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return result;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            String SO_GKDCT_MTB = cursor.getString(cursor.getColumnIndex(SqlQuery.TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn()));
//            int count = cursor.getInt(cursor.getColumnIndex("COUNT"));
//
//            ThongKe thongKe = new ThongKe(SO_GKDCT_MTB, count);
//            result.add(thongKe);
//            cursor.moveToNext();
//        }
//
//        closeCursor(cursor);
//        return result;
//    }
//
//    public List<HistoryProxy> getBydateALLHistoryCtoNoSuccess(String dateSQL, String TYPE_TBL_CTO, Common.DATE_TIME_TYPE typeDefault, Common.KIEU_CHUONG_TRINH kieuChuongTrinh) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, typeDefault);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//        List<HistoryProxy> historyProxyList = new ArrayList<>();
//
//        String[] args = SqlDAO.build(
//                TYPE_TBL_CTO,
//                beginDayTime,
//                endDayTime
//
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getBydateALLHistoryCtoNoSuccess(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return historyProxyList;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            historyProxyList.add(new HistoryProxy(cursor, cursor.getPosition(), kieuChuongTrinh));
//            cursor.moveToNext();
//        }
//
//        if (historyProxyList.isEmpty())
//            closeCursor(cursor);
//        return historyProxyList;
//    }
//
//    public List<HistoryProxy> getBydateALLHistoryCtoSuccess(String dateSQL, String TYPE_TBL_CTO, Common.DATE_TIME_TYPE typeDefault, Common.KIEU_CHUONG_TRINH kieuChuongTrinh) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, typeDefault);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//        List<HistoryProxy> historyProxyList = new ArrayList<>();
//
//        String[] args = SqlDAO.build(
//                TYPE_TBL_CTO,
//                beginDayTime,
//                endDayTime
//
//        );
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getBydateALLHistoryCtoSuccess(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return historyProxyList;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            historyProxyList.add(new HistoryProxy(cursor, cursor.getPosition(), kieuChuongTrinh));
//            cursor.moveToNext();
//        }
//
//        if (historyProxyList.isEmpty())
//            closeCursor(cursor);
//        return historyProxyList;
//    }
//
//
//    public void getByDateDeleteHistory(int idRowDelete) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                idRowDelete
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getByDateDeleteHistory(), args);
//    }
//
//
////    public List<CongToGuiKDProxy> countByDateSessionHistoryCtoByRESULT(String dateSQL) throws Exception {
////        if (!Common.isExistDB())
////            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
////
////        String[] args = SqlDAO.build(
////                dateSQL
////        );
////        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
////
////        Cursor cursor = null;
////        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getAllCongToByDatePB(), args);
////
////        if (cursor == null) {
////            Log.d(TAG, "getAllCongTo: null cursor");
////            return listCongToProxies;
////        }
////
////        cursor.moveToFirst();
////        while (!cursor.isAfterLast()) {
////            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition(), Common.KIEU_CHUONG_TRINH.PHAN_BO));
////            cursor.moveToNext();
////        }
////
////        if (listCongToProxies.isEmpty())
////            closeCursor(cursor);
////        return listCongToProxies;
////    }
//
//
//    public int countByDateSessionHistoryCtoByRESULT(String date_session, Common.TYPE_TBL_CTO typeTblCto, Common.TYPE_SESSION typeSession, Common.TYPE_RESULT typeResult) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                date_session,
//                typeSession.getCode(),
//                typeTblCto.getCode(),
//                typeResult.getCode()
//        );
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.countByDateSessionHistoryCto(), args);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            return cursor.getInt(0);
//        }
//        return 0;
//    }
//
//    public int countByDateAllCongToPB(String date_session) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                date_session
//        );
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.countByDateAllPB(), args);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            return cursor.getInt(0);
//        }
//        return 0;
//    }
//
//
//    public int countByDateAllCongToKD(String date_session) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                date_session
//        );
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.countByDateAllKD(), args);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            return cursor.getInt(0);
//        }
//        return 0;
//    }
//
//    public List<CongToGuiKDProxy> getByDateAllCongToKDByDATE_SESSIONByTYPE_RESULT(String DATE_SESSION, Common.TYPE_SESSION typeSession) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                DATE_SESSION,
//                typeSession.getCode()
//        );
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToKDByDATE_SESSION(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public void getByDateDeleteAllHistory(String dateSQL, Common.TYPE_TBL_CTO TYPE_TBL_CTO) throws Exception {
//        dateSQL = Common.convertDateToDate(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyy, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//
//        Date date = Common.convertDateUIToDateSQL(dateSQL, Common.DATE_TIME_TYPE.ddMMyyyyHHmmss);
//        Date beginDay = Common.getStartOfDay(date);
//        Date endDay = Common.getEndOfDay(date);
//
//        long beginDayTime = beginDay.getTime();
//        long endDayTime = endDay.getTime();
//
//
//        String[] args = SqlDAO.build(
//                TYPE_TBL_CTO,
//                beginDayTime,
//                endDayTime
//
//        );
//        mSqLiteDatabase.execSQL(SqlQuery.getByDateDeleteAllHistory(), args);
//    }
//
//    public void deleteByDateAllCongToPB(String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                dateSQL
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.deleteByDateAllCongToPB(), args);
//    }
//
//    public void getUpdateGhimCtoAllPB(String dateSQL, int TRANG_THAI_GHIM) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                TRANG_THAI_GHIM,
//                dateSQL
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.updateGhimCtoAllPB(), args);
//    }
//
//
//    public int getNumberRowTBL_BOOK(String MA_CTO, String dateSQL) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = build(
//                MA_CTO,
//                dateSQL
//        );
//
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getCheckExistByDateTBL_CTO_PB(), args);
//        if (cursor != null) {
//            if (cursor.getCount() == 0)
//                return 0;
//            else {
//                cursor.moveToFirst();
//                return cursor.getInt(cursor.getColumnIndex(SqlQuery.TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn()));
//            }
//        }
//        return 0;
//    }
//
//    public List<CongToGuiKDProxy> getByDateAllCongToKDByDATE_SESSIONByTYPE_RESULT(String DATE_SESSION, Common.TYPE_SESSION type_session, Common.TYPE_RESULT type_result) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                DATE_SESSION,
//                type_session.getCode(),
//                type_result.getCode(),
//                Common.TYPE_TBL_CTO.KD.getCode()
//        );
//        List<CongToGuiKDProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToKDByDATE_SESSIONByTYPE_RESULT(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToGuiKDProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
//    public List<CongToPBProxy> getByDateAllCongToPBByDATE_SESSIONByTYPE_RESULT(String DATE_SESSION, Common.TYPE_SESSION typeSession) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                DATE_SESSION,
//                typeSession.getCode()
//        );
//        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
//
//        Cursor cursor = null;
//        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToPBByDATE_SESSION(), args);
//
//        if (cursor == null) {
//            Log.d(TAG, "getAllCongTo: null cursor");
//            return listCongToProxies;
//        }
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
//            cursor.moveToNext();
//        }
//
//        if (listCongToProxies.isEmpty())
//            closeCursor(cursor);
//        return listCongToProxies;
//    }
//
////    public List<CongToPBProxy> getByDateAllCongToPBByDATE_SESSIONByTYPE_RESULT(String DATE_SESSION, Common.TYPE_SESSION type_session, Common.TYPE_RESULT type_result) throws Exception {
////        if (!Common.isExistDB())
////            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
////
////        String[] args = SqlDAO.build(
////                DATE_SESSION,
////                type_session.getCode(),
////                type_result.getCode(),
////                Common.TYPE_TBL_CTO.PB.getCode()
////        );
////        List<CongToPBProxy> listCongToProxies = new ArrayList<>();
////
////        Cursor cursor = null;
////        cursor = mSqLiteDatabase.rawQuery(SqlQuery.getByDateAllCongToPBByDATE_SESSIONByTYPE_RESULT(), args);
////
////        if (cursor == null) {
////            Log.d(TAG, "getAllCongTo: null cursor");
////            return listCongToProxies;
////        }
////
////        cursor.moveToFirst();
////        while (!cursor.isAfterLast()) {
////            listCongToProxies.add(new CongToPBProxy(cursor, cursor.getPosition()));
////            cursor.moveToNext();
////        }
////
////        if (listCongToProxies.isEmpty())
////            closeCursor(cursor);
////        return listCongToProxies;
////    }
//
//    public String getByDateHistoryINFO_RESULT(int ID, Common.TYPE_TBL_CTO typeTblCto, Common.TYPE_SESSION mTypeSessionHistory, String mDateSessionHistory) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//        String INFO_RESULT = "";
//        String[] args = build(
//                typeTblCto.getCode(),
//                mDateSessionHistory,
//                mTypeSessionHistory.getCode(),
//                ID
//        );
//
//        Cursor cursor = mSqLiteDatabase.rawQuery(SqlQuery.getBydateHistoryINFO_RESULT(), args);
//        if (cursor != null) {
//            if (cursor.getCount() == 0)
//                return INFO_RESULT;
//            else {
//                cursor.moveToFirst();
//
//                INFO_RESULT = cursor.getString(cursor.getColumnIndex(SqlQuery.TBL_HISTORY.INFO_RESULT.getNameCollumn()));
//            }
//        }
//        return INFO_RESULT;
//    }
//
//    public void updateSO_PBCT_MTB(int idCto, String SO_PBCT_MTB) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                SO_PBCT_MTB,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.updateSO_PBCT_MTB(), args);
//    }
//
//    public void updateMA_DVIQLY_CAPDUOI(int idCto, String MA_DVIQLY_CAPDUOI) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                MA_DVIQLY_CAPDUOI,
//                idCto
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.MA_DVIQLY_CAPDUOI(), args);
//    }
//
//    public void insertTBL_DIENLUC_PB(String MA_DVIQLY_CAPTREN, String MA_DVIQLY_CAPDUOI, String SEARCH) throws Exception {
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(Common.MESSAGE.ex01.getContent());
//
//        String[] args = SqlDAO.build(
//                MA_DVIQLY_CAPTREN,
//                MA_DVIQLY_CAPDUOI,
//                SEARCH
//        );
//
//        mSqLiteDatabase.execSQL(SqlQuery.getInsertTBL_DIENLUC_PB(), args);
//
//    }
//
//    //endregion
}
