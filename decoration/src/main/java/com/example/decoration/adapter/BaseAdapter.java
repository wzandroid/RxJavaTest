package com.example.decoration.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.decoration.R;
import com.example.decoration.adapter.bean.BaseBean;
import com.example.decoration.adapter.holder.BaseHolder;
import com.example.decoration.adapter.holder.TopHolder;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T extends BaseBean> extends RecyclerView.Adapter<BaseHolder<T>>{
    public static final int TOP_HOLDER_TYPE = 10;

    public List<T> mDataList = new ArrayList<>();

    public void setDataList(List<T> dataList){
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mDataList;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TOP_HOLDER_TYPE){
            return new TopHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_holder,parent,false));
        }
        return new BaseHolder<>(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 5 == 0 ? TOP_HOLDER_TYPE : super.getItemViewType(position);
    }
}
