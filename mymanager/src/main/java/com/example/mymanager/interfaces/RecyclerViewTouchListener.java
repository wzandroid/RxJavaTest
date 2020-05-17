package com.example.mymanager.interfaces;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class RecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetector mGestureDetector;

    public RecyclerViewTouchListener(Context context){
        mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){

        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        //把事件交给GestureDetector处理
        if(mGestureDetector.onTouchEvent(motionEvent)){
            return true;
        }else
            return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
