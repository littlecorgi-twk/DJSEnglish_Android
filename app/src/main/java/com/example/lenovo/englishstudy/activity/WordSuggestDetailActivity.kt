package com.example.lenovo.englishstudy.activity

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.example.lenovo.englishstudy.R
import com.example.lenovo.englishstudy.Util.GetRequest_Interface
import com.example.lenovo.englishstudy.bean.WordSuggestDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class WordSuggestDetailActivity : AppCompatActivity() {

    @BindView(R.id.tv_wordSuggestDetail_content)
    internal var tvWordSuggestDetailContent: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_ukphone)
    internal var tvWordSuggestDetailUkphone: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_usphone)
    internal var tvWordSuggestDetailUsphone: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_web_trans)
    internal var tvWordSuggestDetailWebTrans: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_meaningList)
    internal var tvWordSuggestDetailMeaningList: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_blng_sents_part_sentenceEng)
    internal var tvWordSuggestDetailBlngSentsPartSentenceEng: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_blng_sents_part_sentenceTranslation)
    internal var tvWordSuggestDetailBlngSentsPartSentenceTranslation: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_auth_sents_part_foreign)
    internal var tvWordSuggestDetailAuthSentsPartForeign: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_auth_sents_part_source)
    internal var tvWordSuggestDetailAuthSentsPartSource: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_media_sents_part_eng)
    internal var tvWordSuggestDetailMediaSentsPartEng: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_media_sents_part_source)
    internal var tvWordSuggestDetailMediaSentsPartSource: TextView? = null
    @BindView(R.id.button_wordSuggestDetail_ukspeech)
    internal var buttonWordSuggestDetailUkspeech: Button? = null
    @BindView(R.id.button_wordSuggestDetail_usspeech)
    internal var buttonWordSuggestDetailUsspeech: Button? = null
    @BindView(R.id.tv_wordDetail_webTran)
    internal var tvWordDetailWebTran: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_exam_type)
    internal var tvWordSuggestDetailExamType: TextView? = null
    @BindView(R.id.parts_layout)
    internal var partsLayout: LinearLayout? = null
    @BindView(R.id.tv_wordSuggestDetail_blng_sents)
    internal var tvWordSuggestDetailBlngSents: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_auth_sents)
    internal var tvWordSuggestDetailAuthSents: TextView? = null
    @BindView(R.id.tv_wordSuggestDetail_media_sents)
    internal var tvWordSuggestDetailMediaSents: TextView? = null

    private var mMediaPlayer_en: MediaPlayer? = null
    private var mMediaPlayer_us = MediaPlayer()
    private var mUsSpeak: String? = null
    private var mEnSpeak: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_suggest_detail)
        ButterKnife.bind(this)

        val intent = intent
        val word = intent.getStringExtra("Word")
        requestWordSuggestDetail(word)

        buttonWordSuggestDetailUkspeech!!.setOnClickListener {
            mMediaPlayer_en = MediaPlayer()
            mMediaPlayer_en!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            if (mEnSpeak != null) {
                try {
                    mMediaPlayer_en!!.setDataSource(mEnSpeak)
                    mMediaPlayer_en!!.prepare()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mMediaPlayer_en!!.start()
            }
        }

        buttonWordSuggestDetailUsspeech!!.setOnClickListener {
            mMediaPlayer_us = MediaPlayer()
            mMediaPlayer_us.setAudioStreamType(AudioManager.STREAM_MUSIC)
            Toast.makeText(this@WordSuggestDetailActivity, "Click", Toast.LENGTH_SHORT).show()
            Log.d("UsSpeak", mUsSpeak)
            if (mUsSpeak != null) {
                try {
                    mMediaPlayer_us.setDataSource(mUsSpeak)
                    mMediaPlayer_us.prepare()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                mMediaPlayer_us.start()
            }
        }
    }

    fun requestWordSuggestDetail(word: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("http://dict.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val request = retrofit.create(GetRequest_Interface::class.java)

        val call = request.getWordSuggestDetailCall(word)

        call.enqueue(object : Callback<WordSuggestDetail> {
            override fun onResponse(call: Call<WordSuggestDetail>, response: Response<WordSuggestDetail>) {
                showWordSuggestDetail(response.body())
            }

            override fun onFailure(call: Call<WordSuggestDetail>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(this@WordSuggestDetailActivity, "获取单词联想失败1", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun showWordSuggestDetail(wordSuggestDetail: WordSuggestDetail?) {
        if (wordSuggestDetail!!.ec.word.isEmpty()) {
            tvWordSuggestDetailContent!!.text = "查无此词！！！"
        } else {
            tvWordSuggestDetailContent!!.text = wordSuggestDetail.ec.word[0].returnphrase.l.i
            tvWordSuggestDetailUkphone!!.text = StringBuffer("英" + '/'.toString() + wordSuggestDetail.simple.word[0].ukphone + '/'.toString())
            tvWordSuggestDetailUsphone!!.text = StringBuffer("美" + '/'.toString() + wordSuggestDetail.simple.word[0].usphone + '/'.toString())
            var wordMeaningContent = ""
            for (trsBean in wordSuggestDetail.ec.word[0].trs) {
                wordMeaningContent += trsBean.tr[0].l.i[0]
                wordMeaningContent += "\n\n"
            }
            wordMeaningContent = wordMeaningContent.substring(0, wordMeaningContent.length - 2)
            tvWordSuggestDetailMeaningList!!.text = wordMeaningContent
            if (!wordSuggestDetail.blng_sents_part.sentencepair.isEmpty()) {
                tvWordSuggestDetailBlngSentsPartSentenceEng!!.text = Html.fromHtml(wordSuggestDetail.blng_sents_part.sentencepair[0].sentenceeng)
                tvWordSuggestDetailBlngSentsPartSentenceTranslation!!.text = wordSuggestDetail.blng_sents_part.sentencepair[0].sentencetranslation
            } else {
                tvWordSuggestDetailBlngSentsPartSentenceEng!!.visibility = View.GONE
                tvWordSuggestDetailBlngSentsPartSentenceTranslation!!.visibility = View.GONE
                tvWordSuggestDetailBlngSents!!.visibility = View.GONE
            }
            if (!wordSuggestDetail.auth_sents_part.sent.isEmpty()) {
                tvWordSuggestDetailAuthSentsPartForeign!!.text = Html.fromHtml(wordSuggestDetail.auth_sents_part.sent[0].foreign)
                tvWordSuggestDetailAuthSentsPartSource!!.text = Html.fromHtml(wordSuggestDetail.auth_sents_part.sent[0].source)
            } else {
                tvWordSuggestDetailAuthSentsPartForeign!!.visibility = View.GONE
                tvWordSuggestDetailAuthSentsPartSource!!.visibility = View.GONE
                tvWordSuggestDetailAuthSents!!.visibility = View.GONE
            }
            if (!wordSuggestDetail.media_sents_part.sent.isEmpty()) {
                tvWordSuggestDetailMediaSentsPartEng!!.text = Html.fromHtml(wordSuggestDetail.media_sents_part.sent[0].eng)
                tvWordSuggestDetailMediaSentsPartSource!!.text = wordSuggestDetail.media_sents_part.sent[0].snippets.snippet[0].source + wordSuggestDetail.media_sents_part.sent[0].snippets.snippet[0].name
            } else {
                tvWordSuggestDetailMediaSentsPartEng!!.visibility = View.GONE
                tvWordSuggestDetailMediaSentsPartSource!!.visibility = View.GONE
                tvWordSuggestDetailMediaSents!!.visibility = View.GONE
            }
            var wordDetailWebTrans = ""
            wordDetailWebTrans = ""
            for (transBean in wordSuggestDetail.web_trans.webtranslation[0].trans) {
                wordDetailWebTrans += transBean.value
                wordDetailWebTrans += ';'.toString()
            }
            tvWordDetailWebTran!!.text = wordDetailWebTrans
            if (wordSuggestDetail.ec.exam_type == null) {
                tvWordSuggestDetailExamType!!.visibility = View.GONE
            } else {
                var examType = ""
                val examTypeString = wordSuggestDetail.ec.exam_type
                for (a in examTypeString) {
                    examType += a
                    examType += '/'.toString()
                }
                examType = examType.substring(0, examType.length - 1)
                tvWordSuggestDetailExamType!!.text = examType
            }

            partsLayout!!.removeAllViews()
            var tvCounter = 0
            for (webtranslationBean in wordSuggestDetail.web_trans.webtranslation) {
                val view = LayoutInflater.from(this).inflate(R.layout.parts_item, partsLayout, false)
                val tvPartsItemCounter = view.findViewById<TextView>(R.id.tv_parts_item_counter)
                val tvPartsItemContent = view.findViewById<TextView>(R.id.tv_parts_item_content)
                val tvPartsItemMeaning = view.findViewById<TextView>(R.id.tv_parts_item_meaning)
                tvCounter++
                val tvCounterString = StringBuilder()
                tvCounterString.insert(tvCounterString.length, tvCounter)
                tvCounterString.insert(tvCounterString.length, '.')
                tvPartsItemCounter.text = tvCounterString
                tvPartsItemContent.text = webtranslationBean.key
                var webTrans = ""
                for (transBean in webtranslationBean.trans) {
                    webTrans += transBean.value
                    webTrans += ';'.toString()
                }
                tvPartsItemMeaning.text = webTrans
                partsLayout!!.addView(view)
            }

            mUsSpeak = wordSuggestDetail.longman.wordList[0].entry.head[0].videocal[0]
            mEnSpeak = wordSuggestDetail.longman.wordList[0].entry.head[0].videocal[0]
        }
    }
}
