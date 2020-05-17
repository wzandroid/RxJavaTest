package e.user.rxjavatest.bean.holder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.PageFragment;
import e.user.rxjavatest.R;
import e.user.rxjavatest.adapter.BaseMultiAdapter;
import e.user.rxjavatest.bean.PageBean;
import e.user.rxjavatest.interfaces.MultiType;
import e.user.rxjavatest.view.XTabLayout;

public class ViewPageHolder extends BaseMultiAdapter.BaseHolderView {
    private ViewPager viewPager;
    private XTabLayout xTabLayout;
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> nameList = new ArrayList<>();
    private FragmentStatePagerAdapter statePagerAdapter;
    private boolean isTop;

    public ViewPageHolder(@NonNull View itemView, FragmentActivity activity) {
        super(itemView);
        viewPager = itemView.findViewById(R.id.view_page);
        xTabLayout = itemView.findViewById(R.id.x_tab_layout);
        statePagerAdapter = new PageAdapter(activity.getSupportFragmentManager());
        viewPager.setAdapter(statePagerAdapter);
        xTabLayout.setupWithViewPager(viewPager);
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

    public void changeTab(boolean isTop) {
        if(this.isTop != isTop){
            this.isTop = isTop;
            xTabLayout.setTop(isTop);
        }
    }

    private class PageAdapter extends FragmentStatePagerAdapter implements XTabLayout.DoubleTitle{

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            getTitle(position);
            getSubTitle(position);
            return nameList.get(position);
        }

        @Override
        public CharSequence getTitle(int position) {
            return nameList.get(position);
        }

        @Override
        public CharSequence getSubTitle(int position) {
            return "副"+nameList.get(position);
        }
    }
}
