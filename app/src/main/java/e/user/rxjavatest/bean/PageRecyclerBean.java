package e.user.rxjavatest.bean;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.interfaces.MultiType;

public class PageRecyclerBean implements MultiType {
    private List<DataBean> dataList;
    public String typeName;
    public boolean select;

    public PageRecyclerBean(String type){
        typeName = type;
        for(int i=0;i<20;i++){
            getDataList().add(new DataBean(typeName+i));
        }

    }

    @Override
    public int getMultiType() {
        return 0;
    }

    public List<DataBean> getDataList(){
        if(dataList == null) dataList = new ArrayList<>();
        return dataList;
    }
}
