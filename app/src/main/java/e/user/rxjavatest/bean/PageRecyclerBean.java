package e.user.rxjavatest.bean;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.interfaces.MultiType;

public class PageRecyclerBean implements MultiType {
    private List<DataBean> dataList;

    public PageRecyclerBean(){
        for(int i=0;i<20;i++){
            getDataList().add(new DataBean("商品名称"+i));
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
