package com.example.lenovo.englishstudy.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.activity.LoginActivity;
import com.example.lenovo.englishstudy.activity.SettingActivity;
import com.example.lenovo.englishstudy.activity.UserActivity;
import com.example.lenovo.englishstudy.userdefined.MyView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class UserFragment extends Fragment implements MyView.OnRootClickListener {
    private LinearLayout oneItem;
    private LinearLayout twoItem;
    private LinearLayout threeItem;
    private LinearLayout log;
    private TextView login, login_msg;
    private CircleImageView photo;
    private Boolean iflogin = FALSE;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.userfragment, container, false);
        oneItem = view.findViewById(R.id.u_one_item);
        twoItem = view.findViewById(R.id.u_two_item);
        threeItem = view.findViewById(R.id.u_three_item);
        login = view.findViewById(R.id.login);
        login_msg = view.findViewById(R.id.login_msg);
        photo = view.findViewById(R.id.photo);
        log = view.findViewById(R.id.log);
//        SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences("data1", Context.MODE_PRIVATE);
//        String imageUrl1 = sharedPreferences3.getString("user_photo", "");
//        String name1 = sharedPreferences3.getString("user_name", "");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        final String user_name = sharedPreferences.getString("user_name", "");
        final String user_photo = sharedPreferences.getString("user_photo", "");

        if (!user_name.equals("") && !user_photo.equals("")) {
            login.setText(user_name);
            login_msg.setText("点击查看个人主页");
            Glide.with(getContext()).load(user_photo).into(photo);
            iflogin = TRUE;
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!iflogin) {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), 1);
                } else {
//                    startActivityForResult(new Intent(getContext(), LoginActivity.class), 1);
                    Intent intent = new Intent(getActivity(), UserActivity.class);
//                    intent.putExtra("u_name", user_name);
//                   intent.putExtra("u_photo", user_photo);
                    startActivity(intent);
                }

            }
        });
        initView();
        return view;
    }

    //Fragment由不可见变为可见
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString("user_photo", "");
        String name = sharedPreferences.getString("user_name", "");

        if (!name.equals("")) {
            Log.d("12345", name);
            login.setText(name);
            login_msg.setText("点击查看个人主页");
            iflogin = true;
        }
        if (!imageUrl.equals("")) {
            Glide.with(getContext()).load(imageUrl).into(photo);
        }
        if(imageUrl.equals("") && name.equals("")) {
            //把资源文件中的图片转换成bitmap
            Resources resources = getContext().getResources();
            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_photo);
            photo.setImageBitmap(bitmap);

            login.setText("未登录");
            login_msg.setText("登录收藏喜欢的美文");
            Log.d("343434","2");
            iflogin = false;
        }

//        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if(!name.equals("")) {
//            editor.putString("user_name", name);
//        }
//        if(!imageUrl.equals("")) {
//            editor.putString("user_photo", imageUrl);
//        }
//        editor.commit();

//
//        SharedPreferences sharedPreferences3 = getActivity().getSharedPreferences("data1", Context.MODE_PRIVATE);
//        String imageUrl1 = sharedPreferences3.getString("user_photo", "");
//        String name1 = sharedPreferences3.getString("user_name", "");
//        if(imageUrl1.equals("") && name1.equals("null")) {
//            //把资源文件中的图片转换成bitmap
//            Resources resources = getContext().getResources();
//            Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_photo);
//            photo.setImageBitmap(bitmap);
//
//            login.setText("未登录");
//            login_msg.setText("登录收藏喜欢的美文");
//            Log.d("343434","2");
//            iflogin = false;
//        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    login.setText(data.getStringExtra("user_name"));
                    login_msg.setText("点击查看个人主页");
                    Glide.with(getContext()).load(data.getStringExtra("user_photo")).into(photo);
                    iflogin = TRUE;

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("user_name", data.getStringExtra("user_name"));
                    editor.putString("user_photo", data.getStringExtra("user_photo"));
                    editor.commit();
                }
                break;
            default:
        }

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
            case 6:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
        }

    }

}