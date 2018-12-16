package freelancer.gcsnuoc.database.config;

import freelancer.gcsnuoc.database.anonation.AutoIncrement;
import freelancer.gcsnuoc.database.anonation.Collumn;
import freelancer.gcsnuoc.database.anonation.EnumNameCollumn;
import freelancer.gcsnuoc.database.anonation.Params;
import freelancer.gcsnuoc.database.anonation.PrimaryKey;
import freelancer.gcsnuoc.database.anonation.Table;

import static freelancer.gcsnuoc.database.anonation.TYPE.INTEGER;
import static freelancer.gcsnuoc.database.anonation.TYPE.TEXT;

/**
 * Created by VinhNB on 10/10/2017.
 */

@Table(name = "TBL_CUSTOMER")
public class TBL_CUSTOMER implements Cloneable{
    @EnumNameCollumn()
    public enum table {
        ID,
        ID_TBL_BOOK,
        MA_NVIEN,
        NAME,
        CUSTOMER_ADDRESS,
        STATUS,
        FOCUS;

        public static String getName() {
            return "TBL_CUSTOMER";
        }
    }



    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID", type = INTEGER, other = "NOT NULL")
    private int ID;

    @Collumn(name = "ID_TBL_BOOK", type = INTEGER)
    private int ID_TBL_BOOK;

    @Collumn(name = "MA_NVIEN", type = TEXT)
    private String MA_NVIEN;

    @Collumn(name = "NAME", type = TEXT)
    private String NAME;

    @Collumn(name = "CUSTOMER_ADDRESS", type = TEXT)
    private String CUSTOMER_ADDRESS;

    @Collumn(name = "STATUS", type = TEXT)
    private String STATUS;

    @Collumn(name = "FOCUS", type = TEXT)
    private String FOCUS;

    public TBL_CUSTOMER() {
    }

    public TBL_CUSTOMER(@Params(name = "ID") int ID,
                        @Params(name = "ID_TBL_BOOK") int ID_TBL_BOOK,
                        @Params(name = "MA_NVIEN") String MA_NVIEN,
                        @Params(name = "NAME") String NAME,
                        @Params(name = "CUSTOMER_ADDRESS") String CUSTOMER_ADDRESS,
                        @Params(name = "STATUS") String STATUS,
                        @Params(name = "FOCUS") String FOCUS) {
        this.ID = ID;
        this.ID_TBL_BOOK = ID_TBL_BOOK;
        this.MA_NVIEN = MA_NVIEN;
        this.NAME = NAME;
        this.CUSTOMER_ADDRESS = CUSTOMER_ADDRESS;
        this.STATUS = STATUS;
        this.FOCUS = FOCUS;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_TBL_BOOK() {
        return ID_TBL_BOOK;
    }

    public void setID_TBL_BOOK(int ID_TBL_BOOK) {
        this.ID_TBL_BOOK = ID_TBL_BOOK;
    }

    public String getMA_NVIEN() {
        return MA_NVIEN;
    }

    public void setMA_NVIEN(String MA_NVIEN) {
        this.MA_NVIEN = MA_NVIEN;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCUSTOMER_ADDRESS() {
        return CUSTOMER_ADDRESS;
    }

    public void setCUSTOMER_ADDRESS(String CUSTOMER_ADDRESS) {
        this.CUSTOMER_ADDRESS = CUSTOMER_ADDRESS;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getFOCUS() {
        return FOCUS;
    }

    public void setFOCUS(String FOCUS) {
        this.FOCUS = FOCUS;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
