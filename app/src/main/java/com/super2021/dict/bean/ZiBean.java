package com.super2021.dict.bean;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * 字模块.
 */
public class ZiBean {
    private int id;
    private String zi;
    private int zi_bihua;
    private String explanation;
    private String more;
    private String bushou;
    private List<String> pinyin;
    private List<String> pinyin_origin;

    /**
     * 获取String[]型数据.
     * @return 组合后的字符串
     */
    @NonNull
    @Override
    public String toString() {
        return "ListBean{" +
                "zi='" + zi + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", bushou='" + bushou + '\'' +
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
     * 获取字.
     * @return 字
     */
    public String getZi() {
        return zi;
    }

    /**
     * 设置字.
     * @param zi 字
     */
    public void setZi(String zi) {
        this.zi = zi;
    }

    /**
     * 获取字的笔画.
     * @return 字的笔画
     */
    public int getZi_bihua() {
        return zi_bihua;
    }

    /**
     * 设置字的笔画.
     * @param zi_bihua 字的笔画
     */
    public void setZi_bihua(int zi_bihua) {
        this.zi_bihua = zi_bihua;
    }

    /**
     * 获取字的详解.
     * @return 字的详解
     */
    public String getMore() {
        return more;
    }

    /**
     * 设置字的详解.
     * @param more 字的详解
     */
    public void setMore(String more) {
        this.more = more;
    }

    /**
     * 获取字的解释.
     * @return 字的解释
     */
    public String getExplanation() {
        return explanation;
    }

    /**
     * 设置字的解释.
     * @param explanation 字的解释
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * 获取字的部首.
     * @return 字的部首
     */
    public String getBushou() {
        return bushou;
    }

    /**
     * 设置字的部首.
     * @param bushou 字的部首
     */
    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    /**
     * 获取字的拼音.
     * @return 字的拼音
     */
    public List<String> getPinyin() {
        return pinyin;
    }

    /**
     * 设置拼音.
     * @param pinyin 拼音
     */
    public void setPinyin(List<String> pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * 获取字的拼音来源.
     * @return 字的拼音来源
     */
    public List<String> getPinyin_origin() {
        return pinyin_origin;
    }

    /**
     * 设置字的拼音来源.
     * @param pinyin_origin 字的拼音来源
     */
    public void setPinyin_origin(List<String> pinyin_origin) {
        this.pinyin_origin = pinyin_origin;
    }

}
