package freelancer.gcsnuoc.setting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.app.GCSApplication;
import freelancer.gcsnuoc.entities.SettingObject;
import freelancer.gcsnuoc.sharepref.baseSharedPref.SharePrefManager;
import freelancer.gcsnuoc.utils.Common;

import static freelancer.gcsnuoc.utils.Common.KEY_PREF_IS_MAX_NOT_PERCENT;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_MAX;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_PERCENT;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_PORT;
import static freelancer.gcsnuoc.utils.Common.KEY_PREF_URL;
import static freelancer.gcsnuoc.utils.Common.PREF_BOOK;
import static freelancer.gcsnuoc.utils.Common.PREF_SETTING;
import static freelancer.gcsnuoc.utils.Common.TIME_DELAY_ANIM;
import static freelancer.gcsnuoc.utils.Common.word_count;
import static freelancer.gcsnuoc.utils.Common.word_remove_last;
import static freelancer.gcsnuoc.utils.Log.getInstance;

public class SettingActivity extends BaseActivity {

    private Bundle savedInstanceState;
    private EditText mEtURL;
    private EditText mEtPort;
    private Button mBtnSave;
    private RadioButton mRadioMax;
    private RadioButton mRadioPercent;
    private TextView mTvPercent;
    private TextView mTvMax;
    private SeekBar mSeekPercent;
    private EditText mEtMax;
    private SettingObject settingObject;

    private SharePrefManager mPrefManager;
    private int iStart, iEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        setContentView(R.layout.activity_setting);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.ac_setting_toolbar);
        myToolbar.setTitle("Cài đăt cấu hình");
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.savedInstanceState = savedInstanceState;

        try {
            //setup file debug
            init();
            handleListener();
            setAction(savedInstanceState);
        } catch (Exception e) {
            try {
                getInstance().loge(SettingActivity.class, e.getMessage());
            } catch (Exception e1) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                e1.printStackTrace();
            }
        }
    }


    @Override
    protected void init() {
        mEtURL = (EditText) findViewById(R.id.ac_setting_et_url);
        mEtPort = (EditText) findViewById(R.id.ac_setting_et_port);
//        mEtPort.setText("8080");
//        mEtPort.setHint("8080");
        mEtPort.setText("");
        mEtPort.setHint("Nhập cổng nếu cần!");
        mEtPort.setEnabled(true);

        mRadioPercent = (RadioButton) findViewById(R.id.ac_setting_radio_percent);
        mRadioMax = (RadioButton) findViewById(R.id.ac_setting_radio_detail);

        mTvPercent = (TextView) findViewById(R.id.ac_setting_tv_percent);
        mTvMax = (TextView) findViewById(R.id.ac_setting_tv_m3);

        mSeekPercent = (SeekBar) findViewById(R.id.ac_setting_seek_percent);
        mSeekPercent.setMax(100);
        mSeekPercent.setProgress(0);

        mEtMax = (EditText) findViewById(R.id.ac_setting_et_max);
        mBtnSave = (Button) findViewById(R.id.ac_setting_btn_save);

    }

    @Override
    protected void handleListener() throws Exception {
        mRadioPercent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mRadioPercent.isPressed()) {
                    mRadioMax.setSelected(!isChecked);
                    mEtMax.setEnabled(!isChecked);
                    mRadioMax.setChecked(!isChecked);

                    mRadioPercent.setSelected(isChecked);
                    mSeekPercent.setEnabled(isChecked);
                    mRadioPercent.setChecked(isChecked);
                }
            }
        });

        mRadioMax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mRadioMax.isPressed()) {
                    mRadioPercent.setSelected(!isChecked);
                    mSeekPercent.setEnabled(!isChecked);
                    mRadioPercent.setChecked(!isChecked);

                    mRadioMax.setSelected(isChecked);
                    mEtMax.setEnabled(isChecked);
                    mRadioMax.setChecked(isChecked);
                }
            }
        });

        mSeekPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    mTvPercent.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mEtURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("TAG", "iStart " + iStart + ", iEnd " + iEnd);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        String str = mEtURL.getText().toString();
                        StringBuilder sb = new StringBuilder(str);
                        int indextLast = sb.lastIndexOf(":");
                        int indexSelectStart = mEtURL.getSelectionStart();
                        int indexSelectEnd = mEtURL.getSelectionEnd();
                        if (indextLast == -1)
                            mEtURL.setInputType(InputType.TYPE_CLASS_TEXT);
                        else {
                            if (indexSelectStart == indexSelectEnd) {
                                if (indexSelectStart > indextLast) {
                                    mEtURL.setInputType(InputType.TYPE_CLASS_NUMBER);
                                } else {
                                    mEtURL.setInputType(InputType.TYPE_CLASS_TEXT);
                                }
                            }
                        }

                        mEtURL.postInvalidate();
                    }
                }, 11);
            }
        });

        mEtURL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = mEtURL.getText().toString();
                        int count = word_count(s.toString(), ":");
                        if (count == 2) {
                            mEtURL.setText(word_remove_last(s.toString(), ':'));
                            mEtURL.setSelection(mEtURL.getText().length());
                            Toast.makeText(SettingActivity.this, "Không cho phép nhập 2 kí tự này", Toast.LENGTH_SHORT).show();
                            if (mEtURL.getSelectionEnd() == mEtURL.getText().length()) {
                                mEtURL.setInputType(InputType.TYPE_CLASS_NUMBER);
                            } else {
                                mEtURL.setInputType(InputType.TYPE_CLASS_TEXT);
                            }
                        } else if (1 == count) {
                            mEtPort.setText("");
                            mEtPort.setHint("Không cần nhập");
                            mEtPort.setEnabled(false);
                            if (mEtURL.getSelectionEnd() == mEtURL.getText().length()) {
                                mEtURL.setInputType(InputType.TYPE_CLASS_NUMBER);
                            } else {
                                mEtURL.setInputType(InputType.TYPE_CLASS_TEXT);
                            }

                        } else {
                            mEtPort.setEnabled(true);
                            mEtPort.setHint(TextUtils.isEmpty(mEtPort.getText().toString()) ? "Nhập cổng nếu cần!" : mEtPort.getText().toString());
                            mEtURL.setInputType(InputType.TYPE_CLASS_TEXT);
                        }

                        mEtPort.postInvalidate();
                    }
                });
            }
        });
    }


    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
        //init
        //init shared preref
        mPrefManager = SharePrefManager.getInstance(this);

        this.checkSharePreference();
        //load data
        loadSettingData();
        //fill data
        fillSettingData();
    }

    private void checkSharePreference() {
        if (!mPrefManager.checkExistSharePref(PREF_SETTING)) {
            mPrefManager.addSharePref(PREF_SETTING, MODE_PRIVATE);
            mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE)
                    .edit()
                    .putString(KEY_PREF_URL, "")
                    .putInt(KEY_PREF_PORT, 0)
                    .putBoolean(KEY_PREF_IS_MAX_NOT_PERCENT, false)
                    .putInt(KEY_PREF_PERCENT, 0)
                    .putInt(KEY_PREF_MAX, 0)
                    .commit();
        }
    }

    @Override
    protected void doTaskOnCreate() {

    }

    @Override
    protected void doTaskOnResume() {

    }

    private void fillSettingData() {
        mEtURL.setText(settingObject.getURL());
        int count = word_count(settingObject.getURL(), ":");
        if (count == 2) {
            Toast.makeText(this, "Gặp vấn đề khi khôi phục cấu hình.\n Vui lòng kiểm tra lại cấu hình", Toast.LENGTH_SHORT).show();
            return;
        } else if (1 == count) {
            mEtPort.setText("");
            mEtPort.setEnabled(false);
            mEtPort.setHint("Không cần nhập");
        } else {
            mEtPort.setText(settingObject.getPort() == 0 ? "" : settingObject.getPort() + "");
            mEtPort.setHint(settingObject.getPort() == 0 ? "Nhập cổng nếu cần!" : settingObject.getPort() + "");
            mEtPort.setEnabled(true);
        }

        boolean isMax = settingObject.isMaxNotPercent();

        mRadioMax.setSelected(isMax);
        mRadioMax.setChecked(isMax);
        mEtMax.setEnabled(isMax);

        mRadioPercent.setSelected(!isMax);
        mRadioPercent.setChecked(!isMax);
        mSeekPercent.setEnabled(!isMax);


        mSeekPercent.setProgress(settingObject.getPercent());
        if (settingObject.getMax() != 0)
            mEtMax.setText(settingObject.getMax() + "");

        mTvPercent.setText(mSeekPercent.getProgress() + "%");
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

    @Override
    protected void onResume() {
        super.onResume();
        loadSettingData();
        //fill data
        fillSettingData();
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
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    public void clickButtonSave(View view) {
        Common.runAnimationClickView(view, R.anim.twinking_view, TIME_DELAY_ANIM);

        //validate input
        if (!isValidateInputSetting())
            return;

        //update setting object
        boolean isChoosedPercent = mRadioPercent.isSelected();
        settingObject.setMaxNotPercent(!isChoosedPercent);
        if (isChoosedPercent) {
            settingObject.setPercent(mSeekPercent.getProgress());
        } else {
            settingObject.setMax(Integer.parseInt(mEtMax.getText().toString()));
        }

        settingObject.setURL(mEtURL.getText().toString());
        settingObject.setPort(TextUtils.isEmpty(mEtPort.getText().toString()) ? 0 : Integer.parseInt(mEtPort.getText().toString()));

        //save data db
        if (!isSaveDatabaseSuccess(settingObject))
            Toast.makeText(this, "Gặp lỗi khi lưu cấu hình!", Toast.LENGTH_SHORT).show();
        else {
            GCSApplication.setSettingObject(settingObject);
            Toast.makeText(this, "Đã lưu cấu hình!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isSaveDatabaseSuccess(SettingObject settingObject) {
        if (!mPrefManager.checkExistSharePref(PREF_SETTING)) {
            return false;
        }

        mPrefManager.getSharePref(PREF_SETTING, MODE_PRIVATE)
                .edit()
                .putString(KEY_PREF_URL, settingObject.getURL())
                .putInt(KEY_PREF_PORT, settingObject.getPort())
                .putBoolean(KEY_PREF_IS_MAX_NOT_PERCENT, settingObject.isMaxNotPercent())
                .putInt(KEY_PREF_PERCENT, settingObject.getPercent())
                .putInt(KEY_PREF_MAX, settingObject.getMax())
                .commit();
        return true;
    }

    private boolean isValidateInputSetting() {
        if (TextUtils.isEmpty(mEtURL.getText().toString())) {
            Toast.makeText(this, "Cần nhập đường dẫn máy chủ", Toast.LENGTH_SHORT).show();
            return false;
        }

        int count = word_count(settingObject.getURL(), ":");
        if (count == 0) {

        } else if (count == 1) {
            if (!TextUtils.isEmpty(mEtPort.getText())) {
                Toast.makeText(this, "Không cần nhập cổng máy chủ", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(this, "Không cần nhập cổng máy chủ", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mRadioPercent.isSelected()) {
            if (mSeekPercent.getProgress() == 0) {
                Toast.makeText(this, "Mức % cảnh báo phải khác 0%", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            if (TextUtils.isEmpty(mEtMax.getText().toString()) || Integer.parseInt(mEtMax.getText().toString()) == 0) {
                Toast.makeText(this, "Mức giới hạn cảnh báo phải khác 0 m3", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //check input
        return true;
    }
}
