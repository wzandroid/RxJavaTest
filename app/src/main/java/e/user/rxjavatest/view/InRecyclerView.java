package e.user.rxjavatest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewParent;

public class InRecyclerView extends RecyclerView {

    private float downX ;    //按下时 的X坐标
    private float downY ;    //按下时 的Y坐标

    public InRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        float x = ev.getX();
//        float y = ev.getY();
//        ViewParent viewParent = findRecyclerParent();
//        switch(ev.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                downX = x;
//                downY = y;
//                if(viewParent!=null)viewParent.requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dx = x - downX;
//                float dy = y - downY;
//                int orientation = getOrientation(dx,dy);
//                switch(orientation){
//                    case 'b':
//                        if(canScrollVertically(-1)){
//                            if(viewParent!=null)viewParent.requestDisallowInterceptTouchEvent(false);
//                        }else{
//                            if(viewParent!=null)viewParent.requestDisallowInterceptTouchEvent(true);
//                        }
//                        break;
//                    case 't':
//                        RecyclerView recyclerView = (RecyclerView) viewParent;
//                        if(recyclerView!=null ){
//                            if(recyclerView.canScrollVertically(-1)){
//                                recyclerView.requestDisallowInterceptTouchEvent(true);
//                            }else{
//                                recyclerView.requestDisallowInterceptTouchEvent(false);
//                            }
//                        }
//                        break;
//                    case 'l':
//                    case 'r':
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent e) {
//        float x= e.getX();
//        float y = e.getY();
//        switch (e.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                //将按下时的坐标存储
//                downX = x;
//                downY = y;
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //获取到距离差
//                float dx= x-downX;
//                float dy = y-downY;
//                Log.d("ACTION_MOVE","ACTION_MOVE");
//                //通过距离差判断方向
//                int orientation = getOrientation(dx, dy);
//                int[] location={0,0};
//                getLocationOnScreen(location);
//                switch (orientation) {
//                    case 'b':
//                        Log.d("TAG","bottom");
//                        //内层RecyclerView下拉到最顶部时候不再处理事件
//                        ViewParent parent = findRecyclerParent();
//                        if(!canScrollVertically(-1)){
//                            if(outRecyclerView!=null)outRecyclerView.requestDisallowInterceptTouchEvent(false);
//                        }else{
//                            if(outRecyclerView!=null)outRecyclerView.requestDisallowInterceptTouchEvent(true);
//                        }
//                        break;
//                    case 't':
//                        Log.d("TAG","top");
//                        //外层RecyclerView 上拉到最底部时 内部recyclerView 处理滑动事件
//                        if(outRecyclerView!=null){
//                            if(outRecyclerView.canScrollVertically(1)){
//                                Log.d("TAG","不要拦截");
//                                if(outRecyclerView!=null)outRecyclerView.requestDisallowInterceptTouchEvent(true);
//                            }else{
//                                if(outRecyclerView!=null)outRecyclerView.requestDisallowInterceptTouchEvent(false);
////                            return true;
//                            }
//                        }
//                        break;
//                    case 'r':
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                    //左右滑动交给ViewPager处理
//                    case 'l':
//                        getParent().requestDisallowInterceptTouchEvent(false);
//                        break;
//                }
//                break;
//        }
//        return super.onTouchEvent(e);
//    }

    private ViewParent findRecyclerParent(){
        ViewParent parent = getParent();
        while(parent != null){
            if(parent instanceof MyRecyclerView) return parent;
            parent = parent.getParent();
        }
        return null;
    }

    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx)>Math.abs(dy)){
            //X轴移动
            return dx>0?'r':'l';//右,左
        }else{
            //Y轴移动
            return dy>0?'b':'t';//下//上
        }
    }
}
