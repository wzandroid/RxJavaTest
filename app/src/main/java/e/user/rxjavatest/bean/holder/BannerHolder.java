package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.MultiAdapter;
import e.user.rxjavatest.bean.BannerBean;
import e.user.rxjavatest.interfaces.MultiType;
import e.user.rxjavatest.view.FlyBanner;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-20
 */
public class BannerHolder extends MultiAdapter.BaseHolderView {
    private FlyBanner flyBanner;

    public BannerHolder(@NonNull View itemView) {
        super(itemView);
        flyBanner = itemView.findViewById(R.id.banner_view);
    }

    @Override
    protected void bindData(MultiType bean) {
        if(bean instanceof BannerBean){
            BannerBean bannerBean = (BannerBean)bean;
            flyBanner.setImagesUrl(bannerBean.getDataList());
        }
    }
}
