package com.example.testnestedscroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.example.testnestedscroll.adapter.DataAdapter;
import com.example.testnestedscroll.adapter.RecyclerPagerAdapter;
import com.example.testnestedscroll.bean.PagerBean;

public class MainActivity extends AppCompatActivity {
    private DataAdapter dataAdapter,bottomAdapter;
    private RecyclerPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        startActivity(new Intent(this,ScrollActivity.class));
    }

    private void initData() {
        for(int i=0;i<10;i++){
            dataAdapter.getDataList().add("测试数据"+i);
            pagerAdapter.getDataList().add(new PagerBean());
            bottomAdapter.getDataList().add("测试分类"+i);
        }
        dataAdapter.notifyDataSetChanged();
        pagerAdapter.notifyDataSetChanged();
        bottomAdapter.notifyDataSetChanged();
    }

    private void initUI() {
        RecyclerView topRecycler = findViewById(R.id.top_recycler);
        RecyclerView tabRecycler = findViewById(R.id.tab_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        topRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        dataAdapter = new DataAdapter();
        topRecycler.setAdapter(dataAdapter);
        topRecycler.setNestedScrollingEnabled(false);

        tabRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        bottomAdapter = new DataAdapter();
        tabRecycler.setAdapter(bottomAdapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(tabRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        pagerAdapter = new RecyclerPagerAdapter();
        recyclerView.setAdapter(pagerAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
//        RecyclerView
//        RecyclerView recyclerView = findViewById(R.id.recycler_view);
//        RecyclerView bottomRecycler = findViewById(R.id.bottom_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
//        bottomRecycler.setLayoutManager(new GridLayoutManager(this,2));
//        dataAdapter = new DataAdapter();
//        bottomAdapter = new DataAdapter();
//        recyclerView.setAdapter(dataAdapter);
//        bottomRecycler.setAdapter(bottomAdapter);
    }
}
