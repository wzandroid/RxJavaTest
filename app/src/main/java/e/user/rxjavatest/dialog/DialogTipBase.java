package e.user.rxjavatest.dialog;

import android.view.Gravity;

import e.user.rxjavatest.R;

public abstract class DialogTipBase extends BaseDialog {

    @Override
    protected int setMargin() {
        return getActivity().getResources().getDimensionPixelSize(R.dimen.dp_49);
    }

    @Override
    protected boolean setTouchOutSide() {
        return false;
    }

    @Override
    protected int setAnimations() {
        return 0;
    }

    @Override
    protected int getGravity() {
        return Gravity.CENTER;
    }
}