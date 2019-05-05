package e.user.rxjavatest.adapter;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.PageRecyclerBean;
import e.user.rxjavatest.interfaces.MultiType;

public class TabAdapter extends BaseMultiAdapter<PageRecyclerBean, TabAdapter.TabHolderView> {

    @Override
    protected int getHolderRes(int type) {
        return R.layout.item_recycler_lab;
    }

    @Override
    protected TabHolderView createHolder(View view, int type) {
        return new TabHolderView(view,itemClickCallback);
    }

    public void selectPosition(int position){
        if(position!=-1){
            for(int i=0;i<getDataList().size();i++){
                getDataList().get(i).select = i==position;
            }
        }
        notifyDataSetChanged();
    }

    public static class TabHolderView extends BaseMultiAdapter.BaseHolderView{
        private TextView tabTv;
        public TabHolderView(@NonNull View itemView, ItemClickCallback callback) {
            super(itemView);
            tabTv = itemView.findViewById(R.id.tab_name_tv);
            tabTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callback!=null) {
                        callback.itemClick(getAdapterPosition());
                    }
                }
            });
        }

        @Override
        protected void bindData(MultiType bean) {
            super.bindData(bean);
            if(bean instanceof PageRecyclerBean){
                tabTv.setText(((PageRecyclerBean) bean).typeName);
                tabTv.setTextColor(itemView.getContext().getResources().getColor(
                        ((PageRecyclerBean) bean).select?android.R.color.holo_red_dark:android.R.color.black));
            }
        }
    }
}
