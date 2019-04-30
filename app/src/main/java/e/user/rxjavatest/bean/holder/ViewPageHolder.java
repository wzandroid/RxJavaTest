package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.PageFragment;
import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.PageBean;
import e.user.rxjavatest.interfaces.MultiType;

public class ViewPageHolder extends BaseMultiAdapter.BaseHolderView {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentStatePagerAdapter statePagerAdapter;
    private int displayHeight;

    public ViewPageHolder(@NonNull View itemView, FragmentActivity activity) {
        super(itemView);
        viewPager = itemView.findViewById(R.id.view_page);
        tabLayout = itemView.findViewById(R.id.tab_layout);
        statePagerAdapter = new FragmentStatePagerAdapter(activity.getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

        };
        viewPager.setAdapter(statePagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        displayHeight = getDisplayHeight(activity);
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof PageBean){
            fragments.clear();
            PageBean pageBean = (PageBean)bean;
            for(int i=0;i<pageBean.getTitleList().size();i++){
                fragments.add(PageFragment.getInstance(pageBean.getTitleList().get(i)));
            }
            statePagerAdapter.notifyDataSetChanged();
        }
    }

    public RecyclerView getScrollView(){
        return ((PageFragment)fragments.get(viewPager.getCurrentItem())).getRecyclerView();
    }

    private int getDisplayHeight(FragmentActivity activity){
        //状态栏高度
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            statusBarHeight =activity.getResources().getDimensionPixelSize(resourceId);
        }
        //屏幕高度
        DisplayMetrics dm = activity.getApplicationContext().getResources().getDisplayMetrics();
        final float scale = dm.density;
        int i = (int) (54 * scale + 0.5f);
        return dm.heightPixels-statusBarHeight-i;
    }
}
