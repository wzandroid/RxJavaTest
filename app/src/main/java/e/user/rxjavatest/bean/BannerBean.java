package e.user.rxjavatest.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.interfaces.MultiType;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-20
 */
public class BannerBean implements MultiType {
    private String[] data = {"https://j-image.missfresh.cn/mis_img_20190814180023748.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190815180440114.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190815113214882.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190801171559443.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190814180023748.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190813201803851.jpg?mryxw=750&mryxh=448"};

    private List<String> dataList = new ArrayList<>();

    @Override
    public int getMultiType() {
        return MultiAdapter.BANNER_TYPE;
    }

    public List<String> getDataList(){
        dataList.clear();
        Collections.addAll(dataList,data);
        return dataList;
    }
}
