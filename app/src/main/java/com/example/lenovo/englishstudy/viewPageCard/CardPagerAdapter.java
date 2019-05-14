package com.example.lenovo.englishstudy.viewPageCard;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.WordsDetailActivity;

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
                .load(item.getmArticle().getImg())
                .into(imageView);
        final Button button = view.findViewById(R.id.button_adapter_collection);
        if (item.getmArticle().isIsCollection()) {
            button.setBackgroundResource(R.drawable.ic_bookmark_24dp);
        } else {
            button.setBackgroundResource(R.drawable.ic_bookmart_un_24dp);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag = item.getmArticle().isIsCollection();
                // 判断当前flag是点赞还是取消赞,是的话就给bean值减1，否则就加1
                if (flag) {
                    item.getmArticle().setIsCollection(false);
                    Toast.makeText(context, "取消收藏", Toast.LENGTH_SHORT).show();
                    AnimationTools.scale(button);
                    button.setBackgroundResource(R.drawable.ic_bookmart_un_24dp);
                } else {
                    item.getmArticle().setIsCollection(true);
                    Toast.makeText(context, "收藏", Toast.LENGTH_SHORT).show();
                    AnimationTools.scale(button);
                    button.setBackgroundResource(R.drawable.ic_bookmark_24dp);
                }
                notifyDataSetChanged();
            }
        });
    }

}
