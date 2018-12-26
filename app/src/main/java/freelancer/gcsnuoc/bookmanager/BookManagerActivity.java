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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.SqlDAO;
import freelancer.gcsnuoc.detail.DetailActivity;
import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.login.LoginActivity;
import freelancer.gcsnuoc.server.GCSAPIInterface;
import freelancer.gcsnuoc.server.GCSApi;
import freelancer.gcsnuoc.server.model.GetData.BookInfoPost;
import freelancer.gcsnuoc.server.model.GetData.DataGetModelServer;
import freelancer.gcsnuoc.server.model.GetToken.TokenModelServer;
import freelancer.gcsnuoc.server.model.LoginServer.LoginPost;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.utils.Common;
import retrofit2.Call;
import retrofit2.Response;

import static freelancer.gcsnuoc.utils.Common.INTENT_KEY_MANHANVIEN;
import static freelancer.gcsnuoc.utils.Common.INTENT_KEY_PASS;
import static freelancer.gcsnuoc.utils.Common.INTENT_KEY_USER;
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

    public enum TYPE_FILTER {
        FILTER_BY_BOTTOM_MENU,
        FILTER_BY_SEARCH,
        NONE_FILTER;
    }

    private static boolean isLoadedFolder = false;

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
            } catch (Exception e1) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
            }
        }
    }

    @Override
    protected void doTaskOnResume() {
        try {
            refreshData();

            //filter data
            if (mPrefManager == null)
                mPrefManager = SharePrefManager.getInstance(this);
            isFilteringBottomMenu = mPrefManager.getSharePref(Common.PREF_BOOK, MODE_PRIVATE).
                    getBoolean(Common.KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM, false);
            filterData(mTYPEFilter, String.valueOf(isFilteringBottomMenu));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề. Vui lòng thử tìm kiếm lại!", Toast.LENGTH_SHORT).show();
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
                        if (bookItemProxy.getStatusBook() != BookItem.STATUS_BOOK.UPLOADED) {
                            dataFilter.add(bookItemProxy);
                        }
                    }
                } else {
                    dataFilter = mSqlDAO.selectAllTBL_BOOK();
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
                dataFilter = mSqlDAO.selectAllTBL_BOOK();
                break;
        }

        booksAdapter.updateList(dataFilter);
        mRvBook.invalidate();
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

        //database
        mDatabase = SqlConnect.getInstance(this).open();
        mSqlDAO = new SqlDAO(mDatabase, this);
        //hiển thị folder trên sdcard
        if (!isLoadedFolder) {
            Common.showFolder(this);
            isLoadedFolder = !isLoadedFolder;
        }

        MA_NVIEN = getIntent().getExtras().getString(INTENT_KEY_MANHANVIEN, "");
        USER_NAME = getIntent().getExtras().getString(INTENT_KEY_USER, "");
        PASS = getIntent().getExtras().getString(INTENT_KEY_PASS, "");

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
                        case R.id.nav_bot_download:
                            showDialogDownload();
                            break;

                        case R.id.nav_bot_filter:
                            if (mPrefManager == null)
                                mPrefManager = SharePrefManager.getInstance(BookManagerActivity.this);
                            isFilteringBottomMenu = mPrefManager.getSharePref(Common.PREF_BOOK, MODE_PRIVATE).
                                    getBoolean(Common.KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM, false);
                            isFilteringBottomMenu = !isFilteringBottomMenu;
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
        });
    }

    private void showDialogUpload() {
//        final Dialog dialogConfig = new Dialog(this);
//        dialogConfig.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialogConfig.setContentView(R.layout.dialog_download);
//        dialogConfig.setCanceledOnTouchOutside(true);
//        dialogConfig.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
//        dialogConfig.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//        dialogConfig.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        final Window window = dialogConfig.getWindow();
//        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//
//        final TextView tvStatus = dialogConfig.findViewById(R.id.dialog_download_tv_status);
//        final TextView tvCountBook = dialogConfig.findViewById(R.id.dilog_download_tv_count_book);
//        final ProgressBar pbarDownload = dialogConfig.findViewById(R.id.dialog_download_pbar_download);
//        final TextView tvPercent = dialogConfig.findViewById(R.id.dialog_download_tv_percent);
//        final Button btnDownload = dialogConfig.findViewById(R.id.dialog_download_btn_download);
//
//        pbarDownload.setMax(100);
//        pbarDownload.setProgress(0);
//
//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    int i = 0;
//                    tvCountBook.setText("6");
//                    do {
//                        pbarDownload.setProgress(i);
//                        tvPercent.setText(i);
//                        tvStatus.setText(i + "%");
//                        Thread.sleep(Common.TIME_DELAY_ANIM);
//                    } while (i < 100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        dialogConfig.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                onResume();
//                dialogConfig.dismiss();
//            }
//        });
    }

    private void showDialogDownload() throws Exception {
        final Dialog dialogDownload = new Dialog(this);
        dialogDownload.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogDownload.setContentView(R.layout.dialog_download);
        dialogDownload.setCanceledOnTouchOutside(true);
        dialogDownload.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialogDownload.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogDownload.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Window window = dialogDownload.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        final TextView tvStatus = (TextView) dialogDownload.findViewById(R.id.dialog_download_tv_status);
        final TextView tvCountBook = (TextView) dialogDownload.findViewById(R.id.dilog_download_tv_count_book);
        final ProgressBar pbarDownload = (ProgressBar) dialogDownload.findViewById(R.id.dialog_download_pbar_download);
        final TextView tvPercent = (TextView) dialogDownload.findViewById(R.id.dialog_download_tv_percent);
        final Button btnDownload = (Button) dialogDownload.findViewById(R.id.dialog_download_btn_download);

        pbarDownload.setMax(100);
        pbarDownload.setProgress(0);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = GCSApi.getClient().create(GCSAPIInterface.class);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startGetTokenAndGetDataBook();
                    }
                }).start();

//                try {
//                    int i = 0;
//                    tvCountBook.setText("6");
//                    do {
//                        pbarDownload.setProgress(i);
//                        tvPercent.setText(i);
//                        tvStatus.setText(i + "%");
//                        Thread.sleep(Common.TIME_DELAY_ANIM);
//                    } while (i < 100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        });

        dialogDownload.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onResume();
                dialogDownload.dismiss();
            }
        });

        dialogDownload.show();
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
        setUIDownload("Đang tải token từ máy chủ...", 10);

        Call<TokenModelServer> tokenModelServerCall = apiInterface.GetToken(new LoginPost(USER_NAME, PASS));
        Response<TokenModelServer> modelServerResponse = null;
        try {
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
                        setUIDownload("Đang tải dữ liệu sổ từ máy chủ...", 20);

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

            setUIDownload("Đang tải sổ ghi chỉ số từ máy chủ...", 30);

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
                            result.getData()

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

    private void setUIDownload(String textStatus, int progress) {
        BookManagerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

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
                    mSqlDAO.updateResetFocusTBL_BOOK();
                    mSqlDAO.updateFocusTBL_BOOK(ID);

                    //refresh data
                    refreshData();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void clickItem(int pos) {
                if (pos >= mRvBook.getAdapter().getItemCount())
                    return;

                try {
                    //update database by ID
                    int ID = booksAdapter.getList().get(pos).getID();
                    mSqlDAO.updateResetFocusTBL_BOOK();
                    mSqlDAO.updateFocusTBL_BOOK(ID);

                    //refresh data
                    refreshData();

                    //openActivity
                    Intent intent = new Intent(BookManagerActivity.this, DetailActivity.class);
                    intent.putExtra(Common.INTENT_KEY_ID_BOOK, ID);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
                    Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
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

    private void refreshData() throws Exception {

    }

    private void loadDataBook() throws Exception {
        //dump data
        //check exist data
        int rowDataTBL_BOOK = 0;
        rowDataTBL_BOOK = mSqlDAO.getNumberRowTBL_BOOK();
        if (rowDataTBL_BOOK == 0) {
            dumpData();
        }
        dataDump = mSqlDAO.selectAllTBL_BOOK();
    }

    private void dumpData() throws Exception {
        //dumpData
        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 1", BookItem.STATUS_BOOK.NON_WRITING, 0, 43, "2018-12-23T02:51:02", false, false));
        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 2", BookItem.STATUS_BOOK.NON_WRITING, 0, 12, "2018-12-23T02:51:02", false, false));
        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 3", BookItem.STATUS_BOOK.NON_WRITING, 0, 54, "2018-12-23T02:51:02", false, false));
        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 4", BookItem.STATUS_BOOK.NON_WRITING, 0, 21, "2018-12-23T02:51:02", false, false));
        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 5", BookItem.STATUS_BOOK.NON_WRITING, 0, 23, "2018-12-23T02:51:02", false, false));
        mSqlDAO.insertTBL_BOOK(new BookItem("bookName 6", BookItem.STATUS_BOOK.NON_WRITING, 0, 52, "2018-12-23T02:51:02", false, false));

    }
}
