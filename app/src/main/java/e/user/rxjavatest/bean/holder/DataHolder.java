package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.DataBean;
import e.user.rxjavatest.interfaces.MultiType;

public class DataHolder extends BaseMultiAdapter.BaseHolderView {
    private TextView nameTv;
    private ImageView imageView;

    public DataHolder(@NonNull View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.tv_product_name);
        imageView = itemView.findViewById(R.id.iv_product_image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(),"点击了商品",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof DataBean){
            DataBean dataBean = (DataBean) bean;
            nameTv.setText(dataBean.name);
            Glide.with(imageView.getContext()).load(dataBean.image).into(imageView);
        }

    }
}
