package freelancer.gcsnuoc.entities;

public class BookItem implements Cloneable{
    private int ID;
    private String mBookName;
    private STATUS_BOOK mStatusBook;
    private int mCustomerWrited;
    private int mCustomerNotWrite;
    private int mCustomerUploaded;
//    private String mPeriod;
    private int term_book;
    private int month_book;
    private int year_book;
    private String BookCode;
    private boolean mIsFocus;
    private boolean mIsChoose;

    //other info
    private String CODE;
    private String MA_NVIEN;
    private int FigureBookId;

    public BookItem() {
    }

    public BookItem(int ID, String bookName, STATUS_BOOK statusBook, int customerWrited, int customerNotWrite, int term_book, int month_book, int year_book, String bookCode, boolean isFocus, boolean isChoose, String CODE, String MA_NVIEN, int figureBookId, int customerUploaded) {
        this.ID = ID;
        mBookName = bookName;
        mStatusBook = statusBook;
        mCustomerWrited = customerWrited;
        mCustomerNotWrite = customerNotWrite;
        this.term_book = term_book;
        this.month_book = month_book;
        this.year_book = year_book;
        BookCode = bookCode;
        mIsFocus = isFocus;
        mIsChoose = isChoose;
        this.CODE = CODE;
        this.MA_NVIEN = MA_NVIEN;
        FigureBookId = figureBookId;
        mCustomerUploaded = customerUploaded;
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

    public int getCustomerUploaded() {
        return mCustomerUploaded;
    }

    public void setCustomerUploaded(int customerUploaded) {
        mCustomerUploaded = customerUploaded;
    }

    public void setCustomerNotWrite(int customerNotWrite) {
        mCustomerNotWrite = customerNotWrite;
    }

    public int getTerm_book() {
        return term_book;
    }

    public void setTerm_book(int term_book) {
        this.term_book = term_book;
    }

    public int getMonth_book() {
        return month_book;
    }

    public void setMonth_book(int month_book) {
        this.month_book = month_book;
    }

    public int getYear_book() {
        return year_book;
    }

    public void setYear_book(int year_book) {
        this.year_book = year_book;
    }

    public String getBookCode() {
        return BookCode;
    }

    public void setBookCode(String bookCode) {
        BookCode = bookCode;
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

    public String getCODE() {
        return CODE;
    }

    public void setCODE(String CODE) {
        this.CODE = CODE;
    }

    public String getMA_NVIEN() {
        return MA_NVIEN;
    }

    public void setMA_NVIEN(String MA_NVIEN) {
        this.MA_NVIEN = MA_NVIEN;
    }

    public int getFigureBookId() {
        return FigureBookId;
    }

    public void setFigureBookId(int figureBookId) {
        FigureBookId = figureBookId;
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
