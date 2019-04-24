package com.example.lenovo.englishstudy.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.ArticleList;
import com.example.lenovo.englishstudy.pullextend.PullToRefreshLinearLayout;
import com.example.lenovo.englishstudy.viewPageCard.CardItem;
import com.example.lenovo.englishstudy.viewPageCard.CardPagerAdapter;
import com.example.lenovo.englishstudy.viewPageCard.ShadowTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private ViewPager mViewPager;

    private Activity activity;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @BindView(R.id.lin_refresh)
    PullToRefreshLinearLayout linRefresh;
    Handler handler = new Handler();

    @BindView(R.id.sv_scrollView)
    ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment, container, false);
        ButterKnife.bind(view);
        scrollView = view.findViewById(R.id.sv_scrollView);
        mViewPager = view.findViewById(R.id.vp_WordCard);
        linRefresh = view.findViewById(R.id.lin_refresh);
        requestArticleList();
        initEvent();


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initEvent() {
        // linRefresh.hideFooterView();
        linRefresh.setOnHeaderRefreshListener(new PullToRefreshLinearLayout.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshLinearLayout view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linRefresh.onHeaderRefreshComplete();
                        // linRefresh.showFooterView();
                    }
                }, 2000);
            }
        });

        linRefresh.setOnFooterRefreshListener(new PullToRefreshLinearLayout.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshLinearLayout view) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linRefresh.onFooterRefreshComplete();
                    }
                }, 2000);
            }
        });
    }

    public void requestArticleList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<ArticleList> call = request.getArticleList();

        call.enqueue(new Callback<ArticleList>() {
            @Override
            public void onResponse(Call<ArticleList> call, final Response<ArticleList> response) {
                showArticleList(response.body());
            }

            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showArticleList(ArticleList articleList) {
        activity = getActivity();
        mCardAdapter = new CardPagerAdapter(activity);
        for (int i = 0; i < articleList.getData().getTotal(); i++) {
            mCardAdapter.addCardItem(new CardItem(articleList.getData().getList().get(i).getText(), articleList.getData().getList().get(i).getImg()));
        }
        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }
}
