package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

public class MyTabLayout extends TabLayout implements NestedScrollingChild2 {

    private float downX ;    //按下时 的X坐标
    private float downY ;    //按下时 的Y坐标
    private NestedScrollingChildHelper childHelper;
    private final int[] mScrollOffset = new int[2];
    private final int[] mScrollConsumed = new int[2];

    private int mLastTouchX;
    private int mLastTouchY;

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        childHelper = new NestedScrollingChildHelper(this);
        childHelper.setNestedScrollingEnabled(true);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        float x= ev.getX();
//        float y = ev.getY();
//        switch (ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                //将按下时的坐标存储
//                downX = x;
//                downY = y;
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //获取到距离差
//                float dx= x-downX;
//                float dy = y-downY;
//                //通过距离差判断方向
//                int orientation = getOrientation(dx, dy);
//                int[] location={0,0};
//                getLocationOnScreen(location);
//                switch (orientation) {
//                    case 'b':
//                    case 't':
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                    case 'r':
//                    case 'l':
//                        getParent().requestDisallowInterceptTouchEvent(true);
//                        break;
//                }
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastTouchY = (int)(event.getRawY() + 0.5f);
                int nestedScrollAxis = ViewCompat.SCROLL_AXIS_NONE;
                nestedScrollAxis |= ViewCompat.SCROLL_AXIS_HORIZONTAL;  //按位或运算
                startNestedScroll(nestedScrollAxis);
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) (event.getX() + 0.5f);
                int y = (int) (event.getRawY() + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                mLastTouchY = y;
                mLastTouchX = x;
                if (dispatchNestedPreScroll(dx, dy, mScrollConsumed, mScrollOffset)) {
                    dy -= mScrollConsumed[1];
                    if (dy == 0) {
                        return true;
                    }
                } else {
                    scrollBy(0, dy);

                }
                break;
        }
        return true;
    }

    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx)>Math.abs(dy)){
            //X轴移动
            return dx>0?'r':'l';//右,左
        }else{
            //Y轴移动
            return dy>0?'b':'t';//下//上
        }
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        return childHelper.startNestedScroll(axes,type);
    }

    @Override
    public void stopNestedScroll(int type) {
        childHelper.stopNestedScroll(type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        return childHelper.hasNestedScrollingParent(type);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed,
                                        int dyUnconsumed, @Nullable int[] offsetInWindow, int type) {
        return childHelper.dispatchNestedScroll(dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,offsetInWindow,type);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow, int type) {
        return childHelper.dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow,type);
    }
}
