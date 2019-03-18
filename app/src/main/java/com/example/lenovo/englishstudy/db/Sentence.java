package com.example.lenovo.englishstudy.db;

import org.litepal.crud.DataSupport;

public class Sentence extends DataSupport {
    private String sentence;
    private String sentence_translate;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSentence_translate() {
        return sentence_translate;
    }

    public void setSentence_translate(String sentence_translate) {
        this.sentence_translate = sentence_translate;
    }
}
