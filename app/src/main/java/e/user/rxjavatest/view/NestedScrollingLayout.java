package e.user.rxjavatest.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import e.user.rxjavatest.R;

public class NestedScrollingLayout extends RelativeLayout implements NestedScrollingParent2 {
    private static final String TAG = "StickyNavLayout";

    private NestedScrollView scrollView;
    private View imageView;
    private TextView titleView;
    private int maxTransY,titleTransY;
    private NestedScrollingParentHelper parentHelper;
    private boolean bottomToTop; //是否 从下往上滑动

    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int nestedScrollAxes,int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int nestedScrollAxes,int type) {
        parentHelper.onNestedScrollAccepted(child,target,nestedScrollAxes,type);
    }

    @Override
    public void onStopNestedScroll(@NonNull View target,int type) {
        parentHelper.onStopNestedScroll(target,type);
        scrollAnim();//滑动结束后动画补位
    }

    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed,int type) {
        translationScrollView(dyUnconsumed);
    }

    /**
     * dx和dy参数表示滑动的横向和纵向距离，consumed参数表示消耗的横向和纵向距离，如纵向滚动，只需要消耗了dy/2，表示父View和内部View分别处理这次滚动距离的 1/2
     *
     * @param target   内部View
     * @param dx       滑动的横向距离
     * @param dy       滑动的纵向距离
     * @param consumed 外部View消耗的横向和纵向距离
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,int type) {
        bottomToTop = dy>0;
        if(scrollView.getTranslationY()==0){
            //当前scrollView 在顶部
            if(dy<0 && !scrollView.canScrollVertically(1)){
                //向下滑动
                consumed[1] = translationScrollView(dy);
            }
        }else{
            if(dy>0 && !scrollView.canScrollVertically(-1)){
                consumed[1] = translationScrollView(dy);
            }
        }
    }

    @Override
    public int getNestedScrollAxes() {
        return ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    private int translationScrollView(int dy){
        if(dy==0)return 0;
        else if(scrollView.getTranslationY()<=0 && dy>=0) return 0;
        else if(scrollView.getTranslationY()>=maxTransY && dy<=0) return 0;
        else{
            float scrollY = scrollView.getTranslationY() - dy;
            if(scrollY <0){//顶部状态矫正
                scrollView.setTranslationY(0);
                imageView.setTranslationY(-maxTransY/2);
                titleView.setTranslationY(-titleTransY);
                scrollY = dy - scrollView.getTranslationY();
            }else if(scrollY >maxTransY){//底部状态矫正
                scrollView.setTranslationY(maxTransY);
                imageView.setTranslationY(0);
                titleView.setTranslationY(0);
                scrollY = maxTransY - scrollView.getTranslationY() + dy;
            }else{
                scrollView.setTranslationY(scrollY);
                imageView.setTranslationY(imageView.getTranslationY() - dy/2);
                scrollY = dy;
                titleChange(dy);
            }
            return (int)scrollY;
        }
    }

    private void titleChange(int dy){
        float translationY = scrollView.getTranslationY();
        if(Math.abs(translationY) <= titleTransY){
            //开始移动标题
            titleView.setTranslationY(titleView.getTranslationY() - dy);
//            titleView.setTranslationX(titleView.getTranslationX() + dy);
            float scale = 1 - Math.abs(titleView.getTranslationY())/titleTransY;
            if(scale<0.4) scale = 0.4f;
            titleView.setScaleX(scale);
            titleView.setScaleY(scale);
//            titleView.setTextSize(Math.max(10,(titleTransY - Math.abs(titleView.getTranslationY()))/10));
        }
    }

    private void scrollAnim(){
        if(scrollView.getTranslationY()<=titleTransY || scrollView.getTranslationY()== maxTransY) return;
        float curTranslationY = scrollView.getTranslationY();
        float lastTranslationY = bottomToTop?titleTransY:maxTransY;
        float time = bottomToTop?Math.abs(curTranslationY):maxTransY-Math.abs(curTranslationY);
        ObjectAnimator animator = ObjectAnimator.ofFloat(scrollView, "translationY", curTranslationY,lastTranslationY);
        animator.setDuration((long)time/6);
        animator.start();
        imageAnim();
    }

    private void imageAnim(){
        float curTranslationY = imageView.getTranslationY();
        float lastTranslationY = bottomToTop?(titleTransY-maxTransY)/2:0;
        float time = bottomToTop?curTranslationY:(titleTransY-maxTransY)/2-curTranslationY;
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView, "translationY", curTranslationY,lastTranslationY);
        animator.setDuration((long)Math.abs(time/6));
        animator.start();
    }

    public NestedScrollingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        parentHelper = new NestedScrollingParentHelper(this);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        imageView = findViewById(R.id.image_view);
        scrollView = findViewById(R.id.nested_view);
        titleView = findViewById(R.id.title_tv);
        maxTransY = getResources().getDimensionPixelOffset(R.dimen.size_256dp);
        titleTransY = getResources().getDimensionPixelOffset(R.dimen.dp_50);
        scrollView.setTranslationY(maxTransY);
    }
}
