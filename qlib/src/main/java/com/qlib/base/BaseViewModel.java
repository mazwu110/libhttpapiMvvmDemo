package com.qlib.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.qlib.qutils.ToastUtils;

public class BaseViewModel extends ViewModel {
    protected Handler myHandler = new Handler(Looper.getMainLooper());
    protected Context mContext;

    protected void showToast(String msg) {
        if (TextUtils.isEmpty(msg))
            msg = "请求失败，请稍后再试";

        final String showMsg = msg;
        myHandler.post(()->ToastUtils.showToast (showMsg));
    }

    // 强制关闭键盘
    protected void forceHideKeyboard(View myview) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(BaseActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
    }
}