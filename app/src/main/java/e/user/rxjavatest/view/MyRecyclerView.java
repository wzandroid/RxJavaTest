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
