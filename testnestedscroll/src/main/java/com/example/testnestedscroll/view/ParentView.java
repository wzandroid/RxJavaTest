package com.example.testnestedscroll.view;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class ParentView extends RelativeLayout implements NestedScrollingParent2 {

    private View imageView;
    private RecyclerView firstView,bottomView;

    public ParentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        firstView = (RecyclerView) getChildAt(0);
        imageView = getChildAt(1);
        bottomView = (RecyclerView) getChildAt(2);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        //如果是竖直方向滑动，那么启动嵌套滑动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {

    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {

    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //这里的Consumed代表NestScrollView消耗的距离， Unconsumed代表NestScrollView未消耗的距离
        //imageRight根据NestScrollView滑动的距离而进行相应的滑动、。
        Log.d("TAG","dyConsumed = "+dyConsumed+",dyUnconsumed = "+dyUnconsumed);
        int index = indexOfChild(target);
        if(index == 0){
            //第一个recyclerView 滑动
            if(dyUnconsumed!=0){
                if(dyUnconsumed<0 &&isVisibleLocal(firstView))return;
                int showHeight = getViewShowH(firstView);
                if(Math.abs(dyUnconsumed)>showHeight){
//                    ViewGroup.LayoutParams lp = firstView.getLayoutParams();
//                    lp.height = lp.height - showHeight;
//                    firstView.setLayoutParams(lp);
//                    ViewGroup.LayoutParams lp2 = bottomView.getLayoutParams();
//                    lp2.height = lp2.height+showHeight;
//                    bottomView.setLayoutParams(lp2);
                    scrollBy(0,showHeight);
                }else{
//                    ViewGroup.LayoutParams lp = firstView.getLayoutParams();
//                    lp.height = lp.height - dyUnconsumed;
//                    firstView.setLayoutParams(lp);
//                    ViewGroup.LayoutParams lp2 = bottomView.getLayoutParams();
//                    lp2.height = lp2.height+dyUnconsumed;
//                    bottomView.setLayoutParams(lp2);
                    scrollBy(0,dyUnconsumed);
                }
            }
        }else{
            Log.d("TAG","底部View滑动完成");
            //其他的滑动控件
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        int index = indexOfChild(target);
        if(index == 0){
            Log.d("TAG","顶部View要滑动了");
            //第一个View要滑动
            if(bottomView.getLocalVisibleRect(new Rect())){
                //最后一个View可见
                int showHeight = getViewShowH(bottomView);
                if(Math.abs(dy)>showHeight){
                    scrollBy(0,showHeight);
                    consumed[1] = showHeight;
                }else{
                    scrollBy(0,dy);
                    consumed[1] = dy;
                }
            }
        }else{
            //底部View要滚动
            if(firstView.getLocalVisibleRect(new Rect())){
                //顶部View显示出来了
                int showHeight = getViewShowH(firstView);
                if(Math.abs(dy)>showHeight){
                    scrollBy(0,showHeight);
                    consumed[1] = showHeight;
                }else{
                    scrollBy(0,dy);
                    consumed[1] = dy;
                }
            }
        }
    }

    private int getViewShowH(View view){
        Rect rect = new Rect();
        view.getLocalVisibleRect(rect);
        Log.d("TAG","view.top="+rect.top+",view.bottom="+rect.bottom);
        return rect.bottom-rect.top;
    }

    //当 View 有一点点不可见时立即返回false!
    private static boolean isVisibleLocal(View target){
        Rect rect =new Rect();
        target.getLocalVisibleRect(rect);
        return rect.top==0;
    }
}
