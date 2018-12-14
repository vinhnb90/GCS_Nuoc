package freelancer.gcsnuoc.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingObject implements Parcelable {
    private String mURL;
    private String mPort;
    private boolean mIsMaxNotPercent;
    private int mPercent;
    private int mMax;

    public SettingObject(String URL, String port, boolean isMaxNotPercent, int percent, int max) {
        mURL = URL;
        mPort = port;
        mIsMaxNotPercent = isMaxNotPercent;
        mPercent = percent;
        mMax = max;
    }

    protected SettingObject(Parcel in) {
        mURL = in.readString();
        mPort = in.readString();
        mIsMaxNotPercent = in.readByte() != 0;
        mPercent = in.readInt();
        mMax = in.readInt();
    }

    public static final Creator<SettingObject> CREATOR = new Creator<SettingObject>() {
        @Override
        public SettingObject createFromParcel(Parcel in) {
            return new SettingObject(in);
        }

        @Override
        public SettingObject[] newArray(int size) {
            return new SettingObject[size];
        }
    };

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    public String getPort() {
        return mPort;
    }

    public void setPort(String port) {
        mPort = port;
    }

    public boolean isMaxNotPercent() {
        return mIsMaxNotPercent;
    }

    public void setMaxNotPercent(boolean maxNotPercent) {
        mIsMaxNotPercent = maxNotPercent;
    }

    public int getPercent() {
        return mPercent;
    }

    public void setPercent(int percent) {
        mPercent = percent;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mURL);
        dest.writeString(mPort);
        dest.writeByte((byte) (mIsMaxNotPercent ? 1 : 0));
        dest.writeInt(mPercent);
        dest.writeInt(mMax);
    }
}
