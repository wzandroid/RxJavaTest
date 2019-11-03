package e.user.rxjavatest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.BannerBean;
import e.user.rxjavatest.bean.HorizontalBean;
import e.user.rxjavatest.bean.PageBean;
import e.user.rxjavatest.bean.TopBean;
import e.user.rxjavatest.interfaces.MultiType;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-09-16
 */
public class TestRecyclerActivity extends AppCompatActivity {
    private MultiAdapter multiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler);
        initUI();
    }

    private void initUI() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        multiAdapter = new MultiAdapter(this);
        recyclerView.setAdapter(multiAdapter);
        initData();
    }

    private void initData() {
        List<MultiType> tmpList = new ArrayList<>();
        for(int i=0;i<10;i++){
            if(i==0) {
                tmpList.add(new BannerBean());
            } else if(i<8) {
                tmpList.add(new HorizontalBean());
//            } else if(i==6) {
//                tmpList.add(new PageBean());
            } else {
                tmpList.add(new TopBean("Test "+i));
            }
        }
        multiAdapter.setDataList(tmpList);
    }
}
