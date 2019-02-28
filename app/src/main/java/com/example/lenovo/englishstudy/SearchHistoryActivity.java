package com.example.lenovo.englishstudy;

import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.lenovo.englishstudy.db.Sentence;
import com.example.lenovo.englishstudy.userdefined.FlowLayout;

import org.litepal.crud.DataSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchHistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView delete;
    private String[] str;
    private List<Sentence> sentenceList;
    private Set<String> dataList = new HashSet<>();
    private FlowLayout mFlowLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_history);
        toolbar = findViewById(R.id.title2);
        delete = findViewById(R.id.delete);
        initData();
        initChildViews();
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(Sentence.class);
                mFlowLayout.removeAllViews();
                delete.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void initData() {
        sentenceList = DataSupport.findAll(Sentence.class);
        if(sentenceList.size() == 0) {
            delete.setVisibility(View.INVISIBLE);
        }
        for (Sentence sentence: sentenceList) {
            dataList.add(sentence.getSentence());
        }
        str = dataList.toArray(new String[dataList.size()]);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void initChildViews() {
        // TODO Auto-generated method stub
        mFlowLayout = findViewById(R.id.history_flowlayout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;


        for (int i = 0; i < str.length; i++) {
            final TextView textView = new TextView(this);
            textView.setText(str[i]);
            textView.setTextColor(Color.BLACK);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview));
            mFlowLayout.addView(textView, lp);

        }
    }

}
