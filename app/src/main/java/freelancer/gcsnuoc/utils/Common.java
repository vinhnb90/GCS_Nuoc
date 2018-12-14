package freelancer.gcsnuoc.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
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

import freelancer.gcsnuoc.app.GCSApplication;

import static android.support.v4.app.ActivityCompat.requestPermissions;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static freelancer.gcsnuoc.database.SqlHelper.DB_NAME;
import static freelancer.gcsnuoc.database.SqlHelper.PATH_FOLDER_DB;

/**
 * Created by VinhNB on 8/9/2017.
 */

public class Common {
    public static final String PATH_LOG = Environment.getExternalStorageDirectory() + File.separator + "BARCODE_HY" + File.separator;
    public static final String NAME_FILE_LOG = "LogFile.txt";
    public static final int TIME_DELAY_ANIM = 150;

    public static final String PREF_BOOK = "PREF_BOOK";


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
        HHmmss,
        yyyyMMdd,
        yyyyMMddHHmmssSSS,
        yyyyMMddHHmmssSSZ,
        MMddyyyyHHmmssa,
        MMyyyy,
        ddMMyyyyHHmm,
        ddMMyyyy,
        ddMMyyyyHHmmss,
//        YYYY-MM-DD HH:MM:SS.SSS

        FULL;

        @Override
        public String toString() {
            if (this == HHmmss)
                return "HHmmss";
            if (this == yyyyMMdd)
                return "yyyyMMdd";
            if (this == yyyyMMddHHmmssSSS)
                return "yyyyMMddHHmmss";
            if (this == yyyyMMddHHmmssSSZ)
                return "yyyy-MM-dd'T'hh:mm:ssZ";
            if (this == MMyyyy)
                return "MM/yyyy";
            if (this == ddMMyyyy)
                return "dd/MM/yyyy";
            if (this == ddMMyyyyHHmmss)
                return "dd/MM/yyyy HH:mm:ss";
            if (this == ddMMyyyyHHmm)
                return "dd/MM/yyyy HH'h'mm";

            if (this == MMddyyyyHHmmssa)
                //2017-08-01T00:00:00+07:00
                return "MM/dd/yyyy HH:mm:ss a";

            if (this == FULL)
                return "yyyy-MM-dd HH:mm:ss";
            return super.toString();
        }
    }
    //endregion

    public enum KIEU_CHUONG_TRINH {
        PHAN_BO(0, "Chức năng phân bổ"),
        KIEM_DINH(1, "Chức năng gửi kiểm định");

        private int code;
        private String name;

        KIEU_CHUONG_TRINH(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum MENU_BOTTOM_KD {
        ALL(0, "Danh sách thiết bị"),
        DS_GHIM(1, "Danh sách chọn"),
        LICH_SU(2, "Lịch sử"),
        THONG_KE(3, "Thống kê");

        private int code;
        private String name;

        MENU_BOTTOM_KD(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum CHON {
        CHUA_GUI(0, "Chưa gửi"),
        GUI_THANH_CONG(1, "Đã gửi"),
        GUI_THAT_BAI(2, "Đã tồn tại");
        private int code;
        private String name;

        CHON(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static CHON findNameBy(int code) {
            for (CHON v : values()) {
                if (v.getCode() == code) {
                    return v;
                }
            }
            return null;
        }
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
//        public String getName() {
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
        SimpleDateFormat df = new SimpleDateFormat(formatDate.toString());
//        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(Calendar.getInstance().getTime());
    }

    public static String convertDateToDate(String time, DATE_TIME_TYPE typeDefault, DATE_TIME_TYPE typeConvert) {
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
        SimpleDateFormat formatter = new SimpleDateFormat(typeDefault.toString());
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateParse = (Date) formatter.parse(dateUI);
        return dateParse;
    }

    public static long convertDateToLong(String date, DATE_TIME_TYPE typeDefault) {
        SimpleDateFormat formatter = new SimpleDateFormat(typeDefault.toString());
//        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date dateParse;
        try {
            dateParse = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

        return dateParse.getTime();
    }

    public static String convertLongToDate(long time, Common.DATE_TIME_TYPE format) {
        if (time < 0)
            return null;
        if (format == null)
            return null;

        SimpleDateFormat df2 = new SimpleDateFormat(format.toString());
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
    public static final String[] SPECIAL_PERMISSION = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    public static boolean isNeedPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            boolean isAllAllowed = false;
            ArrayList<String> permissionNeedAllow = new ArrayList<>();

            for (String permission :
                    SPECIAL_PERMISSION) {
                if (checkSelfPermission(GCSApplication.getContext(), permission) != PERMISSION_GRANTED)
                    permissionNeedAllow.add(permission);
            }

            if (permissionNeedAllow.size() != 0) {
                requestPermissions(activity, permissionNeedAllow.toArray(new String[permissionNeedAllow.size()]), REQUEST_CODE_PERMISSION);
                return true;
            }
            return false;
        } else {
            return false;
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
}
