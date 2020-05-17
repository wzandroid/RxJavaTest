package e.user.rxjavatest.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.interfaces.MultiType;

public abstract class BaseMultiAdapter<T extends MultiType,H extends BaseMultiAdapter.BaseHolderView> extends RecyclerView.Adapter<H> {
    private List<T> dataList = new ArrayList<>();
    protected ItemClickCallback itemClickCallback;

    public void setItemClickCallback(ItemClickCallback itemClickCallback){
        this.itemClickCallback = itemClickCallback;
    }

    protected abstract int getHolderRes(int type);

    protected abstract H createHolder(View view,int type);

    public void setDataList(List<T> dataList){
        getDataList().clear();
        getDataList().addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return getDataList().get(position).getMultiType();
    }



    public List<T> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }

    @NonNull
    @Override
    public H onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        long time = System.currentTimeMillis();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(getHolderRes(type),viewGroup,false);
        H holder = createHolder(view,type);
        Log.d("Wz",type + " holder create "+(System.currentTimeMillis() - time));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull H holder, int position) {
        long time = System.currentTimeMillis();
        int type = getDataList().get(position).getMultiType();
        holder.bindData(getDataList().get(position));
        Log.d("Wz",type + " holder bind "+(System.currentTimeMillis() - time));
    }

    @Override
    public int getItemCount() {
        return getDataList().size();
    }

    public static class BaseHolderView extends RecyclerView.ViewHolder{

        public BaseHolderView(@NonNull View itemView) {
            super(itemView);
        }

        public BaseHolderView(@NonNull View itemView,ItemClickCallback callback) {
            super(itemView);
        }

        protected void bindData(MultiType bean){

        }
    }

    public interface ItemClickCallback{
        void itemClick(int position);
    }
}
