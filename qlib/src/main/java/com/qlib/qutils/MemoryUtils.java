package com.qlib.qutils;

import android.app.ActivityManager;
import android.content.Context;

import static android.content.Context.ACTIVITY_SERVICE;

public class MemoryUtils {
    public static float getAppMemorySize(Context context, int type) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService (ACTIVITY_SERVICE);
        //最大分配内存
        int memory = activityManager.getMemoryClass ();

        System.out.println ("memory: " + memory);
        //最大分配内存获取方法(app在当前手机的最大分配内存)
        float maxMemory = (float) (Runtime.getRuntime ().maxMemory () * 1.0 / (1024 * 1024));
        //当前分配的总内存(当前APP已经使用的内存)
        float totalMemory = (float) (Runtime.getRuntime ().totalMemory () * 1.0 / (1024 * 1024));
        //剩余内存
        float freeMemory = (float) (Runtime.getRuntime ().freeMemory () * 1.0 / (1024 * 1024));
        if (type == 0)
            return maxMemory;
        else if (type == 1)
            return totalMemory;
        else
            return freeMemory;
    }
}
