package freelancer.gcsnuoc.detail;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.app.GCSApplication;
import freelancer.gcsnuoc.bookmanager.BookManagerActivity;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.SqlDAO;
import freelancer.gcsnuoc.entities.CustomerItem;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.entities.ImageItem;
import freelancer.gcsnuoc.entities.SettingObject;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.utils.Common;
import freelancer.gcsnuoc.utils.zoomiamgeview.ImageViewTouch;

import static freelancer.gcsnuoc.bookmanager.BookManagerActivity.TYPE_FILTER.FILTER_BY_SEARCH;
import static freelancer.gcsnuoc.bookmanager.BookManagerActivity.TYPE_FILTER.FILTER_BY_SL;
import static freelancer.gcsnuoc.bookmanager.BookManagerActivity.trimChildMargins;
import static freelancer.gcsnuoc.utils.Common.*;
import static freelancer.gcsnuoc.utils.Common.DATE_TIME_TYPE.*;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_DETAIL_IS_FILTER_BOTTOM;
import static freelancer.gcsnuoc.utils.Common.PREF_DETAIL;
import static freelancer.gcsnuoc.utils.Common.REQUEST_CODE_PERMISSION;
import static freelancer.gcsnuoc.utils.Log.getInstance;

public class DetailActivity extends BaseActivity {
    private static final String TAG = "DetailActivity";
    private SQLiteDatabase mDatabase;
    private SqlDAO mSqlDAO;
    private static boolean isLoadedFolder = false;
    private TRIGGER_NEED_ALLOW_PERMISSION mTrigger;
    private Bundle savedInstanceState;
    private TextView mTvNameEmp;
    private TextView mTvAndressEmp;
    private TextView mTvPerior;
    private ImageView mImageView;
    private BottomBar mBottomBar;
    private FloatingActionButton mFabCapture;
    private TextView mTvCusCode;
    private TextView mTvInfoBill;
    private TextView mTvOldIndex;
    private EditText mEtNewIndex;
    private RecyclerView mRvCus;
    private RecyclerView mRvCus2;
    private RelativeLayout mRlDetail;
    private View mVListCustomer;
    private TextView mTvWarning;
    private Button mBtnSave;
    private RelativeLayout mRlSluong;
    private TextView mTvSluong;
    private TextView mTvIsQuayVong;
    /*ID_BOOK*/
    private int ID_TBL_BOOK_OF_CUSTOMER;
    private List<DetailProxy> mData = new ArrayList<>();
    private List<DetailProxy> mDataSearch = new ArrayList<>();
    private CustomerAdapter customerAdapter;
    private Customer2Adapter customerAdapter2;
    private int ID_TBL_CUSTOMER_Focus;
    private String timeFileCaptureImage;
    private Bitmap bitmapImageTemp;
    private boolean flagChangeData;
    private SharePrefManager mPrefManager;
    private boolean isFilteringBottomMenu;
    private boolean mIsShowInCludeList;
    private static Bitmap icon;
    private SearchView searchView;
    private SettingObject settingObject;
    private String MA_NVIEN;
    private String USER_NAME;
    private String PASS;
    private boolean isSearching;
    private int lengthTextSearch;
    private String filePathTemp = "";
    private boolean isClickApadater2;
    public static HashMap<Integer, Integer> mIntegerIntegerHashMap = new HashMap<>();

    private boolean isClickMenuSearch;
    private Spinner spnCategory;
    private List<String> listGroup = new ArrayList<>();
    private List<Double> listGroupKey = new ArrayList<>();
    private List<Double> listGroupDouble = new ArrayList<>();
    private DetailProxy mDetailProxyFocusLast;
    private MenuItem filterSlItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        super.hideBar();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ac_detail_toolbar);
        myToolbar.setTitle("Ghi chỉ số");
        spnCategory = (Spinner) findViewById(R.id.ac_detail_toolbar_spnCategory);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.savedInstanceState = savedInstanceState;

        if (icon == null)
            icon = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ic_photo_default);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissionsNeedGrant = needPermission(this);
            if (permissionsNeedGrant.length != 0) {
                mTrigger = TRIGGER_NEED_ALLOW_PERMISSION.ON_CREATE;
                requestPermissions(permissionsNeedGrant, REQUEST_CODE_PERMISSION);
                return;
            }
        }

        doTaskOnCreate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ac_detail_actionbar_menu, menu);

        filterSlItem = menu.findItem(R.id.ac_detail_menu_filter_sl);

        MenuItem menuItem = menu.findItem(R.id.ac_detail_menu_search);
        searchView = (SearchView) menuItem.getActionView();
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(ContextCompat.getColor(this, R.color.rowBookColorNomarl));
        trimChildMargins(searchView);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spnCategory.setAdapter(null);
                spnCategory.postInvalidate();
                isClickMenuSearch = true;
                mFabCapture.setVisibility(View.GONE);
                mBottomBar.setDefaultTabPosition(2);
                mBottomBar.selectTabAtPosition(2);
                mBottomBar.postInvalidate();
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                spnCategory.setAdapter(null);
                spnCategory.postInvalidate();
                isClickMenuSearch = false;
                mBottomBar.post(new Runnable() {
                    @Override
                    public void run() {
                        mFabCapture.setVisibility(View.VISIBLE);
                        mBottomBar.setDefaultTabPosition(0);
                        mBottomBar.selectTabAtPosition(0);
                        mBottomBar.postInvalidate();
                    }
                });
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    if (newText.length() < lengthTextSearch) {
                        //restore data
                        mData.clear();
                        mData.addAll(mDataSearch);
                    }

                    lengthTextSearch = newText.length();
                    if (!isSearching && lengthTextSearch != 0) {
                        //first time search
                        isSearching = true;
                        mDataSearch.clear();
                        mDataSearch.addAll(mData);
                    } else {
                        isSearching = false;
                    }

                    if (lengthTextSearch == 0 && isClickApadater2)
                        showIncludeListCusView(false);
                    else
                        showIncludeListCusView(true);

                    fillDataDetail();

                    if (lengthTextSearch == 0 && isClickApadater2) {

                    } else
                        filterData(FILTER_BY_SEARCH, newText);
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mFabCapture.setVisibility(View.GONE);
                            mFabCapture.postInvalidate();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailActivity.this, "Gặp vấn đề. Vui lòng thử tìm kiếm lại!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });

        filterSlItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showDialogGroupFilterSanLuong();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void showDialogGroupFilterSanLuong() {
        listGroup.clear();
        listGroupDouble.clear();
        listGroupKey.clear();
        List<DetailProxy> dataSearch = null;
        try {
            dataSearch = mSqlDAO.getSelectAllDetailProxyWRITED_UPLOADED(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DetailActivity.this, "Gặp vấn đề khi gom nhóm KH theo sản lượng!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (DetailProxy detailProxy : dataSearch
        ) {
            listGroupDouble.add(tinhSanLuong(detailProxy, null));
        }

        if (listGroupDouble.size() == 0) {
            Toast.makeText(this, "Hiện tại chưa có khách hàng nào có sản lượng", Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.sort(listGroupDouble);
        Collections.reverse(listGroupDouble);

        int count = 1;
        double value = listGroupDouble.get(0);
        for (int i = 1; i < listGroupDouble.size(); i++) {
            if (value == listGroupDouble.get(i)) {
                count++;
                if (i == listGroupDouble.size() - 1) {
                    listGroup.add("Mức SL: " + value + " có [" + count + "]");
                    listGroupKey.add(value);
                } else
                    continue;
            } else {
                listGroup.add("Mức SL: " + value + " có [" + count + "]");
                listGroupKey.add(value);
                value = listGroupDouble.get(i);
                count = 1;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.spinner_item, listGroup);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setClickable(false);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mFabCapture.setVisibility(View.GONE);
                    mBottomBar.setDefaultTabPosition(2);
                    mBottomBar.selectTabAtPosition(2);
                    mBottomBar.postInvalidate();
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                filterData(FILTER_BY_SL, listGroupKey.get(i).toString());
                            } catch (Exception e) {
                                e.printStackTrace();
                                e.printStackTrace();
                                Toast.makeText(DetailActivity.this, "Gặp vấn đề khi tìm kiếm theo sản lượng!\n " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spnCategory.performClick();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIntegerIntegerHashMap.clear();
    }

    private void filterData(BookManagerActivity.TYPE_FILTER typeFilter, String dataFiltering) throws Exception {
        List<DetailProxy> dataFilter = new ArrayList<>();
        switch (typeFilter) {
            case FILTER_BY_BOTTOM_MENU:
                boolean isFilter = Boolean.parseBoolean(dataFiltering);
                if (isFilter) {
                    for (DetailProxy detailProxy : customerAdapter.getList()) {
                        if (detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.NON_WRITING) {
                            dataFilter.add(detailProxy);
                        }
                    }
                } else {
                    dataFilter = mData;
                }
                break;

            case FILTER_BY_SEARCH:
                for (DetailProxy detailProxy : customerAdapter.getList()) {

                    //search by name book
                    List<Object> objects = getKeysFromValue(DetailActivity.mIntegerIntegerHashMap, detailProxy.getIDOfTBL_CUSTOMER());
                    if (objects.size() != 1)
                        return;
                    int indexReal = (int) objects.get(0);

                    if (removeAccent(detailProxy.getCustomerNameOfTBL_CUSTOMER().toLowerCase()).contains(dataFiltering)
                            || String.valueOf(indexReal).contains(dataFiltering))
                        dataFilter.add(detailProxy);
                }
                break;

            case NONE_FILTER:
                dataFilter = mData;
                break;

            case FILTER_BY_SL:
                dataFilter = mSqlDAO.getSelectAllDetailProxyWRITED_UPLOADEDBySL(ID_TBL_BOOK_OF_CUSTOMER, dataFiltering, MA_NVIEN);
                break;
        }

        if (dataFilter.size() > 0) {
            refocusItem(0, dataFilter.get(0).getIDOfTBL_CUSTOMER());
            //update database by ID
//            mSqlDAO.updateResetFocusTBL_CUSTOMER(MA_NVIEN, ID_TBL_BOOK_OF_CUSTOMER);
//            mSqlDAO.updateFocusTBL_CUSTOMER(dataFilter.get(0).getIDOfTBL_CUSTOMER(), true, MA_NVIEN);
            dataFilter.get(0).setFocusCustomer(true);
            updateUI(dataFilter.get(0).getIDOfTBL_CUSTOMER());
        }

        customerAdapter.updateList(dataFilter);
        customerAdapter2.updateList(dataFilter);
        mRvCus.scrollToPosition(0);
        mRvCus2.scrollToPosition(0);
        mRvCus.invalidate();
        mRvCus2.invalidate();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //check permission
//        String[] permissionsNeedGrant = needPermission(this);
//        if (permissionsNeedGrant.length == 0 && mTrigger == TRIGGER_NEED_ALLOW_PERMISSION.NONE) {
//            mTrigger = TRIGGER_NEED_ALLOW_PERMISSION.ON_RESUME;
//            return;
//        }
//
//        doTaskOnResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String[] permissionsNeedGrant = needPermission(this);
        if (permissionsNeedGrant.length != 0) {
            Toast.makeText(this, "Ứng dụng cần cấp quyền đầy đủ!", Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsNeedGrant, REQUEST_CODE_PERMISSION);
            }
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                switch (mTrigger) {
                    case NONE:
                        break;
                    case ON_CREATE:
                        doTaskOnCreate();
                        doTaskOnResume();
                        break;
                    case ON_RESUME:
                        doTaskOnResume();
                        break;
                }

                mTrigger = TRIGGER_NEED_ALLOW_PERMISSION.NONE;
        }
    }

    @Override
    public void doTaskOnResume() {
        try {
//            ID_TBL_CUSTOMER_Focus = findPosFocusInList();
//            refreshData(ID_TBL_CUSTOMER_Focus);

            //filter mData
//            mDetailActivitySharePref = (DetailActivitySharePref) SharePrefManager.getInstance().getSharePref(DetailActivitySharePref.class);
//            filterData(mTYPEFilter, String.valueOf(bookActivitySharePref.isFilteringBottomMenu));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(this, "Gặp vấn đề khi chọn sổ!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private int refreshData(int ID_TBL_CUSTOMER_Focus) throws Exception {
        //fill mData
        mData.clear();
        //TODO
        mData = isFilteringBottomMenu ? mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN) : mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
        if (mData.size() == 0)
            return -1;

        int posNow = findPosFocusNow(ID_TBL_CUSTOMER_Focus);
        if (posNow < 0) {
            posNow = 0;
            ID_TBL_CUSTOMER_Focus = mData.get(0).getIDOfTBL_CUSTOMER();
        }

        customerAdapter.updateList(mData);
        customerAdapter2.updateList(mData);
        final int finalPosNow = posNow;
        final int finalID_TBL_CUSTOMER_Focus = ID_TBL_CUSTOMER_Focus;
        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRvCus.scrollToPosition(finalPosNow);
                mRvCus2.scrollToPosition(finalPosNow);
                mRvCus.postInvalidate();
                mRvCus2.postInvalidate();
                try {
                    updateUI(finalID_TBL_CUSTOMER_Focus);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi làm mới dữ liệu! \nNội dung: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return posNow;
    }

    @Override
    public void doTaskOnCreate() {
        getInstance().setIsModeDebug(true);

        try {
            //get mData intent
            if (!TextUtils.isEmpty(Common.USER)) {
                MA_NVIEN = Common.MA_NVIEN;
                USER_NAME = Common.USER;
            }
            ID_TBL_BOOK_OF_CUSTOMER = Common.ID_TBL_BOOK;

            //setup file debug
            init();
            handleListener();
            setAction(savedInstanceState);
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khởi tạo " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void init() throws Exception {
        mTvNameEmp = (TextView) findViewById(R.id.ac_detail_name_emp);
        mTvCusCode = (TextView) findViewById(R.id.ac_book_manager_tv_cus_code);
        mTvAndressEmp = (TextView) findViewById(R.id.ac_detail_address_emp);
        mTvPerior = (TextView) findViewById(R.id.ac_detail_tv_text_perior);
        mImageView = (ImageView) findViewById(R.id.ac_detail_iv_image);
        mBottomBar = (BottomBar) findViewById(R.id.ac_detail_bottom_menu);
        mFabCapture = (FloatingActionButton) findViewById(R.id.ac_detail_fab_capture);

        mTvInfoBill = (TextView) findViewById(R.id.ac_detail_tv_info_bill);
        mTvOldIndex = (TextView) findViewById(R.id.ac_detail_tv_old_index);
        mEtNewIndex = (EditText) findViewById(R.id.ac_detail_et_new_index);
        mRvCus = (RecyclerView) findViewById(R.id.ac_detail_rv_customer);
        mRvCus2 = (RecyclerView) findViewById(R.id.dialog_detail_list_customer_rv_cus);
        mRlDetail = (RelativeLayout) findViewById(R.id.ac_detail_rl4);
        mVListCustomer = (View) findViewById(R.id.ac_detail_include_detail);
        mTvWarning = (TextView) findViewById(R.id.ac_detail_tv_warning);
        mBtnSave = (Button) findViewById(R.id.ac_detail_btn_save_new_index);
        mRlSluong = (RelativeLayout) findViewById(R.id.ac_detail_rl6);
        mTvIsQuayVong = (TextView) findViewById(R.id.ac_detail_tv_isQuaVong);
        mTvSluong = (TextView) findViewById(R.id.ac_detail_tv_sluong);
        mRlSluong.setVisibility(View.GONE);
        showIncludeListCusView(mIsShowInCludeList);
        mDatabase = SqlConnect.getInstance(this).open();
        mSqlDAO = new SqlDAO(mDatabase, this);
        //hiển thị folder trên sdcard
        if (!isLoadedFolder) {
            showFolder(this);
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
                if (requestCode == INTENT_REQUEST_KEY_CAMERA && resultCode == RESULT_OK) {
                    //getData
                    if (isFilteringBottomMenu) {
                        mBottomBar.setDefaultTabPosition(1);
                        mBottomBar.postInvalidate();
                    }

                    int focusNow = findPosFocusNow(ID_TBL_CUSTOMER_Focus);
                    if (mData.size() <= focusNow) {
                        mData.clear();
                        mData = isFilteringBottomMenu ? mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN) : mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
                        if (mData.size() <= focusNow) {
                            Toast.makeText(this, "Gặp vấn đề khi lấy dữ liệu!\n Vui lòng quay lại màn hình chọn sổ và thao tác lại", Toast.LENGTH_SHORT).show();

                            //delete file temp
                            File fileImage = new File(filePathTemp);
                            if (fileImage.isFile()) {
                                fileImage.delete();
                                flagChangeData = false;
                            }

                            return;
                        }
                    }

                    DetailProxy detailProxy = this.mData.get(focusNow);
                    int ID_BOOK = detailProxy.getID_TBL_BOOKOfTBL_CUSTOMER();
                    int ID_CUSTOMER = detailProxy.getIDOfTBL_CUSTOMER();
//                    String PERIOD = detailProxy.getPeriodOfTBL_BOOK();
//                    String PERIOD_Convert = convertDateToDate(PERIOD, sqlite2, type12);
                    int term = detailProxy.getTerm();
                    int month = detailProxy.getMonth();
                    int year = detailProxy.getYear();
                    String PERIOD_Convert = term + "." + month + "." + year;
                    String TEN_ANH = getImageName(PERIOD_Convert, MA_NVIEN, String.valueOf(ID_BOOK), String.valueOf(ID_CUSTOMER), timeFileCaptureImage);
                    String pathURICapturedAnh = getRecordDirectoryFolder("") + "/" + TEN_ANH;

                    //scale image
                    if (!pathURICapturedAnh.equals(filePathTemp)) {
                        Toast.makeText(this, "Gặp vấn đề khi lấy dữ liệu ảnh!\n Vui lòng quay lại màn hình chọn sổ và thao tác lại", Toast.LENGTH_SHORT).show();

                        //delete file temp
                        File fileImage = new File(filePathTemp);
                        if (fileImage.isFile()) {
                            fileImage.delete();
                            flagChangeData = false;
                        }

                        return;
                    }

                    scaleImage(pathURICapturedAnh, this);

                    //get bitmap tu URI
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    bitmapImageTemp = BitmapFactory.decodeFile(pathURICapturedAnh, options);
                    //set image and set flag is captured again image
                    mImageView.setImageBitmap(bitmapImageTemp);


                    //delete old image and insert
                    String LOCAL_URIOfTBL_IMAGE = detailProxy.getLOCAL_URIOfTBL_IMAGE();
                    if (!TextUtils.isEmpty(LOCAL_URIOfTBL_IMAGE)) {
                        File fileImage = new File(LOCAL_URIOfTBL_IMAGE);
                        if (fileImage.isFile()) {
                            fileImage.delete();
                            flagChangeData = true;
                        }
                    }

                    if (flagChangeData) {
                        //update chua ghi
                        mSqlDAO.updateStatusTBL_CUSTOMER(ID_TBL_CUSTOMER_Focus, CustomerItem.STATUS_Customer.NON_WRITING, MA_NVIEN);
                        flagChangeData = false;
                    }

                    int ID_TBL_IMAGE = detailProxy.getIDOfTBL_IMAGE();
                    mSqlDAO.deleteIMAGE(ID_TBL_IMAGE, MA_NVIEN);

                    ImageItem imageItem = new ImageItem();
                    String convertDate = Common.convertDateToDate(timeFileCaptureImage, type13, sqlite2);
                    imageItem.setCREATE_DAY(convertDate);
                    imageItem.setID_TBL_CUSTOMER(ID_TBL_CUSTOMER_Focus);
                    imageItem.setID_TBL_BOOK_OF_IMAGE(detailProxy.getID_TBL_BOOKOfTBL_CUSTOMER());
                    imageItem.setNAME(TEN_ANH);
                    imageItem.setLOCAL_URI(pathURICapturedAnh);
                    mSqlDAO.insertTBL_IMAGE(imageItem, MA_NVIEN);

                    //refreshUI
                    mData.clear();
                    mData = isFilteringBottomMenu ? mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN) : mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);

                    refreshData(ID_TBL_CUSTOMER_Focus);
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEtNewIndex.requestFocus();
                            mEtNewIndex.postInvalidate();
                        }
                    });
                } else {
                    if (!TextUtils.isEmpty(filePathTemp)) {
                        //delete file temp
                        File fileImage = new File(filePathTemp);
                        if (fileImage.isFile()) {
                            fileImage.delete();
                            flagChangeData = false;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Gặp vấn đề khi chụp ảnh! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onActivityResult: " + e.getMessage());
            } finally {
                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mFabCapture.setClickable(true);
                    }
                });
            }
        }
    }

    @Override
    protected void handleListener() throws Exception {
        mEtNewIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtNewIndex.setHint(mEtNewIndex.getText().toString());
                mEtNewIndex.setText("");
            }
        });

        mEtNewIndex.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mDetailProxyFocusLast == null) {
                            Log.d(TAG, "run: mDetailProxyFocusLast = null");
//                            Toast.makeText(DetailActivity.this, "Gặp vấn đề khi lấy dữ liệu khách hàng hiện tại", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mRlSluong.setVisibility(View.VISIBLE);
                        Double s = TextUtils.isEmpty(mEtNewIndex.getText().toString()) ? 0.0d : Double.parseDouble(mEtNewIndex.getText().toString());
                        mTvSluong.setText(String.valueOf(tinhSanLuong(mDetailProxyFocusLast, s)));
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //set menu bottom
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                try {
                    if (customerAdapter == null)
                        return;

                    switch (tabId) {
                        case R.id.ac_detail_nav_bot_detail:
                            showIncludeListCusView(false);
                            isFilteringBottomMenu = false;
                            mPrefManager.getSharePref(PREF_DETAIL, MODE_PRIVATE).edit().putBoolean(KEY_PREF_DETAIL_IS_FILTER_BOTTOM, isFilteringBottomMenu).commit();
                            mData.clear();
                            mData = mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
                            ID_TBL_CUSTOMER_Focus = mData.get(findPosFocusInList()).getIDOfTBL_CUSTOMER();
                            refreshData(ID_TBL_CUSTOMER_Focus);
                            break;
                        case R.id.ac_detail_nav_bot_filter:
                            if (mPrefManager == null)
                                mPrefManager = SharePrefManager.getInstance(DetailActivity.this);
//                            isFilteringBottomMenu = mPrefManager.getSharePref(PREF_DETAIL, MODE_PRIVATE).
//                                    getBoolean(KEY_PREF_DETAIL_IS_FILTER_BOTTOM, false);
                            isFilteringBottomMenu = true;
                            mPrefManager.getSharePref(PREF_DETAIL, MODE_PRIVATE).edit().putBoolean(KEY_PREF_DETAIL_IS_FILTER_BOTTOM, isFilteringBottomMenu).commit();
                            mData.clear();
                            mData = mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
                            filterData(BookManagerActivity.TYPE_FILTER.FILTER_BY_BOTTOM_MENU, String.valueOf(isFilteringBottomMenu));
                            break;

                        case R.id.ac_detail_nav_bot_list:
                            //show include
                            showIncludeListCusView(true);
                            break;
                    }

                } catch (Exception e) {
                    Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DetailProxy detailProxy = mData.get(findPosFocusNow(ID_TBL_CUSTOMER_Focus));
                    String LOCAL_URI = detailProxy.getLOCAL_URIOfTBL_IMAGE();
                    if (TextUtils.isEmpty(LOCAL_URI))
                        return;
                    //get bitmap tu URI
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeFile(LOCAL_URI, options);
                    if (bitmap == null)
                        return;

                    zoomImage(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "onClick: zoomImage" + e.getMessage());
                }
            }
        });

        mFabCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mFabCapture.setClickable(false);
                    captureImage();
                } catch (Exception e) {
                    e.printStackTrace();
                    mFabCapture.setClickable(true);
                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi chụp ảnh!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showIncludeListCusView(boolean isShow) {
        mIsShowInCludeList = isShow;
        if (isShow)
            isClickApadater2 = false;
        mVListCustomer.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mRlDetail.setVisibility(isShow ? View.GONE : View.VISIBLE);
        mFabCapture.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

//    private void showDialogListCustomer() {
//        final Dialog dialogCusList = new Dialog(this);
//        dialogCusList.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogCusList.setContentView(R.layout.dialog_detail_list_customer);
//        dialogCusList.setCanceledOnTouchOutside(true);
//        dialogCusList.getWindow().setLayout(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
//        dialogCusList.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        dialogCusList.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        Window window = dialogCusList.getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//
////        final RecyclerView recyclerView = (RecyclerView) dialogCusList.findViewById(R.id.rv_history_detail);
////        final TextView tvCountThietBi = (TextView) dialogCusList.findViewById(R.id.tv_count_thietbi);
//
//        dialogCusList.show();
//    }

    private void captureImage() throws Exception {
        filePathTemp = "";
        mData.clear();
        mData = isFilteringBottomMenu ? mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN) : mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
//    đsa

        DetailProxy detailProxy = mData.get(findPosFocusInList());
        if (detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.UPLOADED) {
            Toast.makeText(this, "Không cho phép! Chỉ số khách hàng đã được gửi lên máy chủ!", Toast.LENGTH_SHORT).show();
            return;
        }
        timeFileCaptureImage = getDateTimeNow(type13);
        int ID_BOOK = detailProxy.getID_TBL_BOOKOfTBL_CUSTOMER();
        ID_TBL_CUSTOMER_Focus = detailProxy.getIDOfTBL_CUSTOMER();
        int term = detailProxy.getTerm();
        int month = detailProxy.getMonth();
        int year = detailProxy.getYear();
        String PERIOD_Convert = term + "." + month + "." + year;

        filePathTemp = getRecordDirectoryFolder("")
                + "/"
                + getImageName(PERIOD_Convert, MA_NVIEN, String.valueOf(ID_BOOK), String.valueOf(ID_TBL_CUSTOMER_Focus), timeFileCaptureImage);

        File file = new File(filePathTemp);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(cameraIntent, INTENT_REQUEST_KEY_CAMERA);
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
        //init shared preref
        mPrefManager = SharePrefManager.getInstance(this);

        this.checkSharePreference(mPrefManager);
        isFilteringBottomMenu = mPrefManager.getSharePref(PREF_DETAIL, MODE_PRIVATE).
                getBoolean(KEY_PREF_DETAIL_IS_FILTER_BOTTOM, false);
        if (isFilteringBottomMenu) {
            DetailActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mBottomBar.setDefaultTabPosition(1);
                    mBottomBar.postInvalidate();
                }
            });
        }

        //get index
        //TODO khách hàng yêu cầu sắp xếp đồng bộ với index của tab 0
        mData.clear();
//        mData = mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
        mData = mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
        mIntegerIntegerHashMap.clear();
        if (mData.size() != 0) {
            for (int i = 0; i < mData.size(); i++) {
                mIntegerIntegerHashMap.put(i + 1, mData.get(i).getIDOfTBL_CUSTOMER());
            }
        }

        //load mData
        loadDataDetail();

        //fill mData
        fillDataDetail();
    }

    private void checkSharePreference(SharePrefManager prefManager) {
        if (!mPrefManager.checkExistSharePref(PREF_DETAIL)) {
            mPrefManager.addSharePref(PREF_DETAIL, MODE_PRIVATE);
            mPrefManager.getSharePref(PREF_DETAIL, MODE_PRIVATE)
                    .edit()
                    .putBoolean(KEY_PREF_DETAIL_IS_FILTER_BOTTOM, false)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
        } else
            super.onBackPressed();
    }

    private void closeSearchView() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            searchView.onActionViewCollapsed();
        }
    }

    private void fillDataDetail() throws Exception {
        mTvWarning.setVisibility(mData.size() == 0 ? View.VISIBLE : View.GONE);
        mFabCapture.setVisibility(mData.size() == 0 ? View.GONE : View.VISIBLE);
        if (mData.size() == 0)
            return;

        //setting recycler view
        customerAdapter = new CustomerAdapter(this, new CustomerAdapter.ICustomerAdapterCallback() {
            @Override
            public void clickItem(final int pos, int ID_TBL_CUSTOMER) {
                try {
                    ID_TBL_CUSTOMER_Focus = mData.get(pos).getIDOfTBL_CUSTOMER();
                    refocusItem(pos, ID_TBL_CUSTOMER);
//                    mRvCus.postInvalidate();
//                    mRvCus2.postInvalidate();
                    DetailActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeSearchView();
                        }
                    });
                    refreshData(ID_TBL_CUSTOMER_Focus);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRvCus.setLayoutManager(layoutManager1);
        customerAdapter.setList(mData);
        mRvCus.setHasFixedSize(true);
        mRvCus.setAdapter(customerAdapter);
        final PagerSnapHelper snapHelper = new PagerSnapHelper() {
            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                int pos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
                final int finalTargetPosition = pos;
                DetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ID_TBL_CUSTOMER_Focus = mData.get(finalTargetPosition).getIDOfTBL_CUSTOMER();
                            refocusItem(finalTargetPosition, ID_TBL_CUSTOMER_Focus);
                            refreshData(ID_TBL_CUSTOMER_Focus);
                            //Toast.makeText(DetailActivity.this, "pos" + finalTargetPosition, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DetailActivity.this, "Gặp vấn đề khi thực hiện thao tác vuốt danh sách! \nNội dung: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return finalTargetPosition;
            }
        };

        try {
            snapHelper.attachToRecyclerView(mRvCus);
        } catch (IllegalStateException isex) {
            Log.i(TAG, "fillDataDetail: there is already a {@link RecyclerView.OnFlingListener}\n" +
                    "     * attached to the provided {@link RecyclerView}");
        }

        //setting recycler view 2
        customerAdapter2 = new Customer2Adapter(this, new Customer2Adapter.ICustomerAdapterCallback2() {
            @Override
            public void clickItem(int pos, int ID_TBL_CUSTOMER) {
                if (pos >= mRvCus2.getAdapter().getItemCount())
                    return;

                try {
                    spnCategory.setAdapter(null);
                    spnCategory.postInvalidate();
                    //update rv 1, rv2
                    refocusItem(pos, ID_TBL_CUSTOMER);
                    int posNow = findPosFocusNow(ID_TBL_CUSTOMER);
                    mRvCus.scrollToPosition(posNow);
                    mRvCus2.scrollToPosition(posNow);
                    mRvCus.postInvalidate();
                    mRvCus2.postInvalidate();

                    //TODO KH yêu cầu khi tìm kiếm xong click vào khách hàng thì nó về màn hình để gcs luôn
                    mBottomBar.post(new Runnable() {
                        @Override
                        public void run() {
                            mFabCapture.setVisibility(View.VISIBLE);
                            mBottomBar.setDefaultTabPosition(0);
                            mBottomBar.selectTabAtPosition(0);
                            mBottomBar.postInvalidate();
                        }
                    });

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int a = mBottomBar.getCurrentTabPosition();
                            boolean b = mFabCapture.getVisibility() == View.VISIBLE;
                            boolean c = mVListCustomer.getVisibility() == View.VISIBLE;
                            if ((a == 0 && b) || (a == 0 && !c)) {
                                showIncludeListCusView(false);
                                DetailActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideKeyboard(DetailActivity.this);
                                    }
                                });
                            }
                        }
                    }, 1);

                    isClickApadater2 = true;

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRvCus2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        customerAdapter2.setList(mData);
        mRvCus2.setHasFixedSize(true);
        mRvCus2.setAdapter(customerAdapter2);


        int posNow = findPosFocusNow(ID_TBL_CUSTOMER_Focus);
        refocusItem(posNow, mData.get(posNow).getIDOfTBL_CUSTOMER());

//        mRvCus.scrollToPosition(posNow);
//        mRvCus2.scrollToPosition(posNow);
//
//        mRvCus.postInvalidate();
//        mRvCus2.postInvalidate();
    }

    private void refocusItem(int pos, int ID_TBL_CUSTOMER) throws Exception {
        if (pos >= mRvCus.getAdapter().getItemCount())
            return;
        //update database by ID
        mSqlDAO.updateResetFocusTBL_CUSTOMER(MA_NVIEN, ID_TBL_BOOK_OF_CUSTOMER);
        mSqlDAO.updateFocusTBL_CUSTOMER(ID_TBL_CUSTOMER, true, MA_NVIEN);
        //reset
        refreshData(ID_TBL_CUSTOMER);
    }

    private void updateUI(final int varID_TBL_CUSTOMER_Focus) throws Exception {
        int mPos = findPosFocusNow(varID_TBL_CUSTOMER_Focus);
        if(mPos == -1)
            return;
        final DetailProxy detailProxy = mData.get(mPos);
        mTvNameEmp.setText(detailProxy.getCustomerNameOfTBL_CUSTOMER());
        mTvCusCode.setText(detailProxy.getCustomerCode());
        mTvAndressEmp.setText(detailProxy.getCustomerAddressOfTBL_CUSTOMER());
        int term = detailProxy.getTerm();
        int month = detailProxy.getMonth();
        int year = detailProxy.getYear();
        String PERIOD_Convert = term + " - " + month + "/" + year;
        mTvPerior.setText(PERIOD_Convert);

        String LOCAL_URI = detailProxy.getLOCAL_URIOfTBL_IMAGE();
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(LOCAL_URI)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = BitmapFactory.decodeFile(LOCAL_URI, options);
        }
        mImageView.setImageBitmap(bitmap == null ? icon : bitmap);
        mTvInfoBill.setText("");
        mTvOldIndex.setText(detailProxy.getOLD_INDEXOfTBL_CUSTOMER() + "");
        if (detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.UPLOADED)
            mEtNewIndex.setClickable(false);
        else mEtNewIndex.setClickable(true);
        mEtNewIndex.setText(String.valueOf(detailProxy.getNEW_INDEXOfTBL_CUSTOMER()));

        showTvSanLuong(detailProxy);
    }

    private void showTvSanLuong(DetailProxy detailProxy) {
        double sl = tinhSanLuong(detailProxy, null);
        //TODO KH Muốn show sản lượng
        mRlSluong.setVisibility(View.VISIBLE);
        mTvSluong.setText(String.valueOf(sl));
        if (detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.WRITED || detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.UPLOADED)
            mRlSluong.setVisibility(View.VISIBLE);
        else
            mRlSluong.setVisibility(View.GONE);
        boolean isQuaVong = detailProxy.isQuaVongOfTBL_CUSTOMER();
        mTvIsQuayVong.setVisibility(isQuaVong ? View.VISIBLE : View.INVISIBLE);
        mDetailProxyFocusLast = detailProxy;
    }

    private double tinhSanLuong(DetailProxy detailProxy, @Nullable Double newIndex) {
        double old = detailProxy.getOLD_INDEXOfTBL_CUSTOMER();
        if (newIndex == null)
            newIndex = detailProxy.getNEW_INDEXOfTBL_CUSTOMER();
        double co = detailProxy.getCoefficient();
        return (newIndex - old) * co;
    }

    private int findPosFocusNow(int ID_TBL_CUSTOMER_Focus) {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getIDOfTBL_CUSTOMER() == ID_TBL_CUSTOMER_Focus) {
                return i;
            }
        }

        return -1;
    }


    private void loadDataDetail() throws Exception {
        //dump mData
        //check exist mData
        int rowDataTBL_CUSTOMER = 0;
        rowDataTBL_CUSTOMER = mSqlDAO.getNumberRowTBL_CUSTOMER(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
        if (rowDataTBL_CUSTOMER == 0) {
//            if (ID_TBL_BOOK_OF_CUSTOMER != 3)
//                dumpData();
            return;
        }
        //get Focus
        //load first
        mData.clear();
        mData = isFilteringBottomMenu ? mSqlDAO.getSelectAllDetailProxyNOTWrite(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN) : mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);
//        mData = mSqlDAO.getSelectAllDetailProxy(ID_TBL_BOOK_OF_CUSTOMER, MA_NVIEN);

        int posNow = findPosFocusInList();
        if (posNow == -1) {
            //first focus
            ID_TBL_CUSTOMER_Focus = mData.get(0).getIDOfTBL_CUSTOMER();
            //update database by ID
            mSqlDAO.updateResetFocusTBL_CUSTOMER(MA_NVIEN, ID_TBL_BOOK_OF_CUSTOMER);
            mSqlDAO.updateFocusTBL_CUSTOMER(ID_TBL_CUSTOMER_Focus, true, MA_NVIEN);
        } else {
            ID_TBL_CUSTOMER_Focus = mData.get(posNow).getIDOfTBL_CUSTOMER();
        }
    }

    private int findPosFocusInList() {
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).isFocusOfTBL_CUSTOMER()) {
                return i;
            }
        }

        return -1;
    }

//    private void dumpData() throws Exception {
//        //dumpData
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 1", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 2", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 3", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 4", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 5", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 6", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//        mSqlDAO.insertTBL_CUSTOMER(new CustomerItem(ID_TBL_BOOK_OF_CUSTOMER, "CustomerName 7", "Ha dong 1", CustomerItem.STATUS_Customer.NON_WRITING, false));
//    }

    public void clickButtonSave(View view) {
        DetailActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideKeyboard(DetailActivity.this);
            }
        });

        DetailProxy detailProxy = mData.get(findPosFocusNow(ID_TBL_CUSTOMER_Focus));

        if (detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.UPLOADED) {
            Toast.makeText(this, "Không cho phép! Chỉ số khách hàng đã được gửi lên máy chủ!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mEtNewIndex.getText().toString().isEmpty()) {
            Toast.makeText(this, "Chỉ số mới không được phép rỗng!", Toast.LENGTH_SHORT).show();
            return;
        }


        //TODO khách hàng bắt bỏ bỏ phần bắt buộc chụp ảnh
//        String LOCAL_URI = detailProxy.getLOCAL_URIOfTBL_IMAGE();
//        if (TextUtils.isEmpty(LOCAL_URI)) {
//            Toast.makeText(this, "Cần chụp ảnh chỉ số trước khi lưu!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        //get bitmap tu URI
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(LOCAL_URI, options);
//        if (bitmap == null) {
//            Toast.makeText(this, "Cần phải chụp ảnh trước khi lưu thông tin!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        double newIndex = TextUtils.isEmpty(mEtNewIndex.getText().toString()) ? 0.0d : Double.parseDouble(mEtNewIndex.getText().toString());
        double oldIndex = detailProxy.getOLD_INDEXOfTBL_CUSTOMER();
        double result = newIndex - oldIndex;
        //TODO khách hàng yêu cầu  trường hợp công tơ qua vòng
        //TODO lúc cảnh báo thêm 1 nút chọn công tơ qua vòng VÀ CHO LƯU
        //TODO k cần tính sản lượng
        if (result < 0.0d) {
            showDialogWarning_QUAYVONG();
            return;
        }

        //không phải xoay vòng thì reset
        try {
            mSqlDAO.updateIsQuaVongTBL_CUSTOMER(ID_TBL_CUSTOMER_Focus, false, MA_NVIEN);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickButtonSave: updateIsQuaVongTBL_CUSTOMER fail!");
        }
        //warning
        loadSettingData();

        if (settingObject.isMaxNotPercent()) {
            if (result > settingObject.getMax()) {
                showDialogWarningMaximum(settingObject.getMax(), result);
                return;
            }

            saveData(false);

        } else {
            double OLD_INDEX = detailProxy.getOLD_INDEXOfTBL_CUSTOMER();
            double NEW_INDEX = Double.parseDouble(mEtNewIndex.getText().toString());
            double co = detailProxy.getCoefficient();
            double NowQuatity = (NEW_INDEX - OLD_INDEX) * co;

            double OldQuatity = detailProxy.getPrevQuantity();
            if (OldQuatity == 0) {
                saveData(false);
                return;
            }

            double percent = (NowQuatity / OldQuatity) * 100;
            int cal = (settingObject.getPercent());
            if (percent > cal) {
                showDialogWarningPercent((int) percent, cal);
                return;
            }

            saveData(false);

        }


    }

    private void loadSettingData() {
        //dump
        settingObject = new SettingObject();
        SharedPreferences sharedPreferences = mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE);
        settingObject.setURL(sharedPreferences.getString(KEY_PREF_URL, ""));
        settingObject.setPort(sharedPreferences.getInt(KEY_PREF_PORT, 0));
        settingObject.setMaxNotPercent(sharedPreferences.getBoolean(KEY_PREF_IS_MAX_NOT_PERCENT, false));
        settingObject.setMax(sharedPreferences.getInt(KEY_PREF_MAX, 0));
        settingObject.setPercent(sharedPreferences.getInt(KEY_PREF_PERCENT, 0));
    }

    private void showDialogWarningPercent(int result, int cal) {
        IDialog iDialog = new IDialog() {
            @Override
            protected void clickOK() {
                //save
                saveData(false);
            }

            @Override
            protected void clickCancel() {

            }
        }.setTextBtnOK("Tiếp tục ghi").setTextBtnCancel("Hủy thao tác");
        DetailProxy detailProxy = mData.get(findPosFocusNow(ID_TBL_CUSTOMER_Focus));
        super.showDialog(this, "Sản lượng hiện tăng hơn " +
                (result - cal) +
                "% " +
                "so với kỳ trước và đang vượt quá giới hạn cảnh báo " + settingObject.getPercent() + " %.", iDialog);
    }

    private void saveData(boolean isQuaVong) {
        try {
            mSqlDAO.updateStatusTBL_CUSTOMER(ID_TBL_CUSTOMER_Focus, CustomerItem.STATUS_Customer.WRITED, MA_NVIEN);
            mSqlDAO.updateNEW_INDEXOfTBL_CUSTOMER(ID_TBL_CUSTOMER_Focus, Double.parseDouble(mEtNewIndex.getText().toString()), MA_NVIEN);
            DetailProxy detailProxy = mData.get(findPosFocusNow(ID_TBL_CUSTOMER_Focus));
            String LOCAL_URI = detailProxy.getLOCAL_URIOfTBL_IMAGE();
            //TODO khách hàng bắt bỏ bỏ phần bắt buộc chụp ảnh
            if (!TextUtils.isEmpty(LOCAL_URI)) {
                String TEN_KHANG = detailProxy.getCustomerNameOfTBL_CUSTOMER();
                String MA_DDO = detailProxy.getPointcode();
                String CREATE_DAY = Common.convertDateToDate(detailProxy.getCREATE_DAYOfTBL_IMAGE(), sqlite2, type7);
                double OLD_INDEX = detailProxy.getOLD_INDEXOfTBL_CUSTOMER();
                double NEW_INDEX = Double.parseDouble(mEtNewIndex.getText().toString());
                double co = detailProxy.getCoefficient();
                String sanluong = isQuaVong ? "0.0 (Qua vòng)" : "" + (NEW_INDEX - OLD_INDEX) * co;
                Bitmap bitmap = Common.drawTextOnBitmapCongTo(this, LOCAL_URI, "Tên KH: " + TEN_KHANG, "CS mới: " + NEW_INDEX, "CS cũ: " + OLD_INDEX, "Sản lượng: " + sanluong, "Mã KH: " + detailProxy.getCustomerCode(), "Mã Đ.Đo: " + MA_DDO, "Ngày: " + CREATE_DAY);

                File file = new File(LOCAL_URI);
                try (FileOutputStream out = new FileOutputStream(file)) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 45, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }

            double OldQuatity = detailProxy.getPrevQuantity();
            final int posNow = refreshData(ID_TBL_CUSTOMER_Focus);
            boolean isNextOk = false;
            if (posNow >= 0 && posNow < mData.size() - 1) {
                isNextOk = true;

            }
            final boolean finalIsNextOk = isNextOk;
            if (OldQuatity == 0) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isFilteringBottomMenu) {
                            if (finalIsNextOk) {
                                try {
                                    ID_TBL_CUSTOMER_Focus = mData.get(posNow).getIDOfTBL_CUSTOMER();
                                    refocusItem(posNow, ID_TBL_CUSTOMER_Focus);
                                    refreshData(ID_TBL_CUSTOMER_Focus);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi tự động chuyển sang khách hàng kế tiếp \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            Toast.makeText(DetailActivity.this, "Lưu dữ liệu thành công. \nKhách hàng đã được loại khỏi mục LỌC KHÁCH HÀNG CHƯA GHI", Toast.LENGTH_SHORT).show();
                        } else {
                            if (finalIsNextOk) {
                                try {
                                    ID_TBL_CUSTOMER_Focus = mData.get(posNow + 1).getIDOfTBL_CUSTOMER();
                                    refocusItem(posNow + 1, ID_TBL_CUSTOMER_Focus);
                                    refreshData(ID_TBL_CUSTOMER_Focus);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(DetailActivity.this, "Gặp vấn đề khi tự động chuyển sang khách hàng kế tiếp \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            Toast.makeText(DetailActivity.this, "Lưu dữ liệu thành công.", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, 2000);
            } else {
                if (isFilteringBottomMenu) {
                    if (finalIsNextOk) {
                        try {
                            ID_TBL_CUSTOMER_Focus = mData.get(posNow).getIDOfTBL_CUSTOMER();
                            refocusItem(posNow, ID_TBL_CUSTOMER_Focus);
                            refreshData(ID_TBL_CUSTOMER_Focus);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DetailActivity.this, "Gặp vấn đề khi tự động chuyển sang khách hàng kế tiếp \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(DetailActivity.this, "Lưu dữ liệu thành công. \nKhách hàng đã được loại khỏi mục LỌC KHÁCH HÀNG CHƯA GHI", Toast.LENGTH_SHORT).show();
                } else {
                    if (finalIsNextOk) {
                        try {
                            ID_TBL_CUSTOMER_Focus = mData.get(posNow + 1).getIDOfTBL_CUSTOMER();
                            refocusItem(posNow + 1, ID_TBL_CUSTOMER_Focus);
                            refreshData(ID_TBL_CUSTOMER_Focus);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(DetailActivity.this, "Gặp vấn đề khi tự động chuyển sang khách hàng kế tiếp \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(DetailActivity.this, "Lưu dữ liệu thành công.", Toast.LENGTH_SHORT).show();
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "saveData: " + e.getMessage());
            Toast.makeText(this, "Gặp vấn đề khi lưu dữ liệu " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showDialogWarningMaximum(int objectMax, double result) {
        IDialog iDialog = new IDialog() {
            @Override
            protected void clickOK() {
                //save
                saveData(false);
            }

            @Override
            protected void clickCancel() {

            }
        }.setTextBtnOK("Tiếp tục ghi").setTextBtnCancel("Hủy thao tác");
        DetailProxy detailProxy = mData.get(findPosFocusNow(ID_TBL_CUSTOMER_Focus));
        super.showDialog(this, "Sản lượng hiện là " +
                result +
                " m3 " +
                " đang vượt quá giới hạn cảnh báo " + GCSApplication.getSettingObject().getMax() + " m3.", iDialog);
    }

    private void showDialogWarning_QUAYVONG() {
        IDialog iDialog = new IDialog() {
            @Override
            protected void clickOK() {
                try {
                    mSqlDAO.updateIsQuaVongTBL_CUSTOMER(ID_TBL_CUSTOMER_Focus, true, MA_NVIEN);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickButtonSave: updateIsQuaVongTBL_CUSTOMER fail!");
                }
                //save
                saveData(true);
            }

            @Override
            protected void clickCancel() {

            }
        }.setTextBtnOK("Tiếp tục ghi").setTextBtnCancel("Hủy thao tác");
        super.showDialog(this, "Cảnh báo: Chỉ số mới đang nhỏ hơn chỉ số cũ." +
                "\nTiếp tục ghi nếu đây là trường hợp công tơ qua vòng. Trường hợp này không được tính sản lượng.", iDialog);
    }
}
