package e.user.rxjavatest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.R;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-10-07
 */
public class MyTabView extends View {
    private Paint paint = new Paint();
    private Paint linePaint = new Paint();
    private Rect mBound = new Rect();
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private float mTextSize;
    /**
     * 标签的间隔大小
     */
    private int tabSpace;
    private int tabPaddingWith;
    private int tabPaddingHeight;
    private List<String> tabList = new ArrayList<>();
    /**
     * 真正需要绘制的标签
     */
    private List<String> textList = new ArrayList<>();

    public MyTabView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyTabView, defStyleAttr, 0);
        mTextColor = a.getColor(R.styleable.MyTabView_tab_color, Color.BLACK);
        mTextSize = a.getDimensionPixelSize(R.styleable.MyTabView_tab_size, 100);
        tabSpace = a.getDimensionPixelOffset(R.styleable.MyTabView_tab_space,30);
        tabPaddingWith = a.getDimensionPixelSize(R.styleable.MyTabView_tab_padding_with,10);
        tabPaddingHeight = a.getDimensionPixelSize(R.styleable.MyTabView_tab_padding_height,4);
        a.recycle();  //注意回收
        init();
    }

    private void init(){
        paint.setTextSize(mTextSize);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setColor(mTextColor);

        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(Color.WHITE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(2);
        mBound = new Rect();
    }

    public void setTabList(List<String> tabList){
        this.tabList.clear();
        this.textList.clear();
        this.tabList.addAll(tabList);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int padding = getPaddingLeft() + getPaddingRight();
        int specWidth = widthSize - padding;
        int textWith = 0;
        if(textList.size()==0){
            for(int i=0;i<tabList.size();i++){
                paint.getTextBounds(tabList.get(i),0,tabList.get(i).length(),mBound);

                if(textWith + mBound.width() > specWidth){
                    break;
                }else{
                    textWith = textWith + mBound.width() + tabSpace + tabPaddingWith*2;
                    textList.add(tabList.get(i));
                }
            }
        }
        int width;
        int height ;
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            width =  textWith + padding;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else{
            height = mBound.height() +getPaddingTop()+getPaddingBottom()+tabPaddingHeight*2;
        }
        Log.d("Wz","with="+width+",height="+height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int startWith = getPaddingLeft();
        for(int i = 0; i<textList.size(); i++){
            paint.getTextBounds(textList.get(i), 0, textList.get(i).length(), mBound);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            int baseLineY = (int) (mBound.height()/2 - fontMetrics.top/2 - fontMetrics.bottom/2 + tabPaddingHeight);
            canvas.drawText(textList.get(i),   tabPaddingWith+startWith + i*tabSpace, getPaddingTop()+baseLineY, paint);
            drawRoundRect(canvas,startWith+i*tabSpace);
            startWith = startWith + mBound.width() + tabPaddingWith*2;
        }
    }

    private void drawRoundRect(Canvas canvas,int startWith){
        RectF rectF = new RectF();
        rectF.left = startWith+linePaint.getStrokeWidth()/2;
        rectF.right = rectF.left+mBound.width()+tabPaddingWith*2;
        rectF.top = getPaddingTop();
        rectF.bottom = rectF.top + mBound.height() + tabPaddingHeight*2;
        canvas.drawRoundRect(rectF,10,10,linePaint);
    }
}
