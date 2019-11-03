package com.example.mymanager.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.mymanager.R;
import com.example.mymanager.adapter.ImageAdapter;
import com.example.mymanager.adapter.StringAdapter;
import com.example.mymanager.helper.BannerHelper;
import com.example.mymanager.manager.BannerLayoutManager;
import com.example.mymanager.widget.FlyBanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    private StringAdapter stringAdapter;
    private ImageAdapter imageAdapter;
    private BannerHelper bannerHelper;
    private String[] data = {/*"https://j-image.missfresh.cn/mis_img_20190814180023748.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190815180440114.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190815113214882.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190801171559443.jpg?mryxw=750&mryxh=448",*/
            "https://j-image.missfresh.cn/mis_img_20190814180023748.jpg?mryxw=750&mryxh=448",
            "https://j-image.missfresh.cn/mis_img_20190813201803851.jpg?mryxw=750&mryxh=448"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_banner);
        initView();
        initData();
    }

    private void initView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        BannerLayoutManager layoutManager = new BannerLayoutManager(this,recyclerView,8);
        recyclerView.setLayoutManager(layoutManager);
//        Layoutmameger layoutmameger = new Layoutmameger(this);
//        recyclerView.setLayoutManager(layoutmameger);
        stringAdapter = new StringAdapter();
        recyclerView.setAdapter(stringAdapter);

        RecyclerView bannerView = findViewById(R.id.banner_view);
        imageAdapter = new ImageAdapter();
        bannerHelper = new BannerHelper(bannerView,imageAdapter);
    }

    private void initData(){
        List<String> tmpList = new ArrayList<>();
        for(int i=0;i<8;i++){
            tmpList.add("测试Banner "+i);
        }
        stringAdapter.setDataList(tmpList);

        List<String> imageList = new ArrayList<>();
        Collections.addAll(imageList,data);
        imageAdapter.setDataList(imageList);
        bannerHelper.setAutoPlay(true);
        bannerHelper.setBannerSelectCallBack(new BannerHelper.BannerSelectCallback() {
            @Override
            public void onSelected(int index, int count) {
//                Log.d("Wz","callBack "+index+"/"+count);
//                Toast.makeText(BannerActivity.this,index+"/"+count,Toast.LENGTH_SHORT).show();
            }
        });


        FlyBanner flyBanner = findViewById(R.id.flyBanner);
        flyBanner.setImagesUrl(imageList);
    }


}
