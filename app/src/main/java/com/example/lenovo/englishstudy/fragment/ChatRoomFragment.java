package com.example.lenovo.englishstudy.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.activity.ClientActivity;
import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.adapter.ChatListAdapter;
import com.example.lenovo.englishstudy.bean.FriendList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatRoomFragment extends Fragment {

    private ChatListAdapter chatListAdapter;
    private List<FriendList.DataBean> mFriendList = new ArrayList<>();

    @BindView(R.id.lv_chatRoom_chatList)
    ListView lvChatRoomChatList;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatroomfragment, container, false);

        unbinder = ButterKnife.bind(this, view);
//        requestFriendList();
        mFriendList.add(new FriendList.DataBean(1, "123", "ads"));
        mFriendList.add(new FriendList.DataBean(2, "234", "ads"));
        mFriendList.add(new FriendList.DataBean(3, "345", "ads"));
        mFriendList.add(new FriendList.DataBean(4, "456", "ads"));
        mFriendList.add(new FriendList.DataBean(5, "567", "ads"));
        mFriendList.add(new FriendList.DataBean(6, "678", "ads"));
        mFriendList.add(new FriendList.DataBean(7, "789", "ads"));
        mFriendList.add(new FriendList.DataBean(8, "890", "ads"));
        chatListAdapter = new ChatListAdapter(mFriendList, getContext());
        lvChatRoomChatList.setAdapter(chatListAdapter);
        lvChatRoomChatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ClientActivity.class);
//                intent.putExtra("id", mFriendList.get(position).getId());
                startActivity(intent);
            }
        });
        return view;
    }

    public void requestFriendList() {
        SharedPreferences sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences("user_token", Activity.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.206.19:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        Call<FriendList> call = request.getFriendList(token);

        call.enqueue(new Callback<FriendList>() {
            @Override
            public void onResponse(Call<FriendList> call, final Response<FriendList> response) {
                if (response.body().getStatus() == 0) {
                    mFriendList = response.body().getData();
                    showFriendList(mFriendList);
                } else if (response.body().getMsg().equals("好友数量为零")) {
                    mFriendList.clear();
                    mFriendList.add(new FriendList.DataBean(0, "好友数量为零", "null"));
                    showFriendList(mFriendList);
                } else {
                    Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<FriendList> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getContext(), "获取单词联想失败1", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showFriendList(List<FriendList.DataBean> mFriendList) {
        chatListAdapter = new ChatListAdapter(mFriendList, getContext());
        lvChatRoomChatList.setAdapter(chatListAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
