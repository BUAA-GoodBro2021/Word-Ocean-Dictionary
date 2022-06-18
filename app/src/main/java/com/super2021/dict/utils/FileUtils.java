/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.super2021.dict.utils;

import android.content.Context;

import java.io.File;

/**
 * file文件夹下的工具类.
 */
public class FileUtils {
    /**
     * 保存文件.
     * @param context 上下文环境
     */
    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }
}
