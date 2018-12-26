package freelancer.gcsnuoc.entities;

public class BookItem implements Cloneable{
    private int ID;
    private String mBookName;
    private STATUS_BOOK mStatusBook;
    private int mCustomerWrited;
    private int mCustomerNotWrite;
    private String mPeriod;
    private boolean mIsFocus;
    private boolean mIsChoose;

    //other info
    private int CODE;
    private String MA_NVIEN;

    //other post
    private int IndexId;
    private String departmentId;
    private String pointId;
    private String timeOfUse;
    private float coefficient;
    private String electricityMeterId;
    private int term;
    private int month;
    private int year;
    private String indexType;
    private String startDate;
    private String endDate;
    private String customerId;
    private String customerCode;
    private String img;


    public BookItem() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String bookName) {
        mBookName = bookName;
    }

    public STATUS_BOOK getStatusBook() {
        return mStatusBook;
    }

    public void setStatusBook(STATUS_BOOK statusBook) {
        mStatusBook = statusBook;
    }

    public int getCustomerWrited() {
        return mCustomerWrited;
    }

    public void setCustomerWrited(int customerWrited) {
        mCustomerWrited = customerWrited;
    }

    public int getCustomerNotWrite() {
        return mCustomerNotWrite;
    }

    public void setCustomerNotWrite(int customerNotWrite) {
        mCustomerNotWrite = customerNotWrite;
    }

    public boolean isFocus() {
        return mIsFocus;
    }

    public void setFocus(boolean focus) {
        mIsFocus = focus;
    }

    public boolean isChoose() {
        return mIsChoose;
    }

    public void setChoose(boolean choose) {
        mIsChoose = choose;
    }

    public String getPeriod() {
        return mPeriod;
    }

    public void setPeriod(String period) {
        mPeriod = period;
    }

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
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

    public float getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }

    public String getElectricityMeterId() {
        return electricityMeterId;
    }

    public void setElectricityMeterId(String electricityMeterId) {
        this.electricityMeterId = electricityMeterId;
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

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public enum STATUS_BOOK {
        UPLOADED("ĐÃ GỬI"),
        WRITED("ĐÃ GHI"),
        NON_WRITING("CHƯA GHI");

        private String mStatus;

        STATUS_BOOK(String status) {
            mStatus = status;
        }

        public String getStatus() {
            return mStatus;
        }

        public static STATUS_BOOK findNameBy(String code) {
            for (STATUS_BOOK v : values()) {
                if (v.getStatus().equals(code)) {
                    return v;
                }
            }
            return STATUS_BOOK.NON_WRITING;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
