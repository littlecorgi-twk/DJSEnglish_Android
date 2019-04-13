package com.example.lenovo.englishstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.WordSuggestDetail;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordSuggestDetailActivity extends AppCompatActivity {


    @BindView(R.id.tv_wordSuggestDetail_content)
    TextView tvWordSuggestDetailContent;
    @BindView(R.id.iv_wordSuggestDetail_ukspeech)
    ImageView ivWordSuggestDetailUkspeech;
    @BindView(R.id.tv_wordSuggestDetail_ukphone)
    TextView tvWordSuggestDetailUkphone;
    @BindView(R.id.iv_wordSuggestDetail_usspeech)
    ImageView ivWordSuggestDetailUsspeech;
    @BindView(R.id.tv_wordSuggestDetail_usphone)
    TextView tvWordSuggestDetailUsphone;
    @BindView(R.id.tv_wordSuggestDetail_web_trans)
    TextView tvWordSuggestDetailWebTrans;
    @BindView(R.id.tv_wordSuggestDetail_meaningList)
    TextView tvWordSuggestDetailMeaningList;
    @BindView(R.id.tv_wordSuggestDetail_web_trans_list)
    TextView tvWordSuggestDetailWebTransList;
    @BindView(R.id.tv_wordSuggestDetail_blng_sents_part_sentenceEng)
    TextView tvWordSuggestDetailBlngSentsPartSentenceEng;
    @BindView(R.id.tv_wordSuggestDetail_blng_sents_part_sentenceTranslation)
    TextView tvWordSuggestDetailBlngSentsPartSentenceTranslation;
    @BindView(R.id.tv_wordSuggestDetail_auth_sents_part_foreign)
    TextView tvWordSuggestDetailAuthSentsPartForeign;
    @BindView(R.id.tv_wordSuggestDetail_auth_sents_part_source)
    TextView tvWordSuggestDetailAuthSentsPartSource;
    @BindView(R.id.tv_wordSuggestDetail_media_sents_part_eng)
    TextView tvWordSuggestDetailMediaSentsPartEng;
    @BindView(R.id.tv_wordSuggestDetail_media_sents_part_source)
    TextView tvWordSuggestDetailMediaSentsPartSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_suggest_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String word = intent.getStringExtra("Word");
        Log.d("1234", word);
        requestWordSuggestDetail(word);

    }

    public void requestWordSuggestDetail(final String word) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dict.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<WordSuggestDetail> call = request.getWordSuggestDetailCall(word);

        call.enqueue(new Callback<WordSuggestDetail>() {
            @Override
            public void onResponse(Call<WordSuggestDetail> call, final Response<WordSuggestDetail> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showWordSuggestDetail(response.body());
                    }
                });
            }

            @Override
            public void onFailure(Call<WordSuggestDetail> call, Throwable t) {
                t.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WordSuggestDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void showWordSuggestDetail(WordSuggestDetail wordSuggestDetail) {
        if (wordSuggestDetail.getLongman().getIsGood().equals("false")) {
            tvWordSuggestDetailContent.setText("查无此词！！！");
        } else {
            Log.d("123", wordSuggestDetail.toString());
            Log.d("123", wordSuggestDetail.getEc().getWord().get(0).getReturnphrase().getL().getI());
            tvWordSuggestDetailContent.setText("adsf");
            tvWordSuggestDetailContent.setText(wordSuggestDetail.getEc().getWord().get(0).getReturnphrase().getL().getI());
            tvWordSuggestDetailUkphone.setText(new StringBuffer("英" + '/' + wordSuggestDetail.getSimple().getWord().get(0).getUkphone() + '/'));
            tvWordSuggestDetailUsphone.setText(new StringBuffer("美" + '/' + wordSuggestDetail.getSimple().getWord().get(0).getUsphone() + '/'));
            String wordMeaningContent = "";
            for (WordSuggestDetail.EcBean.WordBean.TrsBean trsBean : wordSuggestDetail.getEc().getWord().get(0).getTrs()) {
                wordMeaningContent += trsBean.getTr().get(0).getL().getI().get(0);
                wordMeaningContent += "\n\n";
            }
            wordMeaningContent = wordMeaningContent.substring(0, wordMeaningContent.length() - 2);
            tvWordSuggestDetailMeaningList.setText(wordMeaningContent);
            tvWordSuggestDetailBlngSentsPart.setText(Html.fromHtml(wordSuggestDetail.getBlng_sents_part().getSentencepair().get(0).getSentenceeng()));
            // tvWordSuggestDetailBlngSentsPart.setText(new StringBuffer(Html.fromHtml(wordSuggestDetail.getBlng_sents_part().getSentencepair().get(0).getSentenceeng()).toString() + '\n' + wordSuggestDetail.getBlng_sents_part().getSentencepair().get(0).getSentencetranslation()));
        }
    }
}
