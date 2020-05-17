package e.user.rxjavatest.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

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
        Log.d("TAG","onNestedScroll==>type="+type+",dyConsumed="+dyConsumed+",dyUnconsumed="+dyUnconsumed);
        ViewPageHolder holder = (ViewPageHolder) recyclerView.getLastViewHolder();
        Log.d("TAG","target = "+target.getClass().getSimpleName()+",holder="+(holder==null));
        if(holder != null){
            holder.changeTab(recyclerView.canScrollVertically(1));
            if(dyUnconsumed != 0){
                holder.getScrollView().scrollBy(0,dyUnconsumed);
            }
        }

    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
        Log.d("TAG","parent==>onNestedPreScroll dy="+dy+",type = "+type);
        Log.d("TAG","parent==>onNestedPreScroll dy="+dy+",type = "+type);
        if(dy<0){
            ViewPageHolder holder = (ViewPageHolder) recyclerView.getLastViewHolder();
            if(holder !=null && holder.getScrollView().canScrollVertically(-1)){
                holder.getScrollView().scrollBy(0,dy);
                if(consumed == null) {
                    consumed = new int[2];
                }
                consumed[1] = dy;
            }
        }
    }
}
