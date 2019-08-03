package com.qlib.qutils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.text.DecimalFormat;

import static android.content.Context.TELEPHONY_SERVICE;

public class QUtils {
    //让手机重启 1 重启成功 cmd=reboot
    public static int PhoneReboot(String cmd) {
        int r = 0;
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(cmd);
            try {
                if (proc.waitFor() == 0) {
                    r = 1;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    public static String getDeviceId(Context context) {
//        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
//        String szImei = TelephonyMgr.getDeviceId();
//        if (TextUtils.isEmpty(szImei)) {
//            szImei = "000000000000";
//        }

        String szImei = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return szImei;
    }

    //获取应用包名
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getScreenWitdh(Context context) {
        return getOSDefaultDisplay(context)[0];
    }

    public static int getScreenHeight(Context context) {
        return getOSDefaultDisplay(context)[1];
    }

    // 获取手机屏幕分辨率
    public static int[] getOSDefaultDisplay(Context context) {
        int[] data = new int[2];
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        data[0] = dm.widthPixels; // 宽度
        data[1] = dm.heightPixels; // 高度

        return data;
    }
    public static int dp2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    // 获取两位小数的数字
    public static String get2PointValue(String value) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(value);
    }

    public static String get2PointValue(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        String tp = df.format(value / 1000);
        if (tp.startsWith(".")) {
            tp = "0" + tp;
        }
        return tp;
    }
}
