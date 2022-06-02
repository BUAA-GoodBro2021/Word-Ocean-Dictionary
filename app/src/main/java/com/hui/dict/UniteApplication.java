package com.hui.dict;

import android.app.Application;


import com.hui.dict.db.DBManager;

public class UniteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库对象
        DBManager.initDB(this);
    }
}
