package freelancer.gcsnuoc.entities;

public class ImageItem implements Cloneable{
    private int ID;
    private int ID_TBL_CUSTOMER;
    private String NAME;
    private String mLOCAL_URI;
    private int OLD_INDEX;
    private int NEW_INDEX;
    private String CREATE_DAY;

    public ImageItem(int ID_TBL_CUSTOMER, String NAME, String LOCAL_URI, int OLD_INDEX, int NEW_INDEX, String CREATE_DAY) {
        this.ID_TBL_CUSTOMER = ID_TBL_CUSTOMER;
        this.NAME = NAME;
        mLOCAL_URI = LOCAL_URI;
        this.OLD_INDEX = OLD_INDEX;
        this.NEW_INDEX = NEW_INDEX;
        this.CREATE_DAY = CREATE_DAY;
    }

    public ImageItem() {
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

    public String getLOCAL_URI() {
        return mLOCAL_URI;
    }

    public void setLOCAL_URI(String LOCAL_URI) {
        mLOCAL_URI = LOCAL_URI;
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
