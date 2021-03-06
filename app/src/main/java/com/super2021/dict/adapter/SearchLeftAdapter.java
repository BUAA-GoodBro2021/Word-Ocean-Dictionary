package com.super2021.dict.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.super2021.dict.R;
import com.super2021.dict.bean.BaseBean;
import com.super2021.dict.utils.CommonUtils;

import java.util.List;

/**
 * 拼音查找和部首查找分类适配器.
 */
public class SearchLeftAdapter extends BaseExpandableListAdapter {
    Context context;
    // 表示分组的列表
    List<String> groupDatas;
    // 将每组对应的子类列表存放到这个集合
    List<List<BaseBean.ResultBean>> childDatas;
    LayoutInflater inflater;
    // 因为拼音和部首都用此适配器，所以通过这个属性，进行类型区分
    int type;
    // 表示被选中的组的位置，和被选中的组里面的item的位置
    int selectGroupPos = 0, selectChildPos = 0;

    /**
     * 设置对象位置.
     * 
     * @param selectChildPos 对象位置
     */
    public void setSelectChildPos(int selectChildPos) {
        this.selectChildPos = selectChildPos;
    }

    /**
     * 设置组的位置.
     * 
     * @param selectGroupPos 组的位置
     */
    public void setSelectGroupPos(int selectGroupPos) {
        this.selectGroupPos = selectGroupPos;
    }

    /**
     * 初始化布局加载器.
     * 
     * @param context    上下文环境
     * @param groupDatas 组的数据
     * @param childDatas 所有子类的数据
     * @param type       类型
     */
    public SearchLeftAdapter(Context context, List<String> groupDatas, List<List<BaseBean.ResultBean>> childDatas,
            int type) {
        this.context = context;
        this.groupDatas = groupDatas;
        this.childDatas = childDatas;
        this.type = type;
        inflater = LayoutInflater.from(context); // 初始化布局加载器
    }

    /**
     * 获取分组数量.
     * 
     * @return 分组数量
     */
    @Override
    public int getGroupCount() {
        return groupDatas.size();
    }

    /**
     * 获取指定分组当中有几个item.
     * 
     * @return 指定分组中item数量
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return childDatas.get(groupPosition).size();
    }

    /**
     * 获取分组指定位置的数据.
     * 
     * @return 分组指定位置的数据
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupDatas.get(groupPosition);
    }

    /**
     * 给出第几组，第几个，求出指定位置的对象.
     * 
     * @return 指定位置的对象
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childDatas.get(groupPosition).get(childPosition);
    }

    /**
     * 获取指定分组id.
     * 
     * @return 指定分组的id
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取指定分组id.
     * 
     * @return 指定分组的is
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 获取稳定的id.
     * 
     * @return false
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取指定分组的当前状态.
     * 
     * @param groupPosition 指定分组
     * @param isExpanded    目前是否处于展开状态
     * @param convertView   选中位置
     * @param parent        属于的分组
     * @return 指定位置的分组
     */
    @SuppressLint("InflateParams")
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exlv_group, null);
            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        // 获取指定位置的数据
        String word = groupDatas.get(groupPosition);
        if (type == CommonUtils.TYPE_PINYIN) {
            holder.groupTv.setText(word);
        } else {
            holder.groupTv.setText(String.format("%s画", word));
        }

        // 因为选中位置会改变颜色，和其他布局颜色不同，所以判断一下，是否是选中位置
        if (selectGroupPos == groupPosition) {
            convertView.setBackgroundColor(Color.rgb(255, 250, 250));
            holder.groupTv.setTextColor(Color.rgb(230, 82, 82));
        } else {
            convertView.setBackgroundResource(R.color.white);
            holder.groupTv.setTextColor(Color.BLACK);
        }
        holder.groupTv.setTextSize(23);
        holder.groupTv.setTypeface(Typeface.DEFAULT);
        holder.groupTv.setGravity(Gravity.CENTER);
        return convertView;
    }

    /**
     * 获取指定对象的当前状态.
     * 
     * @param groupPosition 指定分组
     * @param childPosition 指定对象
     * @param isLastChild   是否是组内最后一个对象
     * @param convertView   选中位置
     * @param parent        属于的分组
     * @return 指定位置的对象
     */
    @SuppressLint("InflateParams")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
            ViewGroup parent) {
        ChildViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_exlv_child, null);
            holder = new ChildViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        BaseBean.ResultBean bean = childDatas.get(groupPosition).get(childPosition);
        if (type == CommonUtils.TYPE_PINYIN) {
            holder.childTv.setText(bean.getPinyin());
        } else {
            holder.childTv.setText(bean.getBushou());
        }
        // 设置改变选中项目的颜色
        if (selectGroupPos == groupPosition && selectChildPos == childPosition) {
            convertView.setBackgroundColor(Color.rgb(255, 250, 250));
            holder.childTv.setTextColor(Color.rgb(230, 101, 101));
        } else {
            convertView.setBackgroundResource(android.R.color.system_accent1_10);
            holder.childTv.setTextColor(Color.BLACK);
        }
        holder.childTv.setTextSize(18);
        holder.childTv.setTypeface(Typeface.DEFAULT);
        holder.childTv.setGravity(Gravity.CENTER);
        return convertView;
    }

    /**
     * 判断是否是选择对象.
     * 
     * @param groupPosition 指定分组
     * @param childPosition 指定对象
     * @return true
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 分组外观.
     */
    static class GroupViewHolder {
        TextView groupTv;

        public GroupViewHolder(View view) {
            groupTv = view.findViewById(R.id.item_group_tv);
        }
    }

    /**
     * 对象外观.
     */
    static class ChildViewHolder {
        TextView childTv;

        public ChildViewHolder(View view) {
            childTv = view.findViewById(R.id.item_child_tv);
        }
    }

}
