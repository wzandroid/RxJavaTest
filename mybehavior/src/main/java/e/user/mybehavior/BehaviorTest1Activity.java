package e.user.mybehavior;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import e.user.mybehavior.adapter.StringAdapter;

public class BehaviorTest1Activity extends AppCompatActivity {

    private StringAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior_test_1);
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
