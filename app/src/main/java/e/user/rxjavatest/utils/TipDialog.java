package e.user.rxjavatest.utils;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import e.user.rxjavatest.R;
import e.user.rxjavatest.dialog.DialogTipBase;

public class TipDialog extends DialogTipBase {
    private static final String TAG = "TipDialog";
    private String msg; //需要展示的消息
    private TextView msgTv;

    @Override
    protected int setLayout() {
        return R.layout.dialog_tip;
    }

    @Override
    protected void initUI(View view) {
        msgTv = view.findViewById(R.id.msg_tv);
        view.findViewById(R.id.close_view).setOnClickListener(listener);
        msgTv.setText(msg);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public static void showDialog(FragmentActivity activity, String msg){
        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        TipDialog dialog = (TipDialog) fm.findFragmentByTag(TAG);
        if(dialog!=null){
            dialog.msg = msg;
            ft.show(dialog);
            ft.commitAllowingStateLoss();
        }else{
            dialog = new TipDialog();
            dialog.msg = msg;
            dialog.show(fm,TAG);
        }
    }
}
