package e.user.mybehavior.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import e.user.mybehavior.R;

public class StringAdapter extends RecyclerView.Adapter<StringAdapter.HolderView> {
    private List<String> dataList = new ArrayList<>();

    public void setDataList(List<String> dataList){
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text,viewGroup,false);
        return new HolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, int i) {
        holderView.titleTv.setText(dataList.get(i));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class HolderView extends RecyclerView.ViewHolder{
        TextView titleTv;
        public HolderView(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.title_tv);
        }
    }
}
