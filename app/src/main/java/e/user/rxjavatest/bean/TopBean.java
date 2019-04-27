package e.user.rxjavatest.bean;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.interfaces.MultiType;

public class TopBean implements MultiType {
    public String topText;

    public TopBean(String topText){
        this.topText = topText;
    }

    @Override
    public int getMultiType() {
        return MultiAdapter.TOP_TYPE;
    }
}
