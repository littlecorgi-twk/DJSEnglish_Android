package com.example.lenovo.englishstudy.viewPageCard;

import com.example.lenovo.englishstudy.bean.ArticleList;

/**
 * @author littlecorgi
 * @Date 2019-01-24 18:16
 * @email a1203991686@126.com
 */
public class CardItem {
    private ArticleList.DataBean.ListBean mArticle;

    public ArticleList.DataBean.ListBean getmArticle() {
        return mArticle;
    }

    public void setmArticle(ArticleList.DataBean.ListBean mArticle) {
        this.mArticle = mArticle;
    }

    public CardItem(ArticleList.DataBean.ListBean mArticle) {
        this.mArticle = mArticle;
    }
}
