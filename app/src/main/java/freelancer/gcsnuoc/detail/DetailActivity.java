package freelancer.gcsnuoc.detail;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.SqlDAO;
import freelancer.gcsnuoc.entities.CustomerItem;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.entities.ImageItem;
import freelancer.gcsnuoc.utils.Common;
import freelancer.gcsnuoc.utils.zoomiamgeview.ImageViewTouch;

import static freelancer.gcsnuoc.utils.Log.getInstance;

public class DetailActivity extends BaseActivity {

    private static final String TAG = "DetailActivity";
    private static final String MANHANVIEN1 = "MANHANVIEN1";
    private SQLiteDatabase mDatabase;
    private SqlDAO mSqlDAO;
    private static boolean isLoadedFolder = false;
    private Common.TRIGGER_NEED_ALLOW_PERMISSION mTrigger;
    private Bundle savedInstanceState;
    private TextView mTvNameEmp;
    private TextView mTvAndressEmp;
    private TextView mTvPerior;
    private ImageView mImageView;
    private BottomBar mBottomBar;
    private FloatingActionButton mFabCapture;
    private TextView mTvInfoBill;
    private TextView mTvOldIndex;
    private TextView mTvNewIndex;
    private RecyclerView mRvCus;
    /*ID_BOOK*/
    private int ID_BOOK;
    private List<DetailProxy> mData = new ArrayList<>();
    private CustomerAdapter customerAdapter;
    private int mPosFocus;
    private String timeFileCaptureImage;
    private Bitmap bitmapImageTemp;
    private boolean flagChangeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        super.hideBar();
        this.savedInstanceState = savedInstanceState;

        if (Common.isNeedPermission(this)) {
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_CREATE;
            return;
        }

        doTaskOnCreate();
    }


    @Override
    protected void onResume() {
        super.onResume();

        //check permission
        if (Common.isNeedPermission(this)) {
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_RESUME;
            return;
        }

        doTaskOnResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Common.REQUEST_CODE_PERMISSION:
                //check permission again
                if (Common.isNeedPermission(this))
                    return;

                switch (mTrigger) {
                    case NONE:
                        break;
                    case ON_CREATE:
                        doTaskOnCreate();
                        break;
                    case ON_RESUME:
                        doTaskOnResume();
                        break;
                }

                mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;
        }
    }

    @Override
    public void doTaskOnResume() {
        try {
            refreshData();

            //filter mData
//            mDetailActivitySharePref = (DetailActivitySharePref) SharePrefManager.getInstance().getSharePref(DetailActivitySharePref.class);
//            filterData(mTYPEFilter, String.valueOf(bookActivitySharePref.isFilteringBottomMenu));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshData() {
        //fill mData
        customerAdapter.updateList(mData);
        mRvCus.postInvalidate();
    }

    @Override
    public void doTaskOnCreate() {
        getInstance().setIsModeDebug(true);

        try {
            //get mData intent
            ID_BOOK = getIntent().getExtras().getInt(Common.INTENT_KEY_ID_BOOK);

            //setup file debug
            init();
            handleListener();
            setAction(savedInstanceState);
        } catch (Exception e) {
            try {
                getInstance().loge(DetailActivity.class, e.getMessage());
            } catch (Exception e1) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void init() throws Exception {
        mTvNameEmp = (TextView) findViewById(R.id.ac_detail_name_emp);
        mTvAndressEmp = (TextView) findViewById(R.id.ac_detail_address_emp);
        mTvPerior = (TextView) findViewById(R.id.ac_detail_tv_text_perior);
        mImageView = (ImageView) findViewById(R.id.ac_detail_iv_image);
        mBottomBar = (BottomBar) findViewById(R.id.ac_detail_bottom_menu);
        mFabCapture = (FloatingActionButton) findViewById(R.id.ac_detail_fab_capture);

        mTvInfoBill = (TextView) findViewById(R.id.ac_detail_tv_info_bill);
        mTvOldIndex = (TextView) findViewById(R.id.ac_detail_tv_old_index);
        mTvNewIndex = (TextView) findViewById(R.id.ac_detail_et_new_index);
        mRvCus = (RecyclerView) findViewById(R.id.ac_detail_rv_customer);

        mDatabase = SqlConnect.getInstance(this).open();
        mSqlDAO = new SqlDAO(mDatabase, this);
        //hiển thị folder trên sdcard
        if (!isLoadedFolder) {
            Common.showFolder(this);
            isLoadedFolder = !isLoadedFolder;
        }

    }

    public void setBitmap(Bitmap bitmap) {
        bitmapImageTemp = bitmap;
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        {
            super.onActivityResult(requestCode, resultCode, data);
            try {
                if (requestCode == Common.INTENT_REQUEST_KEY_CAMERA && resultCode == RESULT_OK) {
                    //getData
                    DetailProxy detailProxy = this.mData.get(mPosFocus);
                    int ID_BOOK = detailProxy.getID_TBL_BOOKOfTBL_CUSTOMER();
                    int ID_CUSTOMER = detailProxy.getID_TBL_CUSTOMEROfTBL_IMAGE();
                    String PERIOD = detailProxy.getPeriodOfTBL_BOOK();
                    String TEN_ANH = Common.getImageName(PERIOD, MANHANVIEN1, String.valueOf(ID_BOOK), String.valueOf(ID_CUSTOMER), timeFileCaptureImage);
                    String pathURICapturedAnh = Common.getRecordDirectoryFolder("PHOTOS");

                    //scale image
                    if (TextUtils.isEmpty(pathURICapturedAnh))
                        return;

                    Common.scaleImage(pathURICapturedAnh + "/" + TEN_ANH, this);

                    //get bitmap tu URI
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    bitmapImageTemp = BitmapFactory.decodeFile(pathURICapturedAnh, options);
                    //set image and set flag is captured again image
                    mImageView.setImageBitmap(bitmapImageTemp);
                    flagChangeData = true;


                    //holder a instance temp of detailProxy
                    CustomerItem customerItemOld = detailProxy.getCustomerItemClone();
                    ImageItem imageItemOld = detailProxy.getImageItemClone();

//                CustomerItem customerItemNew = (CustomerItem) customerItemOld.clone();
//                ImageItem imageItemNew = (ImageItem) imageItemOld.clone();

                    //update Data
//                private int ID;
//                private int ID_TBL_CUSTOMER;
//                private String NAME;
//                private String mLOCAL_URI;
//                private int OLD_INDEX;
//                private int NEW_INDEX;
//                private String CREATE_DAY;


//                imageItemNew.setNAME(TEN_ANH);
//                imageItemNew.setLOCAL_URI(pathURICapturedAnh);
//                imageItemNew.setNEW_INDEX(0);
//                imageItemNew.setCREATE_DAY(timeFileCaptureImage);
//                imageItemNew.setID((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewB.get(indexTuTi).anhTuTi));

//                case IMAGE_MACH_NHI_THU_TUTI:
//                    if (maBdongTuTi == MA_BDONG.B) {
//                        tutiListViewB.get(indexTuTi).setBitmap(typeImage, bitmap);
//                        tutiListViewB.get(indexTuTi).refreshView(tutiListViewB.get(indexTuTi).ivAnhNhiThu);
//                        tutiListViewB.get(indexTuTi).flagChangeData = true;
//
//                        if (tutiListViewB.get(indexTuTi).anhNhiThu != null)
//                            TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewB.get(indexTuTi).anhNhiThu.clone();
//                        else
//                            tutiListViewB.get(indexTuTi).anhNhiThu = new TABLE_ANH_HIENTRUONG();
//
//
//                        tutiListViewB.get(indexTuTi).anhNhiThu.setID_CHITIET_CTO(tableChitietCtoB.getID_CHITIET_CTO());
//                        tutiListViewB.get(indexTuTi).anhNhiThu.setCREATE_DAY(timeFileCaptureAnhNhiThuTuTi);
//                        tutiListViewB.get(indexTuTi).anhNhiThu.setMA_NVIEN(onIDataCommom.getMaNVien());
//                        tutiListViewB.get(indexTuTi).anhNhiThu.setTYPE(IMAGE_MACH_NHI_THU_TUTI.code);
//                        tutiListViewB.get(indexTuTi).anhNhiThu.setTEN_ANH(TEN_ANH);
//                        tutiListViewB.get(indexTuTi).anhNhiThu.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewB.get(indexTuTi).anhNhiThu));
////
//                    } else {
//                        tutiListViewE.get(indexTuTi).setBitmap(typeImage, bitmap);
//                        tutiListViewE.get(indexTuTi).refreshView(tutiListViewE.get(indexTuTi).ivAnhNhiThu);
//                        tutiListViewE.get(indexTuTi).flagChangeData = true;
//
//                        if (tutiListViewE.get(indexTuTi).anhNhiThu != null)
//                            TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewE.get(indexTuTi).anhNhiThu.clone();
//                        else
//                            tutiListViewE.get(indexTuTi).anhNhiThu = new TABLE_ANH_HIENTRUONG();
//
//                        tutiListViewE.get(indexTuTi).anhNhiThu.setID_CHITIET_CTO(tableChitietCtoE.getID_CHITIET_CTO());
//                        tutiListViewE.get(indexTuTi).anhNhiThu.setCREATE_DAY(timeFileCaptureAnhNhiThuTuTi);
//                        tutiListViewE.get(indexTuTi).anhNhiThu.setMA_NVIEN(onIDataCommom.getMaNVien());
//                        tutiListViewE.get(indexTuTi).anhNhiThu.setTYPE(IMAGE_MACH_NHI_THU_TUTI.code);
//                        tutiListViewE.get(indexTuTi).anhNhiThu.setTEN_ANH(TEN_ANH);
//                        tutiListViewE.get(indexTuTi).anhNhiThu.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewE.get(indexTuTi).anhNhiThu));
//
//                    }
//                    break;
//                case IMAGE_NIEM_PHONG_TUTI:
//                    if (maBdongTuTi == MA_BDONG.B) {
//                        tutiListViewB.get(indexTuTi).setBitmap(typeImage, bitmap);
//                        tutiListViewB.get(indexTuTi).refreshView(tutiListViewB.get(indexTuTi).ivAnhNiemPhong);
//                        tutiListViewB.get(indexTuTi).flagChangeData = true;
//
//                        if (tutiListViewB.get(indexTuTi).anhNiemPhong != null)
//                            TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewB.get(indexTuTi).anhNiemPhong.clone();
//                        else
//                            tutiListViewB.get(indexTuTi).anhNiemPhong = new TABLE_ANH_HIENTRUONG();
//
//                        tutiListViewB.get(indexTuTi).anhNiemPhong.setID_CHITIET_CTO(tableChitietCtoB.getID_CHITIET_CTO());
//                        tutiListViewB.get(indexTuTi).anhNiemPhong.setCREATE_DAY(timeFileCaptureAnhNiemPhongTuTi);
//                        tutiListViewB.get(indexTuTi).anhNiemPhong.setMA_NVIEN(onIDataCommom.getMaNVien());
//                        tutiListViewB.get(indexTuTi).anhNiemPhong.setTYPE(IMAGE_NIEM_PHONG_TUTI.code);
//                        tutiListViewB.get(indexTuTi).anhNiemPhong.setTEN_ANH(TEN_ANH);
//                        tutiListViewB.get(indexTuTi).anhNiemPhong.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewB.get(indexTuTi).anhNiemPhong));
////
//                    } else {
//                        tutiListViewE.get(indexTuTi).setBitmap(typeImage, bitmap);
//                        tutiListViewE.get(indexTuTi).refreshView(tutiListViewE.get(indexTuTi).ivAnhNiemPhong);
//                        tutiListViewE.get(indexTuTi).flagChangeData = true;
//
//                        if (tutiListViewE.get(indexTuTi).anhNiemPhong != null)
//                            TABLE_ANH_HIENTRUONGOld = (TABLE_ANH_HIENTRUONG) tutiListViewE.get(indexTuTi).anhNiemPhong.clone();
//                        else
//                            tutiListViewE.get(indexTuTi).anhNiemPhong = new TABLE_ANH_HIENTRUONG();
//
//                        tutiListViewE.get(indexTuTi).anhNiemPhong.setID_CHITIET_CTO(tableChitietCtoE.getID_CHITIET_CTO());
//                        tutiListViewE.get(indexTuTi).anhNiemPhong.setCREATE_DAY(timeFileCaptureAnhNiemPhongTuTi);
//                        tutiListViewE.get(indexTuTi).anhNiemPhong.setMA_NVIEN(onIDataCommom.getMaNVien());
//                        tutiListViewE.get(indexTuTi).anhNiemPhong.setTYPE(IMAGE_NIEM_PHONG_TUTI.code);
//                        tutiListViewE.get(indexTuTi).anhNiemPhong.setTEN_ANH(TEN_ANH);
//                        tutiListViewE.get(indexTuTi).anhNiemPhong.setID_TABLE_ANH_HIENTRUONG((int) mSqlDAO.updateORInsertRows(TABLE_ANH_HIENTRUONG.class, TABLE_ANH_HIENTRUONGOld, tutiListViewE.get(indexTuTi).anhNiemPhong));
//
//                    }
//                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void handleListener() throws Exception {
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    zoomImage(getImage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        mFabCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void captureImage() throws IOException {
        DetailProxy detailProxy = mData.get(mPosFocus);
        timeFileCaptureImage = Common.getDateTimeNow(Common.DATE_TIME_TYPE.type12);
        int ID_BOOK = detailProxy.getID_TBL_BOOKOfTBL_CUSTOMER();
        int ID_CUSTOMER = detailProxy.getID_TBL_CUSTOMEROfTBL_IMAGE();
        String PERIOD = detailProxy.getPeriodOfTBL_BOOK();
        String TEN_ANH = detailProxy.getNAMEOfTBL_IMAGE();

        String fileName = Common.getRecordDirectoryFolder("PHOTOS")
                + "/"
                + Common.getImageName(PERIOD, MANHANVIEN1, String.valueOf(ID_BOOK), String.valueOf(ID_CUSTOMER), timeFileCaptureImage);
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();


        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, Common.INTENT_REQUEST_KEY_CAMERA);
    }

    private Bitmap getImage() {
        DetailProxy detailProxy = mData.get(mPosFocus);
        if (TextUtils.isEmpty(detailProxy.getLOCAL_URIOfTBL_IMAGE()))
            return null;

        String pathURICapturedAnh = Common.getRecordDirectoryFolder("PHOTOS");
        //get bitmap tu URI
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(pathURICapturedAnh, options);
        return bitmap;
    }

    private void zoomImage(Bitmap bmImage) throws Exception {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_image_zoom);
        dialog.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);

        final ImageViewTouch ivtImage = (ImageViewTouch) dialog.findViewById(R.id.dialog_image_zoom_image);

        ivtImage.setImageBitmapReset(bmImage, 0, true);

        dialog.show();
    }

    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
        //load mData
        loadDataDetail();
        //fill mData
        fillDataDetail();
    }

    private void fillDataDetail() {
        //setting recycler view
        customerAdapter = new CustomerAdapter(this, new CustomerAdapter.ICustomerAdapterCallback() {
            @Override
            public void clickItem(int pos) {
                if (pos >= mRvCus.getAdapter().getItemCount())
                    return;

                try {
                    //update database by ID
                    int ID = customerAdapter.getList().get(pos).getIDOfTBL_CUSTOMER();
                    mSqlDAO.updateFocusTBL_CUSTOMER(ID, true);

                    //refresh mData
                    mPosFocus = pos;
                    updateUI();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void scrollToPosition(int pos) {
                mRvCus.scrollToPosition(pos);
                mRvCus.postInvalidate();
            }
        });

        mRvCus.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        final GestureDetector detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // return true if you want to stop the fling
                // return false if you want to allow the fling
                return true;
            }
        });
        mRvCus.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return false;
            }
        });
        customerAdapter.setList(mData);
        mRvCus.setHasFixedSize(true);
        mRvCus.setAdapter(customerAdapter);
    }

    private void updateUI() {
        DetailProxy detailProxy = mData.get(mPosFocus);
        mTvNameEmp.setText(detailProxy.getCustomerNameOfTBL_CUSTOMER());
        mTvAndressEmp.setText(detailProxy.getCustomerAddressOfTBL_CUSTOMER());
        mTvPerior.setText(detailProxy.getPeriodOfTBL_BOOK());
        mImageView.setImageBitmap(getImage());
        mTvInfoBill.setText("Sinh hoat");
        mTvOldIndex.setText(detailProxy.getOLD_INDEXOfTBL_IMAGE() + "");
        mTvNewIndex.setText(detailProxy.getNEW_INDEXOfTBL_IMAGE() + "");
    }


    private void loadDataDetail() throws Exception {
        //dump mData
        //check exist mData
        int rowDataTBL_CUSTOMER = 0;
        rowDataTBL_CUSTOMER = mSqlDAO.getNumberRowTBL_CUSTOMER();
        if (rowDataTBL_CUSTOMER == 0) {
            dumpData();
        }

        mData = mSqlDAO.getSelectAllDetailProxy();
    }

    private void dumpData() throws Exception {
        //dumpData
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 1", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 2", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 3", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 4", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 5", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 6", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_BOOK, "CustomerName 7", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
    }
}
