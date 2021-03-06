package com.super2021.dict;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.super2021.dict.fragment.CollectFragmentAdapter;
import com.super2021.dict.fragment.ZiFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏的Activity组件.
 */
public class CollectionActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager collectVp;
    String[] titles = { "汉字", "成语" };
    List<Fragment> mDatas;

    /**
     * 创建界面.
     * 
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        tabLayout = findViewById(R.id.collect_tabs);
        collectVp = findViewById(R.id.collect_vp);
        initPager();
    }

    /**
     * 初始化ViewPager页面的操作.
     */
    private void initPager() {
        mDatas = new ArrayList<>();
        for (String title : titles) {
            Fragment frag = new ZiFragment();
            Bundle bundle = new Bundle();
            bundle.putString("type", title);
            frag.setArguments(bundle);
            mDatas.add(frag);
        }
        CollectFragmentAdapter adapter = new CollectFragmentAdapter(getSupportFragmentManager(), mDatas, titles);
        collectVp.setAdapter(adapter);
        // 将上下绑定
        tabLayout.setupWithViewPager(collectVp);
    }

    /**
     * 点击事件.
     * 
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if (view.getId() == R.id.collect_iv_back) {
            finish();
        }
    }
}
