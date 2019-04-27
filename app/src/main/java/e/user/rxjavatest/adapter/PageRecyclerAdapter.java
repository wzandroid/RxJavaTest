package e.user.rxjavatest.adapter;

import android.view.View;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.PageRecyclerBean;
import e.user.rxjavatest.bean.holder.PageRecyclerHolder;

public class PageRecyclerAdapter extends BaseMultiAdapter<PageRecyclerBean, PageRecyclerHolder> {
    @Override
    protected int getHolderRes(int type) {
        return R.layout.fragment_recycler;
    }

    @Override
    protected PageRecyclerHolder createHolder(View view, int type) {
        return new PageRecyclerHolder(view);
    }
}
