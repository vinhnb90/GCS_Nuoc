package freelancer.gcsnuoc.database;

/**
 * Created by VinhNB on 8/8/2017.
 */
public class SqlQuery {
    //region TBL_BOOK
    public enum TBL_BOOK {
        ID_TBL_BOOK("ID_TBL_BOOK"),
        NAME("NAME"),
        STATUS("STATUS"),
        CUS_WRITED("CUS_WRITED"),
        CUS_NOT_WRITED("CUS_NOT_WRITED"),
        PERIOD("PERIOD"),
        FOCUS("FOCUS"),
        CHOOSE("CHOOSE");

        private String mNameCollumn;

        TBL_BOOK(String mNameCollumn) {
            this.mNameCollumn = mNameCollumn;
        }

        public String getNameCollumn() {
            return mNameCollumn;
        }

        public static String getName() {
            return "TBL_BOOK";
        }
    }

    public static String getCreateTBL_BOOK() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_BOOK.getName() + " (" +
                TBL_BOOK.ID_TBL_BOOK.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_BOOK.NAME.name() + " TEXT, " +
                TBL_BOOK.STATUS.name() + " TEXT, " +
                TBL_BOOK.CUS_WRITED.name() + " INTEGER, " +
                TBL_BOOK.CUS_NOT_WRITED.name() + " INTEGER, " +
                TBL_BOOK.PERIOD.name() + " TEXT, " +
                TBL_BOOK.FOCUS.name() + " TEXT DEFAULT FALSE, " +
                TBL_BOOK.CHOOSE.name() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_BOOK() {
        return "DROP TABLE IF EXISTS " + TBL_BOOK.getName() + " ;";
    }

    public static String getSelectTBL_BOOK() {
        return "SELECT * " +
                " FROM " +
                TBL_BOOK.getName();
    }

    public static String getInsertTBL_BOOK() {
        return "INSERT INTO " + TBL_BOOK.getName() + " (" +
                TBL_BOOK.NAME.name() + ", " +
                TBL_BOOK.STATUS.name() + ", " +
                TBL_BOOK.CUS_WRITED.name() + ", " +
                TBL_BOOK.CUS_NOT_WRITED.name() + ", " +
                TBL_BOOK.PERIOD.name() + ", " +
                TBL_BOOK.FOCUS.name() + ", " +
                TBL_BOOK.CHOOSE.name() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getName() +
                " SET " +

                TBL_BOOK.STATUS.name() +
                " = ? " +
                ", " +

                TBL_BOOK.CUS_WRITED.name() +
                " = ? " +
                ", " +

                TBL_BOOK.CUS_NOT_WRITED.name() +
                " = ? " +
                ", " +

                TBL_BOOK.PERIOD.name() +
                " = ? " +
                ", " +

                TBL_BOOK.FOCUS.name() +
                " = ? " +
                ", " +

                TBL_BOOK.CHOOSE.name() +
                " = ? " +

                " WHERE " +
                TBL_BOOK.ID_TBL_BOOK.name() +
                " = ? "

                ;
    }

    public static String getUpdateChooseTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getName() +
                " SET " +
                TBL_BOOK.CHOOSE.name() +
                " = ? " +
                " WHERE " +
                TBL_BOOK.ID_TBL_BOOK.name() +
                " = ? "
                ;
    }

    public static String getUpdateFocusTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getName() +
                " SET " +
                TBL_BOOK.FOCUS.name() +
                " = ? " +
                " WHERE " +
                TBL_BOOK.ID_TBL_BOOK.name() +
                " = ? "
                ;
    }
    //endregion

    //region TBL_CUSTOMER
    public enum TBL_CUSTOMER {
        ID_TBL_CUSTOMER("ID_TBL_CUSTOMER"),
        ID_TBL_BOOK("ID_TBL_BOOK"),
        NAME("NAME"),
        CUSTOMER_ADDRESS("CUSTOMER_ADDRESS"),
        STATUS("STATUS"),
        FOCUS("FOCUS");

        private String mNameCollumn;

        TBL_CUSTOMER(String mNameCollumn) {
            this.mNameCollumn = mNameCollumn;
        }

        public String getNameCollumn() {
            return mNameCollumn;
        }

        public static String getName() {
            return "TBL_CUSTOMER";
        }
    }

    public static String getCreateTBL_CUSTOMER() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_CUSTOMER.getName() + " (" +
                TBL_CUSTOMER.ID_TBL_CUSTOMER.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_CUSTOMER.ID_TBL_BOOK.name() + " INTEGER, " +
                TBL_CUSTOMER.NAME.name() + " TEXT, " +
                TBL_CUSTOMER.CUSTOMER_ADDRESS.name() + " TEXT, " +
                TBL_CUSTOMER.STATUS.name() + " TEXT, " +
                TBL_CUSTOMER.FOCUS.name() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_CUSTOMER() {
        return "DROP TABLE IF EXISTS " + TBL_CUSTOMER.getName() + " ;";
    }

    public static String getSelectTBL_CUSTOMER() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.getName();
    }

    public static String getInsertTBL_CUSTOMER() {
        return "INSERT INTO " + TBL_CUSTOMER.getName() + " (" +
                TBL_CUSTOMER.ID_TBL_BOOK.name() + ", " +
                TBL_CUSTOMER.NAME.name() + ", " +
                TBL_CUSTOMER.CUSTOMER_ADDRESS.name() + ", " +
                TBL_CUSTOMER.STATUS.name() + ", " +
                TBL_CUSTOMER.FOCUS.name() +
                ") " + "VALUES (?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +

                TBL_CUSTOMER.ID_TBL_BOOK.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.STATUS.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.FOCUS.name() +
                " = ? " +

                " WHERE " +
                TBL_CUSTOMER.ID_TBL_BOOK.name() +
                " = ? "

                ;
    }

    public static String getUpdateFocusTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +
                TBL_CUSTOMER.FOCUS.name() +
                " = ? " +
                " WHERE " +
                TBL_CUSTOMER.ID_TBL_BOOK.name() +
                " = ? "
                ;
    }
    //endregion

    //region TBL_IMAGE
    public enum TBL_IMAGE {
        ID_TBL_IMAGE("ID_TBL_IMAGE"),
        ID_TBL_CUSTOMER("ID_TBL_CUSTOMER"),
        NAME("NAME"),
        LOCAL_URI("LOCAL_URI"),
        OLD_INDEX("OLD_INDEX"),
        NEW_INDEX("NEW_INDEX"),
        CREATE_DAY("FOCUS");


        private String mNameCollumn;

        TBL_IMAGE(String mNameCollumn) {
            this.mNameCollumn = mNameCollumn;
        }

        public String getNameCollumn() {
            return mNameCollumn;
        }

        public static String getName() {
            return "TBL_IMAGE";
        }
    }

    public static String getCreateTBL_IMAGE() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_IMAGE.getName() + " (" +
                TBL_IMAGE.ID_TBL_IMAGE.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_IMAGE.ID_TBL_CUSTOMER.name() + " INTEGER, " +
                TBL_IMAGE.NAME.name() + " TEXT, " +
                TBL_IMAGE.LOCAL_URI.name() + " TEXT, " +
                TBL_IMAGE.OLD_INDEX.name() + " INTEGER, " +
                TBL_IMAGE.NEW_INDEX.name() + " INTEGER, " +
                TBL_IMAGE.CREATE_DAY.name() + " TEXT DEFAULT FALSE" +
                ");";
    }

    public static String getDropTBL_IMAGE() {
        return "DROP TABLE IF EXISTS " + TBL_IMAGE.getName() + " ;";
    }

    public static String getSelectTBL_IMAGE() {
        return "SELECT * " +
                " FROM " +
                TBL_IMAGE.getName();
    }

    public static String getInsertTBL_IMAGE() {
        return "INSERT INTO " + TBL_IMAGE.getName() + " (" +
                TBL_IMAGE.ID_TBL_CUSTOMER.name() + " INTEGER, " +
                TBL_IMAGE.NAME.name() + ", " +
                TBL_IMAGE.LOCAL_URI.name() + ", " +
                TBL_IMAGE.OLD_INDEX.name() + ", " +
                TBL_IMAGE.NEW_INDEX.name() + ", " +
                TBL_IMAGE.CREATE_DAY.name() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_IMAGE() {
        return "UPDATE " +
                TBL_IMAGE.getName() +
                " SET " +

                TBL_IMAGE.ID_TBL_CUSTOMER.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.LOCAL_URI.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.OLD_INDEX.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.NEW_INDEX.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.CREATE_DAY.name() +
                " = ? " +

                " WHERE " +
                TBL_IMAGE.ID_TBL_IMAGE.name() +
                " = ? "

                ;
    }
//
//    public static String getUpdateCREATE_DAYofTBL_IMAGE() {
//        return "UPDATE " +
//                TBL_IMAGE.getName() +
//                " SET " +
//                TBL_IMAGE.FOCUS.name() +
//                " = ? " +
//                " WHERE " +
//                TBL_IMAGE.ID_TBL_IMAGE.name() +
//                " = ? "
//                ;
//    }
    //endregion

    //region DetailProxy
    public static String getSelectAllDetailProxy() {

      return   "SELECT * FROM (SELECT *  FROM " +
              TBL_CUSTOMER.getName() +
              " LEFT JOIN " +
              TBL_BOOK.getName() +
              " ON " +
              "TBL_CUSTOMER.ID_TBL_BOOK = TBL_BOOK.ID_TBL_BOOK) AS A " +
              "LEFT JOIN TBL_IMAGE ON A.ID_TBL_BOOK = TBL_IMAGE.ID_TBL_CUSTOMER";
    }
    //endregion
}
