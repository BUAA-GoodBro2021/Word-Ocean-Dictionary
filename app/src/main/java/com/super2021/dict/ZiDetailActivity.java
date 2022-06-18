package com.super2021.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.ZiBean;
import com.super2021.dict.db.DBManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ZiDetailActivity extends AppCompatActivity {
    TextView ziTv, pinyinTv, bihuaTv, bushouTv, explanationTv, moreTv;
    ListView explanationLv;
    ImageView collectIv;
    String zi;
    // 数据源
    List<String> mDatas;
    private ArrayAdapter<String> adapter;
    private List<String> explanation;
    private List<String> more;
    // 设置标志位，表示汉字是否被收藏
    boolean isCollect = false;
    // 判断这个汉字是否已经存在于收藏表
    boolean isExistCollection = false;
    // 判断这个汉字是否已经存在于历史表
    boolean isExistHistory = false;

    // 创建界面
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
        adapter = new ArrayAdapter<>(this, R.layout.zi_detail_lv, R.id.zi_detail_tv, mDatas);
        explanationLv.setAdapter(adapter);
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

    // 根据收藏的状态，改变星星的颜色
    private void setCollectIvStyle() {
        if (isCollect) {
            collectIv.setImageResource(R.mipmap.ic_collection_fs);
        } else {
            collectIv.setImageResource(R.mipmap.ic_collection);
        }
    }

    // 刷新信息
    private void notifyView(ZiBean resultBean) {
        ziTv.setText(resultBean.getZi());
        pinyinTv.setText(resultBean.getPinyin().toString());
        bihuaTv.setText(String.format("笔画 : %s",
                resultBean.getZi_bihua()));
        bushouTv.setText(String.format("部首 : %s",
                (resultBean.getBushou() == null ? "无部首" : resultBean.getBushou())));
        explanation = Collections.singletonList(resultBean.getExplanation());
        more = Collections.singletonList(resultBean.getMore());
        // 默认一进去，就显示基本解释
        mDatas.clear();
        mDatas.addAll(explanation);
        adapter.notifyDataSetChanged();
    }

    // 初始化组件
    private void initView() {
        ziTv = findViewById(R.id.word_tv_zi);
        pinyinTv = findViewById(R.id.word_tv_pinyin);
        bihuaTv = findViewById(R.id.word_tv_bihua);
        bushouTv = findViewById(R.id.word_tv_bushou);
        explanationTv = findViewById(R.id.word_tv_js);
        moreTv = findViewById(R.id.word_tv_xxjs);
        explanationLv = findViewById(R.id.word_lv_js);
        collectIv = findViewById(R.id.zi_iv_collection);
    }

    // 点击事件
    public void onClick(View view) {
        if (view.getId() == R.id.zi_iv_back) {
            finish();
        } else if (view.getId() == R.id.zi_iv_collection) {
            // 将收藏状态取反
            isCollect = !isCollect;
            setCollectIvStyle();
        } else if (view.getId() == R.id.word_tv_js) {
            explanationTv.setTextColor(Color.RED);
            moreTv.setTextColor(Color.BLACK);
            // 清空之前数据源
            mDatas.clear();
            mDatas.addAll(explanation);
            adapter.notifyDataSetChanged();
        } else if (view.getId() == R.id.word_tv_xxjs) {
            moreTv.setTextColor(Color.RED);
            explanationTv.setTextColor(Color.BLACK);
            mDatas.clear();
            mDatas.addAll(more);
            adapter.notifyDataSetChanged();
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
