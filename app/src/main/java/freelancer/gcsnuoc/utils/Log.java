package freelancer.gcsnuoc.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static android.util.Log.*;
import static java.lang.System.lineSeparator;

/**
 * Created by VinhNB on 10/4/2017.
 */

public class Log {
    private static boolean isModeDebug = false;
    private static Log sInstance;
    private static String sUriLogFile;
    private static Context sContext;

    private static final String EMPTY_PATH = "Đường dẫn rỗng!";
    private static final String EMPTY_CONTEXT = "Context rỗng!";
    private static final String EMPTY_PERMISSION = "Ứng dụng chưa được cấp quyền ghi đọc thẻ nhớ!";
    private static final String TYPE_DATE_LOG = "dd-MM-yyyy HH:mm:ss";

    private static enum TYPE_LOG {
        v("Logv"),
        d("Logd"),
        i("Logi"),
        w("Logw"),
        e("Loge"),
        a("Loga");
        private String content;

        TYPE_LOG(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    private Log() {

    }

    public Log setIsModeDebug(boolean isModeDebug) {
        Log.isModeDebug = isModeDebug;

        return sInstance;
    }

    public static String getsUriLogFile() {
        return sUriLogFile;
    }

    public Log setupFile(@NonNull Context context, @NonNull String uriLogFile) throws Exception {
        if (context == null)
            throw new NullPointerException(EMPTY_CONTEXT);
        if (TextUtils.isEmpty(uriLogFile.trim()))
            throw new NullPointerException(EMPTY_PATH);

        sUriLogFile = uriLogFile.trim();
        sContext = context;

        this.createFileIfNotExist(uriLogFile);
        return sInstance;
    }

    public static boolean isModeDebug() {
        return isModeDebug;
    }

    public static Log getInstance() {
        if (sInstance == null) {
            sInstance = new Log();
        }
        return sInstance;
    }

    public void logv(Class<?> classz, String messsage) throws Exception {
        //loge
        String tag = classz.getSimpleName();
        w(tag, messsage);

        if (isModeDebug)
            //write file
            writeFileLog(TYPE_LOG.v, messsage);
    }

    public void logd(Class<?> classz, String messsage) throws Exception {
        //loge
        String tag = classz.getSimpleName();
        w(tag, messsage);

        if (isModeDebug)
            //write file
            writeFileLog(TYPE_LOG.d, messsage);
    }

    public void logi(Class<?> classz, String messsage) throws Exception {
        //loge
        String tag = classz.getSimpleName();
        w(tag, messsage);

        if (isModeDebug)
            //write file
            writeFileLog(TYPE_LOG.i, messsage);
    }

    public void logw(Class<?> classz, String messsage) throws Exception {
        //loge
        String tag = classz.getSimpleName();
        w(tag, messsage);

        if (isModeDebug)
            //write file
            writeFileLog(TYPE_LOG.w, messsage);
    }

    public void loge(Class<?> classz, String messsage) throws Exception {
        //loge
        String tag = classz.getSimpleName();
        e(tag, messsage);

        if (isModeDebug)
            //write file
            writeFileLog(TYPE_LOG.e, messsage);
    }

    public void loga(Class<?> classz, String messsage) throws Exception {
        //loge
        String tag = classz.getSimpleName();
        w(tag, messsage);

        if (isModeDebug)
            //write file
            writeFileLog(TYPE_LOG.a, messsage);
    }


    private boolean createFileIfNotExist(String sUriLogFile) throws Exception {
        if (TextUtils.isEmpty(sUriLogFile))
            throw new Exception(EMPTY_PATH);

        //check permission
        if (!isHasPermission())
            throw new Exception(EMPTY_PERMISSION);

        //create file
        File file = new File(sUriLogFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
            showFolderSdcard();
            return false;
        }

        return true;
    }

    private boolean isHasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(sContext.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    private void writeFileLog(TYPE_LOG typeLog, String messsage) throws Exception {
        //create file if not exist
        this.createFileIfNotExist(sUriLogFile);
        File fileLog = new File(sUriLogFile);
        String textOld = getDataFileLog(fileLog);

        PrintWriter writer = new PrintWriter(fileLog, "UTF-8");
        writer.print(textOld);
        writer.print("\n");
        writer.print("\n");
        writer.print("\n");
        writer.println("====================================================");
        writer.println(getDateTimeNow() + "----SESSION  " + typeLog.getContent() + "----- ");
        writer.println("====================================================");
        writer.print("\n");
        writer.println(messsage);
        writer.print("\n");
        writer.close();
    }

    public static String getDateTimeNow() {
        SimpleDateFormat df = new SimpleDateFormat(TYPE_DATE_LOG);
        return df.format(Calendar.getInstance().getTime());
    }


    private String getDataFileLog(File fileLog) throws IOException {
        StringBuilder result = new StringBuilder();
        String sCurrentLine = "";
        BufferedReader br = null;
        FileReader fr = null;

        try {
            InputStream inputStream = new FileInputStream(fileLog);

            fr = new FileReader(fileLog);
            br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            while ((sCurrentLine = br.readLine()) != null) {
                result.append(sCurrentLine);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    result.append(lineSeparator());
                } else
                    result.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }


    private void showFolderSdcard() throws Exception {
        // Load root folder
        File forder = new File(sUriLogFile);
        String parentPath = forder.getParentFile().getName();
        File folderParent = new File(parentPath);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String[] allFilesRoot = forder.list();
            for (int i = 0; i < allFilesRoot.length; i++) {
                allFilesRoot[i] = folderParent + allFilesRoot[i];
            }
            if (allFilesRoot != null)
                MediaScannerConnection.scanFile(sContext, allFilesRoot, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                                d("ExternalStorage", "Scanned " + path + ":");
                                d("ExternalStorage", "uri=" + uri);
                            }
                        });
        } else {
            sContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri
                    .parse("file://" + parentPath)));
        }
    }


}
