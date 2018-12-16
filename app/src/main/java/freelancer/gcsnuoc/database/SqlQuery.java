package freelancer.gcsnuoc.database;

import freelancer.gcsnuoc.database.config.TBL_BOOK;
import freelancer.gcsnuoc.database.config.TBL_CUSTOMER;
import freelancer.gcsnuoc.database.config.TBL_IMAGE;
import freelancer.gcsnuoc.utils.Common;

/**
 * Created by VinhNB on 8/8/2017.
 */
public class SqlQuery {
    //region TBL_BOOK
//    public enum TBL_BOOK {
//        ID("ID"),
//        NAME("NAME"),
//        STATUS("STATUS"),
//        CUS_WRITED("CUS_WRITED"),
//        CUS_NOT_WRITED("CUS_NOT_WRITED"),
//        PERIOD("PERIOD"),
//        FOCUS("FOCUS"),
//        CHOOSE("CHOOSE");
//
//        private String mNameCollumn;
//
//        TBL_BOOK(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String table.getName() {
//            return "TBL_BOOK";
//        }
//    }

    public static String getCreateTBL_BOOK() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_BOOK.table.getName() + " (" +
                TBL_BOOK.table.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_BOOK.table.NAME.name() + " TEXT, " +
                TBL_BOOK.table.STATUS.name() + " TEXT, " +
                TBL_BOOK.table.CUS_WRITED.name() + " INTEGER, " +
                TBL_BOOK.table.CUS_NOT_WRITED.name() + " INTEGER, " +
                TBL_BOOK.table.PERIOD.name() + " TEXT, " +
                TBL_BOOK.table.FOCUS.name() + " TEXT DEFAULT FALSE, " +
                TBL_BOOK.table.CHOOSE.name() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_BOOK() {
        return "DROP TABLE IF EXISTS " + TBL_BOOK.table.getName() + " ;";
    }

    public static String getSelectTBL_BOOK() {
        return "SELECT * " +
                " FROM " +
                TBL_BOOK.table.getName();
    }

    public static String getInsertTBL_BOOK() {
        return "INSERT INTO " + TBL_BOOK.table.getName() + " (" +
                TBL_BOOK.table.NAME.name() + ", " +
                TBL_BOOK.table.STATUS.name() + ", " +
                TBL_BOOK.table.CUS_WRITED.name() + ", " +
                TBL_BOOK.table.CUS_NOT_WRITED.name() + ", " +
                TBL_BOOK.table.PERIOD.name() + ", " +
                TBL_BOOK.table.FOCUS.name() + ", " +
                TBL_BOOK.table.CHOOSE.name() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.table.getName() +
                " SET " +

                TBL_BOOK.table.STATUS.name() +
                " = ? " +
                ", " +

                TBL_BOOK.table.CUS_WRITED.name() +
                " = ? " +
                ", " +

                TBL_BOOK.table.CUS_NOT_WRITED.name() +
                " = ? " +
                ", " +

                TBL_BOOK.table.PERIOD.name() +
                " = ? " +
                ", " +

                TBL_BOOK.table.FOCUS.name() +
                " = ? " +
                ", " +

                TBL_BOOK.table.CHOOSE.name() +
                " = ? " +

                " WHERE " +
                TBL_BOOK.table.ID.name() +
                " = ? "

                ;
    }

    public static String getUpdateChooseTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.table.getName() +
                " SET " +
                TBL_BOOK.table.CHOOSE.name() +
                " = ? " +
                " WHERE " +
                TBL_BOOK.table.ID.name() +
                " = ? "
                ;
    }

    public static String getUpdateFocusTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.table.getName() +
                " SET " +
                TBL_BOOK.table.FOCUS.name() +
                " = ? " +
                " WHERE " +
                TBL_BOOK.table.ID.name() +
                " = ? "
                ;
    }
    //endregion

    //region TBL_CUSTOMER
//    public enum TBL_CUSTOMER {
//        ID("ID"),
//        ID_TBL_BOOK("ID_TBL_BOOK"),
//        NAME("NAME"),
//        CUSTOMER_ADDRESS("CUSTOMER_ADDRESS"),
//        STATUS("STATUS"),
//        FOCUS("FOCUS");
//
//        private String mNameCollumn;
//
//        TBL_CUSTOMER(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String table.getName() {
//            return "TBL_CUSTOMER";
//        }
//    }

    public static String getCreateTBL_CUSTOMER() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMER.table.getName() + " (" +
                TBL_CUSTOMER.table.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_CUSTOMER.table.ID_TBL_BOOK.name() + " INTEGER, " +
                TBL_CUSTOMER.table.NAME.name() + " TEXT, " +
                TBL_CUSTOMER.table.CUSTOMER_ADDRESS.name() + " TEXT, " +
                TBL_CUSTOMER.table.STATUS.name() + " TEXT, " +
                TBL_CUSTOMER.table.FOCUS.name() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_CUSTOMER() {
        return "DROP TABLE IF EXISTS " + TBL_CUSTOMER.table.getName() + " ;";
    }

    public static String getSelectTBL_CUSTOMER() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.table.getName();
    }

    public static String getInsertTBL_CUSTOMER() {
        return "INSERT INTO " + TBL_CUSTOMER.table.getName() + " (" +
                TBL_CUSTOMER.table.ID_TBL_BOOK.name() + " INTEGER, " +
                TBL_CUSTOMER.table.NAME.name() + ", " +
                TBL_CUSTOMER.table.CUSTOMER_ADDRESS.name() + ", " +
                TBL_CUSTOMER.table.STATUS.name() + ", " +
                TBL_CUSTOMER.table.FOCUS.name() +
                ") " + "VALUES (?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.table.getName() +
                " SET " +

                TBL_CUSTOMER.table.ID_TBL_BOOK.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.table.STATUS.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.table.FOCUS.name() +
                " = ? " +

                " WHERE " +
                TBL_CUSTOMER.table.ID.name() +
                " = ? "

                ;
    }

    public static String getUpdateFocusTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.table.getName() +
                " SET " +
                TBL_CUSTOMER.table.FOCUS.name() +
                " = ? " +
                " WHERE " +
                TBL_CUSTOMER.table.ID.name() +
                " = ? "
                ;
    }
    //endregion

    //region TBL_IMAGE
//    public enum TBL_IMAGE {
//        ID("ID"),
//        ID_TBL_CUSTOMER("ID_TBL_CUSTOMER"),
//        NAME("NAME"),
//        LOCAL_URI("LOCAL_URI"),
//        OLD_INDEX("OLD_INDEX"),
//        NEW_INDEX("NEW_INDEX"),
//        CREATE_DAY("FOCUS");
//
//
//        private String mNameCollumn;
//
//        TBL_IMAGE(String mNameCollumn) {
//            this.mNameCollumn = mNameCollumn;
//        }
//
//        public String getNameCollumn() {
//            return mNameCollumn;
//        }
//
//        public static String table.getName() {
//            return "TBL_IMAGE";
//        }
//    }

    public static String getCreateTBL_IMAGE() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_IMAGE.table.getName() + " (" +
                TBL_IMAGE.table.ID.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_IMAGE.table.ID_TBL_CUSTOMER.name() + " INTEGER, " +
                TBL_IMAGE.table.NAME.name() + " TEXT, " +
                TBL_IMAGE.table.LOCAL_URI.name() + " TEXT, " +
                TBL_IMAGE.table.OLD_INDEX.name() + " INTEGER, " +
                TBL_IMAGE.table.NEW_INDEX.name() + " INTEGER, " +
                TBL_IMAGE.table.CREATE_DAY.name() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_IMAGE() {
        return "DROP TABLE IF EXISTS " + TBL_IMAGE.table.getName() + " ;";
    }

    public static String getSelectTBL_IMAGE() {
        return "SELECT * " +
                " FROM " +
                TBL_IMAGE.table.getName();
    }

    public static String getInsertTBL_IMAGE() {
        return "INSERT INTO " + TBL_IMAGE.table.getName() + " (" +
                TBL_IMAGE.table.ID_TBL_CUSTOMER.name() + " INTEGER, " +
                TBL_IMAGE.table.NAME.name() + ", " +
                TBL_IMAGE.table.LOCAL_URI.name() + ", " +
                TBL_IMAGE.table.OLD_INDEX.name() + ", " +
                TBL_IMAGE.table.NEW_INDEX.name() + ", " +
                TBL_IMAGE.table.CREATE_DAY.name() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_IMAGE() {
        return "UPDATE " +
                TBL_IMAGE.table.getName() +
                " SET " +

                TBL_IMAGE.table.ID_TBL_CUSTOMER.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.table.LOCAL_URI.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.table.OLD_INDEX.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.table.NEW_INDEX.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.table.CREATE_DAY.name() +
                " = ? " +

                " WHERE " +
                TBL_IMAGE.table.ID.name() +
                " = ? "

                ;
    }
//
//    public static String getUpdateCREATE_DAYofTBL_IMAGE() {
//        return "UPDATE " +
//                TBL_IMAGE.table.getName() +
//                " SET " +
//                TBL_IMAGE.table.FOCUS.name() +
//                " = ? " +
//                " WHERE " +
//                TBL_IMAGE.table.ID.name() +
//                " = ? "
//                ;
//    }
    //endregion

    //region DetailProxy
    public static String getSelectAllDetailProxy() {
        return "SELECT * " +

                " FROM " +
                TBL_CUSTOMER.table.getName() +
                " INNER JOIN " +
                TBL_IMAGE.table.getName() +

                " ON " +
                TBL_CUSTOMER.table.getName() + "." + TBL_CUSTOMER.table.ID.name() +
                " = " +
                TBL_IMAGE.table.getName() + "." + TBL_IMAGE.table.ID_TBL_CUSTOMER.name() +

                "";
    }
    //endregion
}
