package com.example.mymanager.adapter;

import androidx.annotation.NonNull;
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
        return new StringHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv,parent,false));
    }

    public static class StringHolder extends BaseAdapter.BaseHolder<String>{
        TextView tv;
        public StringHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }

        @Override
        public void BindData(String bean) {
            tv.setText(bean);
        }
    }
}
