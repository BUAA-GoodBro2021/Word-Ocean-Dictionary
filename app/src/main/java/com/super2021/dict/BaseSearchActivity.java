package com.super2021.dict;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.super2021.dict.adapter.SearchLeftAdapter;
import com.super2021.dict.adapter.SearchRightAdapter;
import com.super2021.dict.bean.BaseBean;
import com.super2021.dict.bean.StaticData;
import com.super2021.dict.bean.ZiBean;
import com.super2021.dict.utils.AssetsUtils;
import com.super2021.dict.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 查找的Activity组件.
 */
public class BaseSearchActivity extends AppCompatActivity {
    ExpandableListView exLv;
    PullToRefreshGridView pullGv;
    TextView titleTv;
    List<String> groupDatas; // 表示分组的列表 [A,B,C,D.....]
    List<List<BaseBean.ResultBean>> childDatas; // 将魅族对应的子类i列表存放到这个集合
    SearchLeftAdapter adapter;
    int selGroupPos = 0; // 表示被点击的组的位置
    int selChildPos = 0; // 表示选中组中某一个位置
    // 右侧的GridView的数据源，先声明
    List<ZiBean> gridDatas;
    private SearchRightAdapter gridAdapter;

    int totalPage; // 总页数
    int page = 1; // 当前获取的页数
    String word = ""; // 点击了左侧的哪个拼音或者部首

    /**
     * 创建界面.
     * 
     * @param savedInstanceState 保存实例状态
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pinyin);
        exLv = findViewById(R.id.search_zi_elv);
        pullGv = findViewById(R.id.search_zi_gv);
        titleTv = findViewById(R.id.search_zi_tv);
        // 初始化GriView的数据源内容
        initGridDatas();
    }

    /**
     * 初始化GridView的数据源.
     */
    public void initGridDatas() {
        gridDatas = new ArrayList<>();
        // 设置适配器
        gridAdapter = new SearchRightAdapter(this, gridDatas);
        pullGv.setAdapter(gridAdapter);
    }

    /**
     * 设置GridView的监听器.
     * 
     * @param type 类型
     */
    public void setGVListener(final int type) {
        // 上拉加载的监听器
        pullGv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
                // 判断当前加载的页数，是否小于总页数
                if (page < totalPage) {
                    page++;
                    List<ZiBean> list;
                    StaticData.ListBean listBean = new StaticData.ListBean();
                    if (type == CommonUtils.TYPE_PINYIN) {
                        listBean = Objects.requireNonNull(StaticData.ziBeanPinYinMap.get(word)).get(page - 1);

                    } else if (type == CommonUtils.TYPE_BUSHOU) {
                        listBean = Objects.requireNonNull(StaticData.ziBeanBuShouMap.get(word)).get(page - 1);
                    }
                    list = listBean.getList();
                    totalPage = listBean.getTotalPage();
                    refreshDataByGV(list);
                } else {
                    pullGv.onRefreshComplete(); // 不用加载数据 可以弹出Toast提示信息
                }
            }
        });
        // 点击每一项，能够跳转到详情页面的监听
        pullGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 跳转到文字查询详情页面
                ZiBean bean = gridDatas.get(position);
                String zi = bean.getZi();
                Intent intent = new Intent(getBaseContext(), ZiDetailActivity.class);
                intent.putExtra("zi", zi);
                startActivity(intent);
            }
        });

    }

    /**
     * 更新GridView当中的数据，提示适配器重新加载.
     * 
     * @param list 传入的字的数据
     */
    public void refreshDataByGV(List<ZiBean> list) {
        if (page == 1) { // 加载了新的拼音或者部首对应的集合
            gridDatas.clear();
            gridDatas.addAll(list);
            gridAdapter.notifyDataSetChanged();
        } else { // 进行上拉加载新的一页，获取到的数据，保留原来的数据
            gridDatas.addAll(list);
            gridAdapter.notifyDataSetChanged();
            // 停止显示加载条
            pullGv.onRefreshComplete();
        }
    }

    /**
     * 设置ExpandListView的监听方法.
     * 
     * @param type 传入的字的数据
     */
    public void setExLvListener(final int type) {
        exLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectGroupPos(groupPosition);
                // 获取被点击位置的内容
                selGroupPos = groupPosition;
                int groupSize = childDatas.get(selGroupPos).size();
                if (groupSize <= selChildPos) {
                    selChildPos = groupSize - 1;
                    adapter.setSelectChildPos(selChildPos);
                }
                adapter.notifyDataSetInvalidated(); // 数据没有改变，只是布局背景改变了
                getDataAlterWord(type);
                return false;
            }
        });
        exLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
                    long id) {
                adapter.setSelectGroupPos(groupPosition);
                adapter.setSelectChildPos(childPosition);
                adapter.notifyDataSetInvalidated();
                selGroupPos = groupPosition;
                selChildPos = childPosition;
                getDataAlterWord(type);
                return false;
            }
        });
    }

    /**
     * 改变了左边的选中，从网上获取对应选中的结果，显示在右边.
     * 
     * @param type 类型
     */
    private void getDataAlterWord(int type) {
        List<BaseBean.ResultBean> groupList = childDatas.get(selGroupPos); // 获取选中组
        page = 1;
        BaseBean.ResultBean bean = groupList.get(selChildPos);
        List<ZiBean> list;
        StaticData.ListBean listBean = new StaticData.ListBean();
        if (type == CommonUtils.TYPE_PINYIN) {
            word = bean.getPinyin();
            listBean = Objects.requireNonNull(StaticData.ziBeanPinYinMap.get(word)).get(page - 1);
        } else if (type == CommonUtils.TYPE_BUSHOU) {
            word = bean.getBushou();
            listBean = Objects.requireNonNull(StaticData.ziBeanBuShouMap.get(word)).get(page - 1);
        }
        list = listBean.getList();
        totalPage = listBean.getTotalPage();
        refreshDataByGV(list);
    }

    /**
     * 初始化数据.
     * 
     * @param assetsName assests文件夹的名字
     * @param type       类型
     */
    public void initData(String assetsName, int type) {
        groupDatas = new ArrayList<>();
        childDatas = new ArrayList<>();
        String json = AssetsUtils.getAssetsContent(this, assetsName);
        if (!TextUtils.isEmpty(json)) {
            BaseBean baseBean = new Gson().fromJson(json, BaseBean.class);
            List<BaseBean.ResultBean> list = baseBean.getResult();
            // 整理数据
            List<BaseBean.ResultBean> grouplist = new ArrayList<>(); // 声明每组包含的元素集合
            for (int i = 0; i < list.size(); i++) {
                BaseBean.ResultBean bean = list.get(i); // id,pinyin_key,pinyi
                if (type == CommonUtils.TYPE_PINYIN) {
                    String pinyin_key = bean.getPinyin_key(); // 获取大写字母
                    if (!groupDatas.contains(pinyin_key)) { // 判断是否存在于分组的列表当中
                        groupDatas.add(pinyin_key);
                        // 说明上一个拼音的已经全部录入到grouplist当中了，可以将上一个拼音的集合添加到childDatas
                        if (grouplist.size() > 0) {
                            childDatas.add(grouplist);
                        }
                        // 既然是新的一组，就要创建一个对应的新的子列表
                        grouplist = new ArrayList<>();
                        grouplist.add(bean);
                    } else {
                        // c大写字母存在，说明还在当前这组当中，可以直接添加
                        grouplist.add(bean);
                    }
                } else if (type == CommonUtils.TYPE_BUSHOU) {
                    String bihua = bean.getBihua();
                    if (!groupDatas.contains(bihua)) {
                        groupDatas.add(bihua);
                        // 新的一组，把上一组进行添加
                        if (grouplist.size() > 0) {
                            childDatas.add(grouplist);
                        }
                        // 新的一组，新创建子列表
                        grouplist = new ArrayList<>();
                        grouplist.add(bean);
                    } else {
                        // c当前笔画存在，就不用向组当中添加了
                        grouplist.add(bean);
                    }
                }
            }
            // 循环结束之后，最后一组并没有添加进去，所以需要手动添加
            childDatas.add(grouplist);
            adapter = new SearchLeftAdapter(this, groupDatas, childDatas, type);
            exLv.setAdapter(adapter);

        }
    }

    /**
     * 点击事件.
     * 
     * @param view 点击的视图
     */
    public void onClick(View view) {
        if (view.getId() == R.id.search_zi_iv_back) {
            finish();
        }
    }
}
