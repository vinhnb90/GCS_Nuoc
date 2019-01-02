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

    //post
    //other post
    private int IndexId;
    private String departmentId;
    private String pointId;
    private String pointcode;
    private String timeOfUse;
    private double coefficient;
    private String electricityMeterId;
    private int term;
    private int month;
    private int year;
    private String indexType;
    private String startDate;
    private String endDate;
    private String customerId;
    private int FigureBookId_Customer;
    private String customerCode;

    public CustomerItem() {
    }

    public CustomerItem(int ID, int IDBook, String customerName, String customerAddress, STATUS_Customer statusCustomer, boolean isFocus, double newIndex, double oldIndex, String MA_NVIEN, int indexId, String departmentId, String pointId, String pointcode, String timeOfUse, double coefficient, String electricityMeterId, int term, int month, int year, String indexType, String startDate, String endDate, String customerId, int figureBookId_Customer, String customerCode) {
        this.ID = ID;
        this.IDBook = IDBook;
        mCustomerName = customerName;
        mCustomerAddress = customerAddress;
        mStatusCustomer = statusCustomer;
        mIsFocus = isFocus;
        mNewIndex = newIndex;
        mOldIndex = oldIndex;
        this.MA_NVIEN = MA_NVIEN;
        IndexId = indexId;
        this.departmentId = departmentId;
        this.pointId = pointId;
        this.pointcode = pointcode;
        this.timeOfUse = timeOfUse;
        this.coefficient = coefficient;
        this.electricityMeterId = electricityMeterId;
        this.term = term;
        this.month = month;
        this.year = year;
        this.indexType = indexType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        FigureBookId_Customer = figureBookId_Customer;
        this.customerCode = customerCode;
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

    public int getIndexId() {
        return IndexId;
    }

    public void setIndexId(int indexId) {
        IndexId = indexId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getTimeOfUse() {
        return timeOfUse;
    }

    public void setTimeOfUse(String timeOfUse) {
        this.timeOfUse = timeOfUse;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getElectricityMeterId() {
        return electricityMeterId;
    }

    public void setElectricityMeterId(String electricityMeterId) {
        this.electricityMeterId = electricityMeterId;
    }

    public String getPointcode() {
        return pointcode;
    }

    public void setPointcode(String pointcode) {
        this.pointcode = pointcode;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public int getFigureBookId_Customer() {
        return FigureBookId_Customer;
    }

    public void setFigureBookId_Customer(int figureBookId_Customer) {
        FigureBookId_Customer = figureBookId_Customer;
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
