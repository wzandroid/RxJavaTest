package e.user.rxjavatest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.DataAdapter;
import e.user.rxjavatest.bean.DataBean;

public class PageFragment extends Fragment {
    private DataAdapter dataAdapter;

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
        View view = inflater.inflate(R.layout.fragment_recycler,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initUI(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        dataAdapter = new DataAdapter();
        recyclerView.setAdapter(dataAdapter);
        initData();
    }

    private void initData(){
        List<DataBean> tmpList = new ArrayList<>();
        for (int i=0;i<20;i++){
            tmpList.add(new DataBean("商品"+i));
        }
        dataAdapter.setDataList(tmpList);
    }
}
