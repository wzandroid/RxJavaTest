package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.holder.ViewPageHolder;

public class MyParentView extends RelativeLayout implements NestedScrollingParent2 {
    private NestedScrollingParentHelper parentHelper;
    private MyRecyclerView recyclerView;
    private NestedScrollView scrollView;

    public MyParentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(View.OVER_SCROLL_NEVER);
    }

    public NestedScrollingParentHelper getParentHelper() {
         if(parentHelper == null) parentHelper = new NestedScrollingParentHelper(this);
         return parentHelper;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        recyclerView = findViewById(R.id.recycler_view);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {

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
        if(dyUnconsumed!=0 && recyclerView.getLastViewHolder() != null){
            if(target instanceof MyRecyclerView){
                Log.d("TAG","MyRecycler==>parent==>onNestedScroll==>dyConsumed="+dyConsumed+",dyUnconsumed="+dyUnconsumed);
                //如果外层有未消耗的滑动距离，那么交给内层recyclerView来滑动
                ((ViewPageHolder)recyclerView.getLastViewHolder()).getScrollView().scrollBy(0,dyUnconsumed);
            }else{
                Log.d("TAG","Recycler==>parent==>onNestedScroll==>dyConsumed="+dyConsumed+",dyUnconsumed="+dyUnconsumed);
                recyclerView.scrollBy(0,dyUnconsumed);
            }
        }
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        Log.d("TAG","parent==>onNestedPreScroll");
        if(target instanceof MyRecyclerView){

        }else{
            if(recyclerView.getLastViewHolder() != null ){
                int canScrollY = recyclerView.getLastViewHolder().itemView.getTop();
                Log.d("TAG","parent==>onNestedPreScroll==>canScrollY="+canScrollY+",dy="+dy+",type = "+type);
                if(canScrollY>0){
                    //底部列表没有全部显示出来
                    recyclerView.scrollBy(0,dy);
                    if(consumed == null) consumed = new int[2];
                    consumed[1]= dy;
                }
            }
        }
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }
}
