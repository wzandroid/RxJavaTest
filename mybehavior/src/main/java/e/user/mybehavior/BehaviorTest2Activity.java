package e.user.mybehavior;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import e.user.mybehavior.adapter.StringAdapter;

public class BehaviorTest2Activity extends AppCompatActivity {
    private StringAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_test_2);
        initUI();
    }

    private void initUI(){
        recyclerView = findViewById(R.id.my_list);
        adapter = new StringAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<String> tmpList = new ArrayList<>();
        for(int i=0;i<20;i++){
            tmpList.add("测试数据"+i);
        }
        adapter.setDataList(tmpList);
    }
}
