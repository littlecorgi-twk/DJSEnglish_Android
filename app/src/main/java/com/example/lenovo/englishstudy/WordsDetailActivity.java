package com.example.lenovo.englishstudy;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.userdefined.FlowLayout;

public class WordsDetailActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    private String mNames[] = {
            "welcome","android","TextView",
            "apple","jamy","kobe bryant",
            "jordan","layout","viewgroup",
            "margin","padding","text",
            "name","type","search","logcat"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_detail);

        View view = LayoutInflater.from(this).inflate(R.layout.word_detail_popup_window, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow背景

        initChildViews();
    }

    void initChildViews(){
        FlowLayout mFlowLayout = findViewById(R.id.fl_wordDetail);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for(int i = 0; i < mNames.length; i ++){
            final TextView mTextView = new TextView(this);
            mTextView.setText(mNames[i]);
            mTextView.setTextColor(Color.BLACK);
            mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview));
            mFlowLayout.addView(mTextView, lp);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.showAtLocation(LayoutInflater.from(WordsDetailActivity.this).inflate(R.layout.activity_words_detail, null), Gravity.BOTTOM, 0, 0);
                }
            });
        }
    }
}
