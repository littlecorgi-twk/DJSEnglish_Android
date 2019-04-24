package com.example.lenovo.englishstudy;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.WordMeanig;
import com.example.lenovo.englishstudy.userdefined.FlowLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordsDetailActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private TextView mWord;
    private TextView mPhoneticSymbol;
    private TextView mMeaning;
    private int articleNumber;

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
                .baseUrl("q=account&")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        retrofit2.Call<WordMeanig> call = request.getWordMeaningCall(word);

        call.enqueue(new Callback<WordMeanig>() {
            @Override
            public void onResponse(retrofit2.Call<WordMeanig> call, final Response<WordMeanig> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWordMeaning(response.body());
                    }
                });
            }

            @Override
            public void onFailure(Call<WordMeanig> call, Throwable t) {
                t.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*public void requestWordMeaning(final String word) {
        String wordMeaningUrl = "http://dict.youdao.com/jsonapi?jsonversion=2&client=mobile&q=" + word +
            "&dicts=%7B%22count%22%3A99%2C%22dicts%22%3A%5B%5B%22ec%22%2C%22ce%22%2C%22newcj%22%2C%22newjc%22%2C%22kc%22%2C%22ck%22%2C%22fc%22%2C%22cf%22%2C%22multle%22%2C%22jtj%22%2C%22pic_dict%22%2C%22tc%22%2C%22ct%22%2C%22typos%22%2C%22special%22%2C%22tcb%22%2C%22baike%22%2C%22lang%22%2C%22simple%22%2C%22wordform%22%2C%22exam_dict%22%2C%22ctc%22%2C%22web_search%22%2C%22auth_sents_part%22%2C%22ec21%22%2C%22phrs%22%2C%22input%22%2C%22wikipedia_digest%22%2C%22ee%22%2C%22collins%22%2C%22ugc%22%2C%22media_sents_part%22%2C%22syno%22%2C%22rel_word%22%2C%22longman%22%2C%22ce_new%22%2C%22le%22%2C%22newcj_sents%22%2C%22blng_sents_part%22%2C%22hh%22%5D%2C%5B%22ugc%22%5D%2C%5B%22longman%22%5D%2C%5B%22newjc%22%5D%2C%5B%22newcj%22%5D%2C%5B%22web_trans%22%5D%2C%5B%22fanyi%22%5D%5D%7D&keyfrom=mdict.7.2.0.android&model=honor&mid=5.6.1&imei=659135764921685&vendor=wandoujia&screen=1080x1800&ssid=superman&network=wifi&abtest=2&xmlVersion=5.1";
        HttpUtil.sendHttpRequest(wordMeaningUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WordsDetailActivity.this, "获取单词释义失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final WordMeanig wordMeanig = Utility.handleWordMeaningResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (wordMeanig != null) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WordsDetailActivity.this).edit();
                            editor.putString("wordMeaning", responseText);
                            editor.apply();
                            showWordMeaning(wordMeanig);
                        } else {
                            Toast.makeText(WordsDetailActivity.this, "获取单词释义失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }*/

    public void showWordMeaning(WordMeanig wordMeanig) {
        if (wordMeanig.getSimple().getQuery().equals("no word!")) {
            mWord.setText("查无此词...");
            mPhoneticSymbol.setText("");
            mMeaning.setText("");
        } else {
            mWord.setText(wordMeanig.getSimple().getQuery());
            String phoneticSymbol = "/" + wordMeanig.getSimple().getWord().get(0).getUkphone() + "/";
            mPhoneticSymbol.setText(phoneticSymbol);
            String meaning = "";
            for (WordMeanig.EcBean.WordBean.TrsBean trsBean : wordMeanig.getEc().getWord().get(0).getTrs()) {
                meaning = meaning.concat(trsBean.getTr().get(0).getL().getI().get(0)).concat("\n");
            }
            mMeaning.setText(meaning);
        }
    }
}
