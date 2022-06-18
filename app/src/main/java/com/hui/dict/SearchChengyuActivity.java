package com.hui.dict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
//import com.hui.dict.db.DBManager;
import com.hui.dict.bean.ChengyuBean;
import com.hui.dict.bean.StaticData;
import com.hui.dict.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 成语查找的Activity组件.
 */
public class SearchChengyuActivity extends AppCompatActivity {
    EditText cyEt;
    GridView cyGv;
    List<String> mDatas;
    private ArrayAdapter<String> adapter;

    /**
     * 创建界面.
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chengyu);
        cyEt = findViewById(R.id.searchcy_et);
        cyGv = findViewById(R.id.searchcy_gv);
        mDatas = new ArrayList<>();
        // 创建适配器对象
        adapter = new ArrayAdapter<>(this, R.layout.item_searchcy_gv, R.id.item_searchcy_tv, mDatas);
        cyGv.setAdapter(adapter);
        // 设置GridView的点击1事件
        setGVListener();
    }

    /**
     * GridView每一个Item点击事件的方法.
     */
    private void setGVListener() {
        cyGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = mDatas.get(position);
                startPage(msg);
            }
        });
    }

    /**
     * 恢复.
     */
    @Override
    protected void onResume() {
        super.onResume();
        cyEt.setText("");
        initDatas();
    }

    /**
     * 初始化GridView显示的历史记录数据.
     */
    private void initDatas() {
        mDatas.clear();
        List<String> list = DBManager.queryChengyuHistory();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 点击事件.
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if(view.getId() == R.id.searchcy_iv_back){
            finish();
        }
        else if(view.getId() == R.id.searchcy_iv_search){
            String text = cyEt.getText().toString();
            if (TextUtils.isEmpty(text)) {
                return;
            }
            //跳转到成语详情页面，将输入内容传递过去
            startPage(text);
        }
    }

    /**
     * 携带成语跳转到下一个页面.
     * @param text 要查找的成语
     */
    private void startPage(String text) {
        Intent intent = new Intent(this, ChengyuDetailActivity.class);
        intent.putExtra("chengyu", text);
        ChengyuBean chengyuBean = StaticData.chengyuBeanMap.get(text);
        if (chengyuBean != null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "无此成语！", Toast.LENGTH_LONG).show();
        }
    }
}
