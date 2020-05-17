package e.user.rxjavatest.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import e.user.rxjavatest.R;
import e.user.rxjavatest.utils.DisplayUtils;

public abstract class BaseDialog extends DialogFragment {
    private Dialog dialog;
    protected View.OnClickListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new Dialog(getActivity(), R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(setTouchOutSide());
        //填充对话框的布局
        View view = LayoutInflater.from(getActivity()).inflate(setLayout(), null);
        //初始化控件
        initUI(view);
        //将布局设置给Dialog
        dialog.setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(getGravity());
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = DisplayUtils.getWidthPx()-setMargin();
        if(setMinHeight()>0){
            lp.height = setMinHeight();
        }
        lp.y = setMarginBottom();//设置Dialog距离底部的距离
        //设置背景颜色
        lp.dimAmount = getDim();
        dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialogWindow.setWindowAnimations(setAnimations());
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
                    return onKeyBack();
                }else{
                    return false;
                }
            }
        });
        return dialog;
    }

    protected int getGravity(){
        return Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
    }

    protected int setAnimations(){
        return R.style.ActionSheetDialogAnimation;
    }

    //对返回键处理对话框
    protected boolean onKeyBack(){
        return false;
    }

    //设置弹出窗口后面的背景颜色 0为完全透明，1为完全不透明
    protected float getDim(){return 0.6f;}

    protected int setMargin(){
        return 0;
    }

    protected int setMarginBottom(){
        return 0;
    }

    protected int setMinHeight(){
        return 0;
    }

    protected boolean setTouchOutSide(){
        return true;
    }

    protected abstract int setLayout();

    protected abstract void initUI(View view);

}
