package e.user.rxjavatest.bean;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.interfaces.MultiType;

public class PageBean implements MultiType {

    private List<String> titleList;

    public PageBean(){
        for(int i=0;i<8;i++){
            getTitleList().add("分类"+i);
        }
    }

    @Override
    public int getMultiType() {
        return MultiAdapter.PAGE_TYPE;
    }

    public List<String> getTitleList(){
        if(titleList == null) titleList = new ArrayList<>();
        return titleList;
    }
}
