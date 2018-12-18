package freelancer.gcsnuoc.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.bookmanager.BookManagerActivity;
import freelancer.gcsnuoc.database.SqlConnect;
import freelancer.gcsnuoc.database.SqlDAO;
import freelancer.gcsnuoc.setting.SettingActivity;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.utils.Log.getInstance;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private EditText mEtUser;
    private EditText mEtPass;
    private Button mBtnLogin;
    private ProgressBar mPbarLogin;
    private Bundle savedInstanceState;
    private SQLiteDatabase mDatabase;
    private SqlDAO mSqlDAO;
    private SharePrefManager mPrefManager;
    private Common.TRIGGER_NEED_ALLOW_PERMISSION mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_login);


        if (Common.isNeedPermission(this)) {
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_CREATE;
            return;
        }
        doTaskOnCreate();


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


    public void clickSettingButton(View view) {
        startActivity(new Intent(LoginActivity.this, SettingActivity.class));
    }

    public void clickLoginButton(View view) {
//        if (!validateInput())
//            return;

        try {
            callLogin();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void callLogin() {
        startActivity(new Intent(LoginActivity.this, BookManagerActivity.class));
    }

    @Override
    protected void init() {
        mEtUser = (EditText) findViewById(R.id.ac_login_et_username);
        mEtPass = (EditText) findViewById(R.id.ac_login_et_pass);
        mBtnLogin = (Button) findViewById(R.id.ac_login_btn_login);
        mPbarLogin = (ProgressBar) findViewById(R.id.ac_login_pbar_loading);
    }

    @Override
    protected void handleListener() throws Exception {

    }

    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
        //setup data
        //create database
        mDatabase = SqlConnect.getInstance(this).open();
        mSqlDAO = new SqlDAO(mDatabase, this);

        //call Database access object

        //create shared pref
        mPrefManager = SharePrefManager.getInstance(this);
    }

    @Override
    protected void doTaskOnCreate() {
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
//            refreshData();

            //filter data
//            mPosPrograme = mPrefManager.getSharePref(PREF_CONFIG, MODE_PRIVATE).
//                    getInt(KEY_PREF_POS_PROGRAME, 0);
//            mURLServer = mPrefManager.getSharePref(PREF_CONFIG, MODE_PRIVATE).
//                    getString(KEY_PREF_SERVER_URL, "");
//            mPosDvi = mPrefManager.getSharePref(PREF_CONFIG, MODE_PRIVATE).
//                    getInt(KEY_PREF_POS_DVI, 0);
//            mUser = mPrefManager.getSharePref(PREF_CONFIG, MODE_PRIVATE).
//                    getString(KEY_PREF_USER, "");
//            mPass = mPrefManager.getSharePref(PREF_CONFIG, MODE_PRIVATE).
//                    getString(KEY_PREF_PASS, "");
//            mIsCbSaveChecked = mPrefManager.getSharePref(PREF_CONFIG, MODE_PRIVATE).
//                    getBoolean(KEY_PREF_CB_SAVE, false);
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
        if (Common.isNeedPermission(this)) {
            mTrigger = Common.TRIGGER_NEED_ALLOW_PERMISSION.ON_RESUME;
            return;
        }

        doTaskOnResume();
    }

    private boolean validateInput() {
        if (TextUtils.isEmpty(mEtUser.getText().toString())) {
            mEtUser.setError(Common.MESSAGE.ex03.getContent());
            return false;
        }

        if (TextUtils.isEmpty(mEtPass.getText().toString())) {
            mEtPass.setError(Common.MESSAGE.ex03.getContent());
            return false;
        }

        return true;
    }

}
