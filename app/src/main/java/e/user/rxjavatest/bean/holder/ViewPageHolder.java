package e.user.rxjavatest.bean.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.PageFragment;
import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.PageBean;
import e.user.rxjavatest.interfaces.MultiType;
import e.user.rxjavatest.utils.LogUtils;
import e.user.rxjavatest.view.HomePageTabLayout;

public class ViewPageHolder extends BaseMultiAdapter.BaseHolderView {
    private ViewPager viewPager;
    private HomePageTabLayout tabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private FragmentStatePagerAdapter statePagerAdapter;
    private HomePageTabLayout.TabProvider tabProvider;
    private boolean isTop;

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
        tabProvider = new HomePageTabLayout.TabProvider() {
            @Override
            public HomePageTabLayout.Tab getTab(int position) {
                HomePageTabLayout.Tab tab = new HomePageTabLayout.Tab();
                tab.setName(nameList.get(position));
                tab.setSubName(nameList.get(position)+"副");
                return tab;
            }

            @Override
            public void displayImage(Context context, ImageView imageView, HomePageTabLayout.Tab tab) {

            }

            @Override
            public void onTabClick(int position) {

            }
        };
        tabLayout.setTabProvider(tabProvider);
        tabLayout.setViewPager(viewPager);
    }

    @Override
    protected void bindData(MultiType bean) {
        super.bindData(bean);
        if(bean instanceof PageBean){
            fragments.clear();
            PageBean pageBean = (PageBean)bean;
            for(int i=0;i<pageBean.getTitleList().size();i++){
                nameList.add(pageBean.getTitleList().get(i));
                fragments.add(PageFragment.getInstance(nameList.get(i)));
            }
            statePagerAdapter.notifyDataSetChanged();
            tabLayout.notifyDataSetChanged();
        }
    }

    public RecyclerView getScrollView(){
        return ((PageFragment)fragments.get(viewPager.getCurrentItem())).getRecyclerView();
    }

    public void allScrollTop(){
        PageFragment pageFragment;
        for(int i=0;i < fragments.size();i++){
            if(i != viewPager.getCurrentItem()){
                pageFragment = (PageFragment)fragments.get(i);
                if(pageFragment != null && pageFragment.getRecyclerView() != null)
                    pageFragment.getRecyclerView().scrollToPosition(0);
            }
        }
    }

    public void changeTab(boolean isTop){
        LogUtils.d("TabLayout isTop="+isTop);
        if(isTop)tabLayout.toggleLightStyle();
        else tabLayout.toggleDarkStyle();
    }
}
