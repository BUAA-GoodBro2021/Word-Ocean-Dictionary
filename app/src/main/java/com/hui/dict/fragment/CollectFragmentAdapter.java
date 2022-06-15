package com.hui.dict.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 收藏Fragment适配器.
 */
public class CollectFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> list;
    String[] titles;

    /**
     * 更改数据.
     * @param fm fragment manager
     * @param list fragment列表
     * @param titles 标题
     */
    public CollectFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }

    /**
     * 获取项.
     * @param position 指定位置
     * @return 选择的fragment
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    /**
     * 获取数量.
     * @return list里fragment的数量
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * 获取页标题.
     * @param position 指定位置
     * @return 选择的fragment的标题
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
