package com.hui.dict.bean;

import java.util.List;

/**
 * 图文模块.
 */
public class TuWenBean {

    private long log_id;
    private int words_result_num;
    private int direction;
    private List<WordsResultBean> words_result;

    /**
     * 获取id.
     * @return id
     */
    public long getLog_id() {
        return log_id;
    }

    /**
     * 设置id.
     * @param log_id id
     */
    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    /**
     * 获取字的个数.
     * @return 字的个数
     */
    public int getWords_result_num() {
        return words_result_num;
    }

    /**
     * 设置字的个数.
     * @param words_result_num 字的个数
     */
    public void setWords_result_num(int words_result_num) {
        this.words_result_num = words_result_num;
    }

    /**
     * 获取方向.
     * @return 方向
     */
    public int getDirection() {
        return direction;
    }

    /**
     * 设置方向.
     * @param direction 方向
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * 获取结果模块.
     * @return 结果模块
     */
    public List<WordsResultBean> getWords_result() {
        return words_result;
    }

    /**
     * 获取结果模块.
     * @param words_result 结果模块
     */
    public void setWords_result(List<WordsResultBean> words_result) {
        this.words_result = words_result;
    }

    /**
     * 结果模块.
     */
    public static class WordsResultBean {
        private String words;

        /**
         * 获取结果.
         * @return 结果
         */
        public String getWords() {
            return words;
        }

        /**
         * 获取结果.
         * @param words 结果
         */
        public void setWords(String words) {
            this.words = words;
        }
    }
}
