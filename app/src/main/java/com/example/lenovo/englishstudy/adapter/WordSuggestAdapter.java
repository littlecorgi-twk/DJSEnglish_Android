package com.example.lenovo.englishstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.bean.WordSuggest;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2019-04-01 21:20
 * @email a1203991686@126.com
 */
public class WordSuggestAdapter extends ArrayAdapter<WordSuggest.DataBean.EntriesBean> {
    private int resoureId;

    public WordSuggestAdapter(Context context, int textViewResourceId, List<WordSuggest.DataBean.EntriesBean> objects) {
        super(context, textViewResourceId, objects);
        resoureId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordSuggest.DataBean.EntriesBean entriesBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resoureId, parent, false);
        TextView mTextView_WordContent = view.findViewById(R.id.tv_wordDetail_content);
        TextView mTextView_WordMeaning = view.findViewById(R.id.tv_wordDetail_meaning);
        mTextView_WordContent.setText(entriesBean.getEntry());
        mTextView_WordMeaning.setText(entriesBean.getExplain());
        return view;
    }
}
