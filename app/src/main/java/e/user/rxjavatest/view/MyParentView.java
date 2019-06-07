package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.holder.ViewPageHolder;

public class MyParentView extends RelativeLayout implements NestedScrollingParent2 {
    private NestedScrollingParentHelper parentHelper;
    private MyRecyclerView recyclerView;

    public MyParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public NestedScrollingParentHelper getParentHelper() {
         if(parentHelper == null) parentHelper = new NestedScrollingParentHelper(this);
         return parentHelper;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
        //设置启动纵向嵌套滚动
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes, int type) {
        getParentHelper().onNestedScrollAccepted(child,target,axes,type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target, int type) {
        getParentHelper().onStopNestedScroll(target,type);
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        Log.d("TAG","parent==>onNestedScroll==>type="+type);
        ViewPageHolder holder = (ViewPageHolder) recyclerView.getLastViewHolder();
        if(dyUnconsumed != 0 && holder != null){
            if(target instanceof MyRecyclerView){
                //如果外层有未消耗的滑动距离，那么交给内层recyclerView来滑动
                holder.getScrollView().scrollBy(0,dyUnconsumed);
            }else if(target instanceof RecyclerView){
                recyclerView.scrollBy(0,dyUnconsumed);
                if(holder != null){
                    holder.allScrollTop();
                }
            }
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        Log.d("TAG","parent==>onNestedPreScroll");

        if(target instanceof RecyclerView){
            if(target instanceof MyRecyclerView)return;
            if(dy>0 && recyclerView.canScrollVertically(1)){//向下滚动时
                //如果外层可以滚动，优先外层滚动
                recyclerView.scrollBy(0,dy);
                if(consumed == null) consumed = new int[2];
                consumed[1] = dy;
            }else{//向上滚动时，

            }
        }
    }
}