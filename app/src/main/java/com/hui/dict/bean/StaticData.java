package com.hui.dict.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 静态数据.
 */
public class StaticData {
    public static PinBuBean pinYinBean;
    public static PinBuBean buShouBean;
    public static Map<String, List<ListBean>> ziBeanPinYinMap = new HashMap<>();
    public static Map<String, List<ListBean>> ziBeanBuShouMap = new HashMap<>();
    public static Map<String, ZiBean> ziBeanMap = new HashMap<>();
    public static Map<String, ChengyuBean> chengyuBeanMap = new HashMap<>();

    /**
     * 获取分页信息.
     */
    public static class ListBean {
        private int page;
        private int pageSize;
        private int totalPage;
        private int totalCount;
        private List<ZiBean> list = new ArrayList<>();

        /**
         * 获取页.
         * @return 页
         */
        public int getPage() {
            return page;
        }

        /**
         * 设置页.
         * @param page 页
         */
        public void setPage(int page) {
            this.page = page;
        }

        /**
         * 获取页大小.
         * @return 页大小
         */
        public int getPageSize() {
            return pageSize;
        }

        /**
         * 设置页大小.
         * @param pageSize 页大小
         */
        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        /**
         * 获取页的个数.
         * @return 页的个数
         */
        public int getTotalPage() {
            return totalPage;
        }

        /**
         * 设置页的个数.
         * @param totalPage 页的个数
         */
        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        /**
         * 获取字的个数.
         * @return 字的个数
         */
        public int getTotalCount() {
            return totalCount;
        }

        /**
         * 设置字的个数.
         * @param totalCount 字的个数
         */
        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        /**
         * 获取字的列表.
         * @return 字的列表
         */
        public List<ZiBean> getList() {
            return list;
        }

        /**
         * 设置字的列表.
         * @param list 字的列表
         */
        public void setList(List<ZiBean> list) {
            this.list = list;
        }
    }
}