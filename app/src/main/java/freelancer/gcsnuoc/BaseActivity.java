package freelancer.gcsnuoc;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VinhNB on 8/8/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void hideBar() {
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            // Remember that you should never show the action bar if the
            // status bar is hidden, so hide that too if necessary.
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            if (actionBar != null)
                actionBar.hide();
        }

    }

    protected abstract void init() throws Exception;

    protected abstract void handleListener() throws Exception;

    protected abstract void setAction(final Bundle savedInstanceState) throws Exception;

    protected abstract void doTaskOnCreate();

    protected abstract void doTaskOnResume();

    protected abstract static class IDialog {
        String title;
        String textBtnCancel;
        String textBtnOK;

        public IDialog setTitle(String title) {
            this.title = title;
            return this;
        }

        public IDialog setTextBtnCancel(String textBtnCancel) {
            this.textBtnCancel = textBtnCancel;
            return this;
        }

        public IDialog setTextBtnOK(String textBtnOK) {
            this.textBtnOK = textBtnOK;
            return this;
        }

        protected abstract void clickOK();

        protected abstract void clickCancel();
    }


    protected static void showDialog(Context context, String message, final IDialog iDialog) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gcs_message);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT, android.support.v7.app.ActionBar.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        final TextView tvMessage = (TextView) dialog.findViewById(R.id.tv_message);
        tvMessage.setMovementMethod(new ScrollingMovementMethod());
        final TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title_dialog);
        final Button btHuy = (Button) dialog.findViewById(R.id.btn_huy);
        final Button btContinued = (Button) dialog.findViewById(R.id.btn_tieptuc);
        final View divider = (View) dialog.findViewById(R.id.v_mid_dialog);

        if(!TextUtils.isEmpty(iDialog.textBtnOK))
        {
            btContinued.setText(iDialog.textBtnOK);
        }

        if(!TextUtils.isEmpty(iDialog.textBtnCancel))
        {
            btHuy.setText(iDialog.textBtnCancel);
        }else {
            btHuy.setVisibility(View.GONE);
            divider.setVisibility(View.GONE);
        }

        if(!TextUtils.isEmpty(iDialog.title))
        {
            tvTitle.setText(iDialog.title);
        }

        tvMessage.setText(message);
        btContinued.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDialog.clickOK();
                dialog.dismiss();
            }
        });


        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iDialog.clickCancel();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
