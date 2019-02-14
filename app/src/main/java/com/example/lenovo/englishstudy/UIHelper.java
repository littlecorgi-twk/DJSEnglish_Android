package com.example.lenovo.englishstudy;

import android.content.Context;
import android.content.res.Resources;

/**
 * @author littlecorgi
 * @Date 2019-02-11 14:00
 * @email a1203991686@126.com
 */
public class UIHelper {

    /**
     * dp转px
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * dp转px
     */
    public static int dip2px(float dpValue) {
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
