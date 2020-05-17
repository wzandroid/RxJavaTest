package e.user.mybehavior;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import e.user.mybehavior.adapter.StringAdapter;
import e.user.mybehavior.view.SlideRecyclerView;

public class SlideRecyclerViewActivity extends AppCompatActivity {
    private SlideRecyclerView recyclerView;
    private StringAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_recycler);
        initUI();
    }

    private void initUI(){
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new StringAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        List<String> tmpList = new ArrayList<>();
        for(int i=0;i<20;i++){
            tmpList.add("测试数据"+i);
        }
        adapter.setDataList(tmpList);
    }
}
