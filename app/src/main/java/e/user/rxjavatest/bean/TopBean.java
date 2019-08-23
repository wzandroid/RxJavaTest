package e.user.rxjavatest.bean;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.interfaces.MultiType;

public class TopBean implements MultiType {
    public String topText;
    public String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1566315586591&di=8b6884726b3014878c92e6c6700c2926&imgtype=0&src=http%3A%2F%2Fs0.hao123img.com%2Fres%2Fr%2Fimage%2F2014-05-29%2F60bc65234b78ac7426bc3b9098ae379e.gif";

    public TopBean(String topText){
        this.topText = topText;
    }

    @Override
    public int getMultiType() {
        return MultiAdapter.TOP_TYPE;
    }
}
