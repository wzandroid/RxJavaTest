package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.DataBean;
import e.user.rxjavatest.interfaces.MultiType;

public class DataHolder extends BaseMultiAdapter.BaseHolderView {
    private TextView nameTv;

    public DataHolder(@NonNull View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.name_tv);
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof DataBean){
            DataBean dataBean = (DataBean) bean;
            nameTv.setText(dataBean.name);
        }

    }
}
