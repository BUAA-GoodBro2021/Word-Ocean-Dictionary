package com.super2021.dict;

import android.app.Application;


import com.super2021.dict.db.DBManager;

public class UniteApplication extends Application {

    // 创建界面
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库对象
        DBManager.initDB(this);
    }
}
