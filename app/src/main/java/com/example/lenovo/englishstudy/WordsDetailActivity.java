package com.example.lenovo.englishstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;

public class WordsDetailActivity extends AppCompatActivity {

    TextView words_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_detail);
        words_detail = findViewById(R.id.tv_words_detail);
    }
}
