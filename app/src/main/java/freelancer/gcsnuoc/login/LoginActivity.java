package freelancer.gcsnuoc.login;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.bookmanager.BookManagerActivity;
import freelancer.gcsnuoc.database.GCSH20DAO;
import freelancer.gcsnuoc.database.SqlHelper;
import freelancer.gcsnuoc.database.config.ES_GCS_H20;
import freelancer.gcsnuoc.database.config.TBL_BOOK;
import freelancer.gcsnuoc.database.config.TBL_CUSTOMER;
import freelancer.gcsnuoc.database.config.TBL_IMAGE;
import freelancer.gcsnuoc.setting.SettingActivity;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.sharepref.entity.BookActivitySharePref;
import freelancer.gcsnuoc.sharepref.entity.DetailActivitySharePref;
import freelancer.gcsnuoc.sharepref.entity.LoginActivitySharePref;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.utils.Log.getInstance;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private EditText mEtUser;
    private EditText mEtPass;
    private Button mBtnLogin;
    private ProgressBar mPbarLogin;
    private Bundle savedInstanceState;
    private GCSH20DAO mSqlDAO;
    private SharePrefManager mPrefManager;
    private LoginActivitySharePref loginActivitySharePref;
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
        if (!validateInput())
            return;

        try {
            callLogin();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void callLogin() {
        
    }

    @Override
    protected void initView() {
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
        SqlHelper.setupDB(
                LoginActivity.this,
                ES_GCS_H20.class,
                new Class[]{
                        TBL_BOOK.class,
                        TBL_CUSTOMER.class,
                        TBL_IMAGE.class,
                });


        //call Database access object
        mSqlDAO = new GCSH20DAO(SqlHelper.getIntance().openDB(), this);

        //create shared pref
        ArrayList<Class<?>> setClassSharedPrefConfig = new ArrayList<Class<?>>();
        setClassSharedPrefConfig.add(LoginActivitySharePref.class);
        setClassSharedPrefConfig.add(BookActivitySharePref.class);
        setClassSharedPrefConfig.add(DetailActivitySharePref.class);
        mPrefManager = SharePrefManager.getInstance(this, setClassSharedPrefConfig);
    }

    @Override
    protected void doTaskOnCreate() {
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
//            refreshData();

            //filter data
            loginActivitySharePref = (LoginActivitySharePref) SharePrefManager.getInstance().getSharePref(LoginActivitySharePref.class);

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
