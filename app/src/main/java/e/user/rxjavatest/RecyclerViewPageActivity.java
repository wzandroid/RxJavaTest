package e.user.rxjavatest;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.BannerBean;
import e.user.rxjavatest.bean.HorizontalBean;
import e.user.rxjavatest.bean.PageBean;
import e.user.rxjavatest.bean.TopBean;
import e.user.rxjavatest.interfaces.MultiType;

/**
 * @author wangzhen
 */
public class RecyclerViewPageActivity extends AppCompatActivity {
    private MultiAdapter multiAdapter;
    private SmartRefreshLayout refreshLayout;
    private TextView addTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        addTv = findViewById(R.id.add_tv);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.refresh_layout);

        refreshLayout.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        multiAdapter = new MultiAdapter(this);
        recyclerView.setAdapter(multiAdapter);
        initData();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(800);
            }
        });
        refreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving(header, isDragging, percent, offset, headerHeight, maxDragHeight);
                addTv.setVisibility(offset==0?View.VISIBLE:View.GONE);
            }
        });
    }

    private void initData() {
        List<MultiType> tmpList = new ArrayList<>();
        for(int i=0;i<7;i++){
            if(i==0) {
                tmpList.add(new BannerBean());
            }else if(i<4) {
                tmpList.add(new HorizontalBean());
            }else if(i==6) {
                tmpList.add(new PageBean());
            }else {
                tmpList.add(new TopBean("Test "+i));
            }
        }
        multiAdapter.setDataList(tmpList);
    }
}
