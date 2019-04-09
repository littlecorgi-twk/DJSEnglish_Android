package com.example.lenovo.englishstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.userdefined.MyView;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

public class MessageActivity extends AppCompatActivity {
    private LinearLayout oneItem;
    private String user_name;
    private String user_photo;
    private CircleImageView u_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        oneItem = findViewById(R.id.m_one_item);
        initView();
    }

    public void initView() {
        user_name = getIntent().getStringExtra("u_name");
        user_photo = getIntent().getStringExtra("u_photo");
        oneItem.addView(new MyView(this)
                .initMine2("头像", user_photo, true));
        oneItem.addView(new MyView(this)
                .initMine("昵称", user_name, true));
        oneItem.addView(new MyView(this)
                .initMine("性别", "女", true));
        oneItem.addView(new MyView(this)
                .initMine("生日", "", true));

    }
}
