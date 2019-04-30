package com.example.testnestedscroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private DataAdapter dataAdapter,bottomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
    }

    private void initData() {
        for(int i=0;i<20;i++){
            dataAdapter.getDataList().add("测试数据"+i);
            bottomAdapter.getDataList().add("测试商品"+i);
        }
        dataAdapter.notifyDataSetChanged();
        bottomAdapter.notifyDataSetChanged();
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView bottomRecycler = findViewById(R.id.bottom_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        bottomRecycler.setLayoutManager(new GridLayoutManager(this,2));
        dataAdapter = new DataAdapter();
        bottomAdapter = new DataAdapter();
        recyclerView.setAdapter(dataAdapter);
        bottomRecycler.setAdapter(bottomAdapter);
    }
}
