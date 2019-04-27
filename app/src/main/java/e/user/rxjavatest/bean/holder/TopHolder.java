package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.TopBean;
import e.user.rxjavatest.interfaces.MultiType;

public class TopHolder extends BaseMultiAdapter.BaseHolderView {
    private TextView titleTv;

    public TopHolder(@NonNull View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.top_tv);
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof TopBean){
            TopBean topBean = (TopBean) bean;
            titleTv.setText(topBean.topText);
        }
    }
}
