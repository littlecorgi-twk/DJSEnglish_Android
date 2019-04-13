package com.example.lenovo.englishstudy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lenovo.englishstudy.db.Sentence;
import com.example.lenovo.englishstudy.userdefined.FlowLayout;

import org.litepal.crud.DataSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChooseHistoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView delete;
    private String[] str;
    private List<Sentence> sentenceList;
    private Set<String> dataList = new HashSet<>();
    private FlowLayout mFlowLayout;
    private TextView sentence, translate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_history);
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
            Log.d("123",sentence.getSentence());
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
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showCenterPopupWindow(textView);
                }
            });
        }
    }

    public void showCenterPopupWindow (TextView textView) {
        View contentView = LayoutInflater.from(ChooseHistoryActivity.this).inflate(R.layout.popupwindow, null);
        PopupWindow popupWindow = new PopupWindow(contentView, 950, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        sentence = contentView.findViewById(R.id.sentence);
        translate = contentView.findViewById(R.id.translate);
        //requestWordTranslate(textView.getText().toString());

        for (Sentence sentence: sentenceList) {
            if((sentence.getSentence()).equals(textView.getText())) {
                translate.setText(sentence.getSentence_translate());
            }
        }
        sentence.setText(textView.getText());
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow背景
        popupWindow.setAnimationStyle(R.style.anim_popup_center);
        //设置动画
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);

        final WindowManager.LayoutParams wBackground = getWindow().getAttributes();
        wBackground.alpha = 0.4f;
        getWindow().setAttributes(wBackground);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wBackground.alpha = 1.0f;
                getWindow().setAttributes(wBackground);
            }
        });

    }

}
