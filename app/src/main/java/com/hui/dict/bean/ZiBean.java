package com.hui.dict.bean;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ZiBean {
    private int id;
    private String zi;
    private int zi_bihua;
    private String explanation;
    private String more;
    private String bushou;
    private List<String> pinyin;
    private List<String> pinyin_origin;

    @NonNull
    @Override
    public String toString() {
        return "ListBean{" +
                "zi='" + zi + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", bushou='" + bushou + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getZi() {
        return zi;
    }

    public void setZi(String zi) {
        this.zi = zi;
    }

    public int getZi_bihua() {
        return zi_bihua;
    }

    public void setZi_bihua(int zi_bihua) {
        this.zi_bihua = zi_bihua;
    }

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getBushou() {
        return bushou;
    }

    public void setBushou(String bushou) {
        this.bushou = bushou;
    }

    public List<String> getPinyin() {
        return pinyin;
    }

    public void setPinyin(List<String> pinyin) {
        this.pinyin = pinyin;
    }

    public List<String> getPinyin_origin() {
        return pinyin_origin;
    }

    public void setPinyin_origin(List<String> pinyin_origin) {
        this.pinyin_origin = pinyin_origin;
    }

}
