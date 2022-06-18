package com.super2021.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.ZiBean;

import java.util.ArrayList;

/**
 * 识别图片的Activity组件.
 */
public class IdentifyImgActivity extends AppCompatActivity {
    GridView gv;
    ArrayList<String> mDatas;
    private ArrayAdapter<String> adapter;

    /**
     * 创建界面.
     * 
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_img);
        gv = findViewById(R.id.iden_gv);
        // 获取上一个界面传递的数据
        Bundle bundle = getIntent().getExtras();
        mDatas = bundle.getStringArrayList("wordlist");
        adapter = new ArrayAdapter<>(this, R.layout.item_search_pgv, R.id.item_sgv_tv, mDatas);
        gv.setAdapter(adapter);
        setGVListener();
    }

    /**
     * 设置GridView的监听器.
     */
    private void setGVListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = mDatas.get(position);
                Intent intent = new Intent(IdentifyImgActivity.this, ZiDetailActivity.class);
                intent.putExtra("zi", word);
                ZiBean ziBean = StaticData.ziBeanMap.get(word);
                if (ziBean != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "无此汉字！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * 点击事件.
     * 
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if (view.getId() == R.id.iden_iv_back) {
            finish();
        }
    }
}
