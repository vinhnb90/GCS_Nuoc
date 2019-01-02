package freelancer.gcsnuoc.database;

import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.CustomerItem;
import retrofit2.http.DELETE;

/**
 * Created by VinhNB on 8/8/2017.
 */
public class SqlQuery {

    //region TBL_SESSION
    public enum TBL_SESSION {
        ID_TABLE_SESSION("ID_TABLE_SESSION"),
        USERNAME("USERNAME"),
        PASSWORD("PASSWORD"),
        DATE_LOGIN("DATE_LOGIN"),
        MA_NVIEN("MA_NVIEN");

        private String mNameCollumn;

        TBL_SESSION(String mNameCollumn) {
            this.mNameCollumn = mNameCollumn;
        }

        public String getNameCollumn() {
            return mNameCollumn;
        }

        public static String getName() {
            return "TBL_SESSION";
        }
    }

    public static String getCreateTBL_SESSION() {
        return "CREATE TABLE IF NOT EXISTS " + TBL_SESSION.getName() + " (" +
                TBL_SESSION.ID_TABLE_SESSION.name() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TBL_SESSION.USERNAME.name() + " TEXT, " +
                TBL_SESSION.PASSWORD.name() + " TEXT, " +
                TBL_SESSION.DATE_LOGIN.name() + " TEXT, " +
                TBL_SESSION.MA_NVIEN.name() + " TEXT" +
                ");";
    }

    public static String getDropTBL_SESSION() {
        return "DROP TABLE IF EXISTS " + TBL_SESSION.getName() + " ;";
    }

    public static String getSelectPassTBL_SESSION() {
        return "SELECT * " +
                " FROM " +
                TBL_SESSION.getName() +
                " WHERE " +
                TBL_SESSION.MA_NVIEN +
                "= ?" +
                " AND " +
                TBL_SESSION.USERNAME +
                " = ?";
    }

    public static String getInsertTBL_SESSION() {
        return "INSERT INTO " + TBL_SESSION.getName() + " (" +
                TBL_SESSION.USERNAME.name() + ", " +
                TBL_SESSION.PASSWORD.name() + ", " +
                TBL_SESSION.DATE_LOGIN.name() + ", " +
                TBL_SESSION.MA_NVIEN.name() +
                ") " + "VALUES (?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_SESSION() {
        return "UPDATE " +
                TBL_SESSION.getName() +
                " SET " +

                TBL_SESSION.USERNAME.name() +
                " = ? " +
                ", " +

                TBL_SESSION.PASSWORD.name() +
                " = ? " +
                ", " +

                TBL_SESSION.DATE_LOGIN.name() +
                " = ? " +

                " WHERE " +
                TBL_SESSION.ID_TABLE_SESSION.name() +
                " = ? " +

                " AND " +

                TBL_SESSION.MA_NVIEN +
                "= ? ";
    }

    public static String getDeleteAllTBL_SESSION() {
        return "DELETE FROM " + TBL_SESSION.getName() + " WHERE " + TBL_SESSION.MA_NVIEN + " = ?";
    }

    public static String deleteAllTBL_SESSIONByUSER_NAME() {
        return "DELETE FROM " + TBL_SESSION.getName() + " WHERE " + TBL_SESSION.USERNAME + " = ?";
    }


    public static String checkAccountTBL_SESSION() {
        return "SELECT * FROM " +
                TBL_SESSION.getName() +
                " WHERE " +
                TBL_SESSION.USERNAME +
                " = ?" +
                " AND " +
                TBL_SESSION.PASSWORD +
                " = ?" +
                "";
    }
    //endregion

    //region TBL_BOOK
    public enum TBL_BOOK {
        ID_TBL_BOOK("ID_TBL_BOOK"),
        NAME("NAME"),
        STATUS("STATUS"),
        CUS_WRITED("CUS_WRITED"),
        CUS_NOT_WRITED("CUS_NOT_WRITED"),
        //        PERIOD("PERIOD"),
        term_book("term_book"),
        month_book("month_book"),
        year_book("year_book"),
        BookCode("BookCode"),
        FOCUS("FOCUS"),
        CHOOSE("CHOOSE"),
        MA_NVIEN("MA_NVIEN"),
        CODE("CODE"),
        FigureBookId("FigureBookId");

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
//                TBL_BOOK.PERIOD.name() + " TEXT, " +
                TBL_BOOK.term_book.name() + " INTEGER, " +
                TBL_BOOK.month_book.name() + " TEXT, " +
                TBL_BOOK.year_book.name() + " TEXT, " +
                TBL_BOOK.BookCode.name() + " TEXT, " +
                TBL_BOOK.MA_NVIEN.name() + " TEXT, " +
                TBL_BOOK.FigureBookId.name() + " INTEGER, " +
                TBL_BOOK.CODE.name() + " TEXT, " +
                TBL_BOOK.FOCUS.name() + " TEXT DEFAULT \"FALSE\", " +
                TBL_BOOK.CHOOSE.name() + " TEXT DEFAULT \"FALSE\"" +
                ");";
    }

    public static String getDropTBL_BOOK() {
        return "DROP TABLE IF EXISTS " + TBL_BOOK.getName() + " ;";
    }

    public static String getSelectTBL_BOOK() {
        return "SELECT * " +
                " FROM " +
                TBL_BOOK.getName() +
                " WHERE " +
                TBL_BOOK.MA_NVIEN +
                " = ?";
    }

    public static String selectAllTBL_BOOKHasWrited() {

        return "SELECT * " +
                " FROM " +
                TBL_BOOK.getName() +
                " WHERE " +
                TBL_BOOK.CUS_WRITED +
                " > 0" +
                " AND " +
                TBL_BOOK.MA_NVIEN +
                " = ?";
    }


    public static String getInsertTBL_BOOK() {
        return "INSERT INTO " + TBL_BOOK.getName() + " (" +
                TBL_BOOK.NAME.name() + ", " +
                TBL_BOOK.STATUS.name() + ", " +
                TBL_BOOK.CUS_WRITED.name() + ", " +
                TBL_BOOK.CUS_NOT_WRITED.name() + ", " +
//                TBL_BOOK.PERIOD.name() + ", " +
                TBL_BOOK.term_book.name() + ", " +
                TBL_BOOK.month_book.name() + ", " +
                TBL_BOOK.year_book.name() + ", " +
                TBL_BOOK.BookCode.name() + ", " +
                TBL_BOOK.FigureBookId.name() + ", " +
                TBL_BOOK.FOCUS.name() + ", " +
                TBL_BOOK.CHOOSE.name() + ", " +
                TBL_BOOK.MA_NVIEN.name() + ", " +
                TBL_BOOK.CODE.name() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
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

//                TBL_BOOK.PERIOD.name() +
//                " = ? " +
//                ", " +

                TBL_BOOK.term_book.name() +
                " = ? " +
                ", " +

                TBL_BOOK.month_book.name() +
                " = ? " +
                ", " +

                TBL_BOOK.year_book.name() +
                " = ? " +
                ", " +

                TBL_BOOK.FigureBookId.name() +
                " = ? " +
                ", " +

                TBL_BOOK.BookCode.name() +
                " = ? " +
                ", " +

                TBL_BOOK.FOCUS.name() +
                " = ? " +
                ", " +

                TBL_BOOK.CHOOSE.name() +
                " = ? " +

                " WHERE " +
                TBL_BOOK.ID_TBL_BOOK.name() +
                " = ? " +
                " AND " +
                TBL_BOOK.MA_NVIEN.name() +
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
                " = ? " +
                " AND " +
                TBL_BOOK.MA_NVIEN.name() +
                " = ? "
                ;
    }

    public static String getUpdateFocusTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getName() +
                " SET " +
                TBL_BOOK.FOCUS.name() +
                " = 'TRUE' " +
                " WHERE " +
                TBL_BOOK.ID_TBL_BOOK.name() +
                " = ? " +
                " AND " +
                TBL_BOOK.MA_NVIEN.name() +
                " = ? "
                ;
    }

    public static String getUpdateResetFocusTBL_BOOK() {
        return "UPDATE " +
                TBL_BOOK.getName() +
                " SET " +
                TBL_BOOK.FOCUS.name() +
                " = 'FALSE' " +
                " AND " +
                TBL_BOOK.MA_NVIEN.name() +
                " = ? "
                ;
    }

    public static String getDeleteAllRowTBL_BOOK() {
        return "DELETE FROM TBL_BOOK WHERE " +
                TBL_BOOK.MA_NVIEN +
                " = ?"
                ;
    }

    public static String getDeleteAllRowUploadedTBL_BOOK() {
        return "DELETE FROM TBL_BOOK WHERE " +
                TBL_BOOK.MA_NVIEN +
                " = ?" +
                " AND " +
                TBL_BOOK.STATUS +
                " = " +
                BookItem.STATUS_BOOK.UPLOADED
                ;
    }

    public static String checkExistTBL_BOOK() {
        return "SELECT * " +
                " FROM " +
                TBL_BOOK.getName() +
                " WHERE " +
                TBL_BOOK.CODE +
                " = ?" +
                " AND " +
                TBL_BOOK.term_book +
                " = ?" +
                " AND " +
                TBL_BOOK.month_book +
                " = ?" +
                " AND " +
                TBL_BOOK.year_book +
                " = ?" +
                " AND " +
                TBL_BOOK.BookCode +
                " = ?" +
                " AND " +
                TBL_BOOK.MA_NVIEN +
                " = ?";
    }
    //endregion

    //region TBL_CUSTOMER
    public enum TBL_CUSTOMER {
        ID_TBL_CUSTOMER("ID_TBL_CUSTOMER"),
        ID_TBL_BOOK_OF_CUSTOMER("ID_TBL_BOOK_OF_CUSTOMER"),
        NAME_CUSTOMER("NAME_CUSTOMER"),
        ADDRESS_CUSTOMER("ADDRESS_CUSTOMER"),
        STATUS_CUSTOMER("STATUS_CUSTOMER"),
        FOCUS_CUSTOMER("FOCUS_CUSTOMER"),
        OLD_INDEX("OLD_INDEX"),
        NEW_INDEX("NEW_INDEX"),
        MA_NVIEN("MA_NVIEN"),

        //other post
        IndexId("IndexId"),
        departmentId("departmentId"),
        pointId("pointId"),
        timeOfUse("timeOfUse"),
        coefficient("coefficient"),
        electricityMeterId("electricityMeterId"),
        term("term"),
        month("month"),
        year("year"),
        indexType("indexType"),
        startDate("startDate"),
        endDate("endDate"),
        customerId("customerId"),
        FigureBookId_Customer("FigureBookId_Customer"),
        customerCode("customerCode");

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
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.name() + " INTEGER, " +
                TBL_CUSTOMER.NAME_CUSTOMER.name() + " TEXT, " +
                TBL_CUSTOMER.ADDRESS_CUSTOMER.name() + " TEXT, " +
                TBL_CUSTOMER.STATUS_CUSTOMER.name() + " TEXT, " +
                TBL_CUSTOMER.OLD_INDEX.name() + " REAL, " +
                TBL_CUSTOMER.NEW_INDEX.name() + " REAL, " +
                TBL_CUSTOMER.MA_NVIEN.name() + " TEXT, " +

                TBL_CUSTOMER.IndexId.name() + " INTEGER, " +
                TBL_CUSTOMER.departmentId.name() + " TEXT, " +
                TBL_CUSTOMER.pointId.name() + " TEXT, " +
                TBL_CUSTOMER.timeOfUse.name() + " TEXT, " +
                TBL_CUSTOMER.coefficient.name() + " REAL, " +
                TBL_CUSTOMER.electricityMeterId.name() + " TEXT, " +
                TBL_CUSTOMER.term.name() + " INTEGER, " +
                TBL_CUSTOMER.month.name() + " INTEGER, " +
                TBL_CUSTOMER.year.name() + " INTEGER, " +
                TBL_CUSTOMER.indexType.name() + " TEXT, " +
                TBL_CUSTOMER.startDate.name() + " TEXT, " +
                TBL_CUSTOMER.endDate.name() + " TEXT, " +
                TBL_CUSTOMER.customerId.name() + " TEXT, " +
                TBL_CUSTOMER.FigureBookId_Customer.name() + " INTEGER, " +
                TBL_CUSTOMER.customerCode.name() + " TEXT, " +

                TBL_CUSTOMER.FOCUS_CUSTOMER.name() + " TEXT DEFAULT \"FALSE\"" +
                ");";
    }

    public static String getDropTBL_CUSTOMER() {
        return "DROP TABLE IF EXISTS " + TBL_CUSTOMER.getName() + " ;";
    }

    public static String getSelectTBL_CUSTOMER() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.getName() + "" +
                " WHERE " +
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER +
                " = ?" +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? ";
    }

    public static String getNumberRowStatusTBL_CUSTOMERByBook() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.getName() + "" +
                " WHERE " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? " +
                "AND " +
                TBL_CUSTOMER.STATUS_CUSTOMER +
                "= ?" +
                "AND " +
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER +
                "= ?" +
                "";
    }

    public static String getNumberRowStatusTBL_CUSTOMER() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.getName() + "" +
                " WHERE " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? " +
                "AND " +
                TBL_CUSTOMER.STATUS_CUSTOMER +
                "= ?" +
                "";
    }


//    public static String getNumberRowStatusTBL_CUSTOMERByBook() {
//        return "SELECT * " +
//                " FROM " +
//                TBL_CUSTOMER.getName() + "" +
//                " WHERE " +
//                TBL_CUSTOMER.MA_NVIEN.name() +
//                " = ? " +
//                "AND " +
//                TBL_CUSTOMER.STATUS_CUSTOMER +
//                "= ?" +
//                "AND " +
//                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER +
//                "= ?" +
//                "";
//    }


    public static String getInsertTBL_CUSTOMER() {
        return "INSERT INTO " + TBL_CUSTOMER.getName() + " (" +
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.name() + ", " +
                TBL_CUSTOMER.NAME_CUSTOMER.name() + ", " +
                TBL_CUSTOMER.ADDRESS_CUSTOMER.name() + ", " +
                TBL_CUSTOMER.STATUS_CUSTOMER.name() + ", " +
                TBL_CUSTOMER.FOCUS_CUSTOMER.name() + ", " +

                TBL_CUSTOMER.OLD_INDEX.name() + ", " +
                TBL_CUSTOMER.NEW_INDEX.name() + ", " +
                TBL_CUSTOMER.MA_NVIEN.name() + ", " +
                TBL_CUSTOMER.IndexId.name() + ", " +
                TBL_CUSTOMER.departmentId.name() + ", " +

                TBL_CUSTOMER.pointId.name() + ", " +
                TBL_CUSTOMER.timeOfUse.name() + ", " +
                TBL_CUSTOMER.coefficient.name() + ", " +
                TBL_CUSTOMER.electricityMeterId.name() + ", " +
                TBL_CUSTOMER.term.name() + ", " +

                TBL_CUSTOMER.month.name() + ", " +
                TBL_CUSTOMER.year.name() + ", " +
                TBL_CUSTOMER.indexType.name() + ", " +
                TBL_CUSTOMER.startDate.name() + ", " +
                TBL_CUSTOMER.endDate.name() + ", " +

                TBL_CUSTOMER.customerId.name() + ", " +
                TBL_CUSTOMER.FigureBookId_Customer.name() + ", " +
                TBL_CUSTOMER.customerCode.name() +
                ") " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ");"
                ;
    }


    //other post

    public static String updateTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +

                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.STATUS_CUSTOMER.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.FOCUS_CUSTOMER.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.OLD_INDEX.name() +
                " = ? " +
                ", " +

                TBL_CUSTOMER.NEW_INDEX.name() +
                " = ? " +

                " WHERE " +
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.name() +
                " = ? "
                +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? ";
    }

    public static String getUpdateFocusTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +
                TBL_CUSTOMER.FOCUS_CUSTOMER.name() +
                " = ? " +
                " WHERE " +
                TBL_CUSTOMER.ID_TBL_CUSTOMER.name() +
                " = ? "
                +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? ";
    }


    public static String getUpdateStatusTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +
                TBL_CUSTOMER.STATUS_CUSTOMER.name() +
                " = ? " +
                " WHERE " +
                TBL_CUSTOMER.ID_TBL_CUSTOMER.name() +
                " = ? " +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? ";
    }


    public static String getUpdateResetFocusTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +
                TBL_CUSTOMER.FOCUS_CUSTOMER.name() +
                " = 'FALSE' " +
                " WHERE " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? " +
                " AND " +
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.name() +
                " = ?";
    }

    public static String getIDByFocusTBL_CUSTOMER() {
        return "SELECT " +
                TBL_CUSTOMER.ID_TBL_CUSTOMER + "," +
                TBL_CUSTOMER.FOCUS_CUSTOMER +
                " FROM  " +
                TBL_CUSTOMER.getName() +
                " WHERE " +
                TBL_CUSTOMER.FOCUS_CUSTOMER.name() +
                " = 'TRUE' " +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? ";
    }

    public static String getUpdateNEW_INDEXOfTBL_CUSTOMER() {
        return "UPDATE " +
                TBL_CUSTOMER.getName() +
                " SET " +
                TBL_CUSTOMER.NEW_INDEX.name() +
                " = ? " +
                " WHERE " +
                TBL_CUSTOMER.ID_TBL_CUSTOMER.name() +
                " = ? " +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ?"
                ;
    }

    public static String getUpdateCUS_WRITEDOfTBL_BOOK(boolean isCUS_WRITED) {
        String collumn = isCUS_WRITED ? TBL_BOOK.CUS_WRITED.name() : TBL_BOOK.CUS_NOT_WRITED.name();

        return "UPDATE " +
                TBL_BOOK.getName() +
                " SET " +
                collumn +
                " = ? " +
                " WHERE " +
                TBL_BOOK.ID_TBL_BOOK.name() +
                " = ? " +
                " AND " +
                TBL_BOOK.MA_NVIEN.name() +
                " = ?"
                ;
    }

    public static String getDeleteAllRowTBL_CUSTOMER() {
        return "DELETE FROM TBL_CUSTOMER WHERE " +
                TBL_CUSTOMER.MA_NVIEN +
                " = ?"
                ;
    }

    public static String getDeleteAllRowUploadedTBL_CUSTOMER() {
        return "DELETE FROM TBL_CUSTOMER WHERE " +
                TBL_CUSTOMER.MA_NVIEN +
                " = ?" +
                " AND " +
                TBL_CUSTOMER.STATUS_CUSTOMER +
                " = " +
                CustomerItem.STATUS_Customer.UPLOADED
                ;
    }

    public static String getSelectTBL_CUSTOMERbyStatus() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.getName() + "" +
                " WHERE " +
                TBL_CUSTOMER.MA_NVIEN.name() +
                " = ? " +
                "AND " +
                TBL_CUSTOMER.STATUS_CUSTOMER +
                "= ?" +
                "AND " +
                TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER +
                "= ?" +
                "";
    }

    public static String getCheckExistCustomer() {
        return "SELECT * " +
                " FROM " +
                TBL_CUSTOMER.getName() +
                " WHERE " +
                TBL_CUSTOMER.pointId +
                " = ?" +
                " AND " +
                TBL_CUSTOMER.term +
                " = ?" +
                " AND " +
                TBL_CUSTOMER.month +
                " = ?" +
                " AND " +
                TBL_CUSTOMER.year +
                " = ?" +
                " AND " +
                TBL_CUSTOMER.MA_NVIEN +
                " = ?";
    }
    //endregion

    //region TBL_IMAGE
    public enum TBL_IMAGE {
        ID_TBL_IMAGE("ID_TBL_IMAGE"),
        ID_TBL_CUSTOMER_OF_IMAGE("ID_TBL_CUSTOMER_OF_IMAGE"),
        NAME_IMAGE("NAME_IMAGE"),
        LOCAL_URI("LOCAL_URI"),
        CREATE_DAY("CREATE_DAY"),

        //other
        MA_NVIEN("MA_NVIEN");


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
                TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE.name() + " INTEGER, " +
                TBL_IMAGE.NAME_IMAGE.name() + " TEXT, " +
                TBL_IMAGE.LOCAL_URI.name() + " TEXT, " +
                TBL_IMAGE.MA_NVIEN.name() + " TEXT, " +
                TBL_IMAGE.CREATE_DAY.name() + " TEXT" +
                ");";
    }

    public static String getDropTBL_IMAGE() {
        return "DROP TABLE IF EXISTS " + TBL_IMAGE.getName() + " ;";
    }

    public static String getSelectTBL_IMAGE() {
        return "SELECT * " +
                " FROM " +
                TBL_IMAGE.getName() +
                " WHERE " +
                TBL_IMAGE.MA_NVIEN.name() +
                " = ?"
                ;
    }

    public static String getInsertTBL_IMAGE() {
        return "INSERT INTO " + TBL_IMAGE.getName() + " (" +
                TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE.name() + ", " +
                TBL_IMAGE.NAME_IMAGE.name() + ", " +
                TBL_IMAGE.LOCAL_URI.name() + ", " +
                TBL_IMAGE.MA_NVIEN.name() + ", " +
                TBL_IMAGE.CREATE_DAY.name() +
                ") " + "VALUES (?, ?, ?, ?, ?" +
                ");"
                ;
    }

    public static String updateTBL_IMAGE() {
        return "UPDATE " +
                TBL_IMAGE.getName() +
                " SET " +

                TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.LOCAL_URI.name() +
                " = ? " +
                ", " +

                TBL_IMAGE.CREATE_DAY.name() +
                " = ? " +

                " WHERE " +
                TBL_IMAGE.ID_TBL_IMAGE.name() +
                " = ? " +
                " AND " +
                TBL_IMAGE.MA_NVIEN.name() +
                " = ?"
                ;
    }

    public static String getDeleteIMAGE() {
        return "DELETE FROM TBL_IMAGE WHERE " +
                TBL_IMAGE.ID_TBL_IMAGE +
                " = ?" +
                " AND " +
                TBL_IMAGE.MA_NVIEN.name() +
                " = ?"
                ;

    }
//
//    public static String getUpdateCREATE_DAYofTBL_IMAGE() {
//        return "UPDATE " +
//                TBL_IMAGE.getNameCustomer() +
//                " SET " +
//                TBL_IMAGE.FOCUS_CUSTOMER.name() +
//                " = ? " +
//                " WHERE " +
//                TBL_IMAGE.ID_TBL_IMAGE.name() +
//                " = ? "
//                ;
//    }

    public static String getDeleteAllRowTBL_IMAGE() {
        return "DELETE FROM TBL_IMAGE WHERE " +
                TBL_IMAGE.MA_NVIEN +
                " = ?"
                ;
    }

    public static String getSelectAllTBL_IMAGE() {
        return "SELECT * " +
                " FROM " +
                TBL_IMAGE.getName() +
                " WHERE " +
                TBL_IMAGE.MA_NVIEN +
                " = ?";
    }
    //endregion

    //region DetailProxy
    public static String getSelectAllDetailProxy() {
        return "select * from\n" +
                "(\n" +
                "select * FROM TBL_CUSTOMER LEFT join TBL_BOOK on TBL_BOOK.ID_TBL_BOOK = TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER" +
                " where TBL_CUSTOMER.MA_NVIEN = ? \n" +
                ") as e\n" +
                "LEFT join TBL_IMAGE\n" +
                "on e.ID_TBL_CUSTOMER = TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE" +
                " where e.ID_TBL_BOOK_OF_CUSTOMER = ?";
    }

    //region DetailProxy
    public static String getSelectAllDetailProxyNotWrite() {
        return "select * from\n" +
                "(\n" +
                "select * FROM TBL_CUSTOMER LEFT join TBL_BOOK on TBL_BOOK.ID_TBL_BOOK = TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER" +
                " where TBL_CUSTOMER.MA_NVIEN = ? and TBL_CUSTOMER.STATUS_CUSTOMER = " +
                "'" +
                CustomerItem.STATUS_Customer.NON_WRITING.getStatus() +
                "'" +
                " \n" +
                ") as e\n" +
                "LEFT join TBL_IMAGE\n" +
                "on e.ID_TBL_CUSTOMER = TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE" +
                " where e.ID_TBL_BOOK_OF_CUSTOMER = ?";
    }
    //endregion
}
