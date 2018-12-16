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

    public BookItem(String bookName, STATUS_BOOK statusBook, int customerWrited, int customerNotWrite, String period, boolean isFocus, boolean isChoose) {
        mBookName = bookName;
        mStatusBook = statusBook;
        mCustomerWrited = customerWrited;
        mCustomerNotWrite = customerNotWrite;
        mPeriod = period;
        mIsFocus = isFocus;
        mIsChoose = isChoose;
    }

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
            return null;
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
