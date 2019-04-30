package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class MyRecyclerView2 extends RecyclerView {

    public MyRecyclerView2(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("TAG","MyRecyclerView2 初始化");
    }

    @Override
    public boolean startNestedScroll(int axes, int type) {
        Log.d("TAG","2 startNestedScroll,axes="+axes+",type="+type);
        return super.startNestedScroll(axes, type);
    }

    @Override
    public boolean hasNestedScrollingParent(int type) {
        Log.d("TAG","2 hasNestedScrollingParent = "+super.hasNestedScrollingParent());
        return super.hasNestedScrollingParent(type);
    }
}
