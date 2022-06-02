package com.hui.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.hui.dict.bean.ChengyuBean;
import com.hui.dict.bean.PinBuBean;
import com.hui.dict.bean.StaticData;
import com.hui.dict.bean.ZiBean;
import com.hui.dict.utils.AssetsUtils;
import com.hui.dict.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LoadActivity extends AppCompatActivity {
    // 加载线程是否结束
    final Object isFinish = "";
    // private final int SPLASH_DISPLAY_LENGHT = 2000; // 两秒后进入系统
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_load);
        Thread loadThread=new Thread(){
            @Override
            public void run() {
                synchronized (isFinish){
                    initList();
                }
            }
        };
        Thread mainThread=new Thread(){
            @Override
            public void run() {
                // 动画直接在此加载即可
                synchronized (isFinish) {
                    try{
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        // 加载数据线程
        loadThread.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 延时跳转线程，可放视频
        mainThread.start();
    }

    private void initList() {
        String ziList = AssetsUtils.getAssetsContent(this, CommonUtils.FILE_ZI);
        JsonArray jsonZiArray = JsonParser.parseString(ziList).getAsJsonArray();
        String chengyuList = AssetsUtils.getAssetsContent(this, CommonUtils.FILE_CHENGYU);
        JsonArray jsonChengyuArray = JsonParser.parseString(chengyuList).getAsJsonArray();
        Gson gson = new Gson();
        Map<String, List<ZiBean>> pinYinBeans = new HashMap<>();
        Map<String, List<ZiBean>> buShouBeans = new HashMap<>();
        String pinYinList = AssetsUtils.getAssetsContent(this, CommonUtils.FILE_PINYIN);
        String buShouList = AssetsUtils.getAssetsContent(this, CommonUtils.FILE_BUSHOU);
        PinBuBean pinYinBean = gson.fromJson(pinYinList, PinBuBean.class);
        PinBuBean buShouBean = gson.fromJson(buShouList, PinBuBean.class);
        StaticData.pinYinBean = pinYinBean;
        StaticData.buShouBean = buShouBean;
        StaticData.ziBeanPinYinMap = new HashMap<>();
        StaticData.ziBeanBuShouMap = new HashMap<>();
        for (PinBuBean.ResultBean resultBean : StaticData.pinYinBean.getResult()) {
            pinYinBeans.put(resultBean.getPinyin(), new ArrayList<ZiBean>());
        }
        for (PinBuBean.ResultBean resultBean : StaticData.buShouBean.getResult()) {
            buShouBeans.put(resultBean.getBushou(), new ArrayList<ZiBean>());
        }

        for (JsonElement json : jsonZiArray) {
            ZiBean ziBean = gson.fromJson(json, ZiBean.class);
            StaticData.ziBeanMap.put(ziBean.getZi(), ziBean);
            for (String pinyin : ziBean.getPinyin_origin()) {
                Objects.requireNonNull(pinYinBeans.get(pinyin)).add(ziBean);
            }
            String bushou = ziBean.getBushou();
            if (bushou != null) {
                Objects.requireNonNull(buShouBeans.get(bushou)).add(ziBean);
            }
        }
        for (JsonElement json : jsonChengyuArray) {
            ChengyuBean chengyuBean = gson.fromJson(json, ChengyuBean.class);
            StaticData.chengyuBeanMap.put(chengyuBean.getWord(), chengyuBean);
        }
        for (String key : pinYinBeans.keySet()) {
            List<StaticData.ListBean> listBeans = new ArrayList<>();
            int totalCount = Objects.requireNonNull(pinYinBeans.get(key)).size();
            int restCount = totalCount;
            int pageSize = Math.min(restCount, 48);
            int page = 1;
            int totalPage = (int) (Math.ceil((double) (totalCount) / 48));
            do {
                StaticData.ListBean listBean = new StaticData.ListBean();
                for (int i = (page - 1) * 48; i < (page - 1) * 48 + pageSize; i++) {
                    listBean.getList().add(Objects.requireNonNull(pinYinBeans.get(key)).get(i));
                }
                listBean.setPage(page++);
                listBean.setPageSize(pageSize);
                listBean.setTotalPage(totalPage);
                listBean.setTotalCount(totalCount);
                listBeans.add(listBean);
                restCount -= pageSize;
                pageSize = Math.min(restCount, 48);
            } while (pageSize > 0);
            StaticData.ziBeanPinYinMap.put(key, listBeans);
        }
        for (String key : buShouBeans.keySet()) {
            List<StaticData.ListBean> listBeans = new ArrayList<>();
            int totalCount = Objects.requireNonNull(buShouBeans.get(key)).size();
            int restCount = totalCount;
            int pageSize = Math.min(restCount, 48);
            int page = 1;
            int totalPage = (int) (Math.ceil((double) (totalCount) / 48));
            do {
                StaticData.ListBean listBean = new StaticData.ListBean();
                for (int i = (page - 1) * 48; i < (page - 1) * 48 + pageSize; i++) {
                    listBean.getList().add(Objects.requireNonNull(buShouBeans.get(key)).get(i));
                }
                listBean.setPage(page++);
                listBean.setPageSize(pageSize);
                listBean.setTotalPage(totalPage);
                listBean.setTotalCount(totalCount);
                listBeans.add(listBean);
                restCount -= pageSize;
                pageSize = Math.min(restCount, 48);
            } while (pageSize > 0);
            StaticData.ziBeanBuShouMap.put(key, listBeans);
        }
    }
}