package com.example.lenovo.englishstudy.viewPageCard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.WordsDetailActivity;
import com.example.lenovo.englishstudy.bean.SexagenaryCycle;
import com.example.lenovo.englishstudy.view.ColorText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author littlecorgi
 * @Date 2019-01-24 18:15
 * @email a1203991686@126.com
 */
public class CardPagerAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;
    private Context context;
    private CardView cardView;
    private String sexagenaryCycleYear;
    private String lunarCalendar;
    private SexagenaryCycle mSexagenaryCycle;

    public CardPagerAdapter(Context context) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.context = context;
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.adapter, container, false);
        container.addView(view);
        bind(mData.get(position), view);
        cardView = view.findViewById(R.id.cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WordsDetailActivity.class);
                intent.putExtra("ArticleNumber", mData.get(position).getmArticle().getId());
                context.startActivity(intent);
            }
        });

        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, cardView);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }

    private void bind(final CardItem item, View view) {
        final TextView contentTextView = view.findViewById(R.id.contentTextView);
        ImageView imageView = view.findViewById(R.id.iv_adapter_background);
        contentTextView.setText(item.getmArticle().getText());
        Glide.with(context)
                // .load(item.getmArticle().getImg())
                .load("https://i0.hdslb.com/bfs/album/9e3b2580c20decba17a1065aa8f66baf11bc2032.jpg")
                .into(imageView);
        ColorText colorText = view.findViewById(R.id.colorText_adapter);
        colorText.setText(item.getmArticle().getUpdateTime().substring(8, 10));
        TextView tv_sexagenary_cycle_year = view.findViewById(R.id.tv_sexagenary_cycle_year);
        int year = Integer.parseInt(item.getmArticle().getUpdateTime().substring(0, 4));
        String resultYear = TianGanDiZhiShengXiao.getTianGanName(year) + TianGanDiZhiShengXiao.getDiZhiName(year) + TianGanDiZhiShengXiao.getAnimalYearName(year) + "年";
        tv_sexagenary_cycle_year.setText(resultYear);
        TextView tv_lunar_calendar = view.findViewById(R.id.tv_lunar_calendar);
        String data = item.getmArticle().getUpdateTime().substring(0, 10);
        data = data.replace("-", "");
        try {
            tv_lunar_calendar.setText(LunarCalendar.INSTANCE.solarToLunar(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
