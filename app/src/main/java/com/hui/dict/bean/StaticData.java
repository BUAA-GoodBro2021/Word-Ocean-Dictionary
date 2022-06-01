package com.hui.dict.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaticData {
    public static List<ZiBean> ziBeans = new ArrayList<>();
    public static List<ChengyuBean> chengyuBeans = new ArrayList<>();
    public static PinBuBean pinYinBean;
    public static PinBuBean buShouBean;
    public static Map<String, List<ListBean>> ziBeanPinYinMap = new HashMap<>();
    public static Map<String, List<ListBean>> ziBeanBuShouMap = new HashMap<>();
    public static Map<String, ZiBean> ziBeanMap = new HashMap<>();
    public static Map<String, ChengyuBean> chengyuBeanMap = new HashMap<>();

    public static class ListBean {
        private int page;
        private int pageSize;
        private int totalPage;
        private int totalCount;
        private List<ZiBean> list = new ArrayList<>();

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public List<ZiBean> getList() {
            return list;
        }

        public void setList(List<ZiBean> list) {
            this.list = list;
        }
    }
}
