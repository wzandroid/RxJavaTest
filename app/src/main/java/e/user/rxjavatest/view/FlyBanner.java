package e.user.rxjavatest.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-08-21
 */
public class FlyBanner extends RelativeLayout {

    private static final int RMP = LayoutParams.MATCH_PARENT;
    private static final int RWP = LayoutParams.WRAP_CONTENT;
    private static final int WHAT_AUTO_PLAY = 1000;

    private ViewPager mViewPager;
    //本地图片资源
    private List<Integer> mImages;
    //网络图片资源
    private List<String> mImageUrls;
    //是否只有一张图片
    private boolean mIsOneImg = false;
    //是否可以自动播放
    private boolean mAutoPlayAble = true;
    //是否正在播放
    private boolean mIsAutoPlaying = false;
    //自动播放时间
    private int mAutoPalyTime = 5000;
    //当前页面位置
    private int mCurrentPositon;

    private List<String> mTipsDatas;


    private Handler mAutoPlayHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mCurrentPositon++;
            mViewPager.setCurrentItem(mCurrentPositon);
            mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    };

    public FlyBanner(Context context) {
        this(context, null);
    }

    public FlyBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        setLayout(context);
    }

    private void setLayout(Context context) {
        //关闭view的OverScroll
        setOverScrollMode(OVER_SCROLL_NEVER);

        //添加ViewPager
        mViewPager = new ViewPager(context);
        addView(mViewPager, new LayoutParams(RMP, RMP));
    }

    /**
     * 设置网络图片
     * @param urls
     */
    public void setImagesUrl(List<String> urls) {
        //加载网络图片
        this.mImageUrls = urls;
        if (urls.size() <= 1)
            mIsOneImg = true;
        //初始化ViewPager
        initViewPager();
    }

    private void initViewPager() {

        //设置ViewPager
        FlyPageAdapter adapter = new FlyPageAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        //跳转到首页
        mViewPager.setCurrentItem(1, false);
        //当图片多于1张时开始轮播
        if (!mIsOneImg) {
            startAutoPlay();
        }
    }


    /**
     * 返回真实的位置
     * @param position
     * @return
     */
    private int toRealPosition(int position) {
        int realPosition;
        realPosition = (position - 1) % mImageUrls.size();
        if (realPosition < 0)
            realPosition += mImageUrls.size();

        return realPosition;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mCurrentPositon = position % (mImageUrls.size() + 2);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                int current = mViewPager.getCurrentItem();
                int lastReal = mViewPager.getAdapter().getCount()-2;
                if (current == 0) {
                    mViewPager.setCurrentItem(lastReal, false);
                } else if (current == lastReal+1) {
                    mViewPager.setCurrentItem(1, false);
                }
            }
        }
    };

    private class FlyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //当只有一张图片时返回1
            if (mIsOneImg) {
                return 1;
            }
            //当为网络图片，返回网页图片长度
            return mImageUrls.size() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(toRealPosition(position));
                    }
                }
            });
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getContext()).load(mImageUrls.get(toRealPosition(position))).into(imageView);
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
            if (object != null)
                object = null;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mAutoPlayAble && !mIsOneImg) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    startAutoPlay();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始播放
     */
    public void startAutoPlay() {
        if (mAutoPlayAble && !mIsAutoPlaying) {
            mIsAutoPlaying = true;
            mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
        }
    }

    /**
     * 停止播放
     */
    public void stopAutoPlay() {
        if (mAutoPlayAble && mIsAutoPlaying) {
            mIsAutoPlaying = false;
            mAutoPlayHandler.removeMessages(WHAT_AUTO_PLAY);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stopAutoPlay();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        startAutoPlay();
        super.onAttachedToWindow();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
