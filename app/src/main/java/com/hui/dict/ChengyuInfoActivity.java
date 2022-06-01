package com.hui.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.dict.bean.ChengyuBean;
//import com.hui.dict.db.DBManager;
import com.hui.dict.bean.StaticData;
import com.hui.dict.db.DBManager;

public class ChengyuInfoActivity extends AppCompatActivity {
    TextView ziTv1, ziTv2, ziTv3, ziTv4, pyTv, jsTv, fromTv, exampleTv, yufaTv, suoxieTv;
    ImageView collectIv;
    private String chengyu;
    // 设置标志位，表示汉字是否被收藏
    boolean isCollect = false;
    boolean isExistCollection = false; // 判断这个成语是否已经存在于收藏表
    boolean isExistHistory = false; // 判断这个成语是否已经存在于历史表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chengyu_info);
        initView();
        // 获取上一个页面传递的数据
        Intent intent = getIntent();
        chengyu = intent.getStringExtra("chengyu");
        ChengyuBean chengyuBean = StaticData.chengyuBeanMap.get(chengyu);
        showDataToView(chengyuBean);
        isExistCollection = DBManager.isChengyuExistCollection(chengyu);
        isExistHistory = DBManager.isChengyuExistHistory(chengyu);
        if(isExistHistory){
            DBManager.deleteChengyuHistory(chengyu);
        }
        DBManager.insertChengyuHistory(chengyu);
        isCollect = isExistCollection;
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
     * 将获取到的数据显示在View上
     */
    private void showDataToView(ChengyuBean cyBean) {
        String chengyu = cyBean.getWord();
        ziTv1.setText(String.valueOf(chengyu.charAt(0)));
        ziTv2.setText(String.valueOf(chengyu.charAt(1)));
        ziTv3.setText(String.valueOf(chengyu.charAt(2)));
        ziTv4.setText(String.valueOf(chengyu.charAt(3)));
        pyTv.setText("拼音 : " + cyBean.getPinyin());
        suoxieTv.setText("缩写 : " + cyBean.getAbbreviation());
        jsTv.setText(cyBean.getExplanation());
        fromTv.setText(cyBean.getDerivation());
        exampleTv.setText(cyBean.getExample());
        yufaTv.setText(cyBean.getPinyin());
    }

    /**
     * 查找控件的方法
     */
    private void initView() {
        ziTv1 = findViewById(R.id.cyinfo_tv_zi1);
        ziTv2 = findViewById(R.id.cyinfo_tv_zi2);
        ziTv3 = findViewById(R.id.cyinfo_tv_zi3);
        ziTv4 = findViewById(R.id.cyinfo_tv_zi4);
        pyTv = findViewById(R.id.cyinfo_tv_py);
        jsTv = findViewById(R.id.cyinfo_tv_js);
        fromTv = findViewById(R.id.cyinfo_tv_from);
        exampleTv = findViewById(R.id.cyinfo_tv_example);
        yufaTv = findViewById(R.id.cyinfo_tv_yufa);
        suoxieTv = findViewById(R.id.cyinfo_tv_sx);
        collectIv = findViewById(R.id.cyinfo_iv_collection);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cyinfo_iv_back:
                finish();
                break;
            case R.id.cyinfo_iv_collection:
                isCollect = !isCollect;
                setCollectIvStyle();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isExistCollection && !isCollect) {
            DBManager.deleteChengyuCollection(chengyu);
        }
        if (!isExistCollection && isCollect) {
            DBManager.deleteChengyuCollection(chengyu);
        }
    }
}
