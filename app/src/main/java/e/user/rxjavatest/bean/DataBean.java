package e.user.rxjavatest.bean;

import e.user.rxjavatest.interfaces.MultiType;

public class DataBean implements MultiType {

    public String name;
    public String image = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1233980237,2450439314&fm=26&gp=0.jpg";

    public DataBean(String s){
        name = s;
    }

    @Override
    public int getMultiType() {
        return 0;
    }
}
