package com.super2021.dict.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.super2021.dict.R;
import com.super2021.dict.bean.ZiBean;

import java.util.List;

/**
 *  拼音查询，部首查询页面右侧PullToRrefreshGriView的适配器
 * */
public class SearchRightAdapter extends BaseAdapter {
    Context context;
    List<ZiBean> mDatas;
    LayoutInflater inflater;
    public SearchRightAdapter(Context context, List<ZiBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_pgv,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ZiBean bean = mDatas.get(position);
        String zi = bean.getZi();
        holder.tv.setText(zi);
        return convertView;
    }

    static class ViewHolder{
        TextView tv;
        public ViewHolder(View view){
            tv = view.findViewById(R.id.item_sgv_tv);
        }
    }
}
