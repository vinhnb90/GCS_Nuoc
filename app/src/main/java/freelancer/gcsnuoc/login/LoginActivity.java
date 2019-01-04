package freelancer.gcsnuoc.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.bookmanager.BookManagerActivity;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.SqlDAO;
import freelancer.gcsnuoc.detail.DetailActivity;
import freelancer.gcsnuoc.entities.SESSION;
import freelancer.gcsnuoc.entities.SessionProxy;
import freelancer.gcsnuoc.entities.SettingObject;
import freelancer.gcsnuoc.server.GCSAPIInterface;
import freelancer.gcsnuoc.server.GCSApi;
import freelancer.gcsnuoc.server.model.LoginServer.LoginGet;
import freelancer.gcsnuoc.server.model.LoginServer.LoginPost;
import freelancer.gcsnuoc.setting.SettingActivity;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.utils.Common;
import retrofit2.Call;
import retrofit2.Response;

import static freelancer.gcsnuoc.utils.Common.KEY_PREF_IS_MAX_NOT_PERCENT;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_MAX;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_PERCENT;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_PORT;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_URL;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_USER_NAME;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_USER_PASS;
import static freelancer.gcsnuoc.utils.Common.PREF_LOGIN;
import static freelancer.gcsnuoc.utils.Common.PREF_SETTING;
import static freelancer.gcsnuoc.utils.Common.REQUEST_CODE_PERMISSION;
import static freelancer.gcsnuoc.utils.Common.TIME_DELAY_ANIM;
import static freelancer.gcsnuoc.utils.Log.getInstance;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private static final int HANDLE_TOAST = 1;
    private EditText mEtUser;
    private EditText mEtPass;
    private TextView mEtUserError;
    private TextView mEtPassError;
    private Button mBtnLogin;
    private ProgressBar mPbarLogin;
    private Bundle savedInstanceState;
    private SQLiteDatabase mDatabase;
    private SqlDAO mSqlDAO;
    private SharePrefManager mPrefManager;
    private Common.TRIGGER_NEED_ALLOW_PERMISSION mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;
    private SettingObject settingObject;
    private String mUser;
    private String mPass;
    private GCSAPIInterface apiInterface;
    private GCSAPIInterface.AsyncApi mAsyncApi;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private boolean isCanLoginOffline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_login);
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


    public void clickSettingButton(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_ANIM);
        startActivity(new Intent(LoginActivity.this, SettingActivity.class));
    }

    public void clickLoginButton(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_ANIM);

        if (!validateInput())
            return;

        //if ok then invisible error warning
        mEtUserError.setVisibility(View.INVISIBLE);
        mEtPassError.setVisibility(View.INVISIBLE);

        mUser = mEtUser.getText().toString();
        mPass = mEtPass.getText().toString();

        if (!mPrefManager.checkExistSharePref(PREF_SETTING)) {
            Toast.makeText(this, "Cần cấu hình các thông số!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getString(KEY_PREF_URL, "");
        int ip = mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getInt(KEY_PREF_PORT, 0);

        Common.setURLServer(url, ip);

        //check Data
        try {
            SessionProxy sessionProxy = mSqlDAO.checkAccountTBL_SESSION(mUser, mPass);
            if (sessionProxy != null)
                isCanLoginOffline = true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                mSqlDAO.deleteAllTBL_SESSIONByUSER_NAME(mUser);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        try {
            if (!Common.isNetworkConnected(this) && !isCanLoginOffline) {
                Toast.makeText(this, "Cần kết nối internet để đăng nhập!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Common.isNetworkConnected(this) && isCanLoginOffline) {
                showDialogWarningLogin();
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Có vấn đề về kết nối mạng!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        doTaskLoginOnline();
    }

    private void showDialogWarningLogin() {
        IDialog iDialog = new IDialog() {
            @Override
            protected void clickOK() {
                try {
                    doTaskLoginOffline();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Gặp vấn đề khi truy xuất dữ liệu để đăng nhập offline!\n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void clickCancel() {
                doTaskLoginOnline();
            }
        }.setTextBtnOK("Đăng nhập offline").setTextBtnCancel("Thử đăng nhập online");
        super.showDialog(this, "Hiện không thể đăng nhập online, nhưng tài khoản có thể đăng nhập offline\n", iDialog);
    }

    private void doTaskLoginOffline() throws Exception {
        //MA_NVIEN --> ALL info
        SessionProxy sessionProxy = mSqlDAO.checkAccountTBL_SESSION(mUser, mPass);
        Common.setUserCommon(mUser, sessionProxy.getMA_NVIEN());
        startActivity(new Intent(LoginActivity.this, BookManagerActivity.class));
    }

    private void doTaskLoginOnline() {
        apiInterface = GCSApi.getClient().create(GCSAPIInterface.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                visibleLoading(true);
                String userName = mEtUser.getText().toString();
                String pass = mEtPass.getText().toString();

                Call<LoginGet> loginModelServerCall = apiInterface.EmpLogin(new LoginPost(userName, pass));
                Response<LoginGet> modelServerResponse = null;
                try {
                    modelServerResponse = loginModelServerCall.execute();
                    visibleLoading(false);

                    LoginGet result = new LoginGet();
                    int statusCode = modelServerResponse.code();

                    if (modelServerResponse.isSuccessful()) {
                        if (statusCode == 200) {
                            result = modelServerResponse.body();
                            if (result.getResult() == true) {
                                //convert list data server to data mtb
                                String dateTime = Common.getDateTimeNow(Common.DATE_TIME_TYPE.sqlite2);
                                SESSION session = new SESSION(userName, pass, dateTime, result.getData().getFullName(), result.getData().getEmpId().toString());

                                //save data sharepref
                                mPrefManager.getSharePref(PREF_LOGIN, MODE_PRIVATE).edit()
                                        .putString(KEY_PREF_USER_NAME, userName)
                                        .putString(KEY_PREF_USER_PASS, pass)
                                        .commit();

                                //remove all data
                                mSqlDAO.deleteAllTBL_SESSION(session.getMA_NVIEN());

                                //save data
                                session.setID_TABLE_SESSION(mSqlDAO.insertTBL_SESSION(session));

                                Common.setUserCommon(mUser, result.getData() + "");
                                startActivity(new Intent(LoginActivity.this, BookManagerActivity.class));

                                return;
                            } else {
                                toastUI("Đăng nhập thất bại!\nNội dung: " + result.getMessage());
                                return;
                            }
                        } else {
                            toastUI("Không nhận được phản hồi từ máy chủ! \nCode: " + statusCode);
                        }
                    } else {
                        toastUI("Không kết nối được máy chủ!");
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    toastUI("Có vấn đề về kết nối mạng!\n" + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    toastUI("Có vấn đề về ghi dữ liệu!\n" + e.getMessage());
                }
            }
        }).start();
    }

    private void visibleLoading(final boolean isVisible) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPbarLogin.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    private void toastUI(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void init() {
        mEtUser = (EditText) findViewById(R.id.ac_login_et_username);
        mEtPass = (EditText) findViewById(R.id.ac_login_et_pass);
        mEtUserError = (TextView) findViewById(R.id.ac_login_tv_error_user);
        mEtPassError = (TextView) findViewById(R.id.ac_login_tv_error_pass);
        mBtnLogin = (Button) findViewById(R.id.ac_login_btn_login);
        mPbarLogin = (ProgressBar) findViewById(R.id.ac_login_pbar_loading);

        mEtUserError.setVisibility(View.INVISIBLE);
        mEtPassError.setVisibility(View.INVISIBLE);
        mPbarLogin.setVisibility(View.INVISIBLE);

        //init shared pref
        mPrefManager = SharePrefManager.getInstance(this);

        this.checkSharePreference();
        if (mPrefManager == null)
            mPrefManager = SharePrefManager.getInstance(this);
        mUser = mPrefManager.getSharePref(PREF_LOGIN, MODE_PRIVATE).getString(KEY_PREF_USER_NAME, "");
        mPass = mPrefManager.getSharePref(PREF_LOGIN, MODE_PRIVATE).getString(KEY_PREF_USER_PASS, "");
        mEtUser.setText(mUser);
        mEtPass.setText(mPass);
        //setup data
        //create database
        mDatabase = SqlConnect.getInstance(this).open();
        mSqlDAO = new SqlDAO(mDatabase, this);
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        immersiveMode();
//        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener
//                (new View.OnSystemUiVisibilityChangeListener() {
//                    @Override
//                    public void onSystemUiVisibilityChange(int visibility) {
//                        immersiveMode();
//                    }
//                });
//    }
//
//    public void immersiveMode() {
//        final View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
////                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
////                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                         View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
////                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN
////                        | View.SYSTEM_UI_FLAG_IMMERSIVE
//        );
//    }

    @Override
    protected void handleListener() throws Exception {
        mEtUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEtUserError.setVisibility(View.INVISIBLE);
                        mEtPassError.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEtUserError.setVisibility(View.INVISIBLE);
                        mEtPassError.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
    }

    private void checkSharePreference() {
        if (!mPrefManager.checkExistSharePref(PREF_LOGIN)) {
            mPrefManager.addSharePref(PREF_LOGIN, MODE_PRIVATE);
            mPrefManager.getSharePref(PREF_LOGIN, MODE_PRIVATE)
                    .edit()
                    .putString(Common.KEY_PREF_USER_NAME, "")
                    .putString(Common.KEY_PREF_USER_PASS, "")
                    .commit();
        }
    }

    @Override
    protected void doTaskOnCreate() {
        getInstance().setIsModeDebug(true);

        try {
            //setup file debug
            init();
            handleListener();
            setAction(savedInstanceState);
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;
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
            super.hideBar();
            if (mPrefManager == null)
                mPrefManager = SharePrefManager.getInstance(this);
            mUser = mPrefManager.getSharePref(PREF_LOGIN, MODE_PRIVATE).getString(KEY_PREF_USER_NAME, "");
            mPass = mPrefManager.getSharePref(PREF_LOGIN, MODE_PRIVATE).getString(KEY_PREF_USER_PASS, "");
            mEtUser.setText(mUser);
            mEtPass.setText(mPass);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "clickCbChoose: Gặp vấn đề khi chọn sổ! " + e.getMessage());
            Toast.makeText(LoginActivity.this, "Gặp vấn đề khi chọn sổ!", Toast.LENGTH_SHORT).show();
        }
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

    private boolean validateInput() {
        if (TextUtils.isEmpty(mEtUser.getText().toString())) {
            mEtUserError.setVisibility(View.VISIBLE);
            mEtPassError.setVisibility(View.INVISIBLE);
            mEtUserError.setError(Common.MESSAGE.ex03.getContent());
            Common.runAnimationClickView(mEtUserError, R.anim.twinking_view, TIME_DELAY_ANIM);
            return false;
        }

        if (TextUtils.isEmpty(mEtPass.getText().toString())) {
            mEtPassError.setVisibility(View.VISIBLE);
            mEtUserError.setVisibility(View.INVISIBLE);
            mEtPassError.setError(Common.MESSAGE.ex03.getContent());
            Common.runAnimationClickView(mEtPassError, R.anim.twinking_view, TIME_DELAY_ANIM);
            return false;
        }

        //get shared pref
        if (!mPrefManager.checkExistSharePref(PREF_SETTING)) {
            Toast.makeText(this, "Hãy kiểm tra lại thông tin cấu hình.", Toast.LENGTH_SHORT).show();
            return false;
        }
        settingObject = new SettingObject();
        settingObject.setURL(mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getString(KEY_PREF_URL, ""));
        settingObject.setPort(mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getInt(KEY_PREF_PORT, 0));
        settingObject.setMaxNotPercent(mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getBoolean(KEY_PREF_IS_MAX_NOT_PERCENT, false));
        settingObject.setMax(mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getInt(KEY_PREF_MAX, 0));
        settingObject.setPercent(mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE).getInt(KEY_PREF_PERCENT, 0));

        return isValidateInputSetting(settingObject);
    }


    private boolean isValidateInputSetting(SettingObject settingObject) {
        if (TextUtils.isEmpty(settingObject.getURL())) {
            Toast.makeText(this, "Cần nhập đường dẫn máy chủ tại màn hình cấu hình", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!settingObject.getURL().contains(":") && settingObject.getPort() == 0) {
            Toast.makeText(this, "Cần nhập cổng máy chủ tại màn hình cấu hình", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!settingObject.isMaxNotPercent()) {
            if (settingObject.getPercent() == 0) {
                Toast.makeText(this, "Mức % cảnh báo phải khác 0% tại màn hình cấu hình", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (settingObject.getMax() == 0) {
                Toast.makeText(this, "Mức giới hạn cảnh báo phải khác 0 m3 tại màn hình cấu hình", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //check input
        return true;
    }

}
