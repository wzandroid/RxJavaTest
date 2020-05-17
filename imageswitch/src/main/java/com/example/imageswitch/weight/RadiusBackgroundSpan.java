package com.example.imageswitch.weight;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.imageswitch.MyApplication;

public class RadiusBackgroundSpan extends ReplacementSpan {

    private int mBgColor;
    private int mRadius;
    private int mTextColor;
    private int mTextSize;
    private int mPaddingHorizontal;
    private String mText;
    private int mMarginLeft;
    private int mMarginRight;

    public RadiusBackgroundSpan(int bgColor, int radius, int textColor, int textSize, int paddingHorizontal, String text){
        mBgColor = bgColor;
        mRadius = radius;
        mTextColor = textColor;
        mTextSize = textSize;
        mPaddingHorizontal = paddingHorizontal;
        mText = text;
        mMarginLeft = dp2px(4);
        mMarginRight = dp2px(4);
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable Paint.FontMetricsInt fm) {
        paint.setTextSize(mTextSize);
        return (int) paint.measureText(mText) + 2 * mPaddingHorizontal + mMarginLeft + mMarginRight;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        final int offsetToTop = dp2px(2);
        paint.setTextSize(mTextSize);
        paint.setAntiAlias(true);
        RectF rect = new RectF();
        rect.left = (int) x + mMarginLeft;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int marginVertical = (bottom - top - fontMetrics.descent + fontMetrics.top)/2;
        rect.top = top + marginVertical - offsetToTop; //视觉感觉偏下了，往上一点点
        rect.bottom = bottom - marginVertical;
        rect.right = rect.left + (int) paint.measureText(mText) + 2*mPaddingHorizontal;

        paint.setColor(mBgColor);
        canvas.drawRoundRect(rect, mRadius, mRadius, paint);

        paint.setColor(mTextColor);
        float fontShouldOffsetTop = ((fontMetrics.descent - fontMetrics.ascent)/2f+fontMetrics.ascent)/2 - offsetToTop/2f;
        canvas.drawText(mText,x + mPaddingHorizontal + mMarginLeft,y + fontShouldOffsetTop,paint);
    }

    private static int dp2px(float dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    private static Resources getResources() {
        return MyApplication.getAppContext().getResources();
    }
}
