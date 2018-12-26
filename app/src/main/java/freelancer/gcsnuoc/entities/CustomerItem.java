package freelancer.gcsnuoc.entities;

public class CustomerItem implements Cloneable{
    private int ID;
    private int IDBook;
    private String mCustomerName;
    private String mCustomerAddress;
    private STATUS_Customer mStatusCustomer;
    private boolean mIsFocus;

    //other
    private double mNewIndex;
    private double mOldIndex;
    private String MA_NVIEN;

    public CustomerItem(int IDBook, String customerName, String customerAddress, STATUS_Customer statusCustomer, boolean isFocus, int newIndex, int oldIndex, String MA_NVIEN) {
        this.IDBook = IDBook;
        mCustomerName = customerName;
        mCustomerAddress = customerAddress;
        mStatusCustomer = statusCustomer;
        mIsFocus = isFocus;
        mNewIndex = newIndex;
        mOldIndex = oldIndex;
        this.MA_NVIEN = MA_NVIEN;
    }

    public CustomerItem() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDBook() {
        return IDBook;
    }

    public void setIDBook(int IDBook) {
        this.IDBook = IDBook;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public void setCustomerName(String customerName) {
        mCustomerName = customerName;
    }

    public STATUS_Customer getStatusCustomer() {
        return mStatusCustomer;
    }

    public void setStatusCustomer(STATUS_Customer statusCustomer) {
        mStatusCustomer = statusCustomer;
    }

    public boolean isFocus() {
        return mIsFocus;
    }

    public void setFocus(boolean focus) {
        mIsFocus = focus;
    }

    public String getMA_NVIEN() {
        return MA_NVIEN;
    }

    public void setMA_NVIEN(String MA_NVIEN) {
        this.MA_NVIEN = MA_NVIEN;
    }

    public enum STATUS_Customer {
        UPLOADED("ĐÃ GỬI"),
        WRITED("ĐÃ GHI"),
        NON_WRITING("CHƯA GHI");

        private String mStatus;

        STATUS_Customer(String status) {
            mStatus = status;
        }

        public String getStatus() {
            return mStatus;
        }

        public static STATUS_Customer findNameBy(String code) {
            for (STATUS_Customer v : values()) {
                if (v.getStatus().equals(code)) {
                    return v;
                }
            }
            return STATUS_Customer.NON_WRITING;
        }
    }

    public String getCustomerAddress() {
        return mCustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        mCustomerAddress = customerAddress;
    }

    public double getNewIndex() {
        return mNewIndex;
    }

    public void setNewIndex(double newIndex) {
        mNewIndex = newIndex;
    }

    public double getOldIndex() {
        return mOldIndex;
    }

    public void setOldIndex(double oldIndex) {
        mOldIndex = oldIndex;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
