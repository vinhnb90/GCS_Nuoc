package freelancer.gcsnuoc.database.config;

import freelancer.gcsnuoc.database.anonation.AutoIncrement;
import freelancer.gcsnuoc.database.anonation.Collumn;
import freelancer.gcsnuoc.database.anonation.EnumNameCollumn;
import freelancer.gcsnuoc.database.anonation.Params;
import freelancer.gcsnuoc.database.anonation.PrimaryKey;
import freelancer.gcsnuoc.database.anonation.TYPE;
import freelancer.gcsnuoc.database.anonation.Table;

import static freelancer.gcsnuoc.database.anonation.TYPE.*;
import static freelancer.gcsnuoc.database.anonation.TYPE.INTEGER;

/**
 * Created by VinhNB on 10/10/2017.
 */

@Table(name = "TBL_BOOK")
public class TBL_BOOK implements Cloneable{

    @EnumNameCollumn()
    public enum table {
        ID,
        NAME,
        MA_NVIEN,
        STATUS,
        CUS_WRITED,
        CUS_NOT_WRITED,
        PERIOD,
        FOCUS,
        CHOOSE;

        public static String getName() {
            return "TBL_BOOK";
        }
    }

    @PrimaryKey
    @AutoIncrement
    @Collumn(name = "ID", type = INTEGER, other = "NOT NULL")
    private int ID;

    @Collumn(name = "NAME", type = TEXT)
    private String NAME;

    @Collumn(name = "MA_NVIEN", type = TEXT)
    private String MA_NVIEN;

    @Collumn(name = "STATUS", type = TEXT)
    private String STATUS;

    @Collumn(name = "CUS_WRITED", type = INTEGER)
    private int CUS_WRITED;

    @Collumn(name = "CUS_NOT_WRITED", type = INTEGER)
    private int CUS_NOT_WRITED;

    @Collumn(name = "PERIOD", type = TEXT)
    private String PERIOD;

    @Collumn(name = "FOCUS", type = TEXT)
    private String FOCUS;

    @Collumn(name = "CHOOSE", type = TEXT)
    private String CHOOSE;


    public TBL_BOOK() {
    }

    public TBL_BOOK(@Params(name = "ID") int ID,
                    @Params(name = "NAME") String NAME,
                    @Params(name = "MA_NVIEN") String TEN_ANH,
                    @Params(name = "STATUS") String STATUS,
                    @Params(name = "CUS_WRITED") int CUS_WRITED,
                    @Params(name = "CUS_NOT_WRITED") int CUS_NOT_WRITED,
                    @Params(name = "PERIOD") String PERIOD,
                    @Params(name = "FOCUS") String FOCUS,
                    @Params(name = "CHOOSE") String CHOOSE) {
        this.ID = ID;
        this.NAME = NAME;
        this.MA_NVIEN = MA_NVIEN;
        this.STATUS = STATUS;
        this.CUS_WRITED = CUS_WRITED;
        this.CUS_NOT_WRITED = CUS_NOT_WRITED;
        this.PERIOD = PERIOD;
        this.FOCUS = FOCUS;
        this.CHOOSE = CHOOSE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public int getCUS_WRITED() {
        return CUS_WRITED;
    }

    public void setCUS_WRITED(int CUS_WRITED) {
        this.CUS_WRITED = CUS_WRITED;
    }

    public int getCUS_NOT_WRITED() {
        return CUS_NOT_WRITED;
    }

    public void setCUS_NOT_WRITED(int CUS_NOT_WRITED) {
        this.CUS_NOT_WRITED = CUS_NOT_WRITED;
    }

    public String getPERIOD() {
        return PERIOD;
    }

    public void setPERIOD(String PERIOD) {
        this.PERIOD = PERIOD;
    }

    public String getFOCUS() {
        return FOCUS;
    }

    public void setFOCUS(String FOCUS) {
        this.FOCUS = FOCUS;
    }

    public String getCHOOSE() {
        return CHOOSE;
    }

    public void setCHOOSE(String CHOOSE) {
        this.CHOOSE = CHOOSE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
