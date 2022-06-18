package com.super2021.dict.bean;

import androidx.annotation.NonNull;

/**
 * 成语模块.
 */
public class ChengyuBean {
    private int id;
    private String word;
    private String pinyin;
    private String abbreviation;
    private String explanation;
    private String example;
    private String derivation;

    /**
     * 获取String[]型数据.
     * @return 组合后的字符串
     */
    @NonNull
    @Override
    public String toString() {
        return "ListBean{" +
                "word='" + word + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

    /**
     * 获取id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置id.
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取汉字.
     * @return 字
     */
    public String getWord() {
        return word;
    }

    /**
     * 设置汉字.
     * @param word 字
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * 获取拼音.
     * @return 拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置拼音.
     * @param pinyin 拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * 获取缩写.
     * @return 缩写
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    /**
     * 设置缩写.
     * @param abbreviation 缩写
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    /**
     * 获取释义.
     * @return 释义
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * 设置释义.
     * @param explanation 释义
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * 获取例子.
     * @return 例子
     */
    public String getExample() {
        return example;
    }

    /**
     * 设置例子.
     * @param example  例子
     */
    public void setExample(String example) {
        this.example = example;
    }

    /**
     * 获取出处.
     * @return 出处
     */
    public String getDerivation() {
        return derivation;
    }

    /**
     * 设置出处.
     * @param derivation  出处
     */
    public void setDerivation(String derivation) {
        this.derivation = derivation;
    }
}
