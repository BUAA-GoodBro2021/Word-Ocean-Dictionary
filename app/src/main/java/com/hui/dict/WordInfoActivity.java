package com.hui.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hui.dict.bean.StaticData;
//import com.hui.dict.db.DBManager;
import com.hui.dict.bean.ZiBean;
import com.hui.dict.db.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 文字详情页面
 * */
public class WordInfoActivity extends AppCompatActivity {
    TextView ziTv, pyTv, bihuaTv, bushouTv, jsTv, xxjsTv;
    ListView jsLv;
    ImageView collectIv;
    String zi;
    List<String> mDatas; // 数据源
    private ArrayAdapter<String> adapter;
    private List<String> jijie;
    private List<String> xiangjie;
    // 设置标志位，表示汉字是否被收藏
    boolean isCollect = false;
    boolean isExistCollection = false; // 判断这个汉字是否已经存在于收藏表
    boolean isExistHistory = false; // 判断这个汉字是否已经存在于历史表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_info);
        // 接受上一个页面传递过来的对象
        Intent intent = getIntent();
        zi = intent.getStringExtra("zi");
        ZiBean ziBean = StaticData.ziBeanMap.get(zi);
        initView();
        // 初始化ListView显示的数据源
        mDatas = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.item_word_jslv, R.id.item_wordlv_tv, mDatas);
        jsLv.setAdapter(adapter);
        notifyView(ziBean);
        // 调用判断是否已经收藏了的方法
        isExistCollection = DBManager.isZiExistCollection(zi);
        isExistHistory = DBManager.isZiExistHistory(zi);
        isCollect = isExistCollection; // 记录初始状态
        if(isExistHistory){
            DBManager.deleteZiHistory(zi);
        }
        DBManager.insertZiHistory(zi);
        setCollectIvStyle();
    }

    /* 根据收藏的状态，改变星星的颜色 */
    private void setCollectIvStyle() {
        if (isCollect) {
            collectIv.setImageResource(R.mipmap.ic_collection_fs);
        } else {
            collectIv.setImageResource(R.mipmap.ic_collection);
        }
    }

    /**
     * @des 更新控件信息
     */
    private void notifyView(ZiBean resultBean) {
        ziTv.setText(resultBean.getZi());
        pyTv.setText(resultBean.getPinyin().toString());
        bihuaTv.setText("笔画 : " + resultBean.getZi_bihua());
        bushouTv.setText("部首 : " + (resultBean.getBushou() == null ? "无部首" : resultBean.getBushou()));
        jijie = Collections.singletonList(resultBean.getExplanation());
        xiangjie = Collections.singletonList(resultBean.getMore());
        // 默认一进去，就显示基本解释
        mDatas.clear();
        mDatas.addAll(jijie);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        ziTv = findViewById(R.id.word_tv_zi);
        pyTv = findViewById(R.id.word_tv_pinyin);
        bihuaTv = findViewById(R.id.word_tv_bihua);
        bushouTv = findViewById(R.id.word_tv_bushou);
        jsTv = findViewById(R.id.word_tv_js);
        xxjsTv = findViewById(R.id.word_tv_xxjs);
        jsLv = findViewById(R.id.word_lv_js);
        collectIv = findViewById(R.id.wordinfo_iv_collection);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wordinfo_iv_back:
                finish();
                break;
            case R.id.wordinfo_iv_collection:
                isCollect = !isCollect; // 将收藏状态取反
                setCollectIvStyle();
                break;
            case R.id.word_tv_js:
                jsTv.setTextColor(Color.RED);
                xxjsTv.setTextColor(Color.BLACK);
                // 清空之前数据源
                mDatas.clear();
                mDatas.addAll(jijie);
                adapter.notifyDataSetChanged();
                break;
            case R.id.word_tv_xxjs:
                xxjsTv.setTextColor(Color.RED);
                jsTv.setTextColor(Color.BLACK);
                mDatas.clear();
                mDatas.addAll(xiangjie);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    /*
     * 当activity被销毁时回调的方法
     * 将汉字进行插入或者删除
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isExistCollection && !isCollect) {
            // 原来数据收藏，后来不想收藏了，需要删除
            DBManager.deleteZiCollection(zi);
        }
        if (!isExistCollection && isCollect) {
            // 原来不存在，后来需要收藏，要插入数据
            DBManager.insertZiCollection(zi);
        }
    }
}
