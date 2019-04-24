package com.example.lenovo.englishstudy.viewPageCard;

/**
 * @author littlecorgi
 * @Date 2019-01-24 18:16
 * @email a1203991686@126.com
 */
public class CardItem {
    private String text;
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CardItem(String text, String imageUrl) {
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
