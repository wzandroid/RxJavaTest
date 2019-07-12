package com.example.mymanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter <T extends Object,H extends BaseAdapter.BaseHolder> extends RecyclerView.Adapter<H> {
    private List<T> dataList;

    public void setDataList(List<T> dataList){
        getDataList().clear();
        getDataList().addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    public static class BaseHolder extends RecyclerView.ViewHolder{

        public BaseHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
