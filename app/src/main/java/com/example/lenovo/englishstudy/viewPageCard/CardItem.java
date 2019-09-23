package com.example.lenovo.englishstudy.viewPageCard;

import com.example.lenovo.englishstudy.bean.ArticleList;
import com.example.lenovo.englishstudy.bean.SexagenaryCycle;

/**
 * @author littlecorgi
 * @Date 2019-01-24 18:16
 * @email a1203991686@126.com
 */
public class CardItem {
    private ArticleList.DataBean.ListBean mArticle;

    public CardItem(ArticleList.DataBean.ListBean mArticle) {
        this.mArticle = mArticle;
    }

    public ArticleList.DataBean.ListBean getmArticle() {
        return mArticle;
    }

    public void setmArticle(ArticleList.DataBean.ListBean mArticle) {
        this.mArticle = mArticle;
    }

}
