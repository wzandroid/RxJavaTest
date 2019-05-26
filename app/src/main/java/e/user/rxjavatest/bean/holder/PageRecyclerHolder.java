package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.adapter.DataAdapter;
import e.user.rxjavatest.bean.PageRecyclerBean;
import e.user.rxjavatest.interfaces.MultiType;

public class PageRecyclerHolder extends BaseMultiAdapter.BaseHolderView {
    private DataAdapter dataAdapter;
    public RecyclerView recyclerView;

    public PageRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));
        dataAdapter = new DataAdapter();
        recyclerView.setAdapter(dataAdapter);
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof PageRecyclerBean){
            PageRecyclerBean pageBean = (PageRecyclerBean) bean;
            dataAdapter.setDataList(pageBean.getDataList());
        }
    }
}
