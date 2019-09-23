package com.example.lenovo.englishstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.ArticleDetail;
import com.example.lenovo.englishstudy.bean.MessageVerify;
import com.example.lenovo.englishstudy.bean.WordSuggestDetail;
import com.example.lenovo.englishstudy.userdefined.FlowLayout;
import com.example.lenovo.englishstudy.viewPageCard.AnimationTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WordsDetailActivity extends AppCompatActivity {

    @BindView(R.id.tb_words_detail)
    Toolbar tbWordsDetail;
    @BindView(R.id.fl_wordDetail)
    FlowLayout flWordDetail;
    @BindView(R.id.rv_words_detail)
    RecyclerView rvWordsDetail;
    private PopupWindow popupWindow;
    private TextView mWord;
    private TextView mPhoneticSymbol;
    private TextView mMeaning;
    private int articleNumber;
    private float y1;
    private float y2;
    private View vPopupWindow;
    private boolean flag1;
    private boolean flag2;
    private String token;
    private ImageView mButtonLike;
    private ImageView mButtonCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_words_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        }

        ButterKnife.bind(this);
        SharedPreferences sharedPreferences = getSharedPreferences("user_token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        Intent intent = getIntent();
        articleNumber = intent.getIntExtra("ArticleNumber", 0);
        if (token.equals(""))
            requestArticleDetail(articleNumber);
        else
            requestArticleDetailToken(token, articleNumber);
        initPopupWindow();
        // setSupportActionBar(tbWordsDetail);
        tbWordsDetail.inflateMenu(R.menu.toolbar_words_detail);
        tbWordsDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tbWordsDetail.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_likes:
                        if (flag1) {
                            flag1 = false;
                            requestGetDislikeArticle(articleNumber);
                            AnimationTools.scale(tbWordsDetail.getMenu().getItem(1).getActionView());
                            tbWordsDetail.getMenu().getItem(1).setIcon(R.drawable.ic_like_unheater);
                        } else {
                            flag1 = true;
                            requestGetLikeArticle(articleNumber);
                            AnimationTools.scale(tbWordsDetail.getMenu().getItem(1).getActionView());
                            tbWordsDetail.getMenu().getItem(1).setIcon(R.drawable.ic_like_heater);
                        }
                        break;
                    case R.id.action_notifications:
                        if (flag2) {
                            flag2 = false;
                            requestGetDelCollection(articleNumber);
                            AnimationTools.scale(tbWordsDetail.getMenu().getItem(0).getActionView());
                            tbWordsDetail.getMenu().getItem(0).setIcon(R.drawable.ic_collection_unstar);
                        } else {
                            flag2 = true;
                            requestGetAddCollection(articleNumber);
                            AnimationTools.scale(tbWordsDetail.getMenu().getItem(0).getActionView());
                            tbWordsDetail.getMenu().getItem(0).setIcon(R.drawable.ic_collection_star);
                        }
                        break;

                }
                return true;
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 设置布局管理器
        rvWordsDetail.setLayoutManager(layoutManager);
        // 设置为垂直布局
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        // 设置Adapter
        // rvWordsDetail.setAdapter(recyclerAdapter);
        // 设置item增加、删除动画
        rvWordsDetail.setItemAnimator(new DefaultItemAnimator());
        // 添加分割线
        rvWordsDetail.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_words_detail, menu);
        MenuItem item = menu.findItem(R.id.action_likes);
        mButtonLike = (ImageView) MenuItemCompat.getActionView(item);
        mButtonLike.setBackgroundResource(R.drawable.ic_like_unheater);
        item = menu.findItem(R.id.action_notifications);
        mButtonCollection = (ImageView) MenuItemCompat.getActionView(item);
        mButtonCollection.setBackgroundResource(R.drawable.ic_collection_unstar);
        return true;
    }

    public void requestArticleDetail(final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<ArticleDetail> call = request.getArticleDetail(id);

        call.enqueue(new Callback<ArticleDetail>() {
            @Override
            public void onResponse(Call<ArticleDetail> call, final Response<ArticleDetail> response) {
                initChildViews(response.body());
                flag1 = response.body().getData().isIsLike();
                if (flag1) {
                    tbWordsDetail.getMenu().getItem(1).setIcon(R.drawable.ic_like_heater);
                } else {
                    tbWordsDetail.getMenu().getItem(1).setIcon(R.drawable.ic_like_unheater);
                }
                flag2 = response.body().getData().isIsCollection();
                if (flag2) {
                    tbWordsDetail.getMenu().getItem(0).setIcon(R.drawable.ic_collection_star);
                } else {
                    tbWordsDetail.getMenu().getItem(0).setIcon(R.drawable.ic_collection_unstar);
                }
            }

            @Override
            public void onFailure(Call<ArticleDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "requestArticleDetail获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestArticleDetailToken(String token, final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<ArticleDetail> call = request.getArticleDetail(token, id);

        call.enqueue(new Callback<ArticleDetail>() {
            @Override
            public void onResponse(Call<ArticleDetail> call, final Response<ArticleDetail> response) {
                initChildViews(response.body());
                flag1 = response.body().getData().isIsLike();
                if (flag1) {
                    tbWordsDetail.getMenu().getItem(1).setIcon(R.drawable.ic_like_heater);
                } else {
                    tbWordsDetail.getMenu().getItem(1).setIcon(R.drawable.ic_like_unheater);
                }
                flag2 = response.body().getData().isIsCollection();
                if (flag2) {
                    tbWordsDetail.getMenu().getItem(0).setIcon(R.drawable.ic_collection_star);
                } else {
                    tbWordsDetail.getMenu().getItem(0).setIcon(R.drawable.ic_collection_unstar);
                }
            }

            @Override
            public void onFailure(Call<ArticleDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "requestArticleDetail获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void initChildViews(ArticleDetail articleDetail) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        char[] chs = articleDetail.getData().getText().toCharArray();
        List<String> stringList = new ArrayList<>();
        StringBuffer tempStr = new StringBuffer();
        int j = 0;
        for (int i = 0; i < chs.length; i++) {
            if (chs[i] != ' ') {
                tempStr.append(chs[i]);
                j++;
                if (i == chs.length - 1) {
                    j = 0;
                    stringList.add(tempStr.toString());
                }
            } else {
                j = 0;
                stringList.add(tempStr.toString());
                tempStr = new StringBuffer();
            }
        }
        Log.d("12344567", stringList.toString());
        for (String mWord : stringList) {
            final TextView mTextView = new TextView(this);
            mTextView.setText(mWord);
            mTextView.setTextColor(Color.BLACK);
            // mTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview));
            mTextView.setTextSize(16);
            flWordDetail.addView(mTextView, lp);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestWordMeaning(mTextView.getText().toString());
                    popupWindow.showAtLocation(LayoutInflater.from(WordsDetailActivity.this).inflate(R.layout.activity_words_detail, null), Gravity.BOTTOM, 0, 0);
                }
            });
        }
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

    public void requestWordMeaning(final String word) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dict.youdao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<WordSuggestDetail> call = request.getWordSuggestDetailCall(word);

        call.enqueue(new Callback<WordSuggestDetail>() {
            @Override
            public void onResponse(Call<WordSuggestDetail> call, final Response<WordSuggestDetail> response) {
                showWordMeaning(response.body());
            }

            @Override
            public void onFailure(Call<WordSuggestDetail> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "requestWordMeaning获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showWordMeaning(WordSuggestDetail wordSuggestDetail) {
        if (wordSuggestDetail.getEc() == null) {
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

    public void requestGetLikeArticle(final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<MessageVerify> call = request.getLikeArticle(token, id);

        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, final Response<MessageVerify> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(WordsDetailActivity.this, "requestGetLikeArticle" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WordsDetailActivity.this, "requestGetLikeArticle" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestGetDislikeArticle(final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<MessageVerify> call = request.getDislikeArticle(token, id);

        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, final Response<MessageVerify> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(WordsDetailActivity.this, "requestGetDislikeArticle" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WordsDetailActivity.this, "requestGetDislikeArticle" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestGetAddCollection(final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<MessageVerify> call = request.getAddCollection(token, id);

        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, final Response<MessageVerify> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(WordsDetailActivity.this, "requestGetAddCollection" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WordsDetailActivity.this, "requestGetAddCollection" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestGetDelCollection(final int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<MessageVerify> call = request.getDelCollection(token, id);

        call.enqueue(new Callback<MessageVerify>() {
            @Override
            public void onResponse(Call<MessageVerify> call, final Response<MessageVerify> response) {
                if (response.body().getStatus() == 0) {
                    Toast.makeText(WordsDetailActivity.this, "requestGetDelCollection" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(WordsDetailActivity.this, "requestGetDelCollection" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageVerify> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(WordsDetailActivity.this, "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
