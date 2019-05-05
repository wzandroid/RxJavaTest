package e.user.rxjavatest.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.holder.ViewPageHolder;

public class MyRecyclerView extends RecyclerView /*implements NestedScrollingParent2*/ {

//    private NestedScrollingParentHelper mParentHelper;

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        ViewHolder lastView = getLastViewHolder();
        if(lastView != null ){
            if(isTouchPointInView(((ViewPageHolder)lastView).getScrollView(), (int)ev.getRawX(),(int)ev.getRawY())){
                //tabLayout区域事件不拦截滑动区域事件不拦截
                return false;
            }else return isScrollTop(((ViewPageHolder)lastView).getScrollView());
        }else
            return super.onInterceptTouchEvent(ev);
    }

//    @Override
//    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes, int type) {
//        //如果是竖直方向滑动，就启动嵌套滑动
//        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(@NonNull View view, @NonNull View target, int axes, int type) {
//        Log.d("TAG","onNestedScrollAccepted");
//        mParentHelper.onNestedScrollAccepted(view,target,axes,type);
//    }
//
//    @Override
//    public int getNestedScrollAxes() {
//        return mParentHelper.getNestedScrollAxes();
//    }
//
//    @Override
//    public void onStopNestedScroll(@NonNull View target, int type) {
//        Log.d("TAG","onStopNestedScroll");
//        mParentHelper.onStopNestedScroll(target,type);
//    }
//
//    @Override
//    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
//        //这里的Consumed代表NestScrollView消耗的距离， Unconsumed代表NestScrollView未消耗的距离
//        //imageRight根据NestScrollView滑动的距离而进行相应的滑动。
//        Log.d("TAG","onNestedScroll==>dyConsumed="+dyConsumed+",dyUnconsumed="+dyUnconsumed);
//        scrollBy(0,dyUnconsumed);
//    }
//
//
//
//    @Override
//    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @Nullable int[] consumed, int type) {
//        Log.d("TAG","onNestedPreScroll ==》dy="+dy);
//        ViewHolder lastHolder = getLastViewHolder();
//        if(lastHolder != null && lastHolder.getItemViewType() == MultiAdapter.PAGE_TYPE){
//            //底部列表可见
//            int canScrollY = lastHolder.itemView.getTop();
//            if(canScrollY>0){
//                //底部列表没有全部显示出来
//                scrollBy(0,dy);
//                consumed[1]= dy;
//            }
//        }
////        this.dispatchNestedPreScroll(dx, dy, consumed, (int[])null, type);
//    }

    //获取列表最后一个可见的ViewHolder
    public ViewHolder getLastViewHolder(){
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
        if(manager!=null){
            int posLast = manager.findLastVisibleItemPosition();
            int posStart = manager.findFirstVisibleItemPosition();
            ViewHolder holder = getChildViewHolder(getChildAt(posLast-posStart));
            return holder.getItemViewType() == MultiAdapter.PAGE_TYPE? holder:null;
        }
        return null;
    }

    private boolean isScrollTop(RecyclerView target){
        if(target == null)return true;
        LinearLayoutManager manager = (LinearLayoutManager) target.getLayoutManager();
        return manager!=null && manager.findFirstCompletelyVisibleItemPosition() == 0;
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
