package freelancer.gcsnuoc.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class SettingObject implements Parcelable, Cloneable{
    private String mURL;
    private int mPort;
    private boolean mIsMaxNotPercent;
    private int mPercent;
    private int mMax;

    public SettingObject(String URL, int port, boolean isMaxNotPercent, int percent, int max) {
        mURL = URL;
        mPort = port;
        mIsMaxNotPercent = isMaxNotPercent;
        mPercent = percent;
        mMax = max;
    }

    public SettingObject() {
    }

    protected SettingObject(Parcel in) {
        mURL = in.readString();
        mPort = in.readInt();
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

    public int getPort() {
        return mPort;
    }

    public void setPort(int port) {
        mPort = port;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mURL);
        dest.writeInt(mPort);
        dest.writeByte((byte) (mIsMaxNotPercent ? 1 : 0));
        dest.writeInt(mPercent);
        dest.writeInt(mMax);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
