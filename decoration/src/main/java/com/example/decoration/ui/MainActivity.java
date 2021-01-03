package com.example.decoration.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.decoration.R;
import com.example.decoration.adapter.BaseAdapter;
import com.example.decoration.adapter.bean.BaseBean;
import com.example.decoration.decoration.FloatDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private BaseAdapter<BaseBean> stringAdapter;
    private FloatDecoration floatDecoration;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        stringAdapter = new BaseAdapter<>();
        floatDecoration = new FloatDecoration(BaseAdapter.TOP_HOLDER_TYPE);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(stringAdapter);
        recyclerView.addItemDecoration(floatDecoration);
        initData(15,0);
    }

    private void initData(int count,int top){
        List<BaseBean> data = new ArrayList<>();
        for (int i=0;i<count;i++){
            if (i == 5){
                data.add(new BaseBean("这个是第一条吸顶的view"+top));
                continue;
            }
            if (i == 10){
                data.add(new BaseBean("这个是判断是否需要换行的展示，用来测试换行效果，看看换行后会不会换行，这个要好好看看怎们办，看看有没有变样子，则深奥内购价"+top));
                continue;
            }
            data.add(new BaseBean(String.valueOf(i)));
        }
        floatDecoration.resetFloatView();
        stringAdapter.setDataList(data);
    }

    public void onClick(View view){
        Random rand = new Random();
        initData(rand.nextInt(50),rand.nextInt(5));
    }
}