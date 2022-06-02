/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.hui.dict.utils;

import android.content.Context;

import java.io.File;

public class FileUtils {
    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), "pic.jpg");
    }
}
