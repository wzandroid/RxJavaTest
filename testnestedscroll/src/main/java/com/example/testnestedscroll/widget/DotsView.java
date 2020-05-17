package com.example.testnestedscroll.widget;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

import com.example.testnestedscroll.Utils;

public class DotsView extends View {
    private static final int DOTS_COUNT = 8;
    private static final int OUTER_DOTS_POSITION_ANGLE = 360/DOTS_COUNT;

    private int[] COLORS = {0xA6B0E2,0xF7C76B,0xF4B2A2,0x6FB5F9,0xADE6E9,0xF09682,0xF907A3,0x79CDAD};

    private int width = 0;
    private int height = 0;

    private final Paint[] circlePaints = new Paint[8];

    private int centerX;
    private int centerY;

    private float maxDotsRadius;
    private float maxDotSize;

    private float currentProgress = 0;

    private float currentRadius = 0;
    private float currentDotSize = 0;


    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    public DotsView(Context context) {
        super(context);
        init();
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        for (int i = 0; i < circlePaints.length; i++) {
            circlePaints[i] = new Paint();
            circlePaints[i].setStyle(Paint.Style.FILL);
            circlePaints[i].setAntiAlias(true);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        maxDotSize = 5;
        maxDotsRadius = w / 2 - maxDotSize * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawOuterDotsFrame(canvas);
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (centerX + currentRadius * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (centerY + currentRadius * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            canvas.drawCircle(cX, cY, currentDotSize, circlePaints[i % circlePaints.length]);
        }
    }


    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;

        updateOuterDotsPosition();
        updateDotsPaints();
        updateDotsAlpha();

        postInvalidate();
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    private void updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.0f, 0.3f, 0, maxDotsRadius * 0.8f);
        } else {
            this.currentRadius = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.3f, 1f, 0.8f * maxDotsRadius, maxDotsRadius);
        }
        if (currentProgress == 0) {
            this.currentDotSize = 0;
        } else if (currentProgress < 0.7) {
            this.currentDotSize = maxDotSize;
        } else {
            this.currentDotSize = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.7f, 1f, maxDotSize, 0);
        }
    }

    private void updateDotsPaints() {
        if (currentProgress < 0.5f) {
            float progress = (float) Utils.mapValueFromRangeToRange(currentProgress, 0f, 0.5f, 0, 1f);
            for(int i=0;i<circlePaints.length;i++){
                circlePaints[i].setColor((Integer) argbEvaluator.evaluate(progress,
                        COLORS[i%COLORS.length], COLORS[(i+1)%COLORS.length]));
            }
        } else {
            float progress = (float) Utils.mapValueFromRangeToRange(currentProgress, 0.5f, 1f, 0, 1f);
            for(int i=0;i<circlePaints.length;i++){
                circlePaints[i].setColor((Integer) argbEvaluator.evaluate(progress,
                        COLORS[(i+1)%COLORS.length], COLORS[(i+2)%COLORS.length]));
            }
        }
    }

    public void setColors(@ColorInt int primaryColor, @ColorInt int secondaryColor) {

        invalidate();
    }

    private void updateDotsAlpha() {
        float progress = (float) Utils.clamp(currentProgress, 0.6f, 1f);
        int alpha = (int) Utils.mapValueFromRangeToRange(progress, 0.6f, 1f, 255, 0);
        for(Paint paint:circlePaints){
            paint.setAlpha(alpha);
        }
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (width != 0 && height != 0)
            setMeasuredDimension(width, height);
    }


    public static final Property<DotsView, Float> DOTS_PROGRESS = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };
}
