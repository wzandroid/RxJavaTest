package com.example.decoration.adapter.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.decoration.adapter.bean.BaseBean;

public class BaseHolder<T extends BaseBean> extends RecyclerView.ViewHolder {
    private TextView titleTv;

    public BaseHolder(@NonNull View itemView) {
        super(itemView);
        titleTv = (TextView) itemView;
    }

    public void setData(T bean){
        titleTv.setText(bean.title);
    }
}
