package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.TopBean;
import e.user.rxjavatest.interfaces.MultiType;

public class TopHolder extends BaseMultiAdapter.BaseHolderView {
    private TextView titleTv;
    private ImageView imageView;

    public TopHolder(@NonNull View itemView) {
        super(itemView);
        titleTv = itemView.findViewById(R.id.top_tv);
        imageView = itemView.findViewById(R.id.image_view);
    }

    @Override
    protected void bindData(MultiType bean) {
        if(bean instanceof TopBean){
            TopBean topBean = (TopBean) bean;
            titleTv.setText(topBean.topText);
            Glide.with(imageView).load(topBean.image).into(imageView);
        }
    }
}
