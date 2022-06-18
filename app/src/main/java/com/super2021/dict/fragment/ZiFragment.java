package com.super2021.dict.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.super2021.dict.ChengyuDetailActivity;
import com.super2021.dict.R;
import com.super2021.dict.ZiDetailActivity;
import com.super2021.dict.db.DBManager;
//import com.hui.dict.db.DBManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 字的Fragment适配器
 */
public class ZiFragment extends Fragment {
    private String type;
    GridView gv;
    List<String> mDatas;
    private ArrayAdapter<String> adapter;

    /**
     * 设置视图.
     * @param inflater 设置LayoutInflater
     * @param container 设置容器
     * @param savedInstanceState 保存实例状态
     * @return 视图
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zi, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        type = bundle.getString("type");  //获取当前Fragment显示的数据类型
        gv = view.findViewById(R.id.zifrag_gv);
        mDatas = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(), R.layout.item_search_pgv, R.id.item_sgv_tv, mDatas);
        gv.setAdapter(adapter);
        // 设置GridView的点击事件
        setGVListener();
        return view;
    }

    /**
     * 刷新.
     */
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * 加载数据.
     */
    private void loadData() {
        List<String> list;
        mDatas.clear();
        if (type.equals("汉字")) {
            list = DBManager.queryZiCollection();
        } else {
            list = DBManager.queryChengyuCollection();
        }
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    /**
     * 设置GridView的监听.
     */
    private void setGVListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals("汉字")) {
                    String zi = mDatas.get(position);
                    Intent intent = new Intent(getActivity(), ZiDetailActivity.class);
                    intent.putExtra("zi", zi);
                    startActivity(intent);
                } else {
                    String chengyu = mDatas.get(position);
                    Intent intent = new Intent(getActivity(), ChengyuDetailActivity.class);
                    intent.putExtra("chengyu", chengyu);
                    startActivity(intent);
                }
            }
        });
    }
}
