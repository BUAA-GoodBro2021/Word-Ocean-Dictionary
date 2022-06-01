package com.hui.dict;

import android.os.Bundle;

import com.hui.dict.bean.StaticData;
import com.hui.dict.bean.ZiBean;
import com.hui.dict.utils.CommonUtils;

import java.util.List;

public class SearchPinyinActivity extends BaseSearchActivity {

    String url;   //获取指定拼音对应的网址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(CommonUtils.FILE_PINYIN, CommonUtils.TYPE_PINYIN);
        setExLvListener(CommonUtils.TYPE_PINYIN);
        exLv.expandGroup(0);   //默认展开第一组
        word = "a";     //默认进去时获取的是第一个 a
        List<ZiBean> list = StaticData.ziBeanPinYinMap.get(word).get(page - 1).getList();
        refreshDataByGV(list);
        setGVListener(CommonUtils.TYPE_PINYIN);
    }
}
