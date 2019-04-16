package e.user.rxjavatest.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import e.user.rxjavatest.GlobalApplication;

public class DisplayUtils {
    private static float mScale = 0.0f; // 密度
    private static int mDpi = 0; // dpi
    private static float mFontScale = 0.0f;
    private static int mWidthPixels = 0;
    private static int mHeightPixels = 0;
    private static int gridWithPixels = 0; //标准的两列gridView的item列宽

    public static void init() {
        DisplayMetrics displaysMetrics = new DisplayMetrics();// 初始化一个结构
        WindowManager wm = (WindowManager) GlobalApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displaysMetrics);// 对该结构赋值
        mWidthPixels = displaysMetrics.widthPixels;
        mHeightPixels = displaysMetrics.heightPixels;
        DisplayMetrics dm = GlobalApplication.getInstance().getResources().getDisplayMetrics();
        mScale = dm.density;
        mDpi = dm.densityDpi;
        mFontScale = dm.scaledDensity;
        gridWithPixels = (mWidthPixels - dip2px(12))/2;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        if (mScale == 0) {
            init();
        }
        return (int) (dpValue * mScale + 0.5f);
    }

    public static int getGridWithPx(){
        return gridWithPixels;
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        if (mScale == 0) {
            init();
        }
        return (int) (pxValue / mScale + 0.5f);
    }

    /**
     * 得到的屏幕的宽度
     */
    public static int getWidthPx() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        if (mWidthPixels == 0) {
            init();
        }
        return mWidthPixels;
    }

    /**
     * 得到的屏幕的高度
     */
    public static int getHeightPx() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        if (mHeightPixels == 0) {
            init();
        }
        return mHeightPixels;
    }

    /**
     * 得到屏幕的dpi
     */
    public static int getDensityDpi() {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        if (mDpi == 0) {
            init();
        }
        return mDpi;
    }

    /**
     * 返回状态栏/通知栏的高度
     */
    public static int getStatusHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int px2sp(float pxValue) {
        if (mFontScale == 0) {
            init();
        }
        return (int) (pxValue / mFontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     */
    public static int sp2px(float spValue) {
        if (mFontScale == 0) {
            init();
        }
        return (int) (spValue * mFontScale + 0.5f);
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getWidthPx();
        int height = getHeightPx();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getWidthPx();
        int height = getHeightPx();
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
                - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }
}
