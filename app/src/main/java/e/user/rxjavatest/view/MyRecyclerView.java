package e.user.rxjavatest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent2;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.holder.ViewPageHolder;

public class MyRecyclerView extends RecyclerView {

    private static final int LAST_TYPE = MultiAdapter.PAGE_TYPE;

    public MyRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("Wz","homeRecyclerView onSizeChanged");
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        Log.d("Wz","homeRecyclerView onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d("Wz","homeRecyclerView onLayout");
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        Log.d("Wz","homeRecyclerView onDraw");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.d("Wz","homeRecyclerView dispatchDraw");
    }

    //获取列表最后一个可见的ViewHolder
    public ViewHolder getLastViewHolder(){
        LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();

        if(manager!=null ) {
            View view = manager.findViewByPosition(manager.getItemCount()-1);
            if(view!=null){
                ViewHolder holder = getChildViewHolder(view);
                return holder.getItemViewType() == LAST_TYPE? holder:null;
            }
        }
        return null;
    }
}
