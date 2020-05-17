package com.example.testnestedscroll.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Property;

public class LikeAnimView extends AppCompatImageView {

    private float currentProcess;
    private int maxSize;
    private int centerX,centerY;
    private Bitmap likeBitmap;
    private Bitmap unLikeBitmap;
    private RectF drawRect = new RectF();
    private Paint iconPaint;


    public LikeAnimView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LikeAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        iconPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxSize = w / 2;
        centerX = w/2;
        centerY = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(likeBitmap,null,drawRect,iconPaint);

    }

    public float getCurrentProcess(){
        return currentProcess;
    }

    public void setCurrentProcess(float currentProcess){
        this.currentProcess = currentProcess;
        if(currentProcess <= 1 && currentProcess >= 0){
            drawRect.left = getWidth() * ( 1 - currentProcess ) / 2;
            drawRect.right = getWidth() - drawRect.left;
            drawRect.top = getHeight() * ( 1 - currentProcess ) / 2;
            drawRect.bottom = getHeight() - drawRect.top;
        }else{
            drawRect.left = 0;
            drawRect.top = 0;
            drawRect.right = getWidth();
            drawRect.bottom = getHeight();
        }
        postInvalidate();
    }

    public static final Property<LikeAnimView, Float> DOTS_PROGRESS = new Property<LikeAnimView, Float>(Float.class, "heartSize") {
        @Override
        public Float get(LikeAnimView object) {
            return object.getCurrentProcess();
        }

        @Override
        public void set(LikeAnimView object, Float value) {
            object.setCurrentProcess(value);
        }
    };
}
