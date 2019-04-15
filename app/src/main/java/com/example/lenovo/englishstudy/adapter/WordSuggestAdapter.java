package com.example.lenovo.englishstudy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.bean.WordSuggest;
import com.example.lenovo.englishstudy.searchHistory.OnSearchHistoryListener;
import com.example.lenovo.englishstudy.searchHistory.SearchHistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author littlecorgi
 * @Date 2019-04-01 21:20
 * @email a1203991686@126.com
 */
public class WordSuggestAdapter extends ArrayAdapter<WordSuggest.DataBean.EntriesBean> {
    private int resoureId;
    private OnSearchHistoryListener onSearchHistoryListener;
    private ArrayList<SearchHistoryModel> mHistories;

//    public WordSuggestAdapter(Context context, int textViewResourceId, List<WordSuggest.DataBean.EntriesBean> objects,  ArrayList<SearchHistoryModel> histories) {
//        super(context, textViewResourceId, objects);
//        resoureId = textViewResourceId;
//        this.mHistories = histories;
//    }


    public WordSuggestAdapter(@NonNull Context context, int textViewResourceId, @NonNull List<WordSuggest.DataBean.EntriesBean> objects) {
        super(context, textViewResourceId, objects);
        this.resoureId = textViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        WordSuggest.DataBean.EntriesBean entriesBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resoureId, parent, false);
        TextView mTextView_WordContent = view.findViewById(R.id.tv_wordDetail_content);
        TextView mTextView_WordMeaning = view.findViewById(R.id.tv_wordDetail_meaning);
        Button mButtton_delete = view.findViewById(R.id.button_word_suggest_listview_clear_history);
        mTextView_WordContent.setText(entriesBean.getEntry());
        mTextView_WordMeaning.setText(entriesBean.getExplain());
        mButtton_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSearchHistoryListener != null) {
                    onSearchHistoryListener.onDelete(mHistories.get(position).getTime());
                }
            }
        });
        return view;
    }
}
