package e.user.rxjavatest.bean;

import e.user.rxjavatest.interfaces.MultiType;

public class DataBean implements MultiType {

    public String name;

    public DataBean(String s){
        name = s;
    }

    @Override
    public int getMultiType() {
        return 0;
    }
}
