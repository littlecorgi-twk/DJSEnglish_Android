package com.example.lenovo.englishstudy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.ArticleDetail;
import com.example.lenovo.englishstudy.bean.WordSuggestDetail;
import com.example.lenovo.englishstudy.userdefined.FlowLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordsDetailActivity extends AppCompatActivity implements View.OnTouchListener {

    private PopupWindow popupWindow;
    private TextView mWord;
    private TextView mPhoneticSymbol;
    private TextView mMeaning;
    private Button mButton;
    private int articleNumber;
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    private String mNames[] = {
            "welcome", "android", "TextView",
            "apple", "jamy", "kobe bryant",
            "jordan", "layout", "viewgroup",
            "margin", "padding", "text",
            "name", "type", "search", "logcat"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_detail);
        Intent intent = getIntent();
        articleNumber = intent.getIntExtra("ArticleNumber", 0);
        initPopupWindow();
        initChildViews();
    }

    void initPopupWindow() {
        View view = LayoutInflater.from(this).inflate(R.layout.word_detail_popup_window, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow背景
        popupWindow.setAnimationStyle(R.style.contextMenuAnim);
        // 设置PopupWindow动画

        mWord = view.findViewById(R.id.tv_word);
        mPhoneticSymbol = view.findViewById(R.id.tv_phonetic_symbol);
        mMeaning = view.findViewById(R.id.tv_meaning);
        mButton = view.findViewById(R.id.button_word_detail_popup_window_changeHeight);

        mButton.setOnTouchListener(this);
    }

    void initChildViews() {
        FlowLayout mFlowLayout = findViewById(R.id.fl_wordDetail);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < mNames.length; i++) {
            final TextView mTextView = new TextView(this);
            mTextView.setText(mNames[i]);
            mTextView.setTextColor(Color.BLACK);
            mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview));
            mFlowLayout.addView(mTextView, lp);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestWordMeaning(mTextView.getText().toString());
                    popupWindow.showAtLocation(LayoutInflater.from(WordsDetailActivity.this).inflate(R.layout.activity_words_detail, null), Gravity.BOTTOM, 0, 0);
                }
            });
        }
    }

    public void requestWordMeaning(final String word) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dict.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        retrofit2.Call<WordSuggestDetail> call = request.getWordSuggestDetailCall(word);

        call.enqueue(new Callback<WordSuggestDetail>() {
            @Override
            public void onResponse(retrofit2.Call<WordSuggestDetail> call, final Response<WordSuggestDetail> response) {
                showWordMeaning(response.body());
            }

            @Override
            public void onFailure(Call<WordSuggestDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showWordMeaning(WordSuggestDetail wordSuggestDetail) {
        if (wordSuggestDetail.getEc().getWord().isEmpty()) {
            mWord.setText("查无此词...");
            mPhoneticSymbol.setText("");
            mMeaning.setText("");
        } else {
            mWord.setText(wordSuggestDetail.getEc().getWord().get(0).getReturnphrase().getL().getI());
            String phoneticSymbol = "/" + wordSuggestDetail.getSimple().getWord().get(0).getUkphone() + "/";
            mPhoneticSymbol.setText(phoneticSymbol);
            String meaning = "";
            for (WordSuggestDetail.EcBean.WordBean.TrsBean trsBean : wordSuggestDetail.getEc().getWord().get(0).getTrs()) {
                meaning = meaning.concat(trsBean.getTr().get(0).getL().getI().get(0)).concat("\n");
            }
            mMeaning.setText(meaning);
        }
    }

    public void requestArticleDetail(final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        retrofit2.Call<ArticleDetail> call = request.getArticleDetail(id);

        call.enqueue(new Callback<ArticleDetail>() {
            @Override
            public void onResponse(retrofit2.Call<ArticleDetail> call, final Response<ArticleDetail> response) {
                initChildViews();
            }

            @Override
            public void onFailure(Call<ArticleDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //当手指移动的时候
            y2 = event.getY();
            if (y1 - y2 > 50) {
                Toast.makeText(WordsDetailActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                popupWindow.setHeight(100);
                popupWindow.showAtLocation(LayoutInflater.from(WordsDetailActivity.this).inflate(R.layout.activity_words_detail, null), Gravity.BOTTOM, 0, 0);
            } else if (y2 - y1 > 50) {
                Toast.makeText(WordsDetailActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                popupWindow.setHeight((int) y2);
                popupWindow.showAtLocation(LayoutInflater.from(WordsDetailActivity.this).inflate(R.layout.activity_words_detail, null), Gravity.BOTTOM, 0, 0);
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.i("Lgq", "sssssssll离开了lllll==");
//            updview(nowpersion);
        }
        return super.onTouchEvent(event);
//        return false;
    }
}
