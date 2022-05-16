package com.example.word_ocean_dictionary.utils;

public class URLUtils {

    public static String pinyinurl = "http://v.juhe.cn/xhzd/querypy?key=";

    public static String bushourul = "http://v.juhe.cn/xhzd/querybs?key=";

    public static final String DICTKEY = "3022583457067131a719f84d10efd275";

    public static String wordurl = "http://v.juhe.cn/xhzd/query?key=";

    public static final String CHENGYUKEY = "e8a46192a557700f9a8c9b21eab233e5";
    public static String chengyuurl = "http://v.juhe.cn/chengyu/query?key=";

    public static String getChengyuurl(String word){
        return chengyuurl+CHENGYUKEY+"&word="+word;
    }
    public static String getWordurl(String word){
        return wordurl+DICTKEY+"&word="+word;
    }

    public static String getPinyinurl(String word,int page,int pagesize){
        return pinyinurl+DICTKEY+"&word="+word+"&page="+page+"&pagesize="+pagesize;
    }

    public static String getBushouurl(String bs,int page,int pagesize){
        return bushourul+DICTKEY+"&word="+bs+"&page="+page+"&pagesize="+pagesize;
    }
}
