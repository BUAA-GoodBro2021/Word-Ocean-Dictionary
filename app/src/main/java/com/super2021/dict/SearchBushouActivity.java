package com.super2021.dict;

import android.os.Bundle;

import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.ZiBean;
import com.super2021.dict.utils.CommonUtils;

import java.util.List;
import java.util.Objects;

public class SearchBushouActivity extends BaseSearchActivity {
    //创建界面
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTv.setText(R.string.main_tv_bushou);
        initData(CommonUtils.FILE_BUSHOU, CommonUtils.TYPE_BUSHOU);
        setExLvListener(CommonUtils.TYPE_BUSHOU);
        exLv.expandGroup(0);   //默认展开第一组
        word = "丨";     //默认进去时获取的是第一个 a
        List<ZiBean> list = Objects.requireNonNull(StaticData.ziBeanBuShouMap.get(word)).get(page - 1).getList();
        refreshDataByGV(list);
        setGVListener(CommonUtils.TYPE_BUSHOU);
    }
}
