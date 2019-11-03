package e.user.rxjavatest.bean;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.interfaces.MultiType;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-20
 */
public class HorizontalBean implements MultiType {
    public String image = "https://j-image.missfresh.cn/mis_img_20190815113214882.jpg?mryxw=750&mryxh=448";
    private List<HorizontalProductBean> dataList = new ArrayList<>();

    @Override
    public int getMultiType() {
        return MultiAdapter.HORIZONTAL_TYPE;
    }

    public List<HorizontalProductBean> getDataList(){
        dataList.clear();
        for(int i=0;i<9;i++){
            dataList.add(new HorizontalProductBean("横排货架"+i,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1244717934,3275096654&fm=26&gp=0.jpg"));
        }
        return dataList;
    }
}
