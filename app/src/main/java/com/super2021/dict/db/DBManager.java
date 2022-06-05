package com.super2021.dict.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.super2021.dict.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBManager {
    private static SQLiteDatabase db;

    public static void initDB(Context context) {
        DBOpenHelper helper = new DBOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    // 插入汉字历史记录
    public static void insertZiHistory(String zi) {
        ContentValues values = new ContentValues();
        values.put("zi", zi);
        db.insert(CommonUtils.TABLE_ZI_HISTORY, null, values);
    }

    // 插入汉字收藏记录
    public static void insertZiCollection(String zi) {
        ContentValues values = new ContentValues();
        values.put("zi", zi);
        db.insert(CommonUtils.TABLE_ZI_COLLECTION, null, values);
    }

    // 查找汉字是否在历史表中
    public static boolean isZiExistHistory(String zi) {
        String sql = "select * from " + CommonUtils.TABLE_ZI_HISTORY + " where zi = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{zi});
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    // 查找汉字是否在收藏表中
    public static boolean isZiExistCollection(String zi) {
        String sql = "select * from " + CommonUtils.TABLE_ZI_COLLECTION + " where zi = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{zi});
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    // 删除历史表中的汉字
    public static void deleteZiHistory(String zi) {
        String sql = "delete from " + CommonUtils.TABLE_ZI_HISTORY + " where zi = ?";
        db.execSQL(sql, new String[]{zi});
    }

    // 删除收藏表中的汉字
    public static void deleteZiCollection(String zi) {
        String sql = "delete from " + CommonUtils.TABLE_ZI_COLLECTION + " where zi = ?";
        db.execSQL(sql, new String[]{zi});
    }

    // 查找汉字历史中的全部数据
    public static List<String> queryZiHistory() {
        String sql = "select * from " + CommonUtils.TABLE_ZI_HISTORY;
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String zi = cursor.getString(cursor.getColumnIndexOrThrow("zi"));
            list.add(zi);
        }
        cursor.close();
        Collections.reverse(list);
        return list;
    }

    // 查找汉字收藏中的全部数据
    public static List<String> queryZiCollection() {
        String sql = "select * from " + CommonUtils.TABLE_ZI_COLLECTION;
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String zi = cursor.getString(cursor.getColumnIndexOrThrow("zi"));
            list.add(zi);
        }
        cursor.close();
        Collections.reverse(list);
        return list;
    }

    // 插入成语历史记录
    public static void insertChengyuHistory(String chengyu) {
        ContentValues values = new ContentValues();
        values.put("chengyu", chengyu);
        db.insert(CommonUtils.TABLE_CHENGYU_HISTORY, null, values);
    }

    // 插入成语收藏记录
    public static void insertChengyuCollection(String chengyu) {
        ContentValues values = new ContentValues();
        values.put("chengyu", chengyu);
        db.insert(CommonUtils.TABLE_CHENGYU_COLLECTION, null, values);
    }

    // 查找成语是否在历史表中
    public static boolean isChengyuExistHistory(String chengyu) {
        String sql = "select * from " + CommonUtils.TABLE_CHENGYU_HISTORY + " where chengyu = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{chengyu});
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    // 查找成语是否在收藏表中
    public static boolean isChengyuExistCollection(String chengyu) {
        String sql = "select * from " + CommonUtils.TABLE_CHENGYU_COLLECTION + " where chengyu = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{chengyu});
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        return false;
    }

    // 删除历史表中的成语
    public static void deleteChengyuHistory(String chengyu) {
        String sql = "delete from " + CommonUtils.TABLE_CHENGYU_HISTORY + " where chengyu = ?";
        db.execSQL(sql, new String[]{chengyu});
    }

    // 删除收藏表中的成语
    public static void deleteChengyuCollection(String chengyu) {
        String sql = "delete from " + CommonUtils.TABLE_CHENGYU_COLLECTION + " where chengyu = ?";
        db.execSQL(sql, new String[]{chengyu});
    }

    // 查找成语历史中的全部数据
    public static List<String> queryChengyuHistory() {
        String sql = "select * from " + CommonUtils.TABLE_CHENGYU_HISTORY;
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String chengyu = cursor.getString(cursor.getColumnIndexOrThrow("chengyu"));
            list.add(chengyu);
        }
        cursor.close();
        Collections.reverse(list);
        return list;
    }

    // 查找成语收藏中的全部数据
    public static List<String> queryChengyuCollection() {
        String sql = "select * from " + CommonUtils.TABLE_CHENGYU_COLLECTION;
        Cursor cursor = db.rawQuery(sql, null);
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String chengyu = cursor.getString(cursor.getColumnIndexOrThrow("chengyu"));
            list.add(chengyu);
        }
        cursor.close();
        Collections.reverse(list);
        return list;
    }
}
