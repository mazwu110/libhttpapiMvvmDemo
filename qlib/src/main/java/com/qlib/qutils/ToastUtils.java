package com.qlib.qutils;

import android.widget.Toast;
import com.qlib.base.QApp;

public class ToastUtils {
    private static Toast toast;
    private static QApp sContext;

    public static void init(QApp app) {
        sContext = app;
    }

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(sContext, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
            toast = Toast.makeText(sContext, msg, Toast.LENGTH_LONG);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }
}
