package e.user.rxjavatest;

import android.app.Application;

import e.user.rxjavatest.utils.DisplayUtils;

public class GlobalApplication extends Application {

    private static GlobalApplication instanct;

    @Override
    public void onCreate() {
        super.onCreate();
        instanct = this;
        DisplayUtils.init();
    }

    public static GlobalApplication getInstance(){
        return instanct;
    }
}
