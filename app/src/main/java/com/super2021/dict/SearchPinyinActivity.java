package com.super2021.dict;

import android.os.Bundle;

import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.ZiBean;
import com.super2021.dict.utils.CommonUtils;

import java.util.List;
import java.util.Objects;

/**
 * 拼音查找的Activity组件.
 */
public class SearchPinyinActivity extends BaseSearchActivity {
    /**
     * 创建界面.
     * 
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(CommonUtils.FILE_PINYIN, CommonUtils.TYPE_PINYIN);
        setExLvListener(CommonUtils.TYPE_PINYIN);
        exLv.expandGroup(0); // 默认展开第一组
        word = "a"; // 默认进去时获取的是第一个 a
        List<ZiBean> list = Objects.requireNonNull(StaticData.ziBeanPinYinMap.get(word)).get(page - 1).getList();
        refreshDataByGV(list);
        setGVListener(CommonUtils.TYPE_PINYIN);
    }
}
