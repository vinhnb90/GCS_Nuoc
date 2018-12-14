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

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.setting.SettingActivity;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.utils.Log.getInstance;

public class LoginActivity extends BaseActivity {

    private EditText mEtUser;
    private EditText mEtPass;
    private Button mBtnLogin;
    private ProgressBar mPbarLogin;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        this.savedInstanceState = savedInstanceState;
        setContentView(R.layout.activity_login);
        if (Common.isNeedPermission(this)) {
            return;
        }

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Common.REQUEST_CODE_PERMISSION: {
                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED
                        || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(LoginActivity.this, "Unable to show permission required", Toast.LENGTH_LONG).show();
                    Log.e(getClass().getName(), "Unable to show permission required");
                } else {
                    try {
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
                return;
            }
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
