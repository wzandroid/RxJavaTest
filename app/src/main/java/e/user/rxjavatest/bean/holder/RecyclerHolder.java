package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.adapter.PageRecyclerAdapter;
import e.user.rxjavatest.adapter.TabAdapter;
import e.user.rxjavatest.bean.RecyclerBean;
import e.user.rxjavatest.interfaces.MultiType;

public class RecyclerHolder extends BaseMultiAdapter.BaseHolderView {
    private PageRecyclerAdapter pageAdapter;
    private TabAdapter tabAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    public RecyclerHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = itemView.findViewById(R.id.recycler_view);
        final RecyclerView tabView = itemView.findViewById(R.id.tab_view);
        linearLayoutManager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false);
        tabView.setLayoutManager(linearLayoutManager);
        tabAdapter = new TabAdapter();
        tabView.setAdapter(tabAdapter);
        LinearSnapHelper tabSnap = new LinearSnapHelper();
        tabSnap.attachToRecyclerView(tabView);
        recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        pageAdapter = new PageRecyclerAdapter();
        recyclerView.setAdapter(pageAdapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    tabView.smoothScrollToPosition(position);
                    tabAdapter.selectPosition(position);
                }
            }
        });
        tabAdapter.setItemClickCallback(new BaseMultiAdapter.ItemClickCallback() {
            @Override
            public void itemClick(int position) {
                if(position!=-1)
                    recyclerView.smoothScrollToPosition(position);
                tabAdapter.selectPosition(position);
            }
        });
    }

    public RecyclerView getScrollView(){
        //获取当前显示的RecyclerView
        PageRecyclerHolder holder = (PageRecyclerHolder) recyclerView.getChildViewHolder(((LinearLayoutManager)recyclerView.getLayoutManager()).getChildAt(0));
        return holder.recyclerView;
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof RecyclerBean){
            RecyclerBean recyclerBean = (RecyclerBean) bean;
            tabAdapter.setDataList(recyclerBean.getDataList());
            pageAdapter.setDataList(recyclerBean.getDataList());
        }
    }
}
