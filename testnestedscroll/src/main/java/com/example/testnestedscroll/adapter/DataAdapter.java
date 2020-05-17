package com.example.testnestedscroll.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testnestedscroll.R;

import java.util.ArrayList;
import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.HolderView> {
    private List<String> dataList;

    public List<String> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }

    public void setDataList(List<String> dataList){
        getDataList().clear();
        getDataList().addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tv,viewGroup,false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, int i) {
        holderView.tv.setText(getDataList().get(i));
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    public static class HolderView extends RecyclerView.ViewHolder{
        TextView tv;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
