package com.qlib.base;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class BaseViewModel extends ViewModel {
    protected Context mContext;

    protected void showToast(String msg) {
        if (TextUtils.isEmpty(msg))
            msg = "请求失败，请稍后再试";
        Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
    }

    // 强制关闭键盘
    protected void forceHideKeyboard(View myview) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(BaseActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myview.getWindowToken(), 0);
    }
}