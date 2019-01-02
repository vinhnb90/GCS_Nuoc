package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import freelancer.gcsnuoc.database.SqlQuery;

public class DetailProxy extends CursorItemProxy {
    private static final String TAG = "DetailProxy";
    private CustomerItem mCustomerItem;
    private ImageItem mImageItem;
    private BookItem mBookItem;
    private Bitmap mBitmap;

    public DetailProxy(@NonNull Cursor mCursor, int mIndex) {
        super(mCursor, mIndex);
        mCustomerItem = new CustomerItem();
        mImageItem = new ImageItem();
        mBookItem = new BookItem();
    }


    //region BOOK
//    public String getPeriodOfTBL_BOOK() {
//        if (TextUtils.isEmpty(mBookItem.getPeriod())) {
//            Cursor cursor = getmCursor();
//            cursor.moveToPosition(getmIndex());
//            String PERIOD = SqlQuery.TBL_BOOK.PERIOD.getNameCollumn();
//            mBookItem.setPeriod(cursor.getString(cursor.getColumnIndex(PERIOD)));
//        }
//        return mBookItem.getPeriod();
//    }
    //endregion


    //region IMAGE
    public int getIDOfTBL_IMAGE() {
        if (mImageItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID = SqlQuery.TBL_IMAGE.ID_TBL_IMAGE.getNameCollumn();
            mImageItem.setID(cursor.getInt(cursor.getColumnIndex(ID)));
        }
        return mImageItem.getID();
    }

    public int getID_TBL_CUSTOMEROfTBL_IMAGE() {
        if (mImageItem.getID_TBL_CUSTOMER() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_CUSTOMER = SqlQuery.TBL_IMAGE.ID_TBL_CUSTOMER_OF_IMAGE.getNameCollumn();
            mImageItem.setID_TBL_CUSTOMER(cursor.getInt(cursor.getColumnIndex(ID_TBL_CUSTOMER)));
        }
        return mImageItem.getID_TBL_CUSTOMER();
    }

    public String getNAMEOfTBL_IMAGE() {
        if (TextUtils.isEmpty(mImageItem.getNAME())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_IMAGE.NAME_IMAGE.getNameCollumn();
            mImageItem.setNAME(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mImageItem.getNAME();
    }

    public String getLOCAL_URIOfTBL_IMAGE() {
        if (TextUtils.isEmpty(mImageItem.getLOCAL_URI())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String LOCAL_URI = SqlQuery.TBL_IMAGE.LOCAL_URI.getNameCollumn();
            mImageItem.setLOCAL_URI(cursor.getString(cursor.getColumnIndex(LOCAL_URI)));
        }
        return mImageItem.getLOCAL_URI();
    }

    public Bitmap getBitmap() {
        if (TextUtils.isEmpty(getLOCAL_URIOfTBL_IMAGE())) {
            mBitmap = null;
            return mBitmap;
        }

        //get bitmap tu URI
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        mBitmap = BitmapFactory.decodeFile(getLOCAL_URIOfTBL_IMAGE(), options);
        return mBitmap;
    }

    public double getOLD_INDEXOfTBL_CUSTOMER() {
        if (mCustomerItem.getOldIndex() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String OLD_INDEX = SqlQuery.TBL_CUSTOMER.OLD_INDEX.getNameCollumn();
            mCustomerItem.setOldIndex(cursor.getInt(cursor.getColumnIndex(OLD_INDEX)));
        }
        return mCustomerItem.getOldIndex();
    }

    public double getNEW_INDEXOfTBL_CUSTOMER() {
        if (mCustomerItem.getNewIndex() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NEW_INDEX = SqlQuery.TBL_CUSTOMER.NEW_INDEX.getNameCollumn();
            mCustomerItem.setNewIndex(cursor.getInt(cursor.getColumnIndex(NEW_INDEX)));
        }
        return mCustomerItem.getNewIndex();
    }

    public String getCREATE_DAYOfTBL_IMAGE() {
        if (TextUtils.isEmpty(mImageItem.getCREATE_DAY())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String CREATE_DAY = SqlQuery.TBL_IMAGE.CREATE_DAY.getNameCollumn();
            mImageItem.setCREATE_DAY(cursor.getString(cursor.getColumnIndex(CREATE_DAY)));
        }
        return mImageItem.getCREATE_DAY();
    }

    //endregion


    //region CUSTOMER
    public int getIDOfTBL_CUSTOMER() {
        if (mCustomerItem.getID() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_CUSTOMER_MAIN = SqlQuery.TBL_CUSTOMER.ID_TBL_CUSTOMER.getNameCollumn();
            mCustomerItem.setID(cursor.getInt(cursor.getColumnIndex(ID_TBL_CUSTOMER_MAIN)));
        }
        return mCustomerItem.getID();
    }

    public int getID_TBL_BOOKOfTBL_CUSTOMER() {
        if (mCustomerItem.getIDBook() == 0) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String ID_TBL_BOOK = SqlQuery.TBL_CUSTOMER.ID_TBL_BOOK_OF_CUSTOMER.getNameCollumn();
            mCustomerItem.setIDBook(cursor.getInt(cursor.getColumnIndex(ID_TBL_BOOK)));
        }
        return mCustomerItem.getIDBook();
    }

    public String getCustomerNameOfTBL_CUSTOMER() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerName())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String NAME = SqlQuery.TBL_CUSTOMER.NAME_CUSTOMER.getNameCollumn();
            mCustomerItem.setCustomerName(cursor.getString(cursor.getColumnIndex(NAME)));
        }
        return mCustomerItem.getCustomerName();
    }

    public String getCustomerAddressOfTBL_CUSTOMER() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerAddress())) {
            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String CustomerAddress = SqlQuery.TBL_CUSTOMER.ADDRESS_CUSTOMER.getNameCollumn();
            mCustomerItem.setCustomerAddress(cursor.getString(cursor.getColumnIndex(CustomerAddress)));
        }
        return mCustomerItem.getCustomerAddress();
    }

    public void setFocusCustomer(boolean isFocus)
    {
        mCustomerItem.setFocus(isFocus);
    }

    public CustomerItem.STATUS_Customer getStatusCustomerOfTBL_CUSTOMER() {
        if (mCustomerItem.getStatusCustomer() == null) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String STATUS = SqlQuery.TBL_CUSTOMER.STATUS_CUSTOMER.getNameCollumn();
            String data = cursor.getString(cursor.getColumnIndex(STATUS));
            mCustomerItem.setStatusCustomer(CustomerItem.STATUS_Customer.findNameBy(data));
        }
        return mCustomerItem.getStatusCustomer();
    }

    public boolean isFocusOfTBL_CUSTOMER() {
        Cursor cursor = getmCursor();
        cursor.moveToPosition(getmIndex());
        String isFocus = SqlQuery.TBL_CUSTOMER.FOCUS_CUSTOMER.getNameCollumn();
        boolean data = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(isFocus)));
        mCustomerItem.setFocus(data);
        return mCustomerItem.isFocus();
    }

    public CustomerItem getCustomerItemClone() {
        CustomerItem customerItem = new CustomerItem();
        customerItem.setID(this.getIDOfTBL_CUSTOMER());
        customerItem.setIDBook(getID_TBL_BOOKOfTBL_CUSTOMER());
        customerItem.setCustomerName(getCustomerNameOfTBL_CUSTOMER());
        customerItem.setCustomerAddress(getCustomerAddressOfTBL_CUSTOMER());
        customerItem.setStatusCustomer(getStatusCustomerOfTBL_CUSTOMER());
        customerItem.setFocus(isFocusOfTBL_CUSTOMER());
        return customerItem;
    }

    public ImageItem getImageItemClone() {
        ImageItem customerItem = new ImageItem();

        customerItem.setID(this.getIDOfTBL_IMAGE());
        customerItem.setID_TBL_CUSTOMER(getIDOfTBL_CUSTOMER());
        customerItem.setLOCAL_URI(getLOCAL_URIOfTBL_IMAGE());
        customerItem.setCREATE_DAY(getCREATE_DAYOfTBL_IMAGE());
        return mImageItem;
    }

    public BookItem getBookItem() {
        return mBookItem;
    }

    public void resetAll() {
        mCustomerItem = new CustomerItem();
        mImageItem = new ImageItem();
        mBookItem = new BookItem();
        mBitmap = null;
    }

    public int getIndexIdOfTBL_CUSTOMER() {
        if (mCustomerItem.getIndexId() == 0) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String IndexId = SqlQuery.TBL_CUSTOMER.IndexId.getNameCollumn();
            mCustomerItem.setIndexId(cursor.getInt(cursor.getColumnIndex(IndexId)));
        }
        return mCustomerItem.getIndexId();
    }

    public String getPointId() {
        if (TextUtils.isEmpty(mCustomerItem.getPointId())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String pointId = SqlQuery.TBL_CUSTOMER.pointId.getNameCollumn();
            mCustomerItem.setPointId(cursor.getString(cursor.getColumnIndex(pointId)));
        }
        return mCustomerItem.getPointId();
    }

    public String getTimeOfUse() {
        if (TextUtils.isEmpty(mCustomerItem.getTimeOfUse())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String timeOfUse = SqlQuery.TBL_CUSTOMER.timeOfUse.getNameCollumn();
            mCustomerItem.setTimeOfUse(cursor.getString(cursor.getColumnIndex(timeOfUse)));
        }
        return mCustomerItem.getTimeOfUse();
    }

    public double getCoefficient() {
        if (mCustomerItem.getCoefficient() == 0.0d) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String coefficient = SqlQuery.TBL_CUSTOMER.coefficient.getNameCollumn();
            mCustomerItem.setCoefficient(cursor.getDouble(cursor.getColumnIndex(coefficient)));
        }
        return mCustomerItem.getCoefficient();
    }

    public String getElectricityMeterId() {
        if (TextUtils.isEmpty(mCustomerItem.getElectricityMeterId())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String electricityMeterId = SqlQuery.TBL_CUSTOMER.electricityMeterId.getNameCollumn();
            mCustomerItem.setElectricityMeterId(cursor.getString(cursor.getColumnIndex(electricityMeterId)));
        }
        return mCustomerItem.getElectricityMeterId();
    }

    public int getTerm() {
        if (mCustomerItem.getTerm() == 0) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String term = SqlQuery.TBL_CUSTOMER.term.getNameCollumn();
            mCustomerItem.setTerm(cursor.getInt(cursor.getColumnIndex(term)));
        }
        return mCustomerItem.getTerm();
    }
    public int getMonth() {
        if (mCustomerItem.getMonth() == 0) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String month = SqlQuery.TBL_CUSTOMER.month.getNameCollumn();
            mCustomerItem.setMonth(cursor.getInt(cursor.getColumnIndex(month)));
        }
        return mCustomerItem.getMonth();
    }
    public int getYear() {
        if (mCustomerItem.getYear() == 0) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String year = SqlQuery.TBL_CUSTOMER.year.getNameCollumn();
            mCustomerItem.setYear(cursor.getInt(cursor.getColumnIndex(year)));
        }
        return mCustomerItem.getYear();
    }

    public String getDepartmentId() {
        if (TextUtils.isEmpty(mCustomerItem.getDepartmentId())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String departmentId = SqlQuery.TBL_CUSTOMER.departmentId.getNameCollumn();
            mCustomerItem.setDepartmentId(cursor.getString(cursor.getColumnIndex(departmentId)));
        }
        return mCustomerItem.getDepartmentId();
    }


    public String getIndexType() {
        if (TextUtils.isEmpty(mCustomerItem.getIndexType())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String indexType = SqlQuery.TBL_CUSTOMER.indexType.getNameCollumn();
            mCustomerItem.setIndexType(cursor.getString(cursor.getColumnIndex(indexType)));
        }
        return mCustomerItem.getIndexType();
    }

    public String getStartDate() {
        if (TextUtils.isEmpty(mCustomerItem.getStartDate())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String startDate = SqlQuery.TBL_CUSTOMER.startDate.getNameCollumn();
            mCustomerItem.setStartDate(cursor.getString(cursor.getColumnIndex(startDate)));
        }
        return mCustomerItem.getStartDate();
    }

    public String getEndDate() {
        if (TextUtils.isEmpty(mCustomerItem.getEndDate())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String endDate = SqlQuery.TBL_CUSTOMER.endDate.getNameCollumn();
            mCustomerItem.setEndDate(cursor.getString(cursor.getColumnIndex(endDate)));
        }
        return mCustomerItem.getEndDate();
    }

    public String getCustomerId() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerId())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String customerId = SqlQuery.TBL_CUSTOMER.customerId.getNameCollumn();
            mCustomerItem.setCustomerId(cursor.getString(cursor.getColumnIndex(customerId)));
        }
        return mCustomerItem.getCustomerId();
    }

    public String getCustomerCode() {
        if (TextUtils.isEmpty(mCustomerItem.getCustomerCode())) {

            Cursor cursor = getmCursor();
            cursor.moveToPosition(getmIndex());
            String customerCode = SqlQuery.TBL_CUSTOMER.customerCode.getNameCollumn();
            mCustomerItem.setCustomerCode(cursor.getString(cursor.getColumnIndex(customerCode)));
        }
        return mCustomerItem.getCustomerCode();
    }


    //endregion
}
