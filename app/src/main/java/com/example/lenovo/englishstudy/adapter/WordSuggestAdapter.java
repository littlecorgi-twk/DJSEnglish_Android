package com.example.lenovo.englishstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.bean.WordSuggest;
import com.example.lenovo.englishstudy.searchHistory.OnSearchHistoryListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author littlecorgi
 * @Date 2019-04-01 21:20
 * @email a1203991686@126.com
 */
public class WordSuggestAdapter extends BaseAdapter {

    private Context mContext;
    private OnSearchHistoryListener onSearchHistoryListener;
    private List<WordSuggest.DataBean.EntriesBean> mList = new ArrayList<>();
    private boolean flag; // flag为true代表搜索结果，false代表历史

    public WordSuggestAdapter(Context mContext, List<WordSuggest.DataBean.EntriesBean> mList, boolean flag) {
        this.mContext = mContext;
        this.mList = mList;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.word_suggest_listview, null);
            viewHolder.mTextView_WordContent = convertView.findViewById(R.id.tv_wordDetail_content);
            viewHolder.mTextView_WordMeaning = convertView.findViewById(R.id.tv_wordDetail_meaning);
            viewHolder.mButtton_delete = convertView.findViewById(R.id.button_word_suggest_listview_clear_history);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView_WordContent.setText(mList.get(position).getEntry());
        viewHolder.mTextView_WordMeaning.setText(mList.get(position).getExplain());
        if (flag) {
            viewHolder.mButtton_delete.setVisibility(View.GONE);
        } else {
            viewHolder.mButtton_delete.setVisibility(View.VISIBLE);
            viewHolder.mButtton_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onSearchHistoryListener != null) {
                        mOnItemDeleteListener.onDeleteClick(position);
                    }
                }
            });
        }
        return convertView;
    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }

    class ViewHolder {
        TextView mTextView_WordContent;
        TextView mTextView_WordMeaning;
        Button mButtton_delete;
    }

}
