package freelancer.gcsnuoc.bookmanager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.GCSH20DAO;
import freelancer.gcsnuoc.detail.DetailActivity;
import freelancer.gcsnuoc.entities.BookItem;
import freelancer.gcsnuoc.entities.BookItemProxy;
import freelancer.gcsnuoc.login.LoginActivity;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.sharepref.entity.BookActivitySharePref;
import freelancer.gcsnuoc.utils.Common;

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
    private GCSH20DAO mGCSH20DAO;
    private SQLiteDatabase mDatabase;

    private Common.TRIGGER_NEED_ALLOW_PERMISSION mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;
    private TYPE_FILTER mTYPEFilter = TYPE_FILTER.NONE_FILTER;
    private BookActivitySharePref bookActivitySharePref;

    private enum TYPE_FILTER {
        FILTER_BY_BOTTOM_MENU,
        FILTER_BY_SEARCH,
        NONE_FILTER;
    }

    private static boolean isLoadedFolder = false;

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mDatabase = SqlConnect.getInstance(this).open();
            mGCSH20DAO = new GCSH20DAO(mDatabase, this);
            //hiển thị folder trên sdcard
            if (!isLoadedFolder) {
                Common.showFolder(this);
                isLoadedFolder = !isLoadedFolder;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "onStart: Gặp vấn đề khi load file dữ liệu sdcard! " + e.getMessage());
            Toast.makeText(this, "Gặp vấn đề khi load file dữ liệu sdcard!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_book_manager);

        if (Common.isNeedPermission(this)) {
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_CREATE;
            return;
        }

        doTaskOnCreate();
    }

    @Override
    public void doTaskOnCreate() {
        getInstance().setIsModeDebug(true);

        try {
            //setup file debug
            initView();
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
            bookActivitySharePref = (BookActivitySharePref) SharePrefManager.getInstance().getSharePref(BookManagerActivity.class);
            filterData(mTYPEFilter, String.valueOf(bookActivitySharePref.isFilteringBottomMenu));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(BookManagerActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.ac_book_manager_actionbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.ac_book_manager_menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    filterData(TYPE_FILTER.FILTER_BY_SEARCH, newText);
                } catch (Exception e) {
                    e.printStackTrace();
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
        if (Common.isNeedPermission(this)) {
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
                    dataFilter = mGCSH20DAO.selectAllTBL_BOOK();
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
                dataFilter = mGCSH20DAO.selectAllTBL_BOOK();
                break;
        }

        booksAdapter.updateList(dataFilter);
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
    protected void initView() {
        mEtNameEmp = findViewById(R.id.ac_book_manager_name_emp);
        mEtAndressEmp = findViewById(R.id.ac_book_manager_address_emp);
        mEtPerior = findViewById(R.id.ac_book_manager_tv_text_perior);
        mRvBook = findViewById(R.id.ac_book_manager_rv_books);
        mBottomBar = findViewById(R.id.ac_book_manager_bottom_menu);
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
                            if (bookActivitySharePref == null)
                                bookActivitySharePref = (BookActivitySharePref) SharePrefManager.getInstance().getSharePref(BookManagerActivity.class);

                            boolean isFilteringBottomMenuNow = !bookActivitySharePref.isFilteringBottomMenu;
                            filterData(TYPE_FILTER.FILTER_BY_BOTTOM_MENU, String.valueOf(isFilteringBottomMenuNow));
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
        final Dialog dialogConfig = new Dialog(this);
        dialogConfig.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogConfig.setContentView(R.layout.dialog_download);
        dialogConfig.setCanceledOnTouchOutside(true);
        dialogConfig.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        dialogConfig.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialogConfig.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        final Window window = dialogConfig.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        final TextView tvStatus = dialogConfig.findViewById(R.id.dialog_download_tv_status);
        final TextView tvCountBook = dialogConfig.findViewById(R.id.dilog_download_tv_count_book);
        final ProgressBar pbarDownload = dialogConfig.findViewById(R.id.dialog_download_pbar_download);
        final TextView tvPercent = dialogConfig.findViewById(R.id.dialog_download_tv_percent);
        final Button btnDownload = dialogConfig.findViewById(R.id.dialog_download_btn_download);

        pbarDownload.setMax(100);
        pbarDownload.setProgress(0);

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int i = 0;
                    tvCountBook.setText("6");
                    do {
                        pbarDownload.setProgress(i);
                        tvPercent.setText(i);
                        tvStatus.setText(i + "%");
                        Thread.sleep(Common.TIME_DELAY_ANIM);
                    } while (i < 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        dialogConfig.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onResume();
                dialogConfig.dismiss();
            }
        });
    }

    private void download() {

    }


    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
        //load data
        loadDataBook();
        //fill data
        fillDataBook();
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
                    mGCSH20DAO.updateChooseTBL_BOOK(ID, isChecked);
                    mGCSH20DAO.updateFocusTBL_BOOK(ID, true);

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
                    mGCSH20DAO.updateFocusTBL_BOOK(ID, true);

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
        rowDataTBL_BOOK = mGCSH20DAO.getNumberRowTBL_BOOK();
        if (rowDataTBL_BOOK == 0) {
            dumpData();
        }
        dataDump = mGCSH20DAO.selectAllTBL_BOOK();
    }

    private void dumpData() throws Exception {
        //dumpData
        mGCSH20DAO.insertTBL_BOOK(new BookItem("bookName 1", BookItem.STATUS_BOOK.NON_WRITING, 0, 43,"2017-11-23T22:18:45", false, false));
        mGCSH20DAO.insertTBL_BOOK(new BookItem("bookName 2", BookItem.STATUS_BOOK.NON_WRITING, 0, 12, "2017-11-23T22:18:45", false, false));
        mGCSH20DAO.insertTBL_BOOK(new BookItem("bookName 3", BookItem.STATUS_BOOK.NON_WRITING, 0, 54,"2017-11-23T22:18:45",  false, false));
        mGCSH20DAO.insertTBL_BOOK(new BookItem("bookName 4", BookItem.STATUS_BOOK.NON_WRITING, 0, 21,"2017-11-23T22:18:45",  false, false));
        mGCSH20DAO.insertTBL_BOOK(new BookItem("bookName 5", BookItem.STATUS_BOOK.NON_WRITING, 0, 23, "2017-11-23T22:18:45", false, false));
        mGCSH20DAO.insertTBL_BOOK(new BookItem("bookName 6", BookItem.STATUS_BOOK.NON_WRITING, 0, 52, "2017-11-23T22:18:45", false, false));

    }
}
