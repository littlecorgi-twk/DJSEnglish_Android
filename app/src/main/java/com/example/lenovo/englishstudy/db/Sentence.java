package com.example.lenovo.englishstudy.db;

import org.litepal.crud.DataSupport;

public class Sentence extends DataSupport {
    private String sentence;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
