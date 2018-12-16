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

@Table(name = "TBL_IMAGE")
public class TBL_IMAGE implements Cloneable{
    @EnumNameCollumn()
    public enum table {
        ID,
        ID_TBL_CUSTOMER,
        NAME,
        MA_NVIEN,
        LOCAL_URI,
        OLD_INDEX,
        NEW_INDEX,
        CREATE_DAY;

        public static String getName() {
            return "TBL_IMAGE";
        }
    }

    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID", type = INTEGER, other = "NOT NULL")
    private int ID;

    @Collumn(name = "ID_TBL_CUSTOMER", type = INTEGER, other = "NOT NULL")
    private int ID_TBL_CUSTOMER;

    @Collumn(name = "NAME", type = TEXT)
    private String NAME;

    @Collumn(name = "MA_NVIEN", type = TEXT)
    private String MA_NVIEN;

    @Collumn(name = "LOCAL_URI", type = TEXT)
    private String LOCAL_URI;

    @Collumn(name = "OLD_INDEX", type = INTEGER)
    private int OLD_INDEX;

    @Collumn(name = "NEW_INDEX", type = INTEGER)
    private int NEW_INDEX;

    @Collumn(name = "CREATE_DAY", type = TEXT)
    private String CREATE_DAY;

    public TBL_IMAGE() {
    }

    public TBL_IMAGE(@Params(name = "ID") int ID,
                     @Params(name = "ID_TBL_CUSTOMER") int ID_TBL_CUSTOMER,
                     @Params(name = "NAME") String NAME,
                     @Params(name = "MA_NVIEN") String MA_NVIEN,
                     @Params(name = "LOCAL_URI") String LOCAL_URI,
                     @Params(name = "OLD_INDEX") int OLD_INDEX,
                     @Params(name = "NEW_INDEX") int NEW_INDEX,
                     @Params(name = "CREATE_DAY") String CREATE_DAY) {
        this.ID = ID;
        this.ID_TBL_CUSTOMER = ID_TBL_CUSTOMER;
        this.NAME = NAME;
        this.MA_NVIEN = MA_NVIEN;
        this.LOCAL_URI = LOCAL_URI;
        this.OLD_INDEX = OLD_INDEX;
        this.NEW_INDEX = NEW_INDEX;
        this.CREATE_DAY = CREATE_DAY;
        ;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_TBL_CUSTOMER() {
        return ID_TBL_CUSTOMER;
    }

    public void setID_TBL_CUSTOMER(int ID_TBL_CUSTOMER) {
        this.ID_TBL_CUSTOMER = ID_TBL_CUSTOMER;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getMA_NVIEN() {
        return MA_NVIEN;
    }

    public void setMA_NVIEN(String MA_NVIEN) {
        this.MA_NVIEN = MA_NVIEN;
    }

    public String getLOCAL_URI() {
        return LOCAL_URI;
    }

    public void setLOCAL_URI(String LOCAL_URI) {
        this.LOCAL_URI = LOCAL_URI;
    }

    public int getOLD_INDEX() {
        return OLD_INDEX;
    }

    public void setOLD_INDEX(int OLD_INDEX) {
        this.OLD_INDEX = OLD_INDEX;
    }

    public int getNEW_INDEX() {
        return NEW_INDEX;
    }

    public void setNEW_INDEX(int NEW_INDEX) {
        this.NEW_INDEX = NEW_INDEX;
    }

    public String getCREATE_DAY() {
        return CREATE_DAY;
    }

    public void setCREATE_DAY(String CREATE_DAY) {
        this.CREATE_DAY = CREATE_DAY;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
