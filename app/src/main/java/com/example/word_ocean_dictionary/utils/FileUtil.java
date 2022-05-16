/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.example.word_ocean_dictionary.utils;

import android.content.Context;

import java.io.File;

public class FileUtil {
    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }
}
