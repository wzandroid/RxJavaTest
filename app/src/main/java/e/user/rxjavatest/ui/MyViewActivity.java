package e.user.rxjavatest.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import e.user.rxjavatest.R;
import e.user.rxjavatest.view.MyTabView;

/**
 * File description.
 *
 * @author 王震
 * @date 2019-10-07
 */
public class MyViewActivity extends AppCompatActivity {
    private MyTabView myTabView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);

        myTabView = findViewById(R.id.my_tab);

        List<String> tabList = new ArrayList<>();
        for(int i=0;i<20;i++){
            tabList.add(i+"号标签");
        }
        myTabView.setTabList(tabList);
    }
}
