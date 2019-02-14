package com.example.lenovo.englishstudy.ViewPagerCard;

import android.support.v7.widget.CardView;

/**
 * @author littlecorgi
 * @Date 2019-01-24 17:35
 * @email a1203991686@126.com
 */
public interface CardAdapter {
    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
