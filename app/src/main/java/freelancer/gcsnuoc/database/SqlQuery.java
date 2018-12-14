package freelancer.gcsnuoc.database;

import freelancer.gcsnuoc.utils.Common;

/**
 * Created by VinhNB on 8/8/2017.
 */

public class SqlQuery {
    public enum TBL_BOOK {
        ID("ID"),
        NAME("NAME"),
        STATUS("STATUS"),
        CUS_WRITED("CUS_WRITED"),
        CUS_NOT_WRITED("CUS_NOT_WRITED"),
        FOCUS("FOCUS"),
        CHOOSE("CHOOSE");

        private String mNameCollumn;

        TBL_BOOK(String mNameCollumn) {
            this.mNameCollumn = mNameCollumn;
        }

        public String getNameCollumn() {
            return mNameCollumn;
        }

        public static String getNameTable() {
            return "TBL_BOOK";
        }
    }


    public static String getCreateTBL_BOOK() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_BOOK.getNameTable() + " (" +
                TBL_BOOK.ID.getNameCollumn() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_BOOK.NAME.getNameCollumn() + " TEXT, " +
                TBL_BOOK.STATUS.getNameCollumn() + " TEXT, " +
                TBL_BOOK.CUS_WRITED.getNameCollumn() + " INTEGER, " +
                TBL_BOOK.CUS_NOT_WRITED.getNameCollumn() + " INTEGER, " +
                TBL_BOOK.FOCUS.getNameCollumn() + " TEXT DEFAULT FALSE, " +
                TBL_BOOK.CHOOSE.getNameCollumn() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_BOOK() {
        return "DROP TABLE IF EXISTS " + TBL_BOOK.getNameTable() + " ;";
    }

    public static String getSelectTBL_BOOK() {
        return "SELECT * " +
                " FROM " +
                TBL_BOOK.getNameTable();
    }

    public static String getInsertTBL_BOOK() {
        return "INSERT INTO " + TBL_BOOK.getNameTable() + " (" +
                TBL_BOOK.NAME.getNameCollumn() + ", " +
                TBL_BOOK.STATUS.getNameCollumn() + ", " +
                TBL_BOOK.CUS_WRITED.getNameCollumn() + ", " +
                TBL_BOOK.CUS_NOT_WRITED.getNameCollumn() + ", " +
                TBL_BOOK.FOCUS.getNameCollumn() + ", " +
                TBL_BOOK.CHOOSE.getNameCollumn() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getNameTable() +
                " SET " +

                TBL_BOOK.STATUS.getNameCollumn() +
                " = ? " +
                ", " +

                TBL_BOOK.CUS_WRITED.getNameCollumn() +
                " = ? " +
                ", " +

                TBL_BOOK.CUS_NOT_WRITED.getNameCollumn() +
                " = ? " +
                ", " +

                TBL_BOOK.FOCUS.getNameCollumn() +
                " = ? " +
                ", " +

                TBL_BOOK.CHOOSE.getNameCollumn() +
                " = ? " +

                " WHERE " +
                TBL_BOOK.ID.getNameCollumn() +
                " = ? "

                ;
    }


    public static String getUpdateChooseTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getNameTable() +
                " SET " +
                TBL_BOOK.CHOOSE.getNameCollumn() +
                " = ? " +
                " WHERE " +
                TBL_BOOK.ID.getNameCollumn() +
                " = ? "
                ;
    }

    public static String getUpdateFocusTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getNameTable() +
                " SET " +
                TBL_BOOK.FOCUS.getNameCollumn() +
                " = ? " +
                " WHERE " +
                TBL_BOOK.ID.getNameCollumn() +
                " = ? "
                ;
    }
//
//    //region TBL_CTO_PB query
//    public enum TBL_CTO_GUI_KD {
//        CHON("CHON"),
//        ID_TBL_CTO_GUI_KD("ID_TBL_CTO"),
//        STT("STT"),
//        MA_CTO("MA_CTO"),
//        SO_CTO("SO_CTO"),
//        MA_DVIQLY("MA_DVIQLY"),
//        MA_CLOAI("MA_CLOAI"),
//        NGAY_NHAP_HT("NGAY_NHAP_HT"),
//        NAM_SX("NAM_SX"),
//
//        LOAI_SOHUU("LOAI_SOHUU"),
//        TEN_SOHUU("TEN_SOHUU"),
//        MA_BDONG("MA_BDONG"),
//        NGAY_BDONG("NGAY_BDONG"),
//        NGAY_BDONG_HTAI("NGAY_BDONG_HTAI"),
//        SO_PHA("SO_PHA"),
//        SO_DAY("SO_DAY"),
//        DONG_DIEN("DONG_DIEN"),
//        VH_CONG("VH_CONG"),
//        DIEN_AP("DIEN_AP"),
//        HS_NHAN("HS_NHAN"),
//        NGAY_KDINH("NGAY_KDINH"),
//        CHISO_THAO("CHISO_THAO"),
//        HSN("HSN"),
//        NGAY_NHAP("NGAY_NHAP"),
//        SO_GKDCT_MTB("SO_GKDCT_MTB"),
//        //        GhimCto("GhimCongTo"),
////        TaiKhoan("TaiKhoan"),
//        NGAY_NHAP_MTB("NGAY_NHAP_MTB"),
//        //Trạng thái chưa ghim = 0, đã ghim = 1
//        TRANG_THAI_GHIM("TRANG_THAI_GHIM"),
//        //Trạng thái chưa chọn = 0, đã chọn = 1
//        TRANG_THAI_CHON("TRANG_THAI_CHON");
//
////        KieuCongToPhanBo("KieuCongToPhanBo");
//
//        private String mNameCollumn;
//
//        TBL_CTO_GUI_KD(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String getNameTable() {
//            return "TBL_CTO_GUI_KD";
//        }
//    }
//
//    public enum TBL_CTO_PB {
//        ID_TBL_CTO_PB("ID_TBL_CTO_PB"),
//
//        CHON("CHON"),
//        HS_NHAN("HS_NHAN"),
//        MA_DVIQLY("MA_DVIQLY"),
//        NAM_SX("NAM_SX"),
//        MA_CTO("MA_CTO"),
//
//        SO_CTO("SO_CTO"),
//        LOAI_SOHUU("LOAI_SOHUU"),
//        MA_CLOAI("MA_CLOAI"),
//        NGAY_BDONG("NGAY_BDONG"),
//        MA_BDONG("MA_BDONG"),
//
//        NGAY_NHAP("NGAY_NHAP"),
//        NGAY_KDINH("NGAY_KDINH"),
//        SO_DAY("SO_DAY"),
//        VH_CONG("VH_CONG"),
//        SO_PHA("SO_PHA"),
//
//        DIEN_AP("DIEN_AP"),
//        DONG_DIEN("DONG_DIEN"),
//
//        NGAY_NHAP_MTB("NGAY_NHAP_MTB"),
//        //Trạng thái chưa ghim = 0, đã ghim = 1
//        TRANG_THAI_GHIM("TRANG_THAI_GHIM"),
//
//        //Trạng thái chưa chọn = 0, đã chọn = 1
//        TRANG_THAI_CHON("TRANG_THAI_CHON"),
//
//
//        ID_BBAN_KHO("ID_BBAN_KHO"),
//        NGAY_NHAP_HTHONG("NGAY_NHAP_HTHONG"),
//        MA_NVIEN("MA_NVIEN"),
//        SO_BBAN("SO_BBAN"),
//        ID_BBAN_KDINH("ID_BBAN_KDINH"),
//
//        NGAY_GUIKD("NGAY_GUIKD"),
//        NGAY_KDINH_TH("NGAY_KDINH_TH"),
//        LOAI_CTO("LOAI_CTO"),
//        SO_CSO("SO_CSO"),
//        MA_HANG("MA_HANG"),
//
//        CAP_CXAC("CAP_CXAC"),
//        MA_NUOC("MA_NUOC"),
//        ACTION("ACTION"),
//
//        //thêm
//        LOAISOHUU("LOAISOHUU"),
//        NGAY_NHAP_HTHI("NGAY_NHAP_HTHI"),
//        SO_BBAN_KDINH("SO_BBAN_KDINH"),
//        MA_NVIENKDINH("MA_NVIENKDINH"),
//        NGAY_KDINH_HTHI("NGAY_KDINH_HTHI"),
//        SO_PBCT_MTB("SO_PBCT_MTB"),
//        MA_DVIQLY_CAPDUOI("MA_DVIQLY_CAPDUOI");
//
//
//        private String mNameCollumn;
//
//        TBL_CTO_PB(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String getNameTable() {
//            return "TBL_CTO_PB";
//        }
//    }
//
//    public enum TBL_DIENLUC {
//        ID_TBL_DIENLUC("ID_TBL_DIENLUC"),
//        MA_DVIQLY("MA_DVIQLY");
//
//
//        private String mNameCollumn;
//
//        TBL_DIENLUC(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String getNameTable() {
//            return "TBL_DIENLUC";
//        }
//    }
//
//    public enum TBL_DIENLUC_PB {
//        ID_TBL_DIENLUC_PB("ID_TBL_DIENLUC_PB"),
//        MA_DVIQLY_CAPTREN("MA_DVIQLY_CAPTREN"),
//        MA_DVIQLY_CAPDUOI("MA_DVIQLY_CAPDUOI"),
//        SEARCH("SEARCH"),;
//
//
//        private String mNameCollumn;
//
//        TBL_DIENLUC_PB(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String getNameTable() {
//            return "TBL_DIENLUC_PB";
//        }
//    }
//
//    public enum TBL_HISTORY {
//        ID_TBL_HISTORY("ID_TBL_HISTORY"),
//        ID_TBL_CTO("ID_TBL_CTO"),
//        TYPE_TBL_CTO("TYPE_TBL_CTO"),
//        TYPE_SESSION("TYPE_SESSION"),
//        DATE_SESSION("DATE_SESSION"),
//        TYPE_RESULT("TYPE_RESULT"),
//        INFO_SEARCH("INFO_SEARCH"),
//        INFO_RESULT("INFO_RESULT");
//
//        private String mNameCollumn;
//
//        TBL_HISTORY(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String getNameTable() {
//            return "TBL_HISTORY";
//        }
//    }
//
//    public static String getCreateTBL_CTO_GUI_KD() {
//        return "CREATE TABLE IF NOT EXISTS " + TBL_CTO_GUI_KD.getNameTable() + " (" +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                TBL_CTO_GUI_KD.CHON.getNameCollumn() + " INTEGER, " +
//                TBL_CTO_GUI_KD.STT.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.MA_CTO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.SO_CTO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.MA_DVIQLY.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.MA_CLOAI.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_HT.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NAM_SX.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.LOAI_SOHUU.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.TEN_SOHUU.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.MA_BDONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NGAY_BDONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NGAY_BDONG_HTAI.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.SO_PHA.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.SO_DAY.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.DONG_DIEN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.VH_CONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.DIEN_AP.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.HS_NHAN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NGAY_KDINH.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.CHISO_THAO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.HSN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NGAY_NHAP.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn() + " TEXT, " +
////                TBL_CTO_GUI_KD.TaiKhoan.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() + " TEXT, " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() + " INTEGER DEFAULT 0, " +
//                TBL_CTO_GUI_KD.TRANG_THAI_CHON.getNameCollumn() + " INTEGER DEFAULT 0" +
//                ");";
//    }
//
//    public static String getCreateTBL_CTO_PB() {
//        return "CREATE TABLE IF NOT EXISTS " + TBL_CTO_PB.getNameTable() + " (" +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                TBL_CTO_PB.CHON.getNameCollumn() + " INTEGER, " +
//                TBL_CTO_PB.MA_CTO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_CTO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_DVIQLY.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_CLOAI.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NAM_SX.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.LOAI_SOHUU.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_BDONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_BDONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_PHA.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_DAY.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.DONG_DIEN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.VH_CONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.DIEN_AP.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.HS_NHAN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_KDINH.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_NHAP.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() + " INTEGER DEFAULT 0, " +
//                TBL_CTO_PB.TRANG_THAI_CHON.getNameCollumn() + " INTEGER DEFAULT 0, " +
//
//
//                TBL_CTO_PB.ID_BBAN_KHO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_NHAP_HTHONG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_NVIEN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_BBAN.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.ID_BBAN_KDINH.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_GUIKD.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_KDINH_TH.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.LOAI_CTO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_CSO.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_HANG.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.CAP_CXAC.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_NUOC.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.ACTION.getNameCollumn() + " TEXT, " +
//
//                //them
//                TBL_CTO_PB.LOAISOHUU.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_NHAP_HTHI.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_BBAN_KDINH.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_NVIENKDINH.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.NGAY_KDINH_HTHI.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn() + " TEXT, " +
//                TBL_CTO_PB.MA_DVIQLY_CAPDUOI.getNameCollumn() + " TEXT" +
//                ");";
//    }
//
//    public static String getCreateTBL_HISTORY() {
//        return "CREATE TABLE IF NOT EXISTS " + TBL_HISTORY.getNameTable() + " (" +
//                TBL_HISTORY.ID_TBL_HISTORY.getNameCollumn() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                ID_TBL_CTO.getNameCollumn() + " TEXT, " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() + " TEXT, " +
//                TBL_HISTORY.TYPE_SESSION.getNameCollumn() + " TEXT, " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() + " TEXT, " +
//                TBL_HISTORY.TYPE_RESULT.getNameCollumn() + " TEXT, " +
//                TBL_HISTORY.INFO_SEARCH.getNameCollumn() + " TEXT, " +
//                TBL_HISTORY.INFO_RESULT.getNameCollumn() + " TEXT" +
//                ");";
//    }
//
//    public static String getCreateTBL_DIENLUC() {
//        return "CREATE TABLE IF NOT EXISTS " + TBL_DIENLUC.getNameTable() + " (" +
//                TBL_DIENLUC.ID_TBL_DIENLUC.getNameCollumn() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                TBL_DIENLUC.MA_DVIQLY.getNameCollumn() + " TEXT" +
//                ");";
//    }
//
//    public static String getCreateTBL_DIENLUC_PB() {
//        return "CREATE TABLE IF NOT EXISTS " + TBL_DIENLUC_PB.getNameTable() + " (" +
//                TBL_DIENLUC_PB.ID_TBL_DIENLUC_PB.getNameCollumn() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPTREN.getNameCollumn() + " TEXT, " +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPDUOI.getNameCollumn() + " TEXT," +
//                TBL_DIENLUC_PB.SEARCH.getNameCollumn() + " TEXT" +
//
//                ");";
//    }
//
//    public static String getDropTBL_CTO_PB() {
//        return "DROP TABLE IF EXISTS " + TBL_CTO_PB.getNameTable() + " ;";
//    }
//
//    public static String getDropTBL_CTO_GUI_KD() {
//        return "DROP TABLE IF EXISTS " + TBL_CTO_GUI_KD.getNameTable() + " ;";
//    }
//
//    public static String getDropTBL_DIENLUC() {
//        return "DROP TABLE IF EXISTS " + TBL_DIENLUC.getNameTable() + " ;";
//    }
//
//    public static String getDropTBL_HISTORY() {
//        return "DROP TABLE IF EXISTS " + TBL_HISTORY.getNameTable() + " ;";
//    }
//
//    public static String getDropTBL_DIENLUC_PB() {
//        return "DROP TABLE IF EXISTS " + TBL_DIENLUC_PB.getNameTable() + " ;";
//    }
//
//    public static String getInsertTBL_CTO_PB() {
//        return "INSERT INTO " + TBL_CTO_PB.getNameTable() + " (" +
//                TBL_CTO_PB.CHON.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_CTO.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_CTO.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_DVIQLY.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_CLOAI.getNameCollumn() + ", " +
//
////                TBL_CTO_PB.NGAY_NHAP_HTHONG.getNameCollumn() + ", " +
//                TBL_CTO_PB.NAM_SX.getNameCollumn() + ", " +
//                TBL_CTO_PB.LOAI_SOHUU.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_BDONG.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_BDONG.getNameCollumn() + ", " +
//
//                TBL_CTO_PB.SO_PHA.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_DAY.getNameCollumn() + ", " +
//                TBL_CTO_PB.DONG_DIEN.getNameCollumn() + ", " +
//                TBL_CTO_PB.VH_CONG.getNameCollumn() + ", " +
//                TBL_CTO_PB.DIEN_AP.getNameCollumn() + ", " +
//
////                TBL_CTO_PB.HS_NHAN.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_KDINH.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_NHAP.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() + ", " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() + ", " +
//
//                TBL_CTO_PB.TRANG_THAI_CHON.getNameCollumn() + ", " +
//
//                //mới
//                TBL_CTO_PB.ID_BBAN_KHO.getNameCollumn() + ", " +
////                TBL_CTO_PB.NGAY_NHAP_HTHONG.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_NVIEN.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_BBAN.getNameCollumn() + ", " +
//                TBL_CTO_PB.ID_BBAN_KDINH.getNameCollumn() + ", " +
//
//                TBL_CTO_PB.NGAY_GUIKD.getNameCollumn() + ", " +
////                TBL_CTO_PB.NGAY_KDINH_TH.getNameCollumn() + ", " +
//                TBL_CTO_PB.LOAI_CTO.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_CSO.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_HANG.getNameCollumn() + ", " +
//
//                TBL_CTO_PB.CAP_CXAC.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_NUOC.getNameCollumn() + ", " +
////                TBL_CTO_PB.ACTION.getNameCollumn() + ", " +
//
//                //thêm
//                TBL_CTO_PB.LOAISOHUU.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_NHAP_HTHI.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_BBAN_KDINH.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_NVIENKDINH.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_KDINH_HTHI.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_DVIQLY_CAPDUOI.getNameCollumn() +
//
//                ") " + "VALUES (" +
//                "?, ?, ?, ?, ?," +
//                "?, ?, ?, ?, ?," +
//                "?, ?, ?, ?, ?," +
//                "?, ?, ?, ?, ?," +
//                "?, ?, ?, ?, ?," +
//                "?, ?, ?, ?, ?," +
////                "?, ?, ?, ?" +
//                //thêm
//                "?, ?, ?, ?, ?, ?" +
//                ");"
//                ;
//    }
//
//    public static String getInsertTBL_CTO_GUI_KD() {
//        return "INSERT INTO " + TBL_CTO_GUI_KD.getNameTable() + " (" +
//                TBL_CTO_GUI_KD.CHON.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.STT.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.MA_CTO.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.SO_CTO.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.MA_DVIQLY.getNameCollumn() + ", " +
//
//                TBL_CTO_GUI_KD.MA_CLOAI.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_HT.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NAM_SX.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.LOAI_SOHUU.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.TEN_SOHUU.getNameCollumn() + ", " +
//
//                TBL_CTO_GUI_KD.MA_BDONG.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NGAY_BDONG.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NGAY_BDONG_HTAI.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.SO_PHA.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.SO_DAY.getNameCollumn() + ", " +
//
//                TBL_CTO_GUI_KD.DONG_DIEN.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.VH_CONG.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.DIEN_AP.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.HS_NHAN.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NGAY_KDINH.getNameCollumn() + ", " +
//
//                TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn() + ", " +
//
//                TBL_CTO_GUI_KD.CHISO_THAO.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.HSN.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NGAY_NHAP.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.TaiKhoan.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() + ", " +
//                TBL_CTO_GUI_KD.TRANG_THAI_CHON.getNameCollumn() +
//                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
//                ");"
//                ;
//    }
//
//    public static String getInsertTBL_HISTORY() {
//        return "INSERT INTO " + TBL_HISTORY.getNameTable() + " (" +
//                ID_TBL_CTO.getNameCollumn() + ", " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() + ", " +
//                TBL_HISTORY.TYPE_SESSION.getNameCollumn() + ", " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() + ", " +
//                TBL_HISTORY.TYPE_RESULT.getNameCollumn() + ", " +
//                TBL_HISTORY.INFO_SEARCH.getNameCollumn() + ", " +
//                TBL_HISTORY.INFO_RESULT.getNameCollumn() +
//
//                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?" +
//                ");"
//                ;
//    }
//
//    public static String getInsertTBL_DIENLUC_PB() {
//        return "INSERT INTO " + TBL_DIENLUC_PB.getNameTable() + " (" +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPTREN.getNameCollumn() + ", " +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPDUOI.getNameCollumn() + ", " +
//                TBL_DIENLUC_PB.SEARCH.getNameCollumn() +
//                ") " + "VALUES (" +
//                " ?, ? ,?);"
//                ;
//    }
//
//  /*  public static String getSelectTBL_CTO_PB() {
//        return "SELECT " +
//                TBL_CTO_PB.ID_TBL_DIENLUC.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_DVIQLY.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_CLOAI.getNameCollumn() + ", " +
//                TBL_CTO_PB.NAM_SX.getNameCollumn() + ", " +
//                TBL_CTO_PB.SO_CTO.getNameCollumn() + ", " +
//                TBL_CTO_PB.MA_CTO.getNameCollumn() + ", " +
//                TBL_CTO_PB.CHISO_THAO.getNameCollumn() + ", " +
//                TBL_CTO_PB.GhimCto.getNameCollumn() + ", " +
//                TBL_CTO_PB.TaiKhoan.getNameCollumn() + ", " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.TaiKhoan.getNameCollumn() +
//                " = ? "
//                ;
//    }*/
//
//
//    public static String getAllCongToByDatePB() {
//        return "SELECT * " +
//                " FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getByDateAllCongToPBNoSuccess() {
//        return "SELECT * " +
//                " FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_CTO_PB.CHON.getNameCollumn() +
//                " = " + Common.CHON.CHUA_GUI.getCode()
//                ;
//    }
//
//
////    public static String getSelectTBL_CTO_GUI_KD() {
////        return "SELECT " +
////                TBL_CTO_GUI_KD.ID_TBL_DIENLUC.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.MA_DVIQLY.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.MA_CLOAI.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.NAM_SX.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.SO_CTO.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.MA_CTO.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.CHISO_THAO.getNameCollumn() + ", " +
//////                TBL_CTO_GUI_KD.TaiKhoan.getNameCollumn() + ", " +
////                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
////                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
////                " FROM " +
////                TBL_CTO_GUI_KD.getNameTable() +
////                " WHERE " +
////                TBL_CTO_GUI_KD.NGAY_NHAP_MTB +
////                " = ? "
////                ;
////    }
//
//    public static String getByDateAllCongToGhimAndChonKD() {
//        return "SELECT *" +
//                " FROM " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " WHERE " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_GUI_KD.TRANG_THAI_CHON +
//                " = " + Common.TRANG_THAI_CHON.DA_CHON.getCode() +
//                " AND " +
//                TBL_CTO_GUI_KD.CHON +
//                " = " + Common.CHON.CHUA_GUI.getCode();
//    }
//
//
//    public static String getByDateAllCongToGhimAndChonPB() {
//        return "SELECT *" +
//                " FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_CTO_PB.TRANG_THAI_GHIM +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_PB.TRANG_THAI_CHON +
//                " = " + Common.TRANG_THAI_CHON.DA_CHON.getCode() +
//                " AND " +
//                TBL_CTO_PB.CHON +
//                " = " + Common.CHON.CHUA_GUI.getCode();
//    }
//
//
//    public static String getBydateALLHistoryCto() {
//        return "SELECT DISTINCT *" +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " BETWEEN ? AND ? " +
//                " GROUP BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " ORDER BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " DESC";
//    }
//
//    public static String getDviCapDuoiPB() {
//        return "SELECT DISTINCT *" +
//                " FROM " +
//                TBL_DIENLUC_PB.getNameTable() +
//                " WHERE " +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPTREN.getNameCollumn() +
//                " = ? ";
//    }
//
//    public static String getByDateAllThongKeKD() {
//
//        return "SELECT " +
//                TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn() +
//                ", COUNT (" +
//                TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn() +
//                ") AS COUNT FROM " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " WHERE " +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() +
//                " IN (SELECT " +
//                TBL_HISTORY.ID_TBL_CTO.getNameCollumn() +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE  " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " > ? AND  " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " <? AND " +
//                TBL_HISTORY.TYPE_SESSION.getNameCollumn() +
//                " = '" +
//                Common.TYPE_SESSION.UPLOAD.getCode() +
//                "' AND " +
//                TBL_HISTORY.TYPE_RESULT.getNameCollumn() +
//                " = '" +
//                Common.TYPE_RESULT.SUCCESS.getCode() +
//                "' AND " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = '" +
//                Common.TYPE_TBL_CTO.KD.getCode() +
//                "') GROUP BY " +
//                TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn() +
//                "";
//    }
//
//    public static String getByDateAllThongKePB() {
//
//        return "SELECT " +
//                TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn() +
//                ", COUNT (" +
//                TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn() +
//                ") AS COUNT FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() +
//                " IN (SELECT " +
//                TBL_HISTORY.ID_TBL_CTO.getNameCollumn() +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE  " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " > ? AND  " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " <? AND " +
//                TBL_HISTORY.TYPE_SESSION.getNameCollumn() +
//                " = '" +
//                Common.TYPE_SESSION.UPLOAD.getCode() +
//                "' AND " +
//                TBL_HISTORY.TYPE_RESULT.getNameCollumn() +
//                " = '" +
//                Common.TYPE_RESULT.SUCCESS.getCode() +
//                "' AND " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = '" +
//                Common.TYPE_TBL_CTO.PB.getCode() +
//                "') GROUP BY " +
//                TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn() +
//                "";
//    }
//
//
//    public static String countByDateALLHistoryCto() {
//        return "SELECT DATE_SESSION " +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " BETWEEN ? AND ? " +
//                " GROUP BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " ORDER BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " DESC";
//    }
//
//    public static String getBydateHistoryINFO_RESULT() {
//        return "SELECT " +
//                TBL_HISTORY.INFO_RESULT.getNameCollumn() +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.TYPE_SESSION +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.ID_TBL_CTO +
//                " = ?"
//                ;
//    }
//
//
//    public static String getBydateALLHistoryCtoNoSuccess() {
//        return "SELECT DISTINCT *" +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.TYPE_RESULT.getNameCollumn() +
//                " != '" + Common.TYPE_RESULT.SUCCESS.getCode() + "'" +
//                " AND " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " BETWEEN ? AND ? " +
//                " GROUP BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " ORDER BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " DESC";
//    }
//
//    public static String getBydateALLHistoryCtoSuccess() {
//        return "SELECT DISTINCT *" +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.TYPE_RESULT.getNameCollumn() +
//                " = '" + Common.TYPE_RESULT.SUCCESS.getCode() + "'" +
//                " AND " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " BETWEEN ? AND ? " +
//                " GROUP BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " ORDER BY " +
//                TBL_HISTORY.DATE_SESSION +
//                " DESC";
//    }
//
//    public static String getByDateDeleteAllHistory() {
//        return "DELETE" +
//                " FROM " +
//                TBL_HISTORY.getNameTable() +
//                " WHERE " +
//                TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn() +
//                " BETWEEN ? AND ? "
//                ;
//    }
//
//
//    public static String getAllCongToByDateKD() {
//        return "SELECT * " +
//                " FROM " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " WHERE " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getAllCongToByDateKDNoSuccess() {
//        return "SELECT * " +
//                " FROM " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " WHERE " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_CTO_GUI_KD.CHON.getNameCollumn() +
//                " = " + Common.CHON.CHUA_GUI.getCode()
//                ;
//    }
//
//    public static String getUpdateGhimCtoPB() {
//        return "UPDATE " +
//                TBL_CTO_PB.getNameTable() +
//                " SET " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getUpdateGhimCtoKD() {
//        return "UPDATE " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " SET " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String updateGhimCtoAllKD() {
//        return "UPDATE " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " SET " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
//                " != '" + Common.TRANG_THAI_GHIM.DA_GHIM +
//                "' AND " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getUpdateChonCtoPB() {
//        return "UPDATE " +
//                TBL_CTO_PB.getNameTable() +
//                " SET " +
//                TBL_CTO_PB.CHON.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String updateSO_PBCT_MTB() {
//        return "UPDATE " +
//                TBL_CTO_PB.getNameTable() +
//                " SET " +
//                TBL_CTO_PB.SO_PBCT_MTB.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String MA_DVIQLY_CAPDUOI() {
//        return "UPDATE " +
//                TBL_CTO_PB.getNameTable() +
//                " SET " +
//                TBL_CTO_PB.MA_DVIQLY_CAPDUOI.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getUpdateChonCtoKD() {
//        return "UPDATE " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " SET " +
//                TBL_CTO_GUI_KD.CHON.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getupdateSO_GKDCT_MTBCtoKD() {
//        return "UPDATE " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " SET " +
//                TBL_CTO_GUI_KD.SO_GKDCT_MTB.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getUpdateTRANG_THAI_CHONCtoKD() {
//        return "UPDATE " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " SET " +
//                TBL_CTO_GUI_KD.TRANG_THAI_CHON.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getDeleteAllTBL_CTO_PB() {
//        return "DELETE FROM " +
//                TBL_CTO_PB.getNameTable()
////                +
////                " WHERE " +
////                TBL_CTO_PB.TaiKhoan.getNameCollumn() +
////                " = ?"
//                ;
//    }
//
//    public static String getByDateDeleteHistory() {
//        return "DELETE FROM " +
//                TBL_HISTORY.getNameTable()
//                +
//                " WHERE " +
//                ID_TBL_CTO.getNameCollumn() +
//                " = ? AND " +
//                TBL_HISTORY.TYPE_TBL_CTO +
//                " = ? AND " +
//                TBL_HISTORY.DATE_SESSION +
//                " BETWEEN ? AND ?"
//                ;
//    }
//
//
//    public static String getDeleteAllTBL_CTO_GUI_KD() {
//        return "DELETE FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
////                +
////                " WHERE " +
////                TBL_CTO_GUI_KD.TaiKhoan.getNameCollumn() +
////                " = ?"
//                ;
//    }
//
//    public static String deleteByDateAllCongToKD() {
//        return "DELETE FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ?";
//    }
//
//    public static String deleteByDateAllCongToPB() {
//        return "DELETE FROM " +
//                TBL_CTO_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ?";
//    }
//
//    public static String getByDateSelectCongToGhimPB() {
//        return "SELECT *" +
//                " FROM " +
//                TBL_CTO_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getByDateAllCongToGhimPBNoSuccess() {
//        return "SELECT *" +
//                " FROM " +
//                TBL_CTO_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_CTO_PB.CHON.getNameCollumn() +
//                " = " + Common.CHON.CHUA_GUI.getCode()
//                ;
//    }
//
//    public static String countByDateAllCongToGhimKD() {
//        return "SELECT COUNT(*)" +
//                " FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String countByDateAllCongToGhimPB() {
//        return "SELECT COUNT(*)" +
//                " FROM " +
//                TBL_CTO_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//
//    public static String getByDateSelectCongToGhimKD() {
//        return "SELECT *" +
//                " FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getByDateSelectCongToGhimKDNoSuccess() {
//        return "SELECT *" +
//                " FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.TRANG_THAI_GHIM.getNameCollumn() +
//                " = " + Common.TRANG_THAI_GHIM.DA_GHIM.getCode() +
//                " AND " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? " +
//                " AND " +
//                TBL_CTO_GUI_KD.CHON.getNameCollumn() +
//                " = " + Common.CHON.CHUA_GUI.getCode()
//                ;
//    }
//
//    public static String getDeletePB() {
//        return "DELETE FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn()
//                + " = ? "
//                ;
//    }
//
//
//    public static String getDeleteKD() {
//        return "DELETE FROM " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " WHERE " +
//                TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn()
//                + " = ? "
//                ;
//    }
//
//
//    public static String getCheckExistTBL_CTO_GUI_KD() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.MA_CTO.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//    public static String getcheckExistTBL_DVI_PB_CAPDUOI() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_DIENLUC_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPTREN.getNameCollumn()
//                + " = ?" +
//                " AND " +
//                TBL_DIENLUC_PB.MA_DVIQLY_CAPDUOI.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//    public static String getCheckExistByDateTBL_CTO_GUI_KD() {
//        return "SELECT * FROM " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " WHERE " +
//                TBL_CTO_GUI_KD.MA_CTO.getNameCollumn()
//                + " = ?" +
//                " AND " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB
//                + " = ?"
//                ;
//    }
//
//    public static String getCheckExistTBL_CTO_PB() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_CTO_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_PB.MA_CTO.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//    public static String countByDateSessionHistoryCto() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_HISTORY.getNameTable()
//                +
//                " WHERE " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_SESSION.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_RESULT.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//    public static String countByDateAllPB() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_CTO_PB.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//
//    public static String countByDateAllKD() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_CTO_GUI_KD.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.NGAY_NHAP_MTB.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//
//    public static String getByDateAllCongToKDByDATE_SESSION() {
//        return "SELECT B.* FROM(SELECT " +
//                ID_TBL_CTO.getNameCollumn() +
//                " FROM " +
//                TBL_HISTORY.getNameTable()
//                +
//                " WHERE " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_SESSION.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn()
//                + " = '"
//                + Common.TYPE_TBL_CTO.KD.getCode()
//                + "' ) AS A INNER JOIN " +
//                TBL_CTO_GUI_KD.getNameTable() +
//                " AS B ON A." + ID_TBL_CTO.getNameCollumn() +
//                " = B." + TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn()
//                ;
//    }
//
//    public static String getByDateAllCongToPBByDATE_SESSION() {
//        return "SELECT B.* FROM(SELECT " +
//                ID_TBL_CTO.getNameCollumn() +
//                " FROM " +
//                TBL_HISTORY.getNameTable()
//                +
//                " WHERE " +
//                TBL_HISTORY.DATE_SESSION.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_SESSION.getNameCollumn()
//                + " = ?"
//                + " AND "
//                + TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn()
//                + " = '"
//                + Common.TYPE_TBL_CTO.PB.getCode()
//                + "' ) AS A INNER JOIN " +
//                TBL_CTO_PB.getNameTable() +
//                " AS B ON A." + ID_TBL_CTO.getNameCollumn() +
//                " = B." + TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn()
//                ;
//    }
//
//
//    public static String getByDateAllCongToPBByDATE_SESSIONByTYPE_RESULT() {
//        return "SELECT B.* FROM " +
//                "( " +
//                "(SELECT " + TBL_HISTORY.getNameTable() + "." + TBL_HISTORY.ID_TBL_CTO.getNameCollumn() + " " +
//                "FROM " + TBL_HISTORY.getNameTable() + " " +
//                "WHERE " + TBL_HISTORY.DATE_SESSION.getNameCollumn() + " = ? AND " + TBL_HISTORY.TYPE_SESSION.getNameCollumn() + " = ? AND " + TBL_HISTORY.TYPE_RESULT.getNameCollumn() + " = ? AND  " + TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() + " = ? " +
//                ") " +
//                "AS A " +
//                "INNER JOIN  " +
//                " " + TBL_CTO_PB.getNameTable() + " " +
//                "AS B " +
//                "ON A." + TBL_HISTORY.ID_TBL_CTO.getNameCollumn() + " = B." + TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() + " " +
//                ")";
//    }
//
//
//    public static String getByDateAllCongToKDByDATE_SESSIONByTYPE_RESULT() {
//        return "SELECT B.* FROM " +
//                "( " +
//                "(SELECT " + TBL_HISTORY.getNameTable() + "." + TBL_HISTORY.ID_TBL_CTO.getNameCollumn() + " " +
//                "FROM " + TBL_HISTORY.getNameTable() + " " +
//                "WHERE " + TBL_HISTORY.DATE_SESSION.getNameCollumn() + " = ? AND " + TBL_HISTORY.TYPE_SESSION.getNameCollumn() + " = ? AND " + TBL_HISTORY.TYPE_RESULT.getNameCollumn() + " = ? AND  " + TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() + " = ? " +
//                ") " +
//                "AS A " +
//                "INNER JOIN  " +
//                " " + TBL_CTO_GUI_KD.getNameTable() + " " +
//                "AS B " +
//                "ON A." + TBL_HISTORY.ID_TBL_CTO.getNameCollumn() + " = B." + TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() + " " +
//                ")";
//    }
//
////    public static String getByDateAllCongToPBByDATE_SESSIONByTYPE_RESULT() {
////        return "SELECT B.* FROM " +
////                "( " +
////                "(SELECT " + TBL_HISTORY.getNameTable() + "." + TBL_HISTORY.ID_TBL_CTO.getNameCollumn() + " " +
////                "FROM " + TBL_HISTORY.getNameTable() + " " +
////                "WHERE " + TBL_HISTORY.DATE_SESSION.getNameCollumn() + " = ? AND " + TBL_HISTORY.TYPE_SESSION.getNameCollumn() + " = ? AND " + TBL_HISTORY.TYPE_RESULT.getNameCollumn() + " = ? AND  " + TBL_HISTORY.TYPE_TBL_CTO.getNameCollumn() + " = ? " +
////                ") " +
////                "AS A " +
////                "INNER JOIN  " +
////                " " + TBL_CTO_GUI_KD.getNameTable() + " " +
////                "AS B " +
////                "ON A." + TBL_HISTORY.ID_TBL_CTO.getNameCollumn() + " = B." + TBL_CTO_GUI_KD.ID_TBL_CTO_GUI_KD.getNameCollumn() + " " +
////                ")";
////    }
//
//
//    public static String updateGhimCtoAllPB() {
//        return "UPDATE " +
//                TBL_CTO_PB.getNameTable() +
//                " SET " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_PB.TRANG_THAI_GHIM.getNameCollumn() +
//                " != '" + Common.TRANG_THAI_GHIM.DA_GHIM +
//                "' AND " +
//                TBL_CTO_PB.NGAY_NHAP_MTB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//    //endregion
//
//    //region TBL_DIENLUC query
//    public static String getSelectDienLuc() {
//        return "SELECT " +
//                TBL_DIENLUC.ID_TBL_DIENLUC.getNameCollumn() + ", " +
//                TBL_DIENLUC.MA_DVIQLY.getNameCollumn() +
//                " FROM " +
//                TBL_DIENLUC.getNameTable()
//                ;
//    }
//
//    public static String getCheckExistTBL_DIENLUC() {
//        return "SELECT COUNT(*) FROM " +
//                TBL_DIENLUC.getNameTable()
//                +
//                " WHERE " +
//                TBL_CTO_GUI_KD.MA_DVIQLY.getNameCollumn()
//                + " = ?"
//                ;
//    }
//
//    public static String getInsertTBL_DIENLUC() {
//        return "INSERT INTO " + TBL_DIENLUC.getNameTable() + " (" +
//                TBL_CTO_GUI_KD.MA_DVIQLY.getNameCollumn() +
//                ") " + "VALUES (?)" +
//                ";"
//                ;
//    }
//
//    public static String getUpdateTRANG_THAI_CHONCtoPB() {
//        return "UPDATE " +
//                TBL_CTO_PB.getNameTable() +
//                " SET " +
//                TBL_CTO_PB.TRANG_THAI_CHON.getNameCollumn() +
//                " = ? " +
//                " WHERE " +
//                TBL_CTO_PB.ID_TBL_CTO_PB.getNameCollumn() +
//                " = ? "
//                ;
//    }
//
//    public static String getCheckExistByDateTBL_CTO_PB() {
//        return "SELECT * FROM " +
//                TBL_CTO_PB.getNameTable() +
//                " WHERE " +
//                TBL_CTO_PB.MA_CTO.getNameCollumn()
//                + " = ?" +
//                " AND " +
//                TBL_CTO_PB.NGAY_NHAP_MTB
//                + " = ?"
//                ;
//    }
//
//    //endregion
}
