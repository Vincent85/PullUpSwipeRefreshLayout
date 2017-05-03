package com.nd.cbs.library.customview.util;

import android.content.Context;

/**
 * Author : cbs
 * date:    2017/5/3 0003.
 */

public class SystemUtil {

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
