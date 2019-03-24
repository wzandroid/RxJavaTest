package e.user.rxjavatest.utils;

import android.util.Log;

public class LogUtils {
    private static final String TAG = "TAG";

    public static void d(String msg){
        Log.d(TAG,msg+"  :"+Thread.currentThread().getName());
    }

    public static void e(String msg) {
        Log.e(TAG,msg + "  :"+Thread.currentThread().getName());
    }
}
