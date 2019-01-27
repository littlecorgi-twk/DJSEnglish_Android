package com.example.lenovo.englishstudy.ViewPagerCard;

/**
 * @author littlecorgi
 * @Date 2019-01-24 18:16
 * @email a1203991686@126.com
 */
public class CardItem {
    private int mTextResource;
    private int mTitleResource;

    public CardItem(int title, int text) {
        mTitleResource = title;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getTitle() {
        return mTitleResource;
    }
}
