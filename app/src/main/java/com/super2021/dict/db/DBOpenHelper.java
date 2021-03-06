package com.super2021.dict.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.super2021.dict.utils.CommonUtils;

/**
 * 打开数据库.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(@Nullable Context context) {
        super(context, "word_ocean.db", null, 1);
    }

    /**
     * 创建记录和表.
     * 
     * @param db 要插入的数据库
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        // 创建汉字历史记录
        sql = "create table if not exists " + CommonUtils.TABLE_ZI_HISTORY
                + "(id integer primary key autoincrement,zi varchar(4) not null)";
        db.execSQL(sql);
        // 创建成语历史记录
        sql = "create table if not exists " + CommonUtils.TABLE_CHENGYU_HISTORY
                + "(id integer primary key autoincrement,chengyu varchar(16) not null)";
        db.execSQL(sql);
        // 创建收藏汉字的表
        sql = "create table if not exists " + CommonUtils.TABLE_ZI_COLLECTION
                + "(id integer primary key autoincrement,zi varchar(4) not null)";
        db.execSQL(sql);
        // 创建收藏成语的表
        sql = "create table if not exists " + CommonUtils.TABLE_CHENGYU_COLLECTION
                + "(id integer primary key autoincrement,chengyu varchar(16) not null)";
        db.execSQL(sql);
    }

    /**
     * 更新数据库.
     * 
     * @param db         要插入的数据库
     * @param newVersion 新的视图
     * @param oldVersion 旧的视图
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
