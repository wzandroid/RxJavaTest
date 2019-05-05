package com.example.testnestedscroll;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.testnestedscroll.adapter.SlideFragmentPagerAdapter;
import com.example.testnestedscroll.view.DragScrollDetailsLayout;

public class ScrollActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private DragScrollDetailsLayout mDragScrollDetailsLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);
        initUI();
    }

    private void initUI() {
        viewPager = findViewById(R.id.viewpager);
        mDragScrollDetailsLayout = findViewById(R.id.drag_content);

        final TabLayout tabLayout =  findViewById(R.id.tablayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setScrollPosition(position, 0, false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setAdapter(new SlideFragmentPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        mDragScrollDetailsLayout.setOnSlideDetailsListener(new DragScrollDetailsLayout.OnSlideFinishListener() {
            @Override
            public void onStatueChanged(DragScrollDetailsLayout.CurrentTargetIndex status) {
//                if (status == DragScrollDetailsLayout.CurrentTargetIndex.UPSTAIRS)
//                    mTextView.setText("pull up to show more");
//                else
//                    mTextView.setText("pull down to top");

            }
        });
    }
}
