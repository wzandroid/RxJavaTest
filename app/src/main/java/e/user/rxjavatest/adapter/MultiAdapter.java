package e.user.rxjavatest.adapter;

import android.support.v4.app.FragmentActivity;
import android.view.View;

import e.user.rxjavatest.R;
import e.user.rxjavatest.bean.holder.BannerHolder;
import e.user.rxjavatest.bean.holder.HorizontalHolder;
import e.user.rxjavatest.bean.holder.RecyclerHolder;
import e.user.rxjavatest.bean.holder.TopHolder;
import e.user.rxjavatest.bean.holder.ViewPageHolder;
import e.user.rxjavatest.interfaces.MultiType;

public class MultiAdapter extends BaseMultiAdapter<MultiType, BaseMultiAdapter.BaseHolderView> {

    public static final int TOP_TYPE = 10;
    public static final int PAGE_TYPE = 11;
    public static final int SCROLL_TYPE = 12;
    public static final int BANNER_TYPE = 13;
    public static final int HORIZONTAL_TYPE = 14;

    private FragmentActivity mActivity;

    public MultiAdapter(FragmentActivity activity){
        mActivity = activity;
    }

    @Override
    protected int getHolderRes(int type) {
        switch (type){
            case BANNER_TYPE:
                return R.layout.item_recycler_banner;
            case TOP_TYPE:
                return R.layout.item_recycler_viewpage_top;
            case PAGE_TYPE:
                return R.layout.item_recycler_viewpage_page;
            case SCROLL_TYPE:
                return R.layout.item_recycler_scroll_view;
            case HORIZONTAL_TYPE:
                return R.layout.item_recycler_horizontal;
            default:
                return R.layout.item_recycler_viewpage_top;
        }
    }

    @Override
    protected BaseHolderView createHolder(View view, int type) {
        switch (type){
            case TOP_TYPE:
                return new TopHolder(view);
            case PAGE_TYPE:
                return new ViewPageHolder(view,mActivity);
            case SCROLL_TYPE:
                return new RecyclerHolder(view);
            case BANNER_TYPE:
                return new BannerHolder(view);
            case HORIZONTAL_TYPE:
                return new HorizontalHolder(view);
            default:
                return new TopHolder(view);
        }
    }
}
