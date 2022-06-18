package com.super2021.dict.bean;

import java.util.List;

/**
 * 成语模块.
 */
public class PinBuBean {

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    /**
     * 获取错误原因.
     * 
     * @return 错误原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置错误原因.
     * 
     * @param reason 错误原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取错误代码.
     * 
     * @return 错误代码
     */
    public int getError_code() {
        return error_code;
    }

    /**
     * 设置错误代码.
     * 
     * @param error_code 错误代码
     */
    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    /**
     * 获取详情数组.
     * 
     * @return 详情数组
     */
    public List<ResultBean> getResult() {
        return result;
    }

    /**
     * 设置详情数组.
     * 
     * @param result 详情数组
     */
    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    /**
     * 字的详细信息.
     */
    public static class ResultBean {

        private String id;
        private String pinyin_key;
        private String pinyin;
        private String bihua;
        private String bushou;

        /**
         * 获取id.
         * 
         * @return id
         */
        public String getId() {
            return id;
        }

        /**
         * 设置id.
         * 
         * @param id id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * 获取拼音索引.
         * 
         * @return 拼音索引
         */
        public String getPinyin_key() {
            return pinyin_key;
        }

        /**
         * 设置拼音索引.
         * 
         * @param pinyin_key 拼音索引
         */
        public void setPinyin_key(String pinyin_key) {
            this.pinyin_key = pinyin_key;
        }

        /**
         * 获取拼音.
         * 
         * @return 拼音
         */
        public String getPinyin() {
            return pinyin;
        }

        /**
         * 设置拼音.
         * 
         * @param pinyin 拼音
         */
        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        /**
         * 获取笔画.
         * 
         * @return 笔画
         */
        public String getBihua() {
            return bihua;
        }

        /**
         * 设置笔画.
         * 
         * @param bihua 笔画
         */
        public void setBihua(String bihua) {
            this.bihua = bihua;
        }

        /**
         * 获取部首.
         * 
         * @return 部首
         */
        public String getBushou() {
            return bushou;
        }

        /**
         * 设置部首.
         * 
         * @param bushou 部首
         */
        public void setBushou(String bushou) {
            this.bushou = bushou;
        }
    }
}
