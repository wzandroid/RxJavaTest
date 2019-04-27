package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
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

    public PageRecyclerHolder(@NonNull View itemView) {
        super(itemView);
        RecyclerView recyclerView = itemView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayout.VERTICAL,false));
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
