package com.example.mymanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.mymanager.R;
import com.example.mymanager.adapter.StringAdapter;
import com.example.mymanager.manager.BannerLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    private StringAdapter stringAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_banner);
        initView();
        initData();
    }

    private void initView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        BannerLayoutManager layoutManager = new BannerLayoutManager(this,recyclerView,8);
        recyclerView.setLayoutManager(layoutManager);
        stringAdapter = new StringAdapter();
        recyclerView.setAdapter(stringAdapter);

    }

    private void initData(){
        List<String> tmpList = new ArrayList<>();
        for(int i=0;i<8;i++){
            tmpList.add("测试Banner "+i);
        }
        stringAdapter.setDataList(tmpList);
    }
}
