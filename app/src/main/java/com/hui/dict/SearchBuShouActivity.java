package com.hui.dict;

import android.os.Bundle;

import com.hui.dict.bean.StaticData;
import com.hui.dict.bean.ZiBean;
import com.hui.dict.utils.CommonUtils;

import java.util.List;

public class SearchBuShouActivity extends BaseSearchActivity {

    String url;   //获取指定部首对应的网址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titleTv.setText(R.string.main_tv_bushou);
        initData(CommonUtils.FILE_BUSHOU,CommonUtils.TYPE_BUSHOU);
        setExLvListener(CommonUtils.TYPE_BUSHOU);
        exLv.expandGroup(0);   //默认展开第一组
        word = "丨";     //默认进去时获取的是第一个 a
        List<ZiBean> list = StaticData.ziBeanBuShouMap.get(word).get(page - 1).getList();
        refreshDataByGV(list);
        setGVListener(CommonUtils.TYPE_BUSHOU);
    }
}
