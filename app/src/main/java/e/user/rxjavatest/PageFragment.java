package e.user.rxjavatest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.DataAdapter;
import e.user.rxjavatest.bean.DataBean;

public class PageFragment extends Fragment {
    private DataAdapter dataAdapter;
    private RecyclerView recyclerView;

    public static PageFragment getInstance(String type){
        PageFragment fragment = new PageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TITLE",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUI(View view) {
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        dataAdapter = new DataAdapter();
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getContext()).resumeRequests();//恢复Glide加载图片
                }else {
                    Glide.with(getContext()).pauseRequests();//禁止Glide加载图片
                }
                Log.d("TAG","pageFragment scrollState = "+newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("TAG","pageFragment scrolled = "+dy);
            }
        });
        initData();
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    private void initData(){
        List<DataBean> tmpList = new ArrayList<>();
        for (int i=0;i<40;i++){
            tmpList.add(new DataBean("商品"+i));
        }
        dataAdapter.setDataList(tmpList);
    }
}
