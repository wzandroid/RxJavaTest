package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.adapter.PageRecyclerAdapter;
import e.user.rxjavatest.bean.RecyclerBean;
import e.user.rxjavatest.interfaces.MultiType;

public class RecyclerHolder extends BaseMultiAdapter.BaseHolderView {
    private PageRecyclerAdapter pageAdapter;

    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        RecyclerView recyclerView = itemView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayout.HORIZONTAL,false));
        pageAdapter = new PageRecyclerAdapter();
        recyclerView.setAdapter(pageAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof RecyclerBean){
            RecyclerBean recyclerBean = (RecyclerBean) bean;
            pageAdapter.setDataList(recyclerBean.getDataList());
        }
    }
}
