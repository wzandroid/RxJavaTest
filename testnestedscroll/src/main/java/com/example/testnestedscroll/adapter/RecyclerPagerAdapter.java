package com.example.testnestedscroll.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testnestedscroll.R;
import com.example.testnestedscroll.bean.PagerBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerPagerAdapter extends RecyclerView.Adapter<RecyclerPagerAdapter.HolderView> {

    private List<PagerBean> dataList;

    public List<PagerBean> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new HolderView(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pager,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, int i) {
        holderView.dataAdapter.setDataList(getDataList().get(i).getDataList());
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    public static class HolderView extends RecyclerView.ViewHolder{
        DataAdapter dataAdapter;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            RecyclerView recyclerView = itemView.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));
            dataAdapter = new DataAdapter();
            recyclerView.setAdapter(dataAdapter);
        }
    }
}
