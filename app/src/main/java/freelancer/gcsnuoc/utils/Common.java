package freelancer.gcsnuoc.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import freelancer.gcsnuoc.app.GCSApplication;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static freelancer.gcsnuoc.database.SqlHelper.DB_NAME;
import static freelancer.gcsnuoc.database.SqlHelper.PATH_FOLDER_DB;

/**
 * Created by VinhNB on 8/9/2017.
 */

public class Common {
    public static final int TIME_DELAY_ANIM = 150;

    public static final String PREF_BOOK = "PREF_BOOK";
    public static final String PREF_SETTING = "PREF_SETTING";
    public static final String PREF_DETAIL = "PREF_DETAIL";
    public static final String PREF_CONFIG = "PREF_CONFIG";
    public static final String PREF_LOGIN = "PREF_LOGIN";
    public static final String PREF_INSTALL = "PREF_INSTALL";
    private static final String TAG = "Common";
    public static String MA_NVIEN = "";
    public static String USER = "";
    public static int ID_TBL_BOOK;
    public static boolean isChooseUpload = false;
    public static final String INTENT_KEY_ID_BOOK = "INTENT_KEY_ID_BOOK";
    public static final int INTENT_REQUEST_KEY_CAMERA = 1113;

    public static final String KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM = "KEY_PREF_BOOK_MANAGER_IS_FILTER_BOTTOM";
    public static final String KEY_PREF_DETAIL_IS_FILTER_BOTTOM = "KEY_PREF_DETAIL_IS_FILTER_BOTTOM";
    public static final String KEY_PREF_URL = "KEY_PREF_URL";
    public static final String KEY_PREF_PORT = "KEY_PREF_PORT";
    public static final String KEY_PREF_IS_MAX_NOT_PERCENT = "KEY_PREF_IS_MAX_NOT_PERCENT";
    public static final String KEY_PREF_PERCENT = "KEY_PREF_PERCENT";
    public static final String KEY_PREF_MAX = "KEY_PREF_MAX";
    public static final String KEY_PREF_USER_NAME = "KEY_PREF_USER_NAME";
    public static final String KEY_PREF_USER_PASS = "KEY_PREF_USER_PASS";
    public static final String KEY_PREF_INSTALL_DATE = "KEY_PREF_INSTALL_DATE";
    public static final String PROGRAM_PHOTOS_PATH = "/GCSh2o/PHOTOS/";
    public static final String PROGRAM_PATH = "/GCSh2o/";
    public static final String INTENT_KEY_MANHANVIEN = "INTENT_KEY_MANHANVIEN";
    public static final String INTENT_KEY_USER = "INTENT_KEY_USER";
    public static final String INTENT_KEY_PASS = "INTENT_KEY_PASS";

    public static String URLServer = "";
    private static final float SIZE_HEIGHT_IMAGE = 600;
    private static final int SIZE_WIDTH_IMAGE_BASIC = 500;

    public static void setURLServer(String andressServer, int port) {
        int count = word_count(andressServer, ":");
        if (count == 2)
            return;
        else if (count == 1) {
            URLServer = "http://" + andressServer + "/api/MobileApi/";
        } else {
            if (port == 0)
                URLServer = "http://" + andressServer + "/api/MobileApi/";
            else
                URLServer = "http://" + andressServer + ":" + port + "/api/MobileApi/";
        }
    }

    public static void setUserCommon(String userCommon, String MA_NVIEN) {
            Common.USER = userCommon;
            Common.MA_NVIEN = MA_NVIEN;
    }

    public static void scaleImage(String fileName, Context context) throws Exception {
        File file = new File(fileName);

        BufferedOutputStream bos = null;
        if (!file.exists()) {
            throw new IOException("Không tìm thấy ảnh");
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(fileName, options);

        if (bitmap != null) {
            float w = bitmap.getWidth();
            float h = bitmap.getHeight();
            if (h < w) {
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                bitmap = Common.scaleDown(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true), Common.SIZE_HEIGHT_IMAGE, true);
            } else {
                bitmap = Common.scaleDown(bitmap, Common.SIZE_HEIGHT_IMAGE, true);
            }
            //TODO w lúc nào cũng lớn hơn h và sau khi resize thì h luôn = 600, w > 0, ảnh được xoay nếu w < h tạo ra hình chữ nhật

            bos = new BufferedOutputStream(new FileOutputStream(fileName));
            bos.write(Common.encodeTobase64Byte(bitmap));
            bos.close();
            Common.scanFile(context, new String[]{fileName});
        }

    }

    public static void scanFile(Context ctx, String[] allFiles) {
        MediaScannerConnection.scanFile(ctx, allFiles, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("ExternalStorage", "Scanned " + path + ":");
                        Log.d("ExternalStorage", "uri=" + uri);
                    }
                });
    }

    public static byte[] encodeTobase64Byte(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 45, baos);
        byte[] b = baos.toByteArray();
        return b;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }

    public static void setID_TBL_BOOK(int id) {
        Common.ID_TBL_BOOK = id;
    }

    public static boolean deleteFolderSDcard(String programPath) {

        File dir = new File(Environment.getExternalStorageDirectory().getPath() + programPath);
        try {
            FileUtils.deleteDirectory(dir);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "deleteFolderSDcard: " + e.getMessage());
        }
        return true;
    }


    public enum TRIGGER_NEED_ALLOW_PERMISSION {
        NONE,
        ON_CREATE,
        ON_RESUME;
    }

    public static void runAnimationClickView(final View view, int idAnimation, int timeDelayAnim) {
        if (view == null)
            return;
        if (idAnimation <= 0)
            return;

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), idAnimation);
        if (timeDelayAnim > 0)
            animation.setDuration(timeDelayAnim);

        view.startAnimation(animation);
    }

    //region search tiếng việt
    private static char[] SPECIAL_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
            'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
            'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
            'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
            'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
            'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
            'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
            'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
            'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
            'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự',};

    private static char[] REPLACEMENTS = {'A', 'A', 'A', 'A', 'E', 'E', 'E',
            'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
            'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
            'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
            'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u',};

    public static String removeAccent(String s) {
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    public static char removeAccent(char ch) {

        int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
        if (index >= 0) {
            ch = REPLACEMENTS[index];
        }
        return ch;
    }

    public static boolean isExistDB() {
        File programDbDirectory = new File(PATH_FOLDER_DB);
        if (!programDbDirectory.exists()) {
            programDbDirectory.mkdirs();
        }

        File db = new File(PATH_FOLDER_DB + File.separator + DB_NAME);
        if (!db.exists()) {
            return false;
        } else
            return true;
    }

    public static String getVersionApp(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        String version = "";
        try {
            info = manager.getPackageInfo(
                    context.getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    public static boolean hasPermissionWriteSdcard(Context context) {
        boolean hasPermission = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false;
            } else {
                hasPermission = true;
            }
        } else {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                hasPermission = false;
            else
                hasPermission = true;
        }
        return hasPermission;
    }


    //endregion

    //region Date time
    public enum DATE_TIME_TYPE {
        type1("HHmmss"),
        type2("yyyyMMdd"),
        type3("yyyyMMddHHmmss"),
        type4("yyyy-MM-dd'T'hh:mm:ssZ"),
        type5("MM/yyyy"),
        type6("dd/MM/yyyy"),
        type7("dd/MM/yyyy HH:mm:ss"),
        type8("dd/MM/yyyy HH:mm"),
        //UI
        type9("dd/MM/yyyy HH'h'mm"),
        type10("MM/dd/yyyy HH:mm:ss a"),
        type11("yyyy-MM-dd HH:mm:ss"),
        type12("yyyyMMddHHmm"),
        type13("yyyyMMddHHmmss"),
        //2017-11-23T22:18
        sqlite1("yyyy-MM-dd'T'HH:mm"),
        sqlite2("yyyy-MM-dd'T'HH:mm:ss"),

        typeEx("typeEx");

        public String content;

        DATE_TIME_TYPE(String content) {
            this.content = content;
        }

        public static DATE_TIME_TYPE findDATE_TIME_TYPE(String content) {
            for (DATE_TIME_TYPE dateTimeType : values()) {
                if (dateTimeType.content.equalsIgnoreCase(content))
                    return dateTimeType;
            }
            return null;
        }

        public String getContent() {
            return content;
        }
    }

    //endregion

    public static String getRecordDirectoryFolder(String folderName) {
        String path = Environment.getExternalStorageDirectory().getPath() + Common.PROGRAM_PHOTOS_PATH;

        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
            f.mkdirs();
        }

        File f1 = new File(path, folderName);
        if (!f1.exists()) {
            f1.mkdir();
            f1.mkdirs();
        }

        return f1.getPath();
    }

    public static String getImageName(String PERIOD, String MA_NVIEN, String ID_BOOK, String ID_CUSTOMER, String DATETIME) {
        //Image name: {PERIOD}_{MA_NVIEN}_{ID_BOOK}_{ID_CUSTOMER}_{DATETIME}
        StringBuilder name = new StringBuilder()
                .append(PERIOD).append("_")
                .append(MA_NVIEN).append("_")
                .append(ID_BOOK).append("_")
                .append(ID_CUSTOMER).append("_")
                .append(DATETIME).append(".jpg");
        return name.toString();
    }

    public static void openFolderExternal_1(Context context, String dir){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                +  dir);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setDataAndType(uri, "*/*");
        context.startActivity(Intent.createChooser(intent, "Open folder"));
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
//    public enum REQUEST_CHON {
//        HUY(0, "Gửi yêu cầu hủy"),
//        GUI(1, "Gửi yêu cầu đăng ký");
//        private int code;
//        private String name;
//
//        REQUEST_CHON(int code, String name) {
//            this.code = code;
//            this.name = name;
//        }
//
//        public int getCode() {
//            return code;
//        }
//
//        public String getNameCustomer() {
//            return name;
//        }
//
//        public static REQUEST_CHON findNameBy(int code) {
//            for (REQUEST_CHON v : values()) {
//                if (v.getCode() == code) {
//                    return v;
//                }
//            }
//            return null;
//        }
//    }

    public enum TRANG_THAI_GHIM {
        CHUA_GHIM(0),
        DA_GHIM(1);

        private int code;

        TRANG_THAI_GHIM(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum TRANG_THAI_CHON {
        CHUA_CHON(0),
        DA_CHON(1);

        private int code;

        TRANG_THAI_CHON(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum CHECK {
        SEARCH_THEO_CONG_TO("0", "Search theo công tơ"),
        SEARCH_THEO_BBAN("1", "Search theo biên bản");

        private String code;
        private String string;

        CHECK(String code, String string) {
            this.code = code;
            this.string = string;
        }

        public String getCode() {
            return code;
        }

        public String getString() {
            return string;
        }

        public static CHECK findNameBy(int code) {
            for (CHECK v : values()) {
                if (v.getCode().equals(code)) {
                    return v;
                }
            }
            return null;
        }
    }

    public enum TYPE_RESULT {
        SUCCESS("SUCCESS", "THÀNH CÔNG"),
        ERROR("ERROR", "CÓ LỖI");

        private String code;
        private String title;

        TYPE_RESULT(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public static TYPE_RESULT findNameBy(String code) {
            for (TYPE_RESULT v : values()) {
                if (v.getCode().equals(code)) {
                    return v;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }
    }


    public enum TYPE_SESSION {
        DOWNLOAD("DOWNLOAD", "Thông tin tìm kiếm"),
        UPLOAD("UPLOAD", "Số lượng thiết bị đẩy lên");

        private String code;
        private String title;

        TYPE_SESSION(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public static TYPE_SESSION findNameBy(String code) {
            for (TYPE_SESSION v : values()) {
                if (v.getCode().equals(code)) {
                    return v;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }
    }

    public enum TYPE_TBL_CTO {
        PB("PB"),
        KD("KD");

        private String code;

        TYPE_TBL_CTO(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }


    public enum MESSAGE {
        ex01("ex01", "File dữ liệu không tồn tại trong bộ nhớ sdcard."),
        ex02("ex02", "Gặp vấn đề khi lấy dữ liệu trong máy tính bảng."),
        ex03("ex03", "Không để trống"),
        ex04("ex04", "Tham số truyền vào request soap không tương ứng với số lượng tham số yêu cầu!"),
        ex05("ex05", "Xảy ra lỗi trong quá trình kết nối tới máy chủ! Xin kiểm tra lại kết nối wifi!"),
        ex06("ex06", "Không nhận được dữ liệu trả về từ máy chủ!"),
        ex061("ex061", "Không có dữ liệu"),
        ex07("ex07", "Chưa có kết nối internet, vui lòng kiểm tra lại!"),
        ex08("ex08", "Vui lòng cấu hình địa chỉ máy chủ!"),
        ex09("ex09", "Vui lòng cấu hình mã đơn vị điện lực!"),
        ex10("ex10", "Gặp ván đề khi xóa dữ liệu!."),
        ex11("ex11", "Đã có sẵn thông tin thiết bị trong dữ liệu!."),
        ex12("ex12", "Thành công"),
        ex13("ex13", "Thiết bị không hỗ trợ bluetooth!"),
        ex14("ex14", "Kết nối thiết bị qua bluetooth thành công!"),
        ex15("ex15", "Kết nối thiết bị qua bluetooth thất bại!"),
        ex16("ex16", "Kết nối bluetooth với thiết bị đã ngắt!"),

        ex17("ex17", "Không tìm thấy thiết bị nào!"),
        ex18("ex18", "Ngắt kết nối bluetooth!"),
        ex19("ex19", "Đang quét...!"),
        ex20("ex20", "Dữ liệu nhập rỗng...!"),

        ex21("ex21", "Thao tác tìm kiếm phiên trước chưa kết thúc...!"),
        ex22("ex22", "Chưa có thiết bị nào được chọn...!"),
        ex23("ex23", "Thao tác tải đơn vị phiên trước chưa kết thúc...!"),
        ex24("ex24", "Đăng nhập không thành công, vui lòng kiểm tra lại thông tin!"),
        ex26("ex26", "Đẩy dữ liệu thành công!\nVui lòng xem lại các trạng thái của thiết bị từ máy chủ gửi về!"),
        ex261("ex21", "Quá trình đẩy dữ liệu gặp lỗi! Vui lòng xem chi tiết trong lịch sử."),
        ex27("ex27", "Vui lòng chọn đơn vị!"),
        ex28("ex28", "Dữ liệu được cập nhật mới!"),
        ex29("ex29", "Vui lòng gán đơn vị cấp dưới được phân bổ!"),


        ex("ex", "Gặp vấn đề không xác định.");

        private String code, content;

        MESSAGE(String code, String content) {
            this.code = code;
            this.content = content;
        }

        public String getCode() {
            return code;
        }

        public String getContent() {
            return content;
        }
    }

    public static String getDateTimeNow(Common.DATE_TIME_TYPE formatDate) {
        SimpleDateFormat df = new SimpleDateFormat(formatDate.getContent());
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(Calendar.getInstance().getTime());
    }

    public static String convertDateToDate(String time, DATE_TIME_TYPE typeDefault, DATE_TIME_TYPE typeConvert) throws ParseException {
        if (time == null || time.trim().isEmpty())
            return "";

//        if (typeDefault != DATE_TIME_TYPE.FULL) {
//            time = time.replaceAll("-", "");
//            for (int i = time.length(); i <= 17; i++) {
//                time += "0";
//            }
//        }

        long longDate = Common.convertDateToLong(time, typeDefault);

        String dateByDefaultType = Common.convertLongToDate(longDate, typeConvert);
        return dateByDefaultType;
    }

    public static Date convertDateUIToDateSQL(String dateUI, DATE_TIME_TYPE typeDefault) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(typeDefault.getContent());
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateParse = (Date) formatter.parse(dateUI);
        return dateParse;
    }

    public static long convertDateToLong(String date, DATE_TIME_TYPE typeDefault) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(typeDefault.getContent());
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateParse;
        try {
            dateParse = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw e;
        }

        return dateParse.getTime();
    }

    public static String convertLongToDate(long time, Common.DATE_TIME_TYPE format) {
        if (time < 0)
            return null;
        if (format == null)
            return null;

        SimpleDateFormat df2 = new SimpleDateFormat(format.getContent());
//        df2.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date(time);
        return df2.format(date);
    }

    public static String convertDateSQLToDateUI(String time) {
        if (TextUtils.isEmpty(time))
            return "";

        //convert dang yyyymmdd sang dạng dd/mm/yyyy
        String d = time.substring(6, 8);
        String m = time.substring(4, 6);
        String y = time.substring(0, 4);

        return d + "/" + m + "/" + y;
    }

    public static String convertDateSQLToDateServer(String time) {
        if (TextUtils.isEmpty(time))
            return "";

        //convert dang yyyymmdd sang dạng mm/dd/yyyy
        String d = time.substring(6, 8);
        String m = time.substring(4, 6);
        String y = time.substring(0, 4);

        return m + "/" + d + "/" + y;
    }

    public static String convertDateUIToDateSQL(String time) {
        if (TextUtils.isEmpty(time))
            return "";

        //convert dang dd/mm/yyyy sang dạng yyyymmdd
        String y = time.substring(6, 10);
        String m = time.substring(3, 5);
        String d = time.substring(0, 2);

        return y + m + d;
    }

    public static String checkStringNull(String input) {
        return input == null ? "" : input;
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


//    public static void showFolder(ContextWrapper ctx) throws Exception {
//        if (ctx == null)
//            return;
//
//        if (!Common.isExistDB())
//            throw new FileNotFoundException(MESSAGE.ex01.getContent());
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//
//            // Load root folder
//            File rootFolder = new File(PATH_FOLDER_DB);
//            String[] allFilesRoot = rootFolder.list();
//            for (int i = 0; i < allFilesRoot.length; i++) {
//                allFilesRoot[i] = PATH_FOLDER_DB + allFilesRoot[i];
//            }
//            if (allFilesRoot != null)
//                MediaScannerConnection.scanFile(ctx, allFilesRoot, null,
//                        new MediaScannerConnection.OnScanCompletedListener() {
//                            public void onScanCompleted(String path, Uri uri) {
//                                Log.d("ExternalStorage", "Scanned " + path + ":");
//                                Log.d("ExternalStorage", "uri=" + uri);
//                            }
//                        });
//        } else {
//            ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
//                    .parse("file://" + PATH_FOLDER_DB)));
//        }
//    }

//    public static boolean isNetworkConnected(Context context) throws Exception {
//        if (context == null)
//            return false;
//        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
//                ConnectivityManager.TYPE_WIFI};
//        try {
//            ConnectivityManager connectivityManager =
//                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//            for (int networkType : networkTypes) {
//                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//                if (activeNetworkInfo != null &&
//                        activeNetworkInfo.getType() == networkType)
//                    return true;
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//        return false;
//    }
//
//    public static void showAlertDialog(Context context, final MainActivity.OnClickButtonAlertDialog onClickButtonAlertDialog, String title, String message) throws Exception {
//        try {
//            final Dialog dialogConfig = new Dialog(context);
//            dialogConfig.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialogConfig.setContentView(R.layout.dialog_alert);
//            dialogConfig.setCanceledOnTouchOutside(true);
//            dialogConfig.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
//            dialogConfig.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//            dialogConfig.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            Window window = dialogConfig.getWindow();
//            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
//
//            final TextView etTitle = (TextView) dialogConfig.findViewById(R.id.tv_title_dialog);
//            final TextView etMessage = (TextView) dialogConfig.findViewById(R.id.tv_message_dialog);
//            final Button btCancel = (Button) dialogConfig.findViewById(R.id.btn_dialog_cancel);
//            final Button btOk = (Button) dialogConfig.findViewById(R.id.btn_dialog_ok);
//
//            etTitle.setText(title);
//            etMessage.setText(message);
//
//            //catch click
//            btOk.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try {
//                        onClickButtonAlertDialog.doClickYes();
//                    } catch (Exception e) {
//                        throw e;
//                    } finally {
//                        dialogConfig.cancel();
//                    }
//                }
//            });
//
//
//            btCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onClickButtonAlertDialog.doClickNo();
//                    dialogConfig.cancel();
//                }
//            });
//
//            dialogConfig.show();
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    public static final int REQUEST_CODE_PERMISSION = 100;
    public static final int REQUEST_CODE_OPEN_FOLDER = 101;
    public static final String[] SPECIAL_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public static String[] needPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean isAllAllowed = false;
            ArrayList<String> permissionNeedAllow = new ArrayList<>();

            for (String permission :
                    SPECIAL_PERMISSION) {
                if (checkSelfPermission(GCSApplication.getContext(), permission) != PERMISSION_GRANTED)
                    permissionNeedAllow.add(permission);
            }

            return permissionNeedAllow.toArray(new String[permissionNeedAllow.size()]);
        } else {
            return new String[]{};
        }
    }

    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        if (Build.VERSION.SDK_INT >= 11) {
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);
        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View f = activity.getCurrentFocus();
        if (null != f && null != f.getWindowToken() && EditText.class.isAssignableFrom(f.getClass()))
            imm.hideSoftInputFromWindow(f.getWindowToken(), 0);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static String charsetConvert(String text) {
        Charset charset = Charset.forName("ISO-8859-1");
        CharsetDecoder decoder = charset.newDecoder();
        CharsetEncoder encoder = charset.newEncoder();
        String s = "";
        try {
            // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
            // The new ByteBuffer is ready to be read.
            ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(text));

            // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
            // The new ByteBuffer is ready to be read.
            CharBuffer cbuf = decoder.decode(bbuf);

            s = cbuf.toString();
        } catch (CharacterCodingException e) {
        }

        return s;
    }

    public static void showFolder(ContextWrapper ctx) throws Exception {
        if (ctx == null)
            return;

        if (!Common.isExistDB())
            throw new FileNotFoundException(MESSAGE.ex01.getContent());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            // Load root folder
            File rootFolder = new File(PATH_FOLDER_DB);
            String[] allFilesRoot = rootFolder.list();
            for (int i = 0; i < allFilesRoot.length; i++) {
                allFilesRoot[i] = PATH_FOLDER_DB + allFilesRoot[i];
            }
            if (allFilesRoot != null)
                MediaScannerConnection.scanFile(ctx, allFilesRoot, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                android.util.Log.d("ExternalStorage", "Scanned " + path + ":");
                                Log.d("ExternalStorage", "uri=" + uri);
                            }
                        });
        } else {
            ctx.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
                    .parse("file://" + PATH_FOLDER_DB)));
        }
    }

    /**
     * @param isCountLine: true: chỉ muốn lấy số dòng sẽ được vẽ
     *                     false: vẽ luôn
     * @param canvas:      null: nếu isCountLine == true
     *                     canvas: else
     * @param paint
     * @param x
     * @param textHeight
     * @param maxWidth
     * @param text
     */
    public static int drawTextAndBreakLine(final boolean isCountLine, final Canvas canvas, final Paint paint,
                                           float x, float y, float textHeight, float maxWidth, final String text, int paddingBetweenText) {
        String textToDisplay = text;
        String tempText = "";
        int countLine = 0;
        char[] chars;
        float lastY = y;
        int nextPos = 0;
        int lengthBeforeBreak = textToDisplay.length();
        do {
            countLine++;
            lengthBeforeBreak = textToDisplay.length();
            chars = textToDisplay.toCharArray();
            nextPos = paint.breakText(chars, 0, chars.length, maxWidth, null);
            tempText = textToDisplay.substring(0, nextPos);
            textToDisplay = textToDisplay.substring(nextPos, textToDisplay.length());
            if (!isCountLine) {
                canvas.drawText(tempText, x, lastY, paint);
            }
            lastY += textHeight + paddingBetweenText;
        } while (nextPos < lengthBeforeBreak);

        if (isCountLine)
            return countLine;
        else return -1;
    }

    /**
     * @param context
     * @param PATH_ANH
     * @param VI_TRI_1:   dòng đầu
     * @param VI_TRI_2_1  : dòng thứ 2 bên trái
     * @param VI_TRI_2_2  : dòng thứ 2 bên phải
     * @param VI_TRI_3    : dòng thứ 3 bên trái
     * @param VI_TRI_3_2  : dòng thứ 3 bên phải
     * @param VI_TRI_4_1: dòng thứ 4 dưới cùng bên trái
     * @param VI_TRI_4_2: dòng thứ 4 dưới cùng bên phải
     * @return
     */
    public static Bitmap drawTextOnBitmapCongTo(Context context, String PATH_ANH, String VI_TRI_1, String VI_TRI_2_1, String VI_TRI_2_2, String VI_TRI_3, String VI_TRI_3_2, String VI_TRI_4_1, String VI_TRI_4_2) throws Exception {
        String fileName = PATH_ANH;
        File fBitmap = new File(fileName);
        if (fBitmap.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bmRoot = BitmapFactory.decodeFile(fileName, options);
            if (bmRoot != null) {
                Bitmap.Config bmConfig = bmRoot.getConfig();
                if (bmConfig == null) {
                    bmConfig = android.graphics.Bitmap.Config.ARGB_8888;
                }
                bmRoot = bmRoot.copy(bmConfig, true);

                //TODO Tạo paint để vẽ nền đen
                Paint paint_background = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint_background.setColor(Color.BLACK);

                //TODO tạo paint text để vẽ text
                final int text_size = 20;
                final int paddingBetweenText = 10;
                Paint paint_text = new Paint(Paint.ANTI_ALIAS_FLAG);
                paint_text.setColor(Color.WHITE);
                paint_text.setTextSize(text_size);//textSize = 20
                final int textHeight = Math.round(paint_text.descent() - paint_text.ascent());

                // TODO tính dòng sẽ được vẽ của chuỗi dài chưa xác định TEN_KH, không cần xác định x, y
                int soDongCuaTextVI_TRI_1 = drawTextAndBreakLine(true, null, paint_text, 0, 0, textHeight, bmRoot.getWidth(), VI_TRI_1, paddingBetweenText);

                // TODO tính dòng sẽ được vẽ của chuỗi dài chưa xác định CHI_SO
//                int soDongCuaTextVI_TRI_3 = drawTextAndBreakLine(true, null, paint_text, 0, 0, textHeight, bmRoot.getWidth(), VI_TRI_3, paddingBetweenText);

                //TODO tạo bitmap với diện tích như khung chứa để vẽ ảnh và thông tin
                Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                Bitmap bitmapResult = null;
                //TODO do fix cứng h của nội dung ảnh là SIZE_HEIGHT_IMAGE, nên khi resize thì w thay đổi,
                //TODO nếu < 1 giá trị nào đó thì không đủ bề ngang w để ghi thông tin, (SIZE_HEIGHT_IMAGE >600 thì ghi đủ), ta chèn thêm viền đen vào nếu không đủ ghi
                if (bmRoot.getWidth() > SIZE_WIDTH_IMAGE_BASIC) {
                    bitmapResult = Bitmap.createBitmap(
                            bmRoot.getWidth(),
                            (int) (paddingBetweenText / 2
                                    + (soDongCuaTextVI_TRI_1) * (textHeight + paddingBetweenText)
                                    + textHeight
                                    + paddingBetweenText
                                    + (textHeight + paddingBetweenText)
                                    + SIZE_HEIGHT_IMAGE
                                    + paddingBetweenText
                                    + textHeight
                                    + paddingBetweenText / 2)
                            , conf);
                } else
                    bitmapResult = Bitmap.createBitmap(
                            SIZE_WIDTH_IMAGE_BASIC,
                            (int) (paddingBetweenText / 2
                                    + (soDongCuaTextVI_TRI_1) * (textHeight + paddingBetweenText)
                                    + textHeight
                                    + paddingBetweenText
                                    + (textHeight + paddingBetweenText)
                                    + SIZE_HEIGHT_IMAGE
                                    + paddingBetweenText
                                    + textHeight
                                    + paddingBetweenText / 2)
                            , conf);

                //TODO khởi tạo canvas
                Canvas canvas = new Canvas(bitmapResult);


                //TODO vẽ ảnh lên khung tại tọa độ với trên ảnh (soDongCuaText  + 1dòng TYPE_IMAGE + 1 dòng chỉ số...) + (+ 1 dòng MA_DDO)
                //TODO kiểm tra nếu anh có chiều cao lớn hơn bình thường
                //TODO tức đã được đính kèm info thì ta lấy vị trí ảnh từ
                int x0 = 0;
                int y0 = paddingBetweenText / 2
                        + (soDongCuaTextVI_TRI_1) * (textHeight + paddingBetweenText)
                        + textHeight
                        + paddingBetweenText
                        + (textHeight + paddingBetweenText); //vẽ từ vị trí trên SIZE_HEIGHT_IMAGE

                //TODO tạo khung chứa bitmap từ tọa độ x0, y0 tới .. ...
                RectF frameBitmap = new RectF(x0, y0, x0 + bmRoot.getWidth(), y0 + SIZE_HEIGHT_IMAGE);


                //TODO vẽ full ảnh resultBimap với màu đen làm nền
                canvas.drawRect(0, 0, bitmapResult.getWidth(), bitmapResult.getHeight(), paint_background);

                //TODO nếu đã được ghi
                if (bmRoot.getHeight() > SIZE_HEIGHT_IMAGE) {
                    Rect src = new Rect(x0, (int) (bmRoot.getHeight() - textHeight - paddingBetweenText - Math.round(paddingBetweenText / 2) - SIZE_HEIGHT_IMAGE), bmRoot.getWidth(), bmRoot.getHeight() - textHeight - paddingBetweenText - Math.round(paddingBetweenText / 2));
                    Rect des = null;
                    //TODO nếu ảnh có w <= 600 thì vẽ ảnh ở giữa
                    if (bmRoot.getWidth() <= SIZE_WIDTH_IMAGE_BASIC) {
                        des = new Rect(Math.round((SIZE_WIDTH_IMAGE_BASIC - bmRoot.getWidth()) / 2), y0, SIZE_WIDTH_IMAGE_BASIC - Math.round((SIZE_WIDTH_IMAGE_BASIC - bmRoot.getWidth()) / 2), (int) (y0 + SIZE_HEIGHT_IMAGE));
                    } else {
                        des = new Rect(x0, y0, bitmapResult.getWidth(), (int) (y0 + SIZE_HEIGHT_IMAGE));
                    }
                    //TODO ngược lại vẽ full ảnh
                    //TODO vẽ một phần chính của ảnh bitmap trên trên khung chứa bitmap với bút paint màu đen
                    canvas.drawBitmap(bmRoot, src, des, paint_background);
                } else {
                    //TODO vẽ full ở giữa
                    //TODO nếu ảnh có w <= 600 thì vẽ ảnh ở giữa
                    RectF frameBitmapCenter = null;
                    if (bmRoot.getWidth() <= SIZE_WIDTH_IMAGE_BASIC) {
                        frameBitmapCenter = new RectF(Math.round((SIZE_WIDTH_IMAGE_BASIC - bmRoot.getWidth()) / 2), y0, SIZE_WIDTH_IMAGE_BASIC - Math.round((SIZE_WIDTH_IMAGE_BASIC - bmRoot.getWidth()) / 2), y0 + SIZE_HEIGHT_IMAGE);
                    } else {
                        frameBitmapCenter = new RectF(x0, y0, bitmapResult.getWidth(), y0 + SIZE_HEIGHT_IMAGE);
                    }
                    //TODO vẽ full bitmap trên trên khung chứa bitmap với bút paint màu đen
                    canvas.drawBitmap(bmRoot, null, frameBitmapCenter, paint_background);
                }

                //TODO vẽ TEN_KH
                drawTextAndBreakLine(false, canvas, paint_text, 0, paddingBetweenText / 2 + textHeight, textHeight, bmRoot.getWidth(), VI_TRI_1, paddingBetweenText);

                //TODO vẽ TYPE IMAGE
                //Vẽ 1 khung chứa TYPE IMAGE
                Rect khungTYPE_IMAGE = new Rect();
                paint_text.getTextBounds(VI_TRI_2_1, 0, VI_TRI_2_1.length(), khungTYPE_IMAGE);
                int x_TYPE_IMAGE = 0;
                int y_TYPE_IMAGE = soDongCuaTextVI_TRI_1 * (textHeight + paddingBetweenText) + textHeight;
                canvas.drawRect(x_TYPE_IMAGE, y_TYPE_IMAGE - textHeight, VI_TRI_2_1.length(), y_TYPE_IMAGE + paddingBetweenText, paint_background);

                //Vẽ text TYPE IMAGE
                canvas.drawText(VI_TRI_2_1, x_TYPE_IMAGE, y_TYPE_IMAGE, paint_text);

                //TODO vẽ Ngày
//                    String VI_TRI_2_2 = getDateNow(TYPE_DATENOW.ddMMyyyyHHmmss_Slash_Space_Colon.toString());
                //Vẽ 1 khung chứa DATENOW
                Rect khungDATENOW = new Rect();
                paint_text.getTextBounds(VI_TRI_2_2, 0, VI_TRI_2_2.length(), khungDATENOW);
                int textWidthDATENOW = Math.round(paint_text.measureText(VI_TRI_2_2));
                int x_DATENOW = bitmapResult.getWidth() - textWidthDATENOW;
                int y_DATENOW = y_TYPE_IMAGE;
                canvas.drawRect(x_DATENOW, y_DATENOW - textHeight, bitmapResult.getWidth(), y_DATENOW + paddingBetweenText, paint_background);

                //Vẽ text DATENOW
                canvas.drawText(VI_TRI_2_2, x_DATENOW, y_DATENOW, paint_text);


                Rect khung3_1 = new Rect();
                paint_text.getTextBounds(VI_TRI_3, 0, VI_TRI_3.length(), khung3_1);
                int x_3_1 = 0;
                int y_3_1 = soDongCuaTextVI_TRI_1 * (textHeight + paddingBetweenText) + textHeight + paddingBetweenText + textHeight;
                canvas.drawRect(x_3_1, y_3_1 - textHeight, VI_TRI_3.length(), y_3_1 + paddingBetweenText, paint_background);

                //Vẽ text TYPE IMAGE
                canvas.drawText(VI_TRI_3, x_3_1, y_3_1, paint_text);

                //TODO vẽ Ngày
                //Vẽ 1 khung chứa DATENOW
                Rect khung3_2 = new Rect();
                paint_text.getTextBounds(VI_TRI_3_2, 0, VI_TRI_3_2.length(), khung3_2);
                int textWidth3_2 = Math.round(paint_text.measureText(VI_TRI_3_2));
                int x_3_2 = bitmapResult.getWidth() - textWidth3_2;
                int y_3_2 = y_3_1;
                canvas.drawRect(x_3_2, y_3_2 - textHeight, bitmapResult.getWidth(), y_3_2 + paddingBetweenText, paint_background);

                //Vẽ text DATENOW
                canvas.drawText(VI_TRI_3_2, x_3_2, y_3_2, paint_text);
//

//                //TODO vẽ CHI_SO
//                drawTextAndBreakLine(false, canvas, paint_text, 0, y_3_1 + textHeight + paddingBetweenText, textHeight, bmRoot.getWidth(), VI_TRI_3, paddingBetweenText);

//                //Vẽ 1 khung chứa CHI_SO
//                Rect khungCHI_SO = new Rect();
//                paint_text.getTextBounds(VI_TRI_3, 0, (VI_TRI_3).length(), khungCHI_SO);
//                int x_CHI_SO = 0;
//                int y_CHI_SO = y_3_1 + textHeight + paddingBetweenText;
//                canvas.drawRect(x_CHI_SO, y_CHI_SO - textHeight, bitmapResult.getWidth(), y_CHI_SO + paddingBetweenText, paint_background);
//                //Vẽ text CHI_SO
//                canvas.drawText(VI_TRI_3, x_CHI_SO, y_CHI_SO, paint_text);

                //TODO điền SO_CTO
                Rect khungSO_CTO = new Rect();
                paint_text.getTextBounds(VI_TRI_4_1, 0, (VI_TRI_4_1).length(), khungSO_CTO);
                int x_SO_CTO = 0;
                int y_SO_CTO = bitmapResult.getHeight();
                canvas.drawRect(0, y_SO_CTO - textHeight - paddingBetweenText, VI_TRI_4_1.length(), y_SO_CTO, paint_background);

                //Vẽ text SO_CTO cách thêm 1 khoảng dưới cùng 1 đoạn paddingBetweenText/2
                canvas.drawText(VI_TRI_4_1, x_SO_CTO, y_SO_CTO - paddingBetweenText / 2, paint_text);

                //TODO điền MA_DDO
                Rect khungMA_DDO = new Rect();
                paint_text.getTextBounds(VI_TRI_4_2, 0, VI_TRI_4_2.length(), khungMA_DDO);
                int textWidthMA_DDO = Math.round(paint_text.measureText(VI_TRI_4_2));
                int x_MA_DDO = bitmapResult.getWidth() - textWidthMA_DDO;
                int y_MA_DDO = y_SO_CTO;
                canvas.drawRect(x_MA_DDO, y_MA_DDO - textHeight - paddingBetweenText, bitmapResult.getWidth(), y_MA_DDO, paint_background);

                //Vẽ text MA_DDO cách thêm 1 khoảng dưới cùng 1 đoạn paddingBetweenText/2
                canvas.drawText(VI_TRI_4_2, x_MA_DDO, y_MA_DDO - paddingBetweenText / 2, paint_text);

                BufferedOutputStream bos = null;
                try {
                    bos = new BufferedOutputStream(new FileOutputStream(fileName));
                    bos.write(Common.encodeTobase64Byte(bitmapResult));
                    bos.close();
                    scanFile(context, new String[]{fileName});
                } catch (IOException ex) {
                    throw new Exception(ex.getMessage());
                }
                return bitmapResult;
            } else {
                throw new Exception("Lỗi khi xử lý ảnh!");
            }
        } else {
            throw new Exception("Không có ảnh trong máy!");
        }
    }

    public static void renameFile(String pathOld, String pathNew) {
        File from = new File(pathOld);
        File to = new File(pathNew);
        if (from.exists())
            from.renameTo(to);
    }

    public static boolean isNetworkConnected(Context context) throws Exception {
        if (context == null)
            return false;
        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE,
                ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            throw e;
        }
        return false;
    }

    public static String convertBitmapToByte64(String pathImage) {
        Bitmap imageBitmap = BitmapFactory.decodeFile(pathImage);

        Bitmap immagex = imageBitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 45, baos);
        byte[] imageByte = baos.toByteArray();
        String byteBimap = Base64.encodeToString(imageByte, Base64.NO_WRAP);
        return byteBimap;
    }

    public static int word_count(String text, String key) {
        int count = 0;
        while (text.contains(key)) {
            count++;
            text = text.substring(text.indexOf(key) + key.length());
        }
        return count;
    }

    public static String word_remove_last(String str, char key) {
        StringBuilder sb = new StringBuilder(str);
        int indextLast = sb.lastIndexOf(":");
        return sb.deleteCharAt(indextLast).toString();
    }

    public static List<Object> getKeysFromValue(Map<?, ?> hm, Object value){
        List <Object>list = new ArrayList<Object>();
        for(Object o:hm.keySet()){
            if(hm.get(o).equals(value)) {
                list.add(o);
            }
        }
        return list;
    }

    public static long logInstallDate(Context context) {
        PackageManager pm = context.getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "logInstallDate: " + e.getMessage());
        }
        String appFile = appInfo.sourceDir;
        long installed = new File(appFile).lastModified();
        Log.d(TAG, "logInstallDate: installed = " + installed);
        return installed;
    }
}
