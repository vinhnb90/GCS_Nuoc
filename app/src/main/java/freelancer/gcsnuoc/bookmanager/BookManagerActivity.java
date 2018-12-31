package freelancer.gcsnuoc.bookmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.SqlDAO;
import freelancer.gcsnuoc.detail.DetailActivity;
import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.entities.CustomerItem;
import freelancer.gcsnuoc.entities.DetailProxy;
import freelancer.gcsnuoc.entities.ImageItemProxy;
import freelancer.gcsnuoc.login.LoginActivity;
import freelancer.gcsnuoc.server.GCSAPIInterface;
import freelancer.gcsnuoc.server.GCSApi;
import freelancer.gcsnuoc.server.model.GetData.BookAvailable;
import freelancer.gcsnuoc.server.model.GetData.BookInfoPost;
import freelancer.gcsnuoc.server.model.GetData.DataGetModelServer;
import freelancer.gcsnuoc.server.model.GetData.DeliveryBook;
import freelancer.gcsnuoc.server.model.GetData.IndexValue;
import freelancer.gcsnuoc.server.model.GetToken.TokenModelServer;
import freelancer.gcsnuoc.server.model.LoginServer.LoginPost;
import freelancer.gcsnuoc.server.model.PostData.DataBookPost;
import freelancer.gcsnuoc.server.model.PostData.DataBookPostReceived;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.utils.Common;
import retrofit2.Call;
import retrofit2.Response;

import static freelancer.gcsnuoc.utils.Common.KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM;
import static freelancer.gcsnuoc.utils.Common.PREF_BOOK;
import static freelancer.gcsnuoc.utils.Common.REQUEST_CODE_PERMISSION;
import static freelancer.gcsnuoc.utils.Log.getInstance;

public class BookManagerActivity extends BaseActivity {

    private static final String TAG = "BookManagerActivity";
    private Bundle savedInstanceState;

    private TextView mEtNameEmp;
    private TextView mEtAndressEmp;
    private TextView mEtPerior;
    private RecyclerView mRvBook;
    private BottomBar mBottomBar;
    private BooksAdapter booksAdapter;
    private List<BookItemProxy> dataDump = new ArrayList<>();
    private List<BookItemProxy> dataChooseUpload = new ArrayList<>();
    private SqlDAO mSqlDAO;
    private SQLiteDatabase mDatabase;

    private Common.TRIGGER_NEED_ALLOW_PERMISSION mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;
    private TYPE_FILTER mTYPEFilter = TYPE_FILTER.NONE_FILTER;
    private boolean isFilteringBottomMenu;
    private SharePrefManager mPrefManager;
    private String MA_NVIEN;
    private String USER_NAME;
    private String PASS;
    private GCSAPIInterface apiInterface;

    //data
    private int deliveryBookSize;
    private int indexCustomerSize;
    private List<BookItem> bookItemList;
    private List<CustomerItem> customnerItemList;
    private Map<Integer, DataBookPost> mDataHashMapBookPostsUpload = new HashMap<>();
    private TextView tvStatus;
    //    private TextView tvCountBook;
    private ProgressBar pbarDownload;
    private TextView tvPercent;
    private Button btnDownload;
    private Dialog dialogDownload;

    private TextView tvStatusUpload;
    private TextView tvNotifyResult;
    private TextView tvTitleNotifyResult;
    private ProgressBar pbarUpload;
    private TextView tvPercentUpload;
    private Button btnUpload;
    private Dialog dialogUpload;

    private Thread threadDownload;
    private boolean threadDownloadIsRunning;
    private boolean threadUploadIsRunning;
    private TextView mEtNoData;


    public enum TYPE_FILTER {
        FILTER_BY_BOTTOM_MENU,
        FILTER_BY_SEARCH,
        NONE_FILTER;
    }

    private static boolean isLoadedFolder = false;

    private List<DataBookPostReceived> listPostSucessServer = new ArrayList<>();
    private List<DataBookPostReceived> listPostErrorServer = new ArrayList<>();
    private List<DataBookPostReceived> listPostTimeoutServer = new ArrayList<>();
    private List<DataBookPostReceived> listPostDisConnectServer = new ArrayList<>();
    private List<DataBookPost> listPostErrorClient = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        setContentView(R.layout.activity_book_manager);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ac_book_manager_toolbar);
        myToolbar.setTitle("Quản lý sổ");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.savedInstanceState = savedInstanceState;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissionsNeedGrant = Common.needPermission(this);
            if (permissionsNeedGrant.length != 0) {
                mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_CREATE;
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        immersiveMode();
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
                (new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        immersiveMode();
                    }
                });
    }

    public void immersiveMode() {
        final View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE
        );
    }

    @Override
    public void doTaskOnCreate() {
        getInstance().setIsModeDebug(true);

        try {
            //setup file debug
            init();
            handleListener();
            setAction(savedInstanceState);
        } catch (Exception e) {
            try {
                getInstance().loge(LoginActivity.class, e.getMessage());
                e.printStackTrace();
            } catch (Exception e1) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void doTaskOnResume() {
        try {
            loadDataBook();
            //filter data
            if (mPrefManager == null)
                mPrefManager = SharePrefManager.getInstance(this);
            isFilteringBottomMenu = mPrefManager.getSharePref(Common.PREF_BOOK, MODE_PRIVATE).
                    getBoolean(Common.KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM, false);
            if (isFilteringBottomMenu) {
                mBottomBar.setDefaultTabPosition(2);
                mBottomBar.postInvalidate();
            }

            filterData(mTYPEFilter, String.valueOf(isFilteringBottomMenu));
            if (mRvBook.getAdapter().getItemCount() == 0) {
                mEtNoData.setVisibility(View.VISIBLE);
            } else {
                mEtNoData.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void trimChildMargins(@NonNull ViewGroup vg) {
        final int childCount = vg.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = vg.getChildAt(i);

            if (child instanceof ViewGroup) {
                trimChildMargins((ViewGroup) child);
            }

            final ViewGroup.LayoutParams lp = child.getLayoutParams();
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) lp).leftMargin = 0;
            }
            child.setBackground(null);
            child.setPadding(0, 0, 0, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ac_book_manager_actionbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.ac_book_manager_menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(ContextCompat.getColor(this, R.color.rowBookColorNomarl));
        trimChildMargins(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    loadDataBook();
                    fillDataBook();
                    filterData(TYPE_FILTER.FILTER_BY_SEARCH, newText);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề. Vui lòng thử tìm kiếm lại!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //check permission
        String[] permissionsNeedGrant = Common.needPermission(this);
        if (permissionsNeedGrant.length == 0 && mTrigger == Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE) {
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_RESUME;
            return;
        }
        doTaskOnResume();
    }

    private void filterData(TYPE_FILTER typeFilter, String dataFiltering) throws Exception {
        List<BookItemProxy> dataFilter = new ArrayList<>();
        switch (typeFilter) {
            case FILTER_BY_BOTTOM_MENU:
                boolean isFilter = Boolean.parseBoolean(dataFiltering);
                if (isFilter) {
                    for (BookItemProxy bookItemProxy : booksAdapter.getList()) {
                        if (bookItemProxy.getStatusBook() == BookItem.STATUS_BOOK.NON_WRITING) {
                            dataFilter.add(bookItemProxy);
                        }
                    }
                } else {
                    dataFilter = mSqlDAO.selectAllTBL_BOOK(MA_NVIEN);
                }
                break;

            case FILTER_BY_SEARCH:
                for (BookItemProxy bookItemProxy : booksAdapter.getList()) {

                    //search by name book
                    if (Common.removeAccent(bookItemProxy.getBookName().toLowerCase()).contains(dataFiltering))
                        dataFilter.add(bookItemProxy);
                }
                break;

            case NONE_FILTER:
                dataFilter = mSqlDAO.selectAllTBL_BOOK(MA_NVIEN);
                break;
        }

        if (dataFilter.size() > 0) {
            refocusItem(0);
            dataFilter.get(0).setFocus(true);
            dataDump = mSqlDAO.selectAllTBL_BOOK(MA_NVIEN);
        }

        booksAdapter.updateList(dataFilter);
        mRvBook.invalidate();
    }

    private void refocusItem(int pos) throws Exception {
        try {
            //update database by ID
            if (pos >= booksAdapter.getItemCount())
                return;

            int ID = booksAdapter.getList().get(pos).getID();
            mSqlDAO.updateResetFocusTBL_BOOK(MA_NVIEN);
            mSqlDAO.updateFocusTBL_BOOK(ID, MA_NVIEN);
            booksAdapter.getList().get(pos).setFocus(true);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String[] permissionsNeedGrant = Common.needPermission(this);
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

                mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;
        }
    }

    @Override
    protected void init() throws Exception {
        mEtNameEmp = (TextView) findViewById(R.id.ac_book_manager_name_emp);
        mEtAndressEmp = (TextView) findViewById(R.id.ac_book_manager_address_emp);
        mEtPerior = (TextView) findViewById(R.id.ac_book_manager_tv_text_perior);
        mRvBook = (RecyclerView) findViewById(R.id.ac_book_manager_rv_books);
        mBottomBar = (BottomBar) findViewById(R.id.ac_book_manager_bottom_menu);
        mEtNoData = (TextView) findViewById(R.id.ac_book_manager_tv_no_data);


        //database
        mDatabase = SqlConnect.getInstance(this).open();
        mSqlDAO = new SqlDAO(mDatabase, this);
        //hiển thị folder trên sdcard
        if (!isLoadedFolder) {
            Common.showFolder(this);
            isLoadedFolder = !isLoadedFolder;
        }

        if (!TextUtils.isEmpty(Common.USER)) {
            MA_NVIEN = Common.MA_NVIEN;
            USER_NAME = Common.USER;
            PASS = mSqlDAO.getInfoPassTBL_SESSION(MA_NVIEN, USER_NAME).getPASSWORD();
        }
    }


    @Override
    protected void handleListener() throws Exception {
        //bottom bar
        //set menu bottom
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                try {
                    switch (tabId) {
                        case R.id.nav_bot_list:
                            if (dialogDownload != null && dialogDownload.isShowing())
                                dialogDownload.dismiss();

                            if (dialogDownload != null && dialogDownload.isShowing())
                                dialogDownload.dismiss();
                            break;
                        case R.id.nav_bot_download:
                            showDialogDownload();
                            break;

                        case R.id.nav_bot_filter:
                            if (mPrefManager == null)
                                mPrefManager = SharePrefManager.getInstance(BookManagerActivity.this);
//                            isFilteringBottomMenu = mPrefManager.getSharePref(Common.PREF_BOOK, MODE_PRIVATE).
//                                    getBoolean(Common.KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM, false);
                            isFilteringBottomMenu = true;
                            mPrefManager.getSharePref(Common.PREF_BOOK, MODE_PRIVATE).edit().putBoolean(Common.KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM, isFilteringBottomMenu).commit();
                            filterData(TYPE_FILTER.FILTER_BY_BOTTOM_MENU, String.valueOf(isFilteringBottomMenu));
                            break;

                        case R.id.nav_bot_upload:
                            showDialogUpload();
                            break;

                    }

                } catch (Exception e) {
                    Toast.makeText(BookManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, false);
    }

    private void showDialogUpload() {
        dialogUpload = new Dialog(this);
        dialogUpload.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogUpload.setContentView(R.layout.dialog_upload);
        dialogUpload.setCanceledOnTouchOutside(true);
        dialogUpload.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialogUpload.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogUpload.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Window window = dialogUpload.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        tvStatusUpload = (TextView) dialogUpload.findViewById(R.id.dialog_upload_tv_status);

        tvNotifyResult = (TextView) dialogUpload.findViewById(R.id.dialog_upload_tv_notify_upload);
        tvTitleNotifyResult = (TextView) dialogUpload.findViewById(R.id.tv_capnhat2);
        pbarUpload = (ProgressBar) dialogUpload.findViewById(R.id.dialog_upload_pbar_upload);
        tvPercentUpload = (TextView) dialogUpload.findViewById(R.id.dialog_upload_tv_percent);
        btnUpload = (Button) dialogUpload.findViewById(R.id.dialog_upload_btn_download);
        tvNotifyResult.setText("");
        showNotifyResultUpload(false);

        pbarUpload.setMax(100);
        pbarUpload.setProgress(0);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btnUpload.setClickable(false);
                    //check all data not write yet and notify
                    dataChooseUpload = new ArrayList<>();
                    dataChooseUpload = mSqlDAO.selectAllTBL_BOOK(MA_NVIEN);
                    if (dataChooseUpload.size() == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(BookManagerActivity.this, "Không có dữ liệu nào được chọn để đẩy lên!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }

                    Map<Integer, Integer> map = new HashMap<>();
                    for (int i = 0; i < dataChooseUpload.size(); i++) {
                        int ID_TBL_BOOK_OF_CUSTOMER = dataChooseUpload.get(i).getID();
                        int customerItemsWrited = mSqlDAO.getNumberRowStatusTBL_CUSTOMERByBook(MA_NVIEN, CustomerItem.STATUS_Customer.WRITED, ID_TBL_BOOK_OF_CUSTOMER);
                        int customerItemsNotWrite = mSqlDAO.getNumberRowStatusTBL_CUSTOMERByBook(MA_NVIEN, CustomerItem.STATUS_Customer.NON_WRITING, ID_TBL_BOOK_OF_CUSTOMER);
//                        int customerItemsUploaded = mSqlDAO.getNumberRowStatusTBL_CUSTOMERByBook(MA_NVIEN, CustomerItem.STATUS_Customer.UPLOADED, ID_TBL_BOOK_OF_CUSTOMER);
                        if (customerItemsNotWrite != 0) {
                            map.put(i, customerItemsNotWrite);
                        }
                    }

//                    if (customerItemsWrited == 0 && customerItemsNotWrite == 0 && customerItemsNotWrite == 0) {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(BookManagerActivity.this, "Không có dữ liệu để đẩy lên!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        return;
//                    }

                    if (!map.isEmpty()) {
                        StringBuilder warningMessage = new StringBuilder("Số khách hàng chưa ghi của các sổ chuẩn bị tải lên:");
                        Set<Integer> set = map.keySet();
                        for (Integer index :
                                set) {
                            warningMessage.append("\nSổ " + dataChooseUpload.get(index).getBookName() + ": " + map.get(index).intValue());
                        }
                        warningMessage.append("\n");
                        showDialogWarningUpload(warningMessage);
                        return;
                    }

                    startUpload();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi truy xuất dữ liệu! \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi truy xuất dữ liệu! \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    BookManagerActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnUpload.setClickable(true);
                        }
                    });
                    threadUploadIsRunning = false;
                }
//                try {
//                    btnUpload.setClickable(false);
//                    //check all data not write yet and notify
//                    int customerItemsWrited = mSqlDAO.getNumberRowStatusTBL_CUSTOMER(MA_NVIEN, CustomerItem.STATUS_Customer.WRITED);
//                    if (customerItemsWrited != 0) {
//                        showDialogWarningUpload();
//                        return;
//                    }
//                    startUpload();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi truy xuất dữ liệu! \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }
        });

        dialogUpload.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onResume();
                dialogUpload.dismiss();
                onBackPressed();
            }
        });

        dialogUpload.show();
    }

    private void showNotifyResultUpload(final boolean isShow) {
        BookManagerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStatusUpload.setVisibility(isShow ? View.VISIBLE : View.GONE);
                tvTitleNotifyResult.setVisibility(isShow ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void startUpload() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadUploadIsRunning = true;
                if (apiInterface == null)
                    apiInterface = GCSApi.getClient().create(GCSAPIInterface.class);

                BookManagerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnUpload.setClickable(true);
                    }
                });

                startGetTokenAndUploadDataBook();

                showNotifyResultUpload(true);
                //show
                StringBuilder message = new StringBuilder();
                message.append("Số sổ yêu cầu đẩy lên: " + dataChooseUpload.size());
                message.append("\n");
                message.append("Số khách hàng yêu cầu đẩy lên: " + mDataHashMapBookPostsUpload.size());
                message.append("\n");
                message.append("Số khách hàng đẩy thành công: " + listPostSucessServer.size());
                message.append("\n");
                message.append("Số khách hàng đẩy bị lỗi do máy chủ trả về: " + listPostErrorServer.size());
                message.append("\n");
                message.append("Số khách hàng đẩy bị lỗi do kết nối chờ quá lâu: " + listPostTimeoutServer.size());
                message.append("\n");
                message.append("Số khách hàng đẩy bị lỗi do xử lý lỗi từ clien: " + listPostErrorClient.size());

                tvNotifyResult.setText(message);

                BookManagerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnUpload.setClickable(true);
                    }
                });
                threadUploadIsRunning = false;
            }
        }).start();
    }

    private void startGetTokenAndUploadDataBook() {
        try {
            if (!Common.isNetworkConnected(BookManagerActivity.this)) {
                setUIUpload("Không có kết nối internet!", 0);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            setUIUpload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
        }
        setUIUpload("Đang cập nhật token từ máy chủ...", 10);
        try {
            Thread.sleep(150);
            Call<TokenModelServer> tokenModelServerCall = apiInterface.GetToken(new LoginPost(USER_NAME, PASS));
            Response<TokenModelServer> modelServerResponse = null;

            modelServerResponse = tokenModelServerCall.execute();
            TokenModelServer result = new TokenModelServer();
            int statusCode = modelServerResponse.code();

            if (modelServerResponse.isSuccessful()) {
                if (statusCode == 200) {
                    result = modelServerResponse.body();
                    if (result.getResult() == true) {
                        //convert list data server to data mtb
                        //expiryDate=2018-12-26T21:11:23.8328657+07:00
                        String getExpiryDate = result.getData().getExpiryDate();
//                        String getExpiryDate_Convert = getExpiryDate.substring(0, 17);
                        String token = result.getData().getToken();
                        setUIUpload("Cập nhật token từ máy chủ thành công...", 100);
                        Thread.sleep(150);
                        setUIUpload("Đang chuẩn bị dữ liệu sổ để đẩy lên máy chủ...", 0);
                        Thread.sleep(150);

                        startPrepareDataAndUploadBook(token);

                        return;
                    } else {
                        setUIUpload("Tải token thất bại!\nNội dung: " + result.getMessage(), 0);
                        return;
                    }
                } else {
                    setUIUpload("Không nhận được phản hồi từ máy chủ! \nCode: " + statusCode, 0);
                }
            } else {
                setUIUpload("Không kết nối được máy chủ!", 0);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            setUIUpload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            setUIUpload("Có vấn đề về ghi dữ liệu!\n" + e.getMessage(), 0);
        }
    }

    private void startPrepareDataAndUploadBook(String token) {
        try {
            listPostSucessServer = new ArrayList<>();
            mDataHashMapBookPostsUpload = new HashMap<>();
            listPostErrorServer = new ArrayList<>();
            listPostTimeoutServer = new ArrayList<>();
            listPostDisConnectServer = new ArrayList<>();
            listPostErrorClient = new ArrayList<>();

            if (!Common.isNetworkConnected(BookManagerActivity.this)) {
                setUIUpload("Không có kết nối internet!", 0);
                return;
            }

            setUIUpload("Đang chuẩn bị dữ liệu sổ để đẩy lên máy chủ...", 1);
            Thread.sleep(150);
            for (int k = 0; k < dataChooseUpload.size(); k++) {

                BookItemProxy bookItemProxy = dataChooseUpload.get(k);
                List<DetailProxy> listCustomer = mSqlDAO.getSelectAllDetailProxy(bookItemProxy.getID(), MA_NVIEN);
                for (int m = 0; m < listCustomer.size(); m++) {
                    DataBookPost item = new DataBookPost();
                    DetailProxy detailProxy = listCustomer.get(m);
                    if (detailProxy.getStatusCustomerOfTBL_CUSTOMER() == CustomerItem.STATUS_Customer.WRITED) {
                        item.setIndexId(String.valueOf(detailProxy.getIndexIdOfTBL_CUSTOMER()));
                        item.setToken(token);
                        item.setDepartmentId(detailProxy.getDepartmentId());
                        item.setPointId(detailProxy.getPointId());
                        item.setTimeOfUse(detailProxy.getTimeOfUse());
                        ;
                        item.setCoefficient(String.valueOf(detailProxy.getCoefficient()));
                        ;
                        item.setElectricityMeterId(detailProxy.getElectricityMeterId());
                        ;
                        item.setTerm(String.valueOf(detailProxy.getTerm()));
                        ;
                        item.setMonth(String.valueOf(detailProxy.getMonth()));
                        ;
                        item.setYear(String.valueOf(detailProxy.getYear()));
                        ;

                        item.setIndexType(detailProxy.getIndexType());
                        ;
                        item.setOldValue(String.valueOf(detailProxy.getOLD_INDEXOfTBL_CUSTOMER()));
                        ;
                        item.setNewValue(String.valueOf(detailProxy.getNEW_INDEXOfTBL_CUSTOMER()));
                        ;
                        item.setStartDate(detailProxy.getStartDate());
                        ;
                        item.setEndDate(detailProxy.getEndDate());
                        ;

                        item.setCustomerId(detailProxy.getCustomerId());
                        ;
                        item.setCustomerCode(detailProxy.getCustomerCode());

                        //get image
                        String uri = detailProxy.getLOCAL_URIOfTBL_IMAGE();
                        item.setImg(Common.convertBitmapToByte64(uri));
                        mDataHashMapBookPostsUpload.put(detailProxy.getIDOfTBL_CUSTOMER(), item);
                    }
                }

            }

            setUIUpload(" Xử lý dữ liệu thành công...", 100);
            Thread.sleep(150);

            setUIUpload(" Đang tải lên máy chủ...", 1);
            Thread.sleep(150);
            Set<Integer> keys = mDataHashMapBookPostsUpload.keySet();
            int i = -1;
            for (Iterator<Integer> it = keys.iterator(); it.hasNext(); ) {
                Integer integerIterator = it.next();
                int key = integerIterator.intValue();
                uploadCustomer(key, mDataHashMapBookPostsUpload.get(key));
                i++;
                setUIUpload(" Đang tải lên máy chủ...", (i + 1) * 100 / mDataHashMapBookPostsUpload.size());
                Thread.sleep(50);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setUIUpload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
        } finally {
            //notify

        }
    }

    private void uploadCustomer(int ID_TBL_CUSTOMER, DataBookPost dataBookPost) {
        Call<DataBookPostReceived> tokenModelServerCall = apiInterface.SaveIndexValue(dataBookPost);
        Response<DataBookPostReceived> modelServerResponse = null;
        try {
            modelServerResponse = tokenModelServerCall.execute();
            DataBookPostReceived result = new DataBookPostReceived();
            int statusCode = modelServerResponse.code();

            if (modelServerResponse.isSuccessful()) {
                if (statusCode == 200) {
                    result = modelServerResponse.body();
                    if (result.getResult() == true) {
                        listPostSucessServer.add(result);
                        mSqlDAO.updateStatusTBL_CUSTOMER(ID_TBL_CUSTOMER, CustomerItem.STATUS_Customer.UPLOADED, MA_NVIEN);
                    } else {
                        listPostErrorServer.add(result);
//                        setUIUpload("Tải token thất bại!\nNội dung: " + result.getMessage(), 0);
                        return;
                    }
                } else {
                    listPostTimeoutServer.add(result);
//                    setUIUpload("Không nhận được phản hồi từ máy chủ! \nCode: " + statusCode, 0);
                }
            } else {
                listPostDisConnectServer.add(result);
//                setUIUpload("Không kết nối được máy chủ!", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            listPostErrorClient.add(dataBookPost);
//            setUIUpload("Có vấn đề về ghi dữ liệu!\n" + e.getMessage(), 0);
        }
    }

    /*{
                            //convert list data server to data mtb

                            //get all data book delivery
                            List<DeliveryBook> deliveryBooks = result.getData().getDeliveryBook();
                            bookItemList = new ArrayList<>();
                            deliveryBookSize = deliveryBooks.size(); // == 100 %

                            List<IndexValue> indexValues = result.getData().getIndexValue();
                            customnerItemList = new ArrayList<>();
                            indexCustomerSize = indexValues.size();

                            List<BookAvailable> bookAvailables = result.getData().getBookAvailable();

                            boolean flag = false;

                            for (int i = 0; i < deliveryBookSize; i++) {
                                DeliveryBook deliveryBook = deliveryBooks.get(i);
                                BookItem bookItem = null;
                                //get data book item

                                //search all availble book
                                for (BookAvailable bookAvailable : bookAvailables) {
                                    //1 book
                                    if (bookAvailable.getFigureBookId().intValue() == deliveryBook.getId().intValue()) {
                                        bookItem = new BookItem();
                                        bookItem.setBookName(deliveryBook.getName());
                                        bookItem.setStatusBook(BookItem.STATUS_BOOK.NON_WRITING);
                                        bookItem.setCustomerWrited(0);
                                        bookItem.setCustomerNotWrite(0);
                                        bookItem.setPeriod("");
                                        bookItem.setFocus(i == 0 ? true : false);
                                        bookItem.setChoose(false);
                                        bookItem.setCODE(deliveryBook.getId().intValue());
                                        bookItem.setMA_NVIEN(MA_NVIEN);
                                    }
                                }

                                //seatch all index
                                for (int j = 0; j < indexCustomerSize; j++) {
                                    IndexValue indexValue = indexValues.get(j);
                                    CustomerItem customerItem = null;
                                    if (indexValue.getFigureBookId().intValue() == deliveryBook.getId().intValue()) {
                                        customerItem = new CustomerItem();
                                        customerItem.setIDBook(bookItem.getCODE());
                                        customerItem.setCustomerName(indexValue.getName());
                                        customerItem.setCustomerAddress(indexValue.getAddress());
                                        customerItem.setStatusCustomer(CustomerItem.STATUS_Customer.NON_WRITING);
                                        customerItem.setFocus(j == 0 ? true : false);
                                        customerItem.setOldIndex(indexValue.getOldValue());
                                        customerItem.setNewIndex(0);
                                        customerItem.setMA_NVIEN(MA_NVIEN);

                                        customerItem.setIndexId(indexValue.getIndexId().intValue());
                                        customerItem.setDepartmentId(String.valueOf(indexValue.getDepartmentId()));
                                        customerItem.setPointId(String.valueOf(indexValue.getPointId()));
                                        customerItem.setTimeOfUse(String.valueOf(indexValue.getTimeOfUse()));
                                        customerItem.setCoefficient(indexValue.getCoefficient());
                                        customerItem.setElectricityMeterId(String.valueOf(indexValue.getElectricityMeterId()));
                                        customerItem.setTerm(indexValue.getTerm());
                                        customerItem.setMonth(indexValue.getMonth());
                                        customerItem.setYear(indexValue.getYear());
                                        customerItem.setIndexType(indexValue.getIndexType());
                                        customerItem.setStartDate(indexValue.getStartDate());
                                        customerItem.setEndDate(indexValue.getEndDate());
                                        customerItem.setCustomerId(String.valueOf(indexValue.getCustomerId()));
                                        customerItem.setCustomerCode(String.valueOf(indexValue.getPointId()));
                                        flag = true;
                                    } else {
                                        if (i == bookItemList.size() - 1) {
                                            if (!flag)
                                                Log.d(TAG, "startDownloadBook: indexValues.getCustomerCode = " + indexValues.get(j).getCustomerCode());
                                            else
                                                flag = false;
                                        }
                                    }
                                    if (customerItem != null) {
                                        customnerItemList.add(customerItem);
                                    }
                                }

                                if (bookItem != null) {
                                    bookItem.setCustomerNotWrite(customnerItemList.size());
                                    bookItem.setCustomerNotWrite(0);
                                    bookItemList.add(bookItem);
                                }

                                setUIUpload("Đã cập nhật ...", (i + 1) / deliveryBookSize);
                                Thread.sleep(50);
                            }
                            setUIUpload("Tải về dữ liệu thành công!", 100);
                            Thread.sleep(150);

                            setUIUpload("Đang xử lý lưu dữ liệu!", 0);
                            //save data

                            for (int i = 0; i < bookItemList.size(); i++) {
                                int id = mSqlDAO.insertTBL_BOOK(bookItemList.get(i));
                                bookItemList.get(i).setID(id);
                            }
                            //save data
                            setUIUpload("Đang xử lý lưu dữ liệu!", 50);
                            try {
                                for (int i = 0; i < customnerItemList.size(); i++) {
                                    for (int j = 0; j < bookItemList.size(); j++) {
                                        if (customnerItemList.get(i).getIDBook() == bookItemList.get(j).getCODE()) {
                                            customnerItemList.get(i).setIDBook(bookItemList.get(j).getID());
                                            mSqlDAO.insertTBL_CUSTOMER(customnerItemList.get(i));
                                        }
                                    }
                                }

                                for (int j = 0; j < bookItemList.size(); j++) {
                                    int count = mSqlDAO.countAllByStatusTBL_CUSTOMER(bookItemList.get(j).getID(), MA_NVIEN, CustomerItem.STATUS_Customer.NON_WRITING);
                                    mSqlDAO.updateCUS_WRITEDOfTBL_BOOK(bookItemList.get(j).getID(), count, MA_NVIEN, false);
                                }

                            } catch (Exception e) {
                                mSqlDAO.deleteAllRowTBL_BOOK(MA_NVIEN);
                                throw e;
                            }
                            setUIUpload("Hoàn thành quá trình tải sổ!", 100);

                            return;
                        }*/

    private void showDialogWarningUpload(StringBuilder warningMessage) {
        IDialog iDialog = new IDialog() {
            @Override
            protected void clickOK() {
                //save
                startUpload();
            }

            @Override
            protected void clickCancel() {
            }
        }.setTextBtnOK("Tiếp tục đẩy lên").setTextBtnCancel("Hủy thao tác");
        super.showDialog(this, "Có một số Sổ chưa ghi chỉ số đầy đủ!\n " +
                warningMessage +
                "Nếu chọn tiếp tục, chương trình sẽ chỉ đẩy mỗi dữ liệu những khách hàng ĐÃ GHI. \n", iDialog);
    }

    private void showDialogDownload() throws Exception {
        dialogDownload = new Dialog(this);
        dialogDownload.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDownload.setContentView(R.layout.dialog_download);
        dialogDownload.setCanceledOnTouchOutside(true);
        dialogDownload.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialogDownload.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogDownload.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Window window = dialogDownload.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        tvStatus = (TextView) dialogDownload.findViewById(R.id.dialog_download_tv_status);
//        tvCountBook = (TextView) dialogDownload.findViewById(R.id.dilog_download_tv_count_book);
        pbarDownload = (ProgressBar) dialogDownload.findViewById(R.id.dialog_download_pbar_download);
        tvPercent = (TextView) dialogDownload.findViewById(R.id.dialog_download_tv_percent);
        btnDownload = (Button) dialogDownload.findViewById(R.id.dialog_download_btn_download);

        pbarDownload.setMax(100);
        pbarDownload.setProgress(0);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btnDownload.setClickable(false);
                    //check all data not write yet and notify
                    int customerItemsWrited = mSqlDAO.getNumberRowStatusTBL_CUSTOMER(MA_NVIEN, CustomerItem.STATUS_Customer.WRITED);
                    if (customerItemsWrited != 0) {
                        showDialogWarningDownload();
                        return;
                    }
                    startDownload();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi truy xuất dữ liệu! \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialogDownload.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onResume();
                dialogDownload.dismiss();
                onBackPressed();
            }
        });

        dialogDownload.show();
    }

    private void showDialogWarningDownload() {
        IDialog iDialog = new IDialog() {
            @Override
            protected void clickOK() {
                //save
                startDownload();
            }

            @Override
            protected void clickCancel() {
            }
        }.setTextBtnOK("Tiếp tục tải về").setTextBtnCancel("Hủy thao tác");
        super.showDialog(this, "Có một số khách hàng đã ghi dữ liệu nhưng chưa đẩy lên máy chủ!\n " +
                "Nếu chọn tiếp tục tải về, chương trình sẽ xóa những khách hàng đó\n" +
                "Gợi ý: Hãy kiểm tra lại và đẩy lên máy chủ những dữ liệu đã ghi!", iDialog);
    }

    @Override
    public void onBackPressed() {
        if (threadDownloadIsRunning)
            return;
        if (mBottomBar.getCurrentTab().getId() == R.id.nav_bot_download) {
            mBottomBar.setDefaultTabPosition(0);
            mBottomBar.postInvalidate();
            return;
        }
        super.onBackPressed();

    }

    private void startDownload() {
        threadDownload = new Thread(new Runnable() {
            @Override
            public void run() {
                threadDownloadIsRunning = true;
                apiInterface = GCSApi.getClient().create(GCSAPIInterface.class);

//                try {
//                    startDeleteAllOldData();
//                } catch (final Exception e) {
//                    e.printStackTrace();
//
//                    BookManagerActivity.this.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(BookManagerActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            btnDownload.setClickable(true);
//                        }
//                    });
//                    return;
//                }

                startGetTokenAndGetDataBook();

                BookManagerActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnDownload.setClickable(true);
                    }
                });
                threadDownloadIsRunning = false;
            }
        });
        threadDownload.start();
    }

    private void startDeleteAllOldData() throws Exception {
        //delete all table
        try {
            mSqlDAO.deleteAllRowUploadedTBL_BOOK(MA_NVIEN);
            mSqlDAO.deleteAllRowUploadedTBL_CUSTOMER(MA_NVIEN);
            List<ImageItemProxy> itemProxyList = mSqlDAO.selectAllTBL_IMAGE(MA_NVIEN);
            for (ImageItemProxy itemProxy :
                    itemProxyList) {
                String localURI = itemProxy.getLOCAL_URI();
                File image = new File(localURI);
                if (image.isFile()) {
                    image.delete();
                }
            }

            mSqlDAO.deleteAllRowTBL_IMAGE(MA_NVIEN);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileNotFoundException("Gặp vấn đề khi xóa dữ liệu cũ!\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new FileNotFoundException("Gặp vấn đề khi truy cập dữ liệu cũ!\n" + e.getMessage());
        }
    }

    private void startGetTokenAndGetDataBook() {
        try {
            if (!Common.isNetworkConnected(BookManagerActivity.this)) {
                setUIDownload("Không có kết nối internet!", 0);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            setUIDownload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
        }
        setUIDownload("Đang cập nhật token từ máy chủ...", 10);
        try {
            Thread.sleep(150);
            Call<TokenModelServer> tokenModelServerCall = apiInterface.GetToken(new LoginPost(USER_NAME, PASS));
            Response<TokenModelServer> modelServerResponse = null;

            modelServerResponse = tokenModelServerCall.execute();
            TokenModelServer result = new TokenModelServer();
            int statusCode = modelServerResponse.code();

            if (modelServerResponse.isSuccessful()) {
                if (statusCode == 200) {
                    result = modelServerResponse.body();
                    if (result.getResult() == true) {
                        //convert list data server to data mtb
                        //expiryDate=2018-12-26T21:11:23.8328657+07:00
                        String getExpiryDate = result.getData().getExpiryDate();
                        String getExpiryDate_Convert = getExpiryDate.substring(0, 17);
                        String token = result.getData().getToken();
                        setUIDownload("Cập nhật token từ máy chủ thành công...", 100);
                        Thread.sleep(150);
                        setUIDownload("Đang tải dữ liệu sổ từ máy chủ...", 0);
                        Thread.sleep(150);
                        startDownloadBook(token);

                        return;
                    } else {
                        setUIDownload("Tải token thất bại!\nNội dung: " + result.getMessage(), 0);
                        return;
                    }
                } else {
                    setUIDownload("Không nhận được phản hồi từ máy chủ! \nCode: " + statusCode, 0);
                }
            } else {
                setUIDownload("Không kết nối được máy chủ!", 0);
            }
        } catch (final IOException e) {
            e.printStackTrace();
            setUIDownload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
        } catch (Exception e) {
            e.printStackTrace();
            setUIDownload("Có vấn đề về ghi dữ liệu!\n" + e.getMessage(), 0);
        }
    }

    private void startDownloadBook(String token) {
        try {
            if (!Common.isNetworkConnected(BookManagerActivity.this)) {
                setUIDownload("Không có kết nối internet!", 0);
                return;
            }

            setUIDownload("Đang tải sổ ghi chỉ số từ máy chủ...", 1);

            Call<DataGetModelServer> tokenModelServerCall = apiInterface.EmpGetBookInfo(new BookInfoPost(MA_NVIEN, token));
            Response<DataGetModelServer> modelServerResponse = null;
            try {
                modelServerResponse = tokenModelServerCall.execute();
                DataGetModelServer result = new DataGetModelServer();
                int statusCode = modelServerResponse.code();

                if (modelServerResponse.isSuccessful()) {
                    if (statusCode == 200) {
                        result = modelServerResponse.body();
                        if (result.getResult() == true) {
                            //convert list data server to data mtb

                            //get all data book delivery
                            List<DeliveryBook> deliveryBooks = result.getData().getDeliveryBook();
                            bookItemList = new ArrayList<>();
                            deliveryBookSize = deliveryBooks.size(); // == 100 %

                            List<IndexValue> indexValues = result.getData().getIndexValue();
                            customnerItemList = new ArrayList<>();
                            indexCustomerSize = indexValues.size();

                            List<BookAvailable> bookAvailables = result.getData().getBookAvailable();

                            boolean flag = false;

                            for (int i = 0; i < deliveryBookSize; i++) {
                                DeliveryBook deliveryBook = deliveryBooks.get(i);
                                BookItem bookItem = null;
                                //get data book item

                                //search all availble book
                                for (BookAvailable bookAvailable : bookAvailables) {
                                    //1 book
                                    if (bookAvailable.getFigureBookId().intValue() == deliveryBook.getId().intValue()) {
                                        bookItem = new BookItem();
                                        bookItem.setBookName(deliveryBook.getName());
                                        bookItem.setStatusBook(BookItem.STATUS_BOOK.NON_WRITING);
                                        bookItem.setCustomerWrited(0);
                                        bookItem.setCustomerNotWrite(0);
//                                        bookItem.setPeriod("");
                                        bookItem.setTerm_book(bookAvailable.getTerm());
                                        bookItem.setYear_book(bookAvailable.getYear());
                                        bookItem.setMonth_book(bookAvailable.getMonth());
                                        bookItem.setFigureBookId(bookAvailable.getFigureBookId());
                                        bookItem.setFocus(i == 0 ? true : false);
                                        bookItem.setChoose(false);
                                        bookItem.setCODE(deliveryBook.getCode());
                                        bookItem.setMA_NVIEN(MA_NVIEN);
                                    }
                                }

                                //seatch all index
                                for (int j = 0; j < indexCustomerSize; j++) {
                                    IndexValue indexValue = indexValues.get(j);
                                    CustomerItem customerItem = null;
                                    if (indexValue.getFigureBookId().intValue() == deliveryBook.getId().intValue()) {
                                        customerItem = new CustomerItem();
                                        customerItem.setIDBook(0);
                                        customerItem.setCustomerName(indexValue.getName());
                                        customerItem.setCustomerAddress(indexValue.getAddress());
                                        customerItem.setStatusCustomer(CustomerItem.STATUS_Customer.NON_WRITING);
                                        customerItem.setFocus(j == 0 ? true : false);
                                        customerItem.setOldIndex(indexValue.getOldValue());
                                        customerItem.setNewIndex(0);
                                        customerItem.setMA_NVIEN(MA_NVIEN);

                                        customerItem.setIndexId(indexValue.getIndexId().intValue());
                                        customerItem.setDepartmentId(String.valueOf(indexValue.getDepartmentId()));
                                        customerItem.setPointId(String.valueOf(indexValue.getPointId()));
                                        customerItem.setTimeOfUse(String.valueOf(indexValue.getTimeOfUse()));
                                        customerItem.setCoefficient(indexValue.getCoefficient());
                                        customerItem.setElectricityMeterId(String.valueOf(indexValue.getElectricityMeterId()));
                                        customerItem.setTerm(indexValue.getTerm());
                                        customerItem.setMonth(indexValue.getMonth());
                                        customerItem.setYear(indexValue.getYear());
                                        customerItem.setIndexType(indexValue.getIndexType());
                                        customerItem.setStartDate(indexValue.getStartDate());
                                        customerItem.setFigureBookId_Customer(indexValue.getFigureBookId());
                                        customerItem.setEndDate(indexValue.getEndDate());
                                        customerItem.setCustomerId(String.valueOf(indexValue.getCustomerId()));
                                        customerItem.setCustomerCode(String.valueOf(indexValue.getPointId()));
                                        flag = true;
                                    } else {
                                        if (i == bookItemList.size() - 1) {
                                            if (!flag)
                                                Log.d(TAG, "startDownloadBook: indexValues.getCustomerCode = " + indexValues.get(j).getCustomerCode());
                                            else
                                                flag = false;
                                        }
                                    }
                                    if (customerItem != null) {
                                        customnerItemList.add(customerItem);
                                    }
                                }

                                if (bookItem != null) {
                                    bookItem.setCustomerNotWrite(customnerItemList.size());
                                    bookItem.setCustomerNotWrite(0);
                                    bookItemList.add(bookItem);
                                }

                                setUIDownload("Đã cập nhật ...", (i + 1) / deliveryBookSize);
                                Thread.sleep(50);
                            }
                            setUIDownload("Tải về dữ liệu thành công!", 100);
                            Thread.sleep(150);

                            setUIDownload("Đang xử lý lưu dữ liệu!", 0);
                            //save data

                            for (int i = 0; i < bookItemList.size(); i++) {
                                //check exist book
                                BookItem bookItem = bookItemList.get(i);
                                int idBookExist = mSqlDAO.checkExistTBL_BOOK(bookItem.getCODE(), bookItem.getTerm_book(), bookItem.getMonth_book(), bookItem.getYear_book(), bookItem.getBookCode(), MA_NVIEN);
                                if (idBookExist == 0) {
                                    int id = mSqlDAO.insertTBL_BOOK(bookItem);
                                    bookItemList.get(i).setID(id);
                                } else {
                                    bookItemList.get(i).setID(idBookExist);
                                }
                            }
                            //save data
                            setUIDownload("Đang xử lý lưu dữ liệu!", 50);
                            try {
                                for (int i = 0; i < customnerItemList.size(); i++) {
                                    for (int j = 0; j < bookItemList.size(); j++) {
                                        CustomerItem customerItem = customnerItemList.get(i);
                                        if (customnerItemList.get(i).getFigureBookId_Customer() == bookItemList.get(j).getFigureBookId()) {
                                            customnerItemList.get(i).setIDBook(bookItemList.get(j).getID());
                                            //check Exist customer
                                            int idCustomerExist = mSqlDAO.checkExistCustomer(customerItem.getPointId(), customerItem.getTerm(), customerItem.getMonth(), customerItem.getYear(), MA_NVIEN);
                                            if (idCustomerExist == 0)
                                                mSqlDAO.insertTBL_CUSTOMER(customnerItemList.get(i));
                                            else
                                                Log.d(TAG, "startDownloadBook: customer not insert because has data in local, PointId =  " + customerItem.getPointId());
                                        }
                                    }
                                }

                                for (int j = 0; j < bookItemList.size(); j++) {
                                    int count = mSqlDAO.countAllByStatusTBL_CUSTOMER(bookItemList.get(j).getID(), MA_NVIEN, CustomerItem.STATUS_Customer.NON_WRITING);
                                    mSqlDAO.updateCUS_WRITEDOfTBL_BOOK(bookItemList.get(j).getID(), count, MA_NVIEN, false);
                                }

                            } catch (Exception e) {
                                mSqlDAO.deleteAllRowTBL_BOOK(MA_NVIEN);
                                throw e;
                            }
                            setUIDownload("Hoàn thành quá trình tải sổ!", 100);

                            return;
                        } else {
                            setUIDownload("Tải token thất bại!\nNội dung: " + result.getMessage(), 0);
                            return;
                        }
                    } else {
                        setUIDownload("Không nhận được phản hồi từ máy chủ! \nCode: " + statusCode, 0);
                    }
                } else {
                    setUIDownload("Không kết nối được máy chủ!", 0);
                }
            } catch (final IOException e) {
                e.printStackTrace();
                setUIDownload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
            } catch (Exception e) {
                e.printStackTrace();
                setUIDownload("Có vấn đề về ghi dữ liệu!\n" + e.getMessage(), 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
            setUIDownload("Có vấn đề về kết nối mạng!\n" + e.getMessage(), 0);
        }
    }

    private void setUIDownload(final String textStatus, final int progress) {
        BookManagerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStatus.setText(textStatus);
                pbarDownload.setProgress(progress);
//                tvCountBook.setText(deliveryBookSize);
                tvPercent.setText(progress + "%");
            }
        });
    }

    private void setUIUpload(final String textStatus, final int progress) {
        BookManagerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvStatusUpload.setText(textStatus);
                pbarUpload.setProgress(progress);
//                tvCountBook.setText(deliveryBookSize);
                tvPercentUpload.setText(progress + "%");
            }
        });
    }

    private void download() {

    }


    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
        //init shared preref
        mPrefManager = SharePrefManager.getInstance(this);

        this.checkSharePreference(mPrefManager);

        //load data
        loadDataBook();
        //fill data
        fillDataBook();
    }

    public static void checkSharePreference(SharePrefManager mPrefManager) {
        if (!mPrefManager.checkExistSharePref(PREF_BOOK)) {
            mPrefManager.addSharePref(PREF_BOOK, MODE_PRIVATE);
            mPrefManager.getSharePref(PREF_BOOK, MODE_PRIVATE)
                    .edit()
                    .putBoolean(KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM, false)
                    .commit();
        }
    }

    private void fillDataBook() {
        booksAdapter = new BooksAdapter(this, new BooksAdapter.IBookAdapterCallback() {
            @Override
            public void clickCbChoose(int pos, boolean isChecked) {
                if (pos >= mRvBook.getAdapter().getItemCount())
                    return;

                try {
                    //update database by ID
                    int ID = booksAdapter.getList().get(pos).getID();
//                    mSqlDAO.updateChooseTBL_BOOK(ID, isChecked);
                    mSqlDAO.updateResetFocusTBL_BOOK(MA_NVIEN);
                    mSqlDAO.updateFocusTBL_BOOK(ID, MA_NVIEN);

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void clickItem(int pos) {
                if (pos >= mRvBook.getAdapter().getItemCount())
                    return;

                try {
                    //update database by ID
                    int ID = booksAdapter.getList().get(pos).getID();
                    mSqlDAO.updateResetFocusTBL_BOOK(MA_NVIEN);
                    mSqlDAO.updateFocusTBL_BOOK(ID, MA_NVIEN);

                    //openActivity
                    Intent intent = new Intent(BookManagerActivity.this, DetailActivity.class);
                    Common.setID_TBL_BOOK(ID);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void scrollToPosition(int pos) {
                mRvBook.scrollToPosition(pos);
                mRvBook.postInvalidate();
            }
        });

        booksAdapter.setList(dataDump);
        mRvBook.setLayoutManager(new LinearLayoutManager(this));
        mRvBook.setHasFixedSize(true);
        mRvBook.setAdapter(booksAdapter);
    }

    private void loadDataBook() throws Exception {
        //dump data
        //check exist data
        int rowDataTBL_BOOK = 0;
        rowDataTBL_BOOK = mSqlDAO.getNumberRowTBL_BOOK(MA_NVIEN);
        if (rowDataTBL_BOOK == 0) {
//            dumpData();

            return;
        }
        dataDump = mSqlDAO.selectAllTBL_BOOK(MA_NVIEN);

        for (int j = 0; j < dataDump.size(); j++) {
            int count = mSqlDAO.countAllByStatusTBL_CUSTOMER(dataDump.get(j).getID(), MA_NVIEN, CustomerItem.STATUS_Customer.NON_WRITING);
            mSqlDAO.updateCUS_WRITEDOfTBL_BOOK(dataDump.get(j).getID(), count, MA_NVIEN, false);

            count = mSqlDAO.countAllByStatusTBL_CUSTOMER(dataDump.get(j).getID(), MA_NVIEN, CustomerItem.STATUS_Customer.WRITED);
            mSqlDAO.updateCUS_WRITEDOfTBL_BOOK(dataDump.get(j).getID(), count, MA_NVIEN, true);
        }
    }

//    private void dumpData() throws Exception {
//        //dumpData
//        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 1", BookItem.STATUS_BOOK.NON_WRITING, 0, 43, "2018-12-23T02:51:02", false, false));
//        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 2", BookItem.STATUS_BOOK.NON_WRITING, 0, 12, "2018-12-23T02:51:02", false, false));
//        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 3", BookItem.STATUS_BOOK.NON_WRITING, 0, 54, "2018-12-23T02:51:02", false, false));
//        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 4", BookItem.STATUS_BOOK.NON_WRITING, 0, 21, "2018-12-23T02:51:02", false, false));
//        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 5", BookItem.STATUS_BOOK.NON_WRITING, 0, 23, "2018-12-23T02:51:02", false, false));
//        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 6", BookItem.STATUS_BOOK.NON_WRITING, 0, 52, "2018-12-23T02:51:02", false, false));
//
//    }
}
