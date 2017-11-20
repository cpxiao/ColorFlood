package com.cpxiao.colorflood.mode.extra;

import android.content.Context;
import android.graphics.Color;

import com.cpxiao.R;

/**
 * @author cpxiao on 2017/09/07.
 */
public final class BlockColor {
    public static final int colorBg = 0xffffffff;

    public static int[] get6ColorArray(Context context) {
        return get6ColorArray(context, 6);
    }

    public static int[] get8ColorArray(Context context) {
        return get6ColorArray(context, 8);
    }

    private static int[] get6ColorArray(Context context, int length) {
        String[] colorArrayRes = context.getResources().getStringArray(R.array.colorArray);
        int[] colorArray = new int[length];
        for (int i = 0; i < length; i++) {
            colorArray[i] = Color.parseColor(colorArrayRes[i]);
        }
        return colorArray;
    }
}