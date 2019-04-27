package e.user.rxjavatest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.interfaces.MultiType;

public abstract class BaseMultiAdapter<T extends MultiType,H extends BaseMultiAdapter.BaseHolderView> extends RecyclerView.Adapter<H> {
    private List<T> dataList = new ArrayList<>();

    protected abstract int getHolderRes(int type);

    protected abstract H createHolder(View view,int type);

    public void setDataList(List<T> dataList){
        getDataList().clear();
        getDataList().addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("TAG","position = "+position+",type = " +getDataList().get(position).getMultiType());
        return getDataList().get(position).getMultiType();
    }



    public List<T> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getHolderRes(type),viewGroup,false);
        return createHolder(view,type);
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        holder.bindData(getDataList().get(position));
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    public static class BaseHolderView extends RecyclerView.ViewHolder{

        public BaseHolderView(@NonNull View itemView) {
            super(itemView);
        }

        protected void bindData(MultiType bean){

        }
    }
}
