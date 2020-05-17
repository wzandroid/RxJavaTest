package com.example.testnestedscroll.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.testnestedscroll.R;
import com.example.testnestedscroll.Utils;
import com.example.testnestedscroll.interfaces.OnAnimationEndListener;
import com.example.testnestedscroll.interfaces.OnLikeListener;

public class LikeButton extends FrameLayout implements View.OnClickListener {

    private static final DecelerateInterpolator DECCELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateDecelerateInterpolator ACCELERATE_DECELERATE_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final AnticipateOvershootInterpolator OVERSHOOT_INTERPOLATOR = new AnticipateOvershootInterpolator(6);

    private ImageView icon,shadow;
    private DotsView dotsView;
    private LikeAnimView likeAnimView;
    private OnLikeListener likeListener;
    private OnAnimationEndListener animationEndListener;
    private int iconSize;

    private float animationScaleFactor;

    private boolean isChecked;


    private boolean isEnabled;
    private AnimatorSet animatorSet;

    private Drawable likeDrawable;
    private Drawable unLikeDrawable;

    private Bitmap likeBitmap,unLikeBitmap;

    public LikeButton(Context context) {
        this(context, null);
    }

    public LikeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(!isInEditMode())
            init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        LayoutInflater.from(getContext()).inflate(R.layout.likeview, this, true);
        icon = findViewById(R.id.icon);
        shadow = findViewById(R.id.shadow);
        dotsView = findViewById(R.id.dots);
        likeAnimView = findViewById(R.id.like_view);

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LikeButton, defStyle, 0);

        iconSize = array.getDimensionPixelSize(R.styleable.LikeButton_icon_size, 40);

        likeDrawable = getDrawableFromResource(array, R.styleable.LikeButton_like_drawable);


        if (likeDrawable != null)
            setLikeDrawable(likeDrawable);

        likeBitmap = BitmapFactory.decodeResource(getResources(),R.styleable.LikeButton_like_drawable);
        unLikeBitmap = BitmapFactory.decodeResource(getResources(),R.styleable.LikeButton_unlike_drawable);

        unLikeDrawable = getDrawableFromResource(array, R.styleable.LikeButton_unlike_drawable);

        if (unLikeDrawable != null)
            setUnlikeDrawable(unLikeDrawable);

        int dotPrimaryColor = array.getColor(R.styleable.LikeButton_dots_primary_color, 0);
        int dotSecondaryColor = array.getColor(R.styleable.LikeButton_dots_secondary_color, 0);

        if (dotPrimaryColor != 0 && dotSecondaryColor != 0) {
            dotsView.setColors(dotPrimaryColor, dotSecondaryColor);
        }

        setEnabled(array.getBoolean(R.styleable.LikeButton_is_enabled, true));
        Boolean status = array.getBoolean(R.styleable.LikeButton_liked, false);
        setAnimationScaleFactor(array.getFloat(R.styleable.LikeButton_anim_scale_factor, 3));
        setLiked(status);
        setOnClickListener(this);
        array.recycle();
    }

    private Drawable getDrawableFromResource(TypedArray array, int styleableIndexId) {
        int id = array.getResourceId(styleableIndexId, -1);

        return (-1 != id) ? ContextCompat.getDrawable(getContext(), id) : null;
    }

    @Override
    public void onClick(View v) {

        if (!isEnabled)
            return;

        isChecked = !isChecked;

        icon.setImageDrawable(isChecked ? likeDrawable : unLikeDrawable);

        if (likeListener != null) {
            if (isChecked) {
                likeListener.liked(this);
            } else {
                likeListener.unLiked(this);
            }
        }

        if (animatorSet != null) {
            animatorSet.cancel();
        }

        if (isChecked) {
            icon.animate().cancel();
            icon.setScaleX(0);
            icon.setScaleY(0);
            shadow.animate().cancel();
            shadow.setScaleX(0);
            shadow.setScaleY(0);
            shadow.setAlpha(0.2f);
            dotsView.setCurrentProgress(0);

            animatorSet = new AnimatorSet();

            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(icon, ImageView.SCALE_Y, 0.6f, 1f);
            starScaleYAnimator.setDuration(600);
            starScaleYAnimator.setStartDelay(50);
            starScaleYAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(icon, ImageView.SCALE_X, 0.6f, 1f);
            starScaleXAnimator.setDuration(600);
            starScaleXAnimator.setStartDelay(50);
            starScaleXAnimator.setInterpolator(OVERSHOOT_INTERPOLATOR);

            ObjectAnimator startShadowScaleXAnimator = ObjectAnimator.ofFloat(shadow, ImageView.SCALE_X, 0.4f, 1.4f);
            startShadowScaleXAnimator.setDuration(500);
            startShadowScaleXAnimator.setStartDelay(50);
            startShadowScaleXAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator startShadowScaleYAnimator = ObjectAnimator.ofFloat(shadow, ImageView.SCALE_Y, 0.4f, 1.4f);
            startShadowScaleYAnimator.setDuration(500);
            startShadowScaleYAnimator.setStartDelay(50);
            startShadowScaleYAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);

            ObjectAnimator alphaShadowAnimator = ObjectAnimator.ofFloat(shadow, ImageView.ALPHA,0.2f, 0f);
            alphaShadowAnimator.setDuration(350);
            alphaShadowAnimator.setStartDelay(200);
            startShadowScaleYAnimator.setInterpolator(DECCELERATE_INTERPOLATOR);


            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(dotsView, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(ACCELERATE_DECELERATE_INTERPOLATOR);

            animatorSet.playTogether(
                    starScaleYAnimator,
                    starScaleXAnimator,
                    startShadowScaleXAnimator,
                    startShadowScaleYAnimator,
                    alphaShadowAnimator,
                    dotsAnimator
            );


            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    dotsView.setCurrentProgress(0);
                    icon.setScaleX(1);
                    icon.setScaleY(1);
                    shadow.setScaleX(1);
                    shadow.setScaleY(1);
                }

                @Override public void onAnimationEnd(Animator animation) {
                    if(animationEndListener != null) {
                        animationEndListener.onAnimationEnd(LikeButton.this);
                    }
                }
            });

            animatorSet.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled)
            return true;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*
                Commented out this line and moved the animation effect to the action up event due to
                conflicts that were occurring when library is used in sliding type views.
                icon.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                */
                setPressed(true);
                break;

            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            case MotionEvent.ACTION_UP:
                icon.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(DECCELERATE_INTERPOLATOR);
                icon.animate().scaleX(1).scaleY(1).setInterpolator(DECCELERATE_INTERPOLATOR);
                if (isPressed()) {
                    performClick();
                    setPressed(false);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                setPressed(false);
                break;
        }
        return true;
    }

    public void setLikeDrawableRes(@DrawableRes int resId) {
        likeDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
        }

        if (isChecked) {
            icon.setImageDrawable(likeDrawable);
        }
    }

    public void setLikeDrawable(Drawable likeDrawable) {
        this.likeDrawable = likeDrawable;

        if (iconSize != 0) {
            this.likeDrawable = Utils.resizeDrawable(getContext(), likeDrawable, iconSize, iconSize);
        }

        if (isChecked) {
            icon.setImageDrawable(this.likeDrawable);
        }
        shadow.setImageDrawable(this.likeDrawable);
    }

    public void setUnlikeDrawableRes(@DrawableRes int resId) {
        unLikeDrawable = ContextCompat.getDrawable(getContext(), resId);

        if (iconSize != 0) {
            unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        }

        if (!isChecked) {
            icon.setImageDrawable(unLikeDrawable);
        }
    }

    /**
     * This drawable will be shown when the button is in on unLiked state.
     *
     * @param unLikeDrawable
     */
    public void setUnlikeDrawable(Drawable unLikeDrawable) {
        this.unLikeDrawable = unLikeDrawable;

        if (iconSize != 0) {
            this.unLikeDrawable = Utils.resizeDrawable(getContext(), unLikeDrawable, iconSize, iconSize);
        }

        if (!isChecked) {
            icon.setImageDrawable(this.unLikeDrawable);
        }
    }

    public void setOnLikeListener(OnLikeListener likeListener) {
        this.likeListener = likeListener;
    }

    public void setOnAnimationEndListener(OnAnimationEndListener animationEndListener) {
        this.animationEndListener = animationEndListener;
    }

    /**
     * This set sets the colours that are used for the little dots
     * that will be exploding once the like button is clicked.
     *
     * @param primaryColor
     * @param secondaryColor
     */
    public void setExplodingDotColorsRes(@ColorRes int primaryColor, @ColorRes int secondaryColor) {
        dotsView.setColors(ContextCompat.getColor(getContext(), primaryColor), ContextCompat.getColor(getContext(), secondaryColor));
    }

    public void setExplodingDotColorsInt(@ColorInt int primaryColor, @ColorInt int secondaryColor) {
        dotsView.setColors(primaryColor, secondaryColor);
    }


    /**
     * This function updates the dots view and the circle
     * view with the respective sizes based on the size
     * of the icon being used.
     */
    private void setEffectsViewSize() {
        if (iconSize != 0) {
            dotsView.setSize((int) (iconSize * animationScaleFactor), (int) (iconSize * animationScaleFactor));
        }
    }

    /**
     * Sets the initial state of the button to liked
     * or unliked.
     *
     * @param status
     */
    public void setLiked(Boolean status) {
        if (status) {
            isChecked = true;
            icon.setImageDrawable(likeDrawable);
        } else {
            isChecked = false;
            icon.setImageDrawable(unLikeDrawable);
        }
    }

    /**
     * Returns current like state
     *
     * @return current like state
     */
    public boolean isLiked() {
        return isChecked;
    }

    @Override
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    /**
     * Sets the factor by which the dots should be sized.
     */
    public void setAnimationScaleFactor(float animationScaleFactor) {
        this.animationScaleFactor = animationScaleFactor;
        setEffectsViewSize();
    }

}
