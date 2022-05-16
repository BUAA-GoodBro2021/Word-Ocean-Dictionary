package com.example.word_ocean_dictionary;

import android.app.Application;

import com.example.word_ocean_dictionary.db.DBManager;

import org.xutils.x;

public class UniteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);   //初始化xUtils的模块数据
//        初始化数据库对象
        DBManager.initDB(this);
    }
}
