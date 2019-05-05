package e.user.rxjavatest.bean.holder;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
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
}
