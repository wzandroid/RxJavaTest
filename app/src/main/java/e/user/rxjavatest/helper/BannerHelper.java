package e.user.rxjavatest.helper;

import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

public class BannerHelper {
    private HorizontalLayoutManager layoutManager;
    private boolean autoPlay;
    private TaskHandler taskHandler;

    public BannerHelper(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        if(recyclerView == null){
            throw(new NullPointerException("recyclerView 不能为空"));
        }else{
            layoutManager = new HorizontalLayoutManager(recyclerView);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.removeOnItemTouchListener(onItemTouchListener);
            recyclerView.removeOnScrollListener(scrollListener);
            recyclerView.addOnScrollListener(scrollListener);
            recyclerView.addOnItemTouchListener(onItemTouchListener);
            recyclerView.setAdapter(adapter);

            taskHandler = new TaskHandler(layoutManager);
        }
    }

    public void setAutoPlay(boolean autoPlay){
        this.autoPlay = autoPlay;
        setPlaying(autoPlay);
    }

    public void setPlayDuration(int duration){
        if(taskHandler!=null){
            taskHandler.setDuration(duration);
        }
    }

    private RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setPlaying(false);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setPlaying(true);
                    break;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    };

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                setPlaying(true);
            }
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            if (dx != 0) {
                setPlaying(false);
            }
        }
    };

    public void setBannerSelectCallBack(BannerSelectCallback selectCallBack){
        layoutManager.setBannerSelectCallback(selectCallBack);
    }

    private synchronized void setPlaying(boolean playing) {
        taskHandler.clearMsg();
        if(autoPlay && playing){
            taskHandler.setSendMsg();
        }
    }

    private static class HorizontalLayoutManager extends LinearLayoutManager /*implements RecyclerView.SmoothScroller.ScrollVectorProvider*/{
        private RecyclerView mRecyclerView;
        private BannerSelectCallback mSelectCallback;
        private int currentIndex;
        private int dragTotalX; //横向拖拽总距离
        private BannerSnapHelper snapHelper;


        public HorizontalLayoutManager(RecyclerView recyclerView){
            super(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL,false);
            snapHelper = new BannerSnapHelper();
            mRecyclerView = recyclerView;
            recyclerView.setLayoutManager(this);
            snapHelper.attachToRecyclerView(recyclerView);

        }

        private void setBannerSelectCallback(BannerSelectCallback selectCallback){
            mSelectCallback = selectCallback;
        }

        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        @Override
        public boolean canScrollHorizontally() {
            return true;
        }

        @Override
        public boolean canScrollVertically() {
            return false;
        }

        @Override
        public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
            if(getItemCount()==1){
                return super.scrollHorizontallyBy(dx,recycler,state);
            }
            if(isDragging){
                dragTotalX = dragTotalX+dx;
            }
            fill(dx, recycler);
            offsetChildrenHorizontal(dx * -1);
            recycleViews(dx,recycler);
            return dx;
        }

        private void fill(int dx, RecyclerView.Recycler recycler) {
            //左滑
            if (dx > -2) {
                while (true) {
                    //得到当前已添加（可见）的最后一个子View
                    View lastVisibleView = getChildAt(getChildCount() - 1);
                    if(lastVisibleView == null || lastVisibleView.getRight() - dx > getWidth()){
                        //如果滑动过后，View还是未完全显示出来就 不进行绘制下一个View
                        break;
                    }
                    //得到View对应的位置
                    int layoutPosition = getPosition(lastVisibleView);

                    /**
                     * 例如要显示20个View，当前可见的最后一个View就是第20个，那么下一个要显示的就是第一个
                     * 如果当前显示的View不是第20个，那么就显示下一个，如当前显示的是第15个View，那么下一个显示第16个
                     * 注意区分 childCount 与 itemCount
                     */
                    View nextView;
                    if(layoutPosition == getItemCount() -1){
                        nextView = recycler.getViewForPosition(0);
                    }else{
                        nextView = recycler.getViewForPosition(layoutPosition +1);
                    }
                    addView(nextView);
                    measureChildWithMargins(nextView, 0, 0);
                    int viewWidth = getDecoratedMeasuredWidth(nextView);
                    int viewHeight = getDecoratedMeasuredHeight(nextView);
                    int offsetX = lastVisibleView.getRight();
                    layoutDecorated(nextView, offsetX, 0, offsetX + viewWidth, viewHeight);
                }
            } else { //右滑
                while (true) {
                    View firstVisibleView = getChildAt(0);
                    if(firstVisibleView == null || firstVisibleView.getLeft() - dx < 0){
                        break;
                    }

                    int layoutPosition = getPosition(firstVisibleView);

                    /**
                     * 如果当前第一个可见View为第0个，则左侧显示第20个View 如果不是，下一个就显示前一个
                     */
                    View nextView;
                    if(layoutPosition == 0){
                        nextView = recycler.getViewForPosition(getItemCount() -1);
                    }else{
                        nextView = recycler.getViewForPosition(layoutPosition - 1);
                    }
                    addView(nextView, 0);
                    measureChildWithMargins(nextView, 0, 0);
                    int viewWidth = getDecoratedMeasuredWidth(nextView);
                    int viewHeight = getDecoratedMeasuredHeight(nextView);
                    int offsetX = firstVisibleView.getLeft();
                    layoutDecorated(nextView, offsetX - viewWidth, 0, offsetX, viewHeight);
                }
            }
        }

        private void recycleViews(int dx, RecyclerView.Recycler recycler) {
            for (int i=0; i< getChildCount();i++) {
                View childView = getChildAt(i);
                if(childView != null){
                    //左滑
                    if (dx > 0) {
                        //移除并回收 原点 左侧的子View
                        if (childView.getRight() - dx < 0) {
                            removeAndRecycleView(childView,recycler);
                        }
                    } else { //右滑
                        //移除并回收 右侧即RecyclerView宽度之以外的子View
                        if (childView.getLeft() - dx > getWidth()) {
                            removeAndRecycleView(childView,recycler);
                        }
                    }
                }
            }
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller scroller = new LinearSmoothScroller(recyclerView.getContext()){
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 0.2f;
                }

                @Override
                public PointF computeScrollVectorForPosition(int targetPosition) {
                    return new PointF(1,0);
                }
            };
            scroller.setTargetPosition(position);
            startSmoothScroll(scroller);

        }

        public void showNext(){
            currentIndex = (currentIndex+1)%getItemCount();
            mRecyclerView.smoothScrollToPosition(currentIndex);
        }

        private void postSelectIndex(int index){
            if(mSelectCallback!=null){
                mSelectCallback.onSelected(index,getItemCount());
            }
        }

        private void showSelect(){
            if(isDragging){
                postSelectIndex(snapHelper.getTargetPosition());
            }else{
                postSelectIndex(currentIndex);
            }
        }

        private boolean isDragging;

        @Override
        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);

            switch (state){
                case RecyclerView.SCROLL_STATE_IDLE:
                    //滚动停止时
                    break;
                case RecyclerView.SCROLL_STATE_DRAGGING:
                    //拖拽滚动时
                    isDragging = true;
                    break;
                case RecyclerView.SCROLL_STATE_SETTLING:
                    showSelect();
                    isDragging = false;
                    dragTotalX = 0;
                    break;
            }
        }
    }

    public interface BannerSelectCallback{
        void onSelected(int index, int count);
    }

    private static class TaskHandler extends Handler {
        private static final int MSG_PLAY = 100;
        private int PLAY_DURATION = 3000;

        private WeakReference<HorizontalLayoutManager> mWeakBanner;
        public TaskHandler(HorizontalLayoutManager bannerLayoutManager){
            this.mWeakBanner = new WeakReference<>(bannerLayoutManager);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg != null && msg.what == MSG_PLAY){
                HorizontalLayoutManager bannerLayoutManager = mWeakBanner.get();
                if (bannerLayoutManager != null){
                    bannerLayoutManager.showNext();
                }
            }
        }

        public void setDuration(int duration){
            PLAY_DURATION = duration;
        }

        private void clearMsg(){
            if(hasMessages(MSG_PLAY)){
                removeMessages(MSG_PLAY);
            }
        }

        public void setSendMsg(){
            clearMsg();
            sendEmptyMessageDelayed(MSG_PLAY,PLAY_DURATION);
        }

    }

    private static class BannerSnapHelper extends PagerSnapHelper {
        private int targetPosition;

        private int getTargetPosition(){
            return targetPosition;
        }

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
            int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            if(position >= layoutManager.getItemCount()){
                position = 0;
            }else{
                position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            }
            targetPosition = position;
            return position;
        }
    }
}