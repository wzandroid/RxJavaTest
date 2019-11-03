package com.example.mymanager.adapter;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymanager.R;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-17
 */
public class ImageAdapter extends BaseAdapter<String, ImageAdapter.ImageHolder> {

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Log.d("Wz","onCreateViewHolder");
        return new ImageHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false));
    }

    public static class ImageHolder extends BaseAdapter.BaseHolder<String>{
        private ImageView imageView;
        private TextView tv;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            tv = itemView.findViewById(R.id.tv);
        }

        @Override
        public void BindData(String bean) {
            Log.d("Wz","BindData");
            Glide.with(imageView).load(bean).into(imageView);
            tv.setText(String.valueOf(getAdapterPosition()));
        }


    }
}
