package freelancer.gcsnuoc;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

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
}
