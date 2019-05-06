package com.example.lenovo.englishstudy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.bean.FriendList;

import java.util.List;

public class ChatListAdapter extends BaseAdapter {

    private List<FriendList.DataBean> mFriendList;
    private Context mContext;

    public ChatListAdapter(List<FriendList.DataBean> mFriendList, Context mContext) {
        this.mFriendList = mFriendList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mFriendList.size();
    }

    @Override
    public Object getItem(int position) {
        return mFriendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ChatListAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.chat_list, null);
            viewHolder.mTextView_Name = convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChatListAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView_Name.setText(mFriendList.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        TextView mTextView_Name;
    }
}
