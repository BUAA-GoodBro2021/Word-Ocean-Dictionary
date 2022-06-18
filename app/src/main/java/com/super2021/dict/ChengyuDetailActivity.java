package com.super2021.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.super2021.dict.bean.ChengyuBean;
//import com.hui.dict.db.DBManager;
import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.ZiBean;
import com.super2021.dict.db.DBManager;

/**
 * 成语详情的Activity组件.
 */
public class ChengyuDetailActivity extends AppCompatActivity {
    TextView ziTv1, ziTv2, ziTv3, ziTv4, pinyinTv, explanationTv, fromTv, exampleTv, yufaTv, suoxieTv;
    ImageView collectIv;
    private String chengyu;
    // 设置标志位，表示汉字是否被收藏
    boolean isCollect = false;
    // 判断这个成语是否已经存在于收藏表
    boolean isExistCollection = false;
    // 判断这个成语是否已经存在于历史表
    boolean isExistHistory = false;

    /**
     * 创建界面.
     * 
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengyu_info);
        initView();
        // 获取上一个页面传递的数据
        Intent intent = getIntent();
        chengyu = intent.getStringExtra("chengyu");
        ChengyuBean chengyuBean = StaticData.chengyuBeanMap.get(chengyu);
        assert chengyuBean != null;
        showDataToView(chengyuBean);
        isExistCollection = DBManager.isChengyuExistCollection(chengyu);
        isExistHistory = DBManager.isChengyuExistHistory(chengyu);
        if (isExistHistory) {
            DBManager.deleteChengyuHistory(chengyu);
        }
        DBManager.insertChengyuHistory(chengyu);
        isCollect = isExistCollection;
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
     * 将获取到的数据显示在View上.
     * 
     * @param cyBean 成语模块
     */
    private void showDataToView(ChengyuBean cyBean) {
        String chengyu = cyBean.getWord();
        ziTv1.setText(String.valueOf(chengyu.charAt(0)));
        ziTv2.setText(String.valueOf(chengyu.charAt(1)));
        ziTv3.setText(String.valueOf(chengyu.charAt(2)));
        ziTv4.setText(String.valueOf(chengyu.charAt(3)));
        pinyinTv.setText(String.format("拼音 : %s",
                chengyuBean.getPinyin()));
        suoxieTv.setText(String.format("缩写 : %s",
                chengyuBean.getAbbreviation()));
        explanationTv.setText(chengyuBean.getExplanation());
        fromTv.setText(chengyuBean.getDerivation());
        exampleTv.setText(chengyuBean.getExample());
        yufaTv.setText(chengyuBean.getPinyin());
    }

    /**
     * 查找控件的方法.
     */
    private void initView() {
        ziTv1 = findViewById(R.id.chengyu_tv_zi1);
        ziTv2 = findViewById(R.id.chengyu_tv_zi2);
        ziTv3 = findViewById(R.id.chengyu_tv_zi3);
        ziTv4 = findViewById(R.id.chengyu_tv_zi4);
        pinyinTv = findViewById(R.id.chengyu_tv_pinyin);
        explanationTv = findViewById(R.id.chengyu_tv_explanation);
        fromTv = findViewById(R.id.chengyu_tv_from);
        exampleTv = findViewById(R.id.chengyu_tv_example);
        yufaTv = findViewById(R.id.chengyu_tv_yufa);
        suoxieTv = findViewById(R.id.chengyu_tv_suoxie);
        collectIv = findViewById(R.id.chengyu_iv_collection);
    }

    /**
     * 点击事件.
     * 
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if (view.getId() == R.id.chengyu_iv_back) {
            finish();
        } else if (view.getId() == R.id.chengyu_iv_collection) {
            isCollect = !isCollect;
            setCollectIvStyle();
        } else if (view.getId() == R.id.chengyu_tv_zi1 ||
                view.getId() == R.id.chengyu_tv_zi2 ||
                view.getId() == R.id.chengyu_tv_zi3 ||
                view.getId() == R.id.chengyu_tv_zi4) {
            TextView tv = findViewById(view.getId());
            startPage(tv.getText().toString());
        }
    }

    /**
     * 当activity被销毁时回调的方法.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isExistCollection && !isCollect) {
            DBManager.deleteChengyuCollection(chengyu);
        }
        if (!isExistCollection && isCollect) {
            DBManager.insertChengyuCollection(chengyu);
        }
    }

    private void startPage(String text) {
        Intent intent = new Intent(this, ChengyuDetailActivity.class);
        intent.putExtra("zi", text);
        ZiBean ziBean = StaticData.ziBeanMap.get(text);
        if (ziBean != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "无此汉字！", Toast.LENGTH_LONG).show();
        }
    }
}
