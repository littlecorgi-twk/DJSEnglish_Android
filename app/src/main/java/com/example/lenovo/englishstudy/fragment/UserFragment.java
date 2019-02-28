package com.example.lenovo.englishstudy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lenovo.englishstudy.LoginActivity;
import com.example.lenovo.englishstudy.userdefined.MyView;
import com.example.lenovo.englishstudy.R;

public class UserFragment extends Fragment implements MyView.OnRootClickListener {
    private LinearLayout oneItem;
    private LinearLayout twoItem;
    private LinearLayout threeItem;
    private LinearLayout log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userfragment, container, false);
        oneItem = view.findViewById(R.id.one_item);
        twoItem = view.findViewById(R.id.two_item);
        threeItem = view.findViewById(R.id.three_item);
        log = view.findViewById(R.id.log);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        initView();
        return view;
    }

    public void initView() {
        //添加我的消息
        oneItem.addView(new MyView(getContext())
                .initMine(R.drawable.ic_message, "我的消息", true)
                .setOnRootClickListener(this, 1));
        //添加我的收藏
        oneItem.addView(new MyView(getContext())
                .initMine(R.drawable.ic_collect, "我的收藏夹", true)
                .setOnRootClickListener(this, 2));
        //添加我的评论
        oneItem.addView(new MyView(getContext())
                .initMine(R.drawable.ic_comment, "我的评论", false)
                .setOnRootClickListener(this, 3));

        //添加帮助反馈
        twoItem.addView(new MyView(getContext())
                .initMine(R.drawable.ic_help, "帮助反馈", true)
                .setOnRootClickListener(this, 4));

        //添加推荐美文
        twoItem.addView(new MyView(getContext())
                .initMine(R.drawable.ic_recommend, "推荐美文", false)
                .setOnRootClickListener(this, 5));

        //添加设置
        threeItem.addView(new MyView(getContext())
                .initMine(R.drawable.ic_set, "设置", false)
                .setOnRootClickListener(this, 6));

    }

    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                //   startActivity(new Intent(getContext(), MyDownloadActivity.class));
                break;
            case 2:
                //    startActivity(new Intent(getContext(), MyCollectionActivity.class));
                break;
        }


    }
}
