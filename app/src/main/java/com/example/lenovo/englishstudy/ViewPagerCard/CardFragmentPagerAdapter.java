package com.example.lenovo.englishstudy.ViewPagerCard;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author littlecorgi
 * @Date 2019-01-24 17:34
 * @email a1203991686@126.com
 */
public class CardFragmentPagerAdapter extends FragmentStatePagerAdapter implements  CardAdapter{
    private List<CardFragment> mFragments;
    private float mBaseElevation;

    public CardFragmentPagerAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        this.mFragments = new ArrayList<>();
        this.mBaseElevation = baseElevation;

        for (int i = 0; i < 5; i++) {
            addCardFragment(new CardFragment());
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (CardFragment)fragment);
        return fragment;
    }

    public void addCardFragment(CardFragment fragment) {
        mFragments.add(fragment);
    }
}
