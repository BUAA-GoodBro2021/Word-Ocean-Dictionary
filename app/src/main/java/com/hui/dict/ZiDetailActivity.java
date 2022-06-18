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
import com.hui.dict.bean.ZiBean;
import com.hui.dict.db.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 字的详情的Activity组件.
 */
public class ZiDetailActivity extends AppCompatActivity {
    TextView ziTv, pyTv, bihuaTv, bushouTv, jsTv, xxjsTv;
    ListView jsLv;
    ImageView collectIv;
    String zi;
    // 数据源
    List<String> mDatas;
    private ArrayAdapter<String> adapter;
    private List<String> jijie;
    private List<String> xiangjie;
    // 设置标志位，表示汉字是否被收藏
    boolean isCollect = false;
    // 判断这个汉字是否已经存在于收藏表
    boolean isExistCollection = false;
    // 判断这个汉字是否已经存在于历史表
    boolean isExistHistory = false;

    /**
     * 创建界面.
     * @param savedInstanceState 保存实例状态
     */
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
        assert ziBean != null;
        notifyView(ziBean);
        // 调用判断是否已经收藏了的方法
        isExistCollection = DBManager.isZiExistCollection(zi);
        isExistHistory = DBManager.isZiExistHistory(zi);
        // 记录初始收藏状态
        isCollect = isExistCollection;
        if (isExistHistory) {
            DBManager.deleteZiHistory(zi);
        }
        DBManager.insertZiHistory(zi);
        setCollectIvStyle();
    }

    /**
     * 根据收藏的状态，改变星星的颜色.
     */
    private void setCollectIvStyle() {
        if (isCollect) {
            collectIv.setImageResource(R.mipmap.ic_collection_fs);
        } else {
            collectIv.setImageResource(R.mipmap.ic_collection);
        }
    }

    /**
     * 设置刚进入的显示界面.
     * @param resultBean 成语模块
     */
    private void notifyView(ZiBean resultBean) {
        ziTv.setText(resultBean.getZi());
        pyTv.setText(resultBean.getPinyin().toString());
        bihuaTv.setText(String.format("笔画 : %s",
                resultBean.getZi_bihua()));
        bushouTv.setText(String.format("部首 : %s",
                (resultBean.getBushou() == null ? "无部首" : resultBean.getBushou())));
        jijie = Collections.singletonList(resultBean.getExplanation());
        xiangjie = Collections.singletonList(resultBean.getMore());
        // 默认一进去，就显示基本解释
        mDatas.clear();
        mDatas.addAll(jijie);
        adapter.notifyDataSetChanged();
    }

    /**
     * 查找控件的方法.
     */
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

    /**
     * 点击事件.
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if (view.getId() == R.id.wordinfo_iv_back) {
            finish();
        } else if (view.getId() == R.id.wordinfo_iv_collection) {
            // 将收藏状态取反
            isCollect = !isCollect;
            setCollectIvStyle();
        } else if (view.getId() == R.id.word_tv_js) {
            jsTv.setTextColor(Color.RED);
            xxjsTv.setTextColor(Color.BLACK);
            // 清空之前数据源
            mDatas.clear();
            mDatas.addAll(jijie);
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.word_tv_xxjs) {
            xxjsTv.setTextColor(Color.RED);
            jsTv.setTextColor(Color.BLACK);
            mDatas.clear();
            mDatas.addAll(xiangjie);
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 当activity被销毁时回调的方法.
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
