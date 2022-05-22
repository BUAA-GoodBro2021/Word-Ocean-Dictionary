package com.hui.dict;
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
import com.hui.dict.adapter.SearchLeftAdapter;
import com.hui.dict.adapter.SearchRightAdapter;
import com.hui.dict.bean.PinBuBean;
import com.hui.dict.bean.StaticData;
import com.hui.dict.bean.ZiBean;
//import com.hui.dict.db.DBManager;
import com.hui.dict.utils.AssetsUtils;
import com.hui.dict.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class BaseSearchActivity extends AppCompatActivity {
    ExpandableListView exLv;
    PullToRefreshGridView pullGv;
    TextView titleTv;
    List<String> groupDatas;  //表示分组的列表   [A,B,C,D.....]
    List<List<PinBuBean.ResultBean>> childDatas;  //将魅族对应的子类i列表存放到这个集合
    SearchLeftAdapter adapter;
    int selGroupPos = 0;    //表示被点击的组的位置
    int selChildPos = 0;   //表示选中组中某一个位置
    // 右侧的GridView的数据源，先声明
    List<ZiBean> gridDatas;
    private SearchRightAdapter gridAdapter;

    int totalpage;   //总页数
    int page = 1;   //当前获取的页数
    int pagesize = 48;  //默认一页获取48条数据
    String word = "";   //点击了左侧的哪个拼音或者部首
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pinyin);
        exLv = findViewById(R.id.searchpy_elv);
        pullGv = findViewById(R.id.searchpy_gv);
        titleTv = findViewById(R.id.searchpy_tv);
        // 初始化GriView的数据源内容
        initGridDatas();
    }

    /**
     * 初始化GridView的数据源
     * */
    public void initGridDatas() {
        gridDatas = new ArrayList<>();
//        设置适配器
        gridAdapter = new SearchRightAdapter(this, gridDatas);
        pullGv.setAdapter(gridAdapter);
    }
//    设置GridView的监听器
    public void setGVListener(final int type) {
//        上拉加载的监听器
        pullGv.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        pullGv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
//                判断当前加载的页数，是否小于总页数
                if(page<totalpage){
                    page++;
                    List<ZiBean> list = new ArrayList<>();
                    if (type == CommonUtils.TYPE_PINYIN) {
                        list = StaticData.ziBeanPinYinMap.get(word).get(page - 1).getList();
                    }else if (type == CommonUtils.TYPE_BUSHOU) {
                        list = StaticData.ziBeanBuShouMap.get(word).get(page - 1).getList();
                    }
                    refreshDataByGV(list);
                }else{
                    pullGv.onRefreshComplete();  //不用加载数据  可以弹出Toast提示信息
                }
            }
        });
//        点击每一项，能够跳转到详情页面的监听
        pullGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                跳转到文字查询详情页面
                ZiBean bean = gridDatas.get(position);
                String zi = bean.getZi();
                Intent intent = new Intent(getBaseContext(), WordInfoActivity.class);
                intent.putExtra("zi",zi);
                startActivity(intent);
            }
        });

    }

//    /**
//     * @des 将网络数据保存到数据库当中，为了避免ANR，就使用子线程，完成操作
//     * */
//    public void writeDBByThread(final List<ZiBean> list) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                DBManager.insertListToPywordtb(list);
//            }
//        }).start();
//    }
    /**
     * 更新GridView当中的数据，提示适配器重新加载
     * */
    public void refreshDataByGV(List<ZiBean> list) {
        if (page == 1) {   //加载了新的拼音或者部首对应的集合
            gridDatas.clear();
            gridDatas.addAll(list);
            gridAdapter.notifyDataSetChanged();
        }else{   //进行上拉加载新的一页，获取到的数据，保留原来的数据
            gridDatas.addAll(list);
            gridAdapter.notifyDataSetChanged();
            //停止显示加载条
            pullGv.onRefreshComplete();
        }
    }

    /**
     *  设置ExpandListView的监听方法
     * */
    public void setExLvListener(final int type) {
        exLv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                adapter.setSelectGroupPos(groupPosition);
//                获取被点击位置的内容
                selGroupPos = groupPosition;
                int groupSize = childDatas.get(selGroupPos).size();
                if (groupSize <= selChildPos){
                    selChildPos = groupSize - 1;
                    adapter.setSelectChildPos(selChildPos);
                }
                adapter.notifyDataSetInvalidated();   //数据没有改变，只是布局背景改变了
                //获取数据信息
                getDataAlterWord(type);
                return false;
            }
        });
        exLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                adapter.setSelectGroupPos(groupPosition);
                adapter.setSelectChildPos(childPosition);
                adapter.notifyDataSetInvalidated();
                selGroupPos = groupPosition;
                selChildPos = childPosition;
                //网络加载右面GridView显示内容
                getDataAlterWord(type);
                return false;
            }
        });
    }
    /**
     * 改变了左边的选中，从网上获取对应选中的结果，显示在右边
     * */
    private void getDataAlterWord(int type) {
        List<PinBuBean.ResultBean> groupList = childDatas.get(selGroupPos);  //获取选中组
            page = 1;
            PinBuBean.ResultBean bean = groupList.get(selChildPos);
            List<ZiBean> list = new ArrayList<>();
            if (type == CommonUtils.TYPE_PINYIN) {
                list = StaticData.ziBeanPinYinMap.get(word).get(page - 1).getList();
            }else if (type == CommonUtils.TYPE_BUSHOU) {
                list = StaticData.ziBeanBuShouMap.get(word).get(page - 1).getList();
            }
            refreshDataByGV(list);
    }

    public void initData(String assetsName,int type) {
        /**
         * 读取Assets文件夹昂中的数据，使用Gson解析，将数据分组存储到二维列表当中
         * @param assetsName 文件名称
         * @param type 文件类型   pinyin--0    bushou--1
         * */
        groupDatas = new ArrayList<>();
        childDatas = new ArrayList<>();
        String json = AssetsUtils.getAssetsContent(this, assetsName);
        if (!TextUtils.isEmpty(json)) {
            PinBuBean pinBuBean = new Gson().fromJson(json, PinBuBean.class);
            List<PinBuBean.ResultBean> list = pinBuBean.getResult();
//            整理数据
            List<PinBuBean.ResultBean>grouplist = new ArrayList<>(); //声明每组包含的元素集合
            for (int i = 0; i < list.size(); i++) {
                PinBuBean.ResultBean bean = list.get(i);   // id,pinyin_key,pinyi
                if (type == CommonUtils.TYPE_PINYIN) {
                    String pinyin_key = bean.getPinyin_key();  //获取大写字母
                    if (!groupDatas.contains(pinyin_key)) {   //判断是否存在于分组的列表当中
                        groupDatas.add(pinyin_key);
//                        说明上一个拼音的已经全部录入到grouplist当中了，可以将上一个拼音的集合添加到childDatas
                        if (grouplist.size()>0) {
                            childDatas.add(grouplist);
                        }
//                        既然是新的一组，就要创建一个对应的新的子列表
                        grouplist = new ArrayList<>();
                        grouplist.add(bean);
                    }else{
                        grouplist.add(bean);  //大写字母存在，说明还在当前这组当中，可以直接添加
                    }
                }else if (type==CommonUtils.TYPE_BUSHOU) {
                    String bihua = bean.getBihua();
                    if (!groupDatas.contains(bihua)) {
                        groupDatas.add(bihua);
//                        新的一组，把上一组进行添加
                        if (grouplist.size()>0) {
                            childDatas.add(grouplist);
                        }
//                        新的一组，新创建子列表
                        grouplist = new ArrayList<>();
                        grouplist.add(bean);
                    }else{
                        grouplist.add(bean);//当前笔画存在，就不用向组当中添加了
                    }
                }
            }
//            循环结束之后，最后一组并没有添加进去，所以需要手动添加
            childDatas.add(grouplist);

            adapter = new SearchLeftAdapter(this, groupDatas, childDatas, type);
            exLv.setAdapter(adapter);

        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.searchpy_iv_back:
                finish();
                break;
        }
    }
}
