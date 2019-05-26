package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import e.user.rxjavatest.R;
import e.user.rxjavatest.utils.LogUtils;

public class NestedScrollingLayout extends RelativeLayout implements NestedScrollingParent2 {
    private static final String TAG = "StickyNavLayout";

    private NestedScrollView scrollView;
    private View imageView;
    private int maxTransY;
    private NestedScrollingParentHelper parentHelper;

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
        Log.d("TAG","scrollView.getTranslationY()="+scrollView.getTranslationY());
        consumed[1] = translationScrollView(dy);
    }

    @Override
    public int getNestedScrollAxes() {
        return ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    private int translationScrollView(int dy){
        if(dy==0)return 0;
        else if(scrollView.getTranslationY()<0 && dy>0) return 0;
        else if(scrollView.getTranslationY()>maxTransY && dy<0) return 0;
        else{
            float scrollY = scrollView.getTranslationY() - dy;
//            Log.d("TAG","scrollY="+scrollY);
//            if(scrollY <0){
//                scrollView.setTranslationY(0);
//                return (int)-scrollY;
//            }else if(scrollY >maxTransY){
//                scrollView.setTranslationY(maxTransY);
//                return (int) (maxTransY - scrollY);
//            }
            scrollView.setTranslationY(scrollY);
            imageView.setTranslationY(imageView.getTranslationY() - dy/2);
            return dy;
        }
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
        maxTransY = getResources().getDimensionPixelOffset(R.dimen.size_256dp);
        Log.d("TAG","maxTransY="+maxTransY);
        scrollView.setTranslationY(maxTransY);
    }
}
