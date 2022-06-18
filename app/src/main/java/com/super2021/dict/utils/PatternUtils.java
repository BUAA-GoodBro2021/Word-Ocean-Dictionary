package com.super2021.dict.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * pattern文件夹下的工具类.
 */
public class PatternUtils {
    /**
     * 剔除汉字.
     * @param value 汉字
     * @return 剔除结果
     */
    public static String removeDigital(String value){
        Pattern p = Pattern.compile("[\\d]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除字母.
     * @param value 字母
     * @return 剔除结果
     */
    public static String removeLetter(String value){
        Pattern p = Pattern.compile("[a-zA-Z]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除标点符号.
     * @param value 标点符号
     * @return 剔除结果
     */
    public static String removePunctuation(String value){
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll("");
        return result;
    }

    /**
     * 剔除全部数字，字母，标点符号
     * @param value 值
     * @return 剔除结果
     * */
    public static String removeAll(String value){
        Pattern p = Pattern.compile("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|a-zA-Z\\d]");
        Matcher matcher = p.matcher(value);
        String result = matcher.replaceAll("");
        return result;
    }
}
