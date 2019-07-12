package com.example.mymanager.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymanager.R;

public class StringAdapter extends BaseAdapter<String,StringAdapter.StringHolder> {
    private static final String TAG = "StringAdapter";

    @NonNull
    @Override
    public StringHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        Log.d(TAG,"onCreateViewHolder");
        return new StringHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StringHolder holder, int position) {
        holder.tv.setText(getDataList().get(position));
        Log.d(TAG,"onBindViewHolder");
    }

    public static class StringHolder extends BaseAdapter.BaseHolder{
        TextView tv;
        public StringHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
