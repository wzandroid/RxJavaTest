package e.user.rxjavatest.adapter;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.HorizontalProductBean;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-20
 */
public class HorizontalProductAdapter extends BaseAdapter<HorizontalProductBean,HorizontalProductAdapter.ProductHolder> {

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_horizontal_product,parent,false));
    }

    public class ProductHolder extends BaseAdapter.BaseHolder<HorizontalProductBean>{
        private TextView nameTv;
        private ImageView imageView;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            nameTv = itemView.findViewById(R.id.name_tv);
        }

        @Override
        public void BindData(HorizontalProductBean bean) {
            nameTv.setText(bean.name);
            Glide.with(imageView).load(bean.image).into(imageView);
        }
    }
}
