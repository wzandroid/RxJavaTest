package com.example.testnestedscroll.bean;

import java.util.ArrayList;
import java.util.List;

public class PagerBean {
    private List<String> dataList;

    public List<String> getDataList(){
        if(dataList == null){
            dataList = new ArrayList<>();
            for(int i=0;i<20;i++){
                dataList.add("商品："+i);
            }
        }
        return dataList;
    }
}
