package com.example.lenovo.englishstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.WordSuggestDetail;

import java.util.List;

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
    @BindView(R.id.tv_wordSuggestDetail_ukphone)
    TextView tvWordSuggestDetailUkphone;
    @BindView(R.id.tv_wordSuggestDetail_usphone)
    TextView tvWordSuggestDetailUsphone;
    @BindView(R.id.tv_wordSuggestDetail_web_trans)
    TextView tvWordSuggestDetailWebTrans;
    @BindView(R.id.tv_wordSuggestDetail_meaningList)
    TextView tvWordSuggestDetailMeaningList;
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
    @BindView(R.id.button_wordSuggestDetail_ukspeech)
    Button buttonWordSuggestDetailUkspeech;
    @BindView(R.id.button_wordSuggestDetail_usspeech)
    Button buttonWordSuggestDetailUsspeech;
    @BindView(R.id.tv_wordDetail_webTran)
    TextView tvWordDetailWebTran;
    @BindView(R.id.tv_wordSuggestDetail_exam_type)
    TextView tvWordSuggestDetailExamType;
    @BindView(R.id.parts_layout)
    LinearLayout partsLayout;
    @BindView(R.id.tv_wordSuggestDetail_blng_sents)
    TextView tvWordSuggestDetailBlngSents;
    @BindView(R.id.tv_wordSuggestDetail_auth_sents)
    TextView tvWordSuggestDetailAuthSents;
    @BindView(R.id.tv_wordSuggestDetail_media_sents)
    TextView tvWordSuggestDetailMediaSents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_suggest_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String word = intent.getStringExtra("Word");
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
                showWordSuggestDetail(response.body());
            }

            @Override
            public void onFailure(Call<WordSuggestDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordSuggestDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showWordSuggestDetail(WordSuggestDetail wordSuggestDetail) {
        if (wordSuggestDetail.getEc().getWord().isEmpty()) {
            tvWordSuggestDetailContent.setText("查无此词！！！");
        } else {
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
            if (!wordSuggestDetail.getBlng_sents_part().getSentencepair().isEmpty()) {
                tvWordSuggestDetailBlngSentsPartSentenceEng.setText(Html.fromHtml(wordSuggestDetail.getBlng_sents_part().getSentencepair().get(0).getSentenceeng()));
                tvWordSuggestDetailBlngSentsPartSentenceTranslation.setText(wordSuggestDetail.getBlng_sents_part().getSentencepair().get(0).getSentencetranslation());
            } else {
                tvWordSuggestDetailBlngSentsPartSentenceEng.setVisibility(View.GONE);
                tvWordSuggestDetailBlngSentsPartSentenceTranslation.setVisibility(View.GONE);
                tvWordSuggestDetailBlngSents.setVisibility(View.GONE);
            }
            if (!wordSuggestDetail.getAuth_sents_part().getSent().isEmpty()) {
                tvWordSuggestDetailAuthSentsPartForeign.setText(Html.fromHtml(wordSuggestDetail.getAuth_sents_part().getSent().get(0).getForeign()));
                tvWordSuggestDetailAuthSentsPartSource.setText(Html.fromHtml(wordSuggestDetail.getAuth_sents_part().getSent().get(0).getSource()));
            } else {
                tvWordSuggestDetailAuthSentsPartForeign.setVisibility(View.GONE);
                tvWordSuggestDetailAuthSentsPartSource.setVisibility(View.GONE);
                tvWordSuggestDetailAuthSents.setVisibility(View.GONE);
            }
            if (!wordSuggestDetail.getMedia_sents_part().getSent().isEmpty()) {
                tvWordSuggestDetailMediaSentsPartEng.setText(Html.fromHtml(wordSuggestDetail.getMedia_sents_part().getSent().get(0).getEng()));
                tvWordSuggestDetailMediaSentsPartSource.setText(wordSuggestDetail.getMedia_sents_part().getSent().get(0).getSnippets().getSnippet().get(0).getSource() +
                        wordSuggestDetail.getMedia_sents_part().getSent().get(0).getSnippets().getSnippet().get(0).getName());
            } else {
                tvWordSuggestDetailMediaSentsPartEng.setVisibility(View.GONE);
                tvWordSuggestDetailMediaSentsPartSource.setVisibility(View.GONE);
                tvWordSuggestDetailMediaSents.setVisibility(View.GONE);
            }
            String wordDetailWebTrans = "";
            wordDetailWebTrans = "";
            for (WordSuggestDetail.WebTransBean.WebtranslationBean.TransBean transBean : wordSuggestDetail.getWeb_trans().getWebtranslation().get(0).getTrans()) {
                wordDetailWebTrans += transBean.getValue();
                wordDetailWebTrans += ';';
            }
            tvWordDetailWebTran.setText(wordDetailWebTrans);
            if (!wordSuggestDetail.getEc().getExam_type().isEmpty()) {
                String examType = "";
                List<String> examTypeString = wordSuggestDetail.getEc().getExam_type();
                for (String a : examTypeString) {
                    examType += a;
                    examType += '/';
                }
                examType = examType.substring(0, examType.length() - 1);
                tvWordSuggestDetailExamType.setText(examType);
            } else {
                tvWordSuggestDetailExamType.setVisibility(View.GONE);
            }

            partsLayout.removeAllViews();
            int tvCounter = 0;
            for (WordSuggestDetail.WebTransBean.WebtranslationBean webtranslationBean : wordSuggestDetail.getWeb_trans().getWebtranslation()) {
                View view = LayoutInflater.from(this).inflate(R.layout.parts_item, partsLayout, false);
                TextView tvPartsItemCounter = view.findViewById(R.id.tv_parts_item_counter);
                TextView tvPartsItemContent = view.findViewById(R.id.tv_parts_item_content);
                TextView tvPartsItemMeaning = view.findViewById(R.id.tv_parts_item_meaning);
                tvCounter++;
                StringBuilder tvCounterString = new StringBuilder();
                tvCounterString.insert(tvCounterString.length(), tvCounter);
                tvCounterString.insert(tvCounterString.length(), '.');
                tvPartsItemCounter.setText(tvCounterString);
                tvPartsItemContent.setText(webtranslationBean.getKey());
                String webTrans = "";
                for (WordSuggestDetail.WebTransBean.WebtranslationBean.TransBean transBean : webtranslationBean.getTrans()) {
                    webTrans += transBean.getValue();
                    webTrans += ';';
                }
                tvPartsItemMeaning.setText(webTrans);
                partsLayout.addView(view);
            }
        }
    }
}
