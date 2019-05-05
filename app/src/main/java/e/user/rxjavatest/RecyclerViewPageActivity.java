package e.user.rxjavatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.PageBean;
import e.user.rxjavatest.bean.RecyclerBean;
import e.user.rxjavatest.bean.TopBean;
import e.user.rxjavatest.interfaces.MultiType;

public class RecyclerViewPageActivity extends AppCompatActivity {
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        multiAdapter = new MultiAdapter(this);
        recyclerView.setAdapter(multiAdapter);
        initData();
    }

    private void initData() {
        List<MultiType> tmpList = new ArrayList<>();
        for(int i=0;i<5;i++){
            if(i==4) tmpList.add(new RecyclerBean());
//            else
//                if(i==4) tmpList.add(new PageBean());
            else tmpList.add(new TopBean("Test "+i));
        }
        multiAdapter.setDataList(tmpList);
    }
}
