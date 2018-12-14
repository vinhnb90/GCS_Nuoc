package freelancer.gcsnuoc.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import freelancer.gcsnuoc.BaseActivity;
import freelancer.gcsnuoc.R;
import freelancer.gcsnuoc.entities.SettingObject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.hideBar();
        setContentView(R.layout.activity_setting);
        this.savedInstanceState = savedInstanceState;

        try {
            //setup file debug
            initView();
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
    protected void initView() {
        mEtURL = (EditText) findViewById(R.id.ac_setting_et_url);
        mEtPort = (EditText) findViewById(R.id.ac_setting_et_port);

        mRadioPercent = (RadioButton) findViewById(R.id.ac_setting_radio_detail);
        mRadioMax = (RadioButton) findViewById(R.id.ac_setting_radio_percent);

        mTvPercent = (TextView) findViewById(R.id.ac_setting_tv_percent);
        mTvMax = (TextView) findViewById(R.id.ac_setting_et_port);

        mSeekPercent = (SeekBar) findViewById(R.id.ac_setting_seek_percent);
        mSeekPercent.setMax(100);
        mSeekPercent.setProgress(0);

        mEtMax = (EditText) findViewById(R.id.ac_setting_et_max);
        mEtMax.setText(0);

        mBtnSave = (Button) findViewById(R.id.ac_setting_btn_save);
    }

    @Override
    protected void handleListener() throws Exception {
    }

    @Override
    protected void setAction(Bundle savedInstanceState) throws Exception {
        //load data
        loadSettingData();
        //fill data
        fillSettingData();
    }

    private void fillSettingData() {
        mRadioPercent.setChecked(!settingObject.isMaxNotPercent());
        mRadioMax.setChecked(settingObject.isMaxNotPercent());

        mSeekPercent.setProgress(settingObject.getPercent());
        mEtMax.setText(settingObject.getMax());

        mTvPercent.setText(settingObject.getPercent() + "%");
        mTvMax.setText(settingObject.getMax() + "m3");
    }

    private void loadSettingData() {
        //dump
        settingObject = new SettingObject("192.168.68.90", "8585", true, 67, 356);
    }

    public void clickButtonSave(View view) {
        //validate input
        if (!isValidateInputSetting())
            return;

        //update setting object
        boolean isChoosedPercent = mRadioPercent.isSelected();
        settingObject.setMaxNotPercent(isChoosedPercent);
        if (isChoosedPercent) {
            settingObject.setPercent(mSeekPercent.getProgress());
        } else {
            settingObject.setMax(Integer.parseInt(mTvMax.getText().toString()));
        }

        settingObject.setURL(mEtURL.getText().toString());
        settingObject.setPort(mEtPort.getText().toString());

        //save data db
        if (!isSaveDatabaseSuccess(settingObject))
            Toast.makeText(this, "Cannot save data base !", Toast.LENGTH_SHORT).show();

    }

    private boolean isSaveDatabaseSuccess(SettingObject settingObject) {
        return true;
    }

    private boolean isValidateInputSetting() {
        //check input
        return true;
    }
}
