package e.user.rxjavatest.adapter;

import androidx.annotation.NonNull;

import android.view.View;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.DataBean;
import e.user.rxjavatest.bean.holder.DataHolder;

public class DataAdapter extends BaseMultiAdapter<DataBean, DataHolder> {

    @Override
    protected int getHolderRes(int type) {
        return R.layout.item_page_data;
    }

    @Override
    protected DataHolder createHolder(View view, int type) {
        return new DataHolder(view);
    }
}
