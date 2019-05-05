package com.example.lenovo.englishstudy.viewPageCard;

/**
 * @author littlecorgi
 * @Date 2019-01-24 18:16
 * @email a1203991686@126.com
 */
public class CardItem {
    private String text;
    private String imageUrl;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public CardItem(String text, String imageUrl, int id) {
        this.text = text;
        this.imageUrl = imageUrl;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
