package com.example.mymanager;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mymanager.ui.BannerActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    public void onCLick(View v){
        switch (v.getId()){
            case R.id.tv_1:
                startActivity(new Intent(this, BannerActivity.class));
                break;
        }
    }

    private void initView(){

    }

    private void initData(){

    }
}
