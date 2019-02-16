package com.example.lenovo.englishstudy.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.pullextend.PullToRefreshLinearLayout;
import com.example.lenovo.englishstudy.pullextend.VerticalScrollView;
import com.example.lenovo.englishstudy.viewPageCard.CardItem;
import com.example.lenovo.englishstudy.viewPageCard.CardPagerAdapter;
import com.example.lenovo.englishstudy.viewPageCard.ShadowTransformer;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {
    private ViewPager mViewPager;

    private Activity activity;

    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;

    @BindView(R.id.lin_refresh)
    PullToRefreshLinearLayout linRefresh;
    Handler handler = new Handler();

    @BindView(R.id.sv_scrollView)
    VerticalScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment, container, false);
        ButterKnife.bind(view);
        scrollView = view.findViewById(R.id.sv_scrollView);
        mViewPager = view.findViewById(R.id.vp_WordCard);
        linRefresh = view.findViewById(R.id.lin_refresh);
        init();
        initEvent();
        return view;
    }

    private void init() {
        activity = getActivity();
        mCardAdapter = new CardPagerAdapter(activity);
        mCardAdapter.addCardItem(new CardItem(R.string.title_1, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_2, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_3, R.string.text_1));
        mCardAdapter.addCardItem(new CardItem(R.string.title_4, R.string.text_1));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private void initEvent(){
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
                },2000);
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
                },2000);
            }
        });
    }
}
