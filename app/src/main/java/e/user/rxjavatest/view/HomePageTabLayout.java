package e.user.rxjavatest.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import e.user.rxjavatest.R;


public class HomePageTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    public static final int THEME_LIGHT = 0; // 亮色
    public static final int THEME_DARK = 1; // 暗色

    private static final int INDICATOR_WIDTH_DEFAULT = 16/*dp*/;
    private static final int INDICATOR_HEIGHT_DEFAULT = 4/*dp*/;
    private static final int INDICATOR_CORNER_RADIUS_DEFAULT = 2/*dp*/;
    private static final float TEXT_NORMAL_SIZE_DEFAULT = 14.0F/*sp*/;
    private static final float TEXT_SELECTED_SIZE_DEFAULT = 20.0F/*sp*/;
    private static final int ANIMATOR_DURATION_DEFAULT = 300/*ms*/;
    private static final float IMAGE_SCALE_FACTOR_DEFAULT = 1.3F;
    private static final int DARK_COLOR = Color.parseColor("#474245");
    private static final int LIGHT_COLOR = Color.WHITE;
    private static final int ACCENT_COLOR = Color.parseColor("#ff4891");

    /**
     * Tab item 默认的 layout 参数.
     */
    private LinearLayout.LayoutParams mDefaultTabLayoutParams;
    /**
     * Tab 容器.
     */
    private LinearLayout mTabsContainer;
    /**
     * Tab 相关连的 ViewPager .
     */
    private ViewPager mViewPager;
    /**
     * Tab 数据提供者 .
     */
    private HomePageTabLayout.TabProvider mTabProvider;

    /**
     * 用于绘制显示器
     */
    private Rect mIndicatorRect = new Rect();
    private GradientDrawable mIndicatorDrawable = new GradientDrawable();
    private float mIndicatorHeight;
    private float mIndicatorWidth;
    private float mIndicatorCornerRadius;
    private float mIndicatorMarginLeft;
    private float mIndicatorMarginRight;
    private float mIndicatorMarginBottom;
    private float mIndicatorMarginTop;
    private int mIndicatorColor;
    private int mAnimatorDuration;
    private boolean mIsTextBold;

    /**
     * 用于实现滚动居中
     */
    private Rect mTabRect = new Rect();
    private int mLastScrollX;

    /**
     * 正常显示的文字的大小
     */
    private float mTextNormalSize;
    /**
     * 选中的文字的大小
     */
    private float mTextSelectedSize;
    /**
     * 文字颜色
     */
    private int mTextColor;

    /**
     * 选中的 Tab 的位置
     */
    private int mSelectedTab;
    /**
     * 用于计算的 Tab 的滚动的位置
     */
    private int mCurrentTab;
    /**
     * 用于计算的 Tab 的偏移量
     */
    private float mCurrentPositionOffset;
    /**
     * Tab 数量
     */
    private int mTabCount;
    /**
     * 选中的图片的缩放倍数（大于 1.0f）
     */
    private float mImageScale;

    private int mCurrentTheme; // 当前文字颜色模式

    public HomePageTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HomePageTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFillViewport(true);
        setWillNotDraw(false);
        setClipChildren(false);
        setClipToPadding(false);

        obtainAttributes(context, attrs);

        mTabsContainer = new LinearLayout(context);
        mTabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        mTabsContainer.setGravity(Gravity.LEFT);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.bottomMargin = (int) (mIndicatorHeight + mIndicatorMarginBottom + mIndicatorMarginTop);
        mTabsContainer.setLayoutParams(lp);
        //set padding
        addView(mTabsContainer);
        mDefaultTabLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HomePageTabLayout);

            mIndicatorHeight = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_height,
                    dp2px(INDICATOR_HEIGHT_DEFAULT));
            mIndicatorWidth = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_width,
                    dp2px(INDICATOR_WIDTH_DEFAULT));
            mIndicatorCornerRadius = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_corner_radius,
                    dp2px(INDICATOR_CORNER_RADIUS_DEFAULT));
            mIndicatorMarginLeft = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_margin_left, 0);
            mIndicatorMarginRight = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_margin_right, 0);
            mIndicatorMarginBottom = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_margin_bottom, 0);
            mIndicatorMarginTop = ta.getDimension(R.styleable.HomePageTabLayout_hptv_indicator_margin_top, 0);
            mIndicatorColor = ta.getColor(R.styleable.HomePageTabLayout_hptv_indicator_color,
                    ContextCompat.getColor(getContext(), R.color.red_ff));
            mTextColor = ta.getColor(R.styleable.HomePageTabLayout_hptv_text_color,
                    ContextCompat.getColor(getContext(), R.color.black_474245));
            mTextNormalSize = ta.getDimension(R.styleable.HomePageTabLayout_hptv_text_normal_size,
                    sp2px(TEXT_NORMAL_SIZE_DEFAULT));
            mTextSelectedSize = ta.getDimension(R.styleable.HomePageTabLayout_hptv_text_selected_size,
                    sp2px(TEXT_SELECTED_SIZE_DEFAULT));
            mAnimatorDuration = ta.getInteger(R.styleable.HomePageTabLayout_hptv_animator_duration,
                    ANIMATOR_DURATION_DEFAULT);
            mImageScale = ta.getFloat(R.styleable.HomePageTabLayout_hptv_image_scale,
                    IMAGE_SCALE_FACTOR_DEFAULT);
            mIsTextBold = ta.getBoolean(R.styleable.HomePageTabLayout_hptv_text_bold, false);

            if (!hasScaleAnimator()) {
                mImageScale = 1.0f;
            }
            ta.recycle();
        }
    }

    /**
     * 关联ViewPager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (viewPager == null || viewPager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager is NULL or ViewPager does not have adapter instance.");
        }
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }

    /**
     * 数据有变化
     */
    public void notifyDataSetChanged() {
        mCurrentTab = 0;//更新重置当前选中tab位第一个
        mSelectedTab = 0;
        mTabsContainer.removeAllViews();
        if (mTabProvider == null && mViewPager.getAdapter() instanceof HomePageTabLayout.TabProvider) {
            mTabProvider = (HomePageTabLayout.TabProvider) mViewPager.getAdapter();
        }
        if (mTabProvider == null) {
            throw new IllegalStateException("TabProvider is NULL.");
            //return;
        }
        mTabCount = mViewPager.getAdapter().getCount();
        if (mTabCount > 0) {
            for (int i = 0; i < mTabCount; i++) {
                HomePageTabLayout.Tab tb = mTabProvider.getTab(i);
                if (tb != null) {
                    addTab(i, tb);
                }
            }
            refreshTabs();
        }
    }

    /**
     * 白模式 文字黑 indicate 系统主色
     */
    public void toggleLightStyle() {
        if (mCurrentTheme == THEME_LIGHT) {
            return;
        }
        mCurrentTheme = THEME_LIGHT;
        setTextColor(DARK_COLOR);//
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setIndicatorColor(LIGHT_COLOR);//ACCENT_COLOR
    }

    /**
     * 暗色模式 文字白色
     */
    public void toggleDarkStyle() {
        if (mCurrentTheme == THEME_DARK) {
            return;
        }
        mCurrentTheme = THEME_DARK;
        setTextColor(DARK_COLOR);//LIGHT_COLOR
        setBackgroundColor(getResources().getColor(android.R.color.white));
        setIndicatorColor(ACCENT_COLOR);//LIGHT_COLOR
    }

    private void switchTabWithAnimator(int oldPosition, int position) {
        if (oldPosition != position && mTabsContainer != null
                && oldPosition < mTabsContainer.getChildCount() && position < mTabsContainer.getChildCount()) {
            View oldView = mTabsContainer.getChildAt(oldPosition);
            View newView = mTabsContainer.getChildAt(position);
            if (oldView != null && newView != null) {
                Animator oldTabAnimator = ((HomePageTabLayout.TabView) oldView).getScaleAnimator(false);
                Animator newTabAnimator = ((HomePageTabLayout.TabView) newView).getScaleAnimator(true);
                if (oldTabAnimator != null && newTabAnimator != null) {
                    AnimatorSet localAnimatorSet = new AnimatorSet();
                    localAnimatorSet.setDuration(mAnimatorDuration);
                    localAnimatorSet.setInterpolator(new DecelerateInterpolator());
                    localAnimatorSet.play(oldTabAnimator).with(newTabAnimator);
                    localAnimatorSet.start();
                }
            }
        }
    }

    private boolean hasScaleAnimator() {
        return mTextSelectedSize != mTextNormalSize;
    }

    private void addTab(int position, HomePageTabLayout.Tab tb) {
        HomePageTabLayout.TabView tabView = new HomePageTabLayout.TabView(getContext());
        tabView.init(tb);
        tabView.setTag(Integer.valueOf(position));
        mTabsContainer.addView(tabView, position, mDefaultTabLayoutParams);
    }

    private void refreshTabs() {
        if ((mViewPager != null) && (mTabsContainer.getChildCount() > 0)) {
            for (int i = 0; i < mTabsContainer.getChildCount(); i++) {
                HomePageTabLayout.TabView tabView = (HomePageTabLayout.TabView) mTabsContainer.getChildAt(i);
                tabView.refreshTabView(mSelectedTab == i);
            }
        }
    }

    private void refreshTabsTextColor(@ColorInt int textColor) {
        if ((mViewPager != null) && (mTabsContainer.getChildCount() > 0)) {
            for (int i = 0; i < mTabsContainer.getChildCount(); i++) {
                HomePageTabLayout.TabView tabView = (HomePageTabLayout.TabView) mTabsContainer.getChildAt(i);
                if (tabView != null) {
                    tabView.refreshTextColor(textColor);
                    tabView.setSubTvShow(mCurrentTheme == THEME_LIGHT);
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mTabCount <= 0) {
            return;
        }

        int height = getHeight();
        int paddingLeft = getPaddingLeft();

        calcIndicatorRect();

        if (mIndicatorHeight > 0) {
            mIndicatorDrawable.setColor(mIndicatorColor);
            mIndicatorDrawable.setBounds(paddingLeft + (int) mIndicatorMarginLeft + mIndicatorRect.left,
                    height - (int) mIndicatorHeight - (int) mIndicatorMarginBottom,
                    paddingLeft + mIndicatorRect.right - (int) mIndicatorMarginRight,
                    height - (int) mIndicatorMarginBottom);

            mIndicatorDrawable.setCornerRadius(mIndicatorCornerRadius);
            mIndicatorDrawable.draw(canvas);
        }
    }

    private void calcIndicatorRect() {
        View currentTabView = mTabsContainer.getChildAt(mCurrentTab);
        if (currentTabView == null) {
            return;
        }
        float left = currentTabView.getLeft();
        float right = currentTabView.getRight();

        if (mCurrentTab < mTabCount - 1) {
            View nextTabView = mTabsContainer.getChildAt(mCurrentTab + 1);
            float nextTabLeft = nextTabView.getLeft();
            float nextTabRight = nextTabView.getRight();

            left = left + mCurrentPositionOffset * (nextTabLeft - left);
            right = right + mCurrentPositionOffset * (nextTabRight - right);
        }

        mIndicatorRect.left = (int) left;
        mIndicatorRect.right = (int) right;
        mTabRect.left = (int) left;
        mTabRect.right = (int) right;

        if (mIndicatorWidth < 0) {   //indicatorWidth小于0时,原jpardogo's PagerSlidingTabStrip

        } else {
            float indicatorLeft = currentTabView.getLeft() + (currentTabView.getWidth() - mIndicatorWidth) / 2;

            if (mCurrentTab < mTabCount - 1) {
                View nextTab = mTabsContainer.getChildAt(mCurrentTab + 1);
                indicatorLeft = indicatorLeft + mCurrentPositionOffset * (currentTabView.getWidth() / 2 + nextTab.getWidth() / 2);
            }

            mIndicatorRect.left = (int) indicatorLeft;
            mIndicatorRect.right = (int) (mIndicatorRect.left + mIndicatorWidth);
        }
    }

    /**
     * HorizontalScrollView滚到当前tab,并且居中显示
     */
    private void scrollToCurrentTab() {
        if (mTabCount <= 0) {
            return;
        }

        /**当前Tab的left+当前Tab的Width乘以positionOffset*/
        int offset = (int) (mCurrentPositionOffset * mTabsContainer.getChildAt(mCurrentTab).getWidth());
        int newScrollX = mTabsContainer.getChildAt(mCurrentTab).getLeft() + offset;

        if (mCurrentTab > 0 || offset > 0) {
            /**HorizontalScrollView移动到当前tab,并居中*/
            newScrollX -= getWidth() / 2 - getPaddingLeft();
            calcIndicatorRect();
            newScrollX += ((mTabRect.right - mTabRect.left) / 2);
        }

        if (newScrollX != mLastScrollX) {
            mLastScrollX = newScrollX;
            /** scrollTo（int x,int y）:x,y代表的不是坐标点,而是偏移量
             *  x:表示离起始位置的x水平方向的偏移量
             *  y:表示离起始位置的y垂直方向的偏移量
             */
            scrollTo(newScrollX, 0);
            //smoothScrollTo(newScrollX, 0);
        }
    }

    /**
     * position:当前View的位置
     * mCurrentPositionOffset:当前View的偏移量比例.[
     **/
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentTab = position;
        mCurrentPositionOffset = positionOffset;
        scrollToCurrentTab();
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        if (hasScaleAnimator()) {
            switchTabWithAnimator(mSelectedTab, position);
        }
        mSelectedTab = position;
        refreshTabs();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置文字颜色.
     *
     * @param textColor
     */
    public void setTextColor(@ColorInt int textColor) {
        mTextColor = textColor;
        refreshTabsTextColor(textColor);
    }

    /**
     * 设置指示器颜色.
     *
     * @param color
     */
    public void setIndicatorColor(@ColorInt int color) {
        mIndicatorColor = color;
        invalidate();
    }

    public void setTabProvider(HomePageTabLayout.TabProvider tabProvider) {
        mTabProvider = tabProvider;
    }

    private int dp2px(int dip) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private float px2sp(float pxValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale + 0.5f;
    }

    @SuppressLint("RestrictedApi")
    public void updateTextColor(float factor, int startColor, int endColor) {
        refreshTabsTextColor((Integer) ArgbEvaluator.getInstance().evaluate(factor, startColor, endColor));
    }

    class TabView extends ConstraintLayout {
        private TextView mTvText,mSubTv;
        private ImageView mIvImage;
        private HomePageTabLayout.Tab mTab;
        private boolean mIsShowImg;

        public TabView(Context context) {
            super(context);
            inflate(context, R.layout.layout_home_page_tab, this);
            mTvText = findViewById(R.id.tv_tab_text);
            mSubTv = findViewById(R.id.tv_tab_sub);
            mIvImage = findViewById(R.id.iv_tab_img);
            setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    int position = mTabsContainer.indexOfChild(view);
                    if (position != -1) {
                        if (mTabProvider != null) {
                            mTabProvider.onTabClick(position);
                        }
                        boolean smoothly = Math.abs(position - mSelectedTab) == 1;
                        mViewPager.setCurrentItem(position, smoothly);
                    }
                }
            });
        }

        public void init(HomePageTabLayout.Tab tab) {
            mTab = tab;
            if (!TextUtils.isEmpty(tab.imgUrl)) {
                mIsShowImg = true;
                mIvImage.setVisibility(VISIBLE);
                mTvText.setVisibility(GONE);
                mTabProvider.displayImage(getContext(), mIvImage, mTab);
            } else {
                mIsShowImg = false;
                mIvImage.setVisibility(GONE);
                mTvText.setVisibility(VISIBLE);
                mTvText.setText(mTab.getName());
                mSubTv.setText(mTab.getSubName());
                mTvText.setTextColor(mTextColor);
                if(mIsTextBold) {
                    mTvText.setTypeface(null, Typeface.BOLD);
                }
                mTvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextNormalSize);
            }
        }

        /**
         * Tab 缩放动画，如果是当前选中的tab则动画由小到大，未选中由大到小
         *
         * @param selected
         * @return
         */
        private Animator getScaleAnimator(boolean selected) {
            if (mTab == null) {
                return null;
            }
            mTab.setSelected(selected);
            if (!TextUtils.isEmpty(mTab.getImgUrl())
                    && mTab.getImgOriginalWidth() > 0
                    && mTab.getImgOriginalHeight() > 0) {
                float fromValue = selected ? 1.0F : mImageScale;
                float toValue = selected ? mImageScale : 1.0F;
                ValueAnimator va = ValueAnimator.ofFloat(fromValue, toValue);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float cVal = (Float) animation.getAnimatedValue();
                        LayoutParams lp = (LayoutParams) mIvImage.getLayoutParams();
                        lp.width = (int) (mTab.getImgOriginalWidth() * cVal);
                        lp.height = (int) (mTab.getImgOriginalHeight() * cVal);
                        mIvImage.setLayoutParams(lp);
                    }
                });
                return va;
            } else {
                float fromSize = px2sp(selected ? mTextNormalSize : mTextSelectedSize);
                float toSize = px2sp(selected ? mTextSelectedSize : mTextNormalSize);
                ObjectAnimator ob = ObjectAnimator.ofFloat(mTvText, "textSize", fromSize, toSize);
                return ob;
            }
        }

        public void setTextSize(float textSize) {
            mTvText.setTextSize(textSize);
        }

        public void refreshTabView(boolean selected) {
            if (!TextUtils.isEmpty(mTab.getImgUrl())
                    && mTab.getImgOriginalWidth() > 0
                    && mTab.getImgOriginalHeight() > 0) {
                mTvText.setVisibility(GONE);
                mIvImage.setVisibility(VISIBLE);
                float imageSizeScale = selected ? mImageScale : 1.0F;
                LayoutParams flp = (LayoutParams) mIvImage.getLayoutParams();
                flp.width = (int) (mTab.getImgOriginalWidth() * imageSizeScale);
                flp.height = (int) (mTab.getImgOriginalHeight() * imageSizeScale);
                mIvImage.setLayoutParams(flp);
            } else {
                mIvImage.setVisibility(GONE);
                mTvText.setVisibility(VISIBLE);
                float textSize = selected ? mTextSelectedSize : mTextNormalSize;
                mTvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                mTvText.setTextColor(mTextColor);
                mTvText.setTypeface(null, selected ? Typeface.BOLD : Typeface.NORMAL);
            }
            mTab.setSelected(selected);
            mSubTv.setSelected(selected);
        }

        public void refreshTextColor(@ColorInt int color) {
            mTvText.setTextColor(color);
        }

        public void setSubTvShow(boolean isShow){

            mSubTv.setVisibility(isShow ? VISIBLE : GONE);
        }

        /**
         * 放大.
         */
        public void zoomIn(float factor) {
            if (mIsShowImg) {

            } else {
                float size = mTextNormalSize + factor * (mTextSelectedSize - mTextNormalSize);
                mTvText.setTextSize(px2sp(size));
            }
        }

        /**
         * 缩小
         */
        public void zoomOut(float factor) {
            if (mIsShowImg) {

            } else {
                float size = mTextSelectedSize - factor * (mTextSelectedSize - mTextNormalSize);
                mTvText.setTextSize(px2sp(size));
            }
        }
    }

    public static class Tab {
        private String name;
        private String subName;
        private String imgUrl;
        private boolean selected;
        private int imgOriginalWidth;
        private int imgOriginalHeight;

        public String getSubName() {
            return subName;
        }

        public void setSubName(String subName) {
            this.subName = subName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getImgOriginalWidth() {
            return imgOriginalWidth;
        }

        public void setImgOriginalWidth(int imgOriginalWidth) {
            this.imgOriginalWidth = imgOriginalWidth;
        }

        public int getImgOriginalHeight() {
            return imgOriginalHeight;
        }

        public void setImgOriginalHeight(int imgOriginalHeight) {
            this.imgOriginalHeight = imgOriginalHeight;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

    public interface TabProvider {
        HomePageTabLayout.Tab getTab(int position);

        void displayImage(Context context, ImageView imageView, HomePageTabLayout.Tab tab);

        void onTabClick(int position);
    }
}
