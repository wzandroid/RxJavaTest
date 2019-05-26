package e.user.rxjavatest.bean;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.interfaces.MultiType;

public class RecyclerBean implements MultiType {
    private List<PageRecyclerBean> dataList;

    public RecyclerBean(){
        for(int i=0;i<5;i++){
            getDataList().add(new PageRecyclerBean(i+"分类"));
        }
    }

    @Override
    public int getMultiType() {
        return MultiAdapter.SCROLL_TYPE;
    }

    public List<PageRecyclerBean> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }
}
