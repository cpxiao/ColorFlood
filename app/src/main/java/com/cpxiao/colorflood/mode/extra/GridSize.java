package com.cpxiao.colorflood.mode.extra;

import android.text.TextUtils;

import com.cpxiao.AppConfig;
import com.cpxiao.androidutils.library.utils.StringUtils;

/**
 * @author cpxiao on 2017/09/07.
 */


public  final class GridSize {

    private static final boolean DEBUG = AppConfig.DEBUG;
    /**
     * 方格数量
     */
    public static final String SIZE_KEY = "SIZE_KEY";
    private static final String SIZE_12 = "12 x 12";
    private static final String SIZE_16 = "16 x 16";
    private static final String SIZE_20 = "20 x 20";
    private static final String SIZE_24 = "24 x 24";
    private static final String SIZE_28 = "28 x 28";
    public static final String SIZE_DEFAULT = SIZE_20;
    private static final String[] SIZE_ARRAY = {SIZE_12, SIZE_16, SIZE_20, SIZE_24, SIZE_28};

    public static int getGridCountX(String size) {
        if (StringUtils.isEmpty(size)) {
            return 0;
        }
        try {
            String x = size.substring(0, size.indexOf("x") - 1);
            return Integer.valueOf(x);
        } catch (Exception e) {
            if (DEBUG) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static int getGridCountY(String size) {
        if (StringUtils.isEmpty(size)) {
            return 0;
        }
        try {
            String x = size.substring(size.indexOf("x") + 2, size.length());
            return Integer.valueOf(x);
        } catch (Exception e) {
            if (DEBUG) {
                e.printStackTrace();
            }
        }

        return 0;
    }

    public static String getNextGridSize(String gridSize) {
        int index = -1;
        for (int i = 0; i < SIZE_ARRAY.length; i++) {
            if (TextUtils.equals(gridSize, SIZE_ARRAY[i])) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return SIZE_DEFAULT;
        }
        return SIZE_ARRAY[(index + 1) % SIZE_ARRAY.length];
    }
}
