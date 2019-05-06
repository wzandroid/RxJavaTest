package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import e.user.rxjavatest.adapter.MultiAdapter;

public class MyRecyclerView extends RecyclerView {
    private static final int LAST_TYPE = MultiAdapter.PAGE_TYPE;

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewHolder lastView = getLastViewHolder();
        if(lastView != null ){
            if(isTouchPointInView(lastView.itemView, (int)ev.getRawX(),(int)ev.getRawY())){
                //tabLayout区域事件不拦截滑动区域事件不拦截
                return false;
//            }else{
//                return canScrollVertically(1);//isScrollTop(((ViewPageHolder)lastView).getScrollView());
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow, int type) {
        boolean isNestedScroll = super.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow, type);
        if(dyUnconsumed>0 && canScrollVertically(1)){
            Log.d("TAG","dyUnconsumed = " + dyUnconsumed+",canScrollVertically(i)="+canScrollVertically(1));
        }
        return isNestedScroll;
    }

    //获取列表最后一个可见的ViewHolder
    public ViewHolder getLastViewHolder(){
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        if(manager!=null){
            int posLast = manager.findLastVisibleItemPosition();
            int posStart = manager.findFirstVisibleItemPosition();
            ViewHolder holder = getChildViewHolder(getChildAt(posLast-posStart));
            return holder.getItemViewType() == LAST_TYPE? holder:null;
        }
        return null;
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
}
