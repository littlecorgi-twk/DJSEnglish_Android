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
import com.example.lenovo.englishstudy.bean.SexagenaryCycle;
import com.example.lenovo.englishstudy.pullextend.PullToRefreshLinearLayout;
import com.example.lenovo.englishstudy.viewPageCard.CardItem;
import com.example.lenovo.englishstudy.viewPageCard.CardPagerAdapter;
import com.example.lenovo.englishstudy.viewPageCard.ShadowTransformer;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
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
    private String sexagenaryCycleYear;
    private String lunarCalendar;
    SexagenaryCycle mSexagenaryCycle;

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
                        requestArticleList();
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
                        requestArticleList();
                    }
                }, 2000);
            }
        });
    }

    public void requestArticleList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Observable<ArticleList> observable = request.getArticleList();

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleList articleList) {
                        showArticleList(articleList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(), "获取单词联想失败1", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void showArticleList(ArticleList articleList) {
        if (articleList != null) {
            activity = getActivity();
            mCardAdapter = new CardPagerAdapter(activity);
            for (int i = 0; i < articleList.getData().getTotal(); i++) {
                mCardAdapter.addCardItem(new CardItem(articleList.getData().getList().get(i)));
            }
            mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
            mCardShadowTransformer.enableScaling(true);
            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
            mViewPager.setOffscreenPageLimit(3);
        }
    }
}
