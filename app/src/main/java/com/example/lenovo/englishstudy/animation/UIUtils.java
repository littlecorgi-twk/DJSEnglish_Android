package com.example.lenovo.englishstudy.animation;

import android.content.res.Resources;

public class UIUtils {
    public static int dp2px(double dpi) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dpi + 0.5f);
    }

    public static int statusBarHeignth() {
        return dp2px(25);
    }
}
