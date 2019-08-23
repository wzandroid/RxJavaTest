package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.HorizontalProductAdapter;
import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.HorizontalBean;
import e.user.rxjavatest.interfaces.MultiType;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-20
 */
public class HorizontalHolder extends MultiAdapter.BaseHolderView {
    private ImageView imageView;
    private HorizontalProductAdapter productAdapter;

    public HorizontalHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.banner_image);
        RecyclerView recyclerView = itemView.findViewById(R.id.recycler_view);
        productAdapter = new HorizontalProductAdapter();
        recyclerView.setAdapter(productAdapter);
    }

    @Override
    protected void bindData(MultiType bean) {
        if(bean instanceof HorizontalBean){
            HorizontalBean hBean = (HorizontalBean) bean;
            productAdapter.setDataList(hBean.getDataList());
            Glide.with(imageView).load(hBean.image).into(imageView);
        }
    }
}
