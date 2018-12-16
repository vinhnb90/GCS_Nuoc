package freelancer.gcsnuoc.entities;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * Created by VinhNB on 8/8/2017.
 */

public abstract class CursorItemProxy {
    private Cursor mCursor;
    private Cursor[] mOtherCursor;
    private int mIndex;

    public CursorItemProxy(@NonNull Cursor mCursor, int mIndex) {
        this.mCursor = mCursor;
        this.mIndex = mIndex;
    }

    public Cursor getmCursor() {
        return mCursor;
    }

    public int getmIndex() {
        return mIndex;
    }

    public void setOtherCursor(Cursor... otherCursor) {
        mOtherCursor = otherCursor;
    }

    public Cursor getOtherCursor(int indexCursor) {
        return mOtherCursor[indexCursor];
    }
}
