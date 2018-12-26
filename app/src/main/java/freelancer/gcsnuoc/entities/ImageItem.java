package freelancer.gcsnuoc.entities;

public class ImageItem implements Cloneable{
    private int ID;
    private int ID_TBL_CUSTOMER;
    private String NAME;
    private String mLOCAL_URI;
    private String CREATE_DAY;

    public ImageItem(int ID_TBL_CUSTOMER, String NAME, String LOCAL_URI, String CREATE_DAY) {
        this.ID_TBL_CUSTOMER = ID_TBL_CUSTOMER;
        this.NAME = NAME;
        mLOCAL_URI = LOCAL_URI;
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
