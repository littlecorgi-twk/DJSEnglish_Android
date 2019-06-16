package com.example.lenovo.englishstudy.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.userdefined.MyView;

public class SettingActivity extends AppCompatActivity implements MyView.OnRootClickListener{
    private LinearLayout oneItem;
    private LinearLayout exitLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        oneItem = findViewById(R.id.setting_item);
        oneItem.addView(new MyView(this).initMine("账户安全", "", true)
                .setOnRootClickListener(this, 1));
        oneItem.addView(new MyView(this).initMine("清理缓存", "", true)
                .setOnRootClickListener(this, 2));
        oneItem.addView(new MyView(this).initMine("关于我们", "", true)
                .setOnRootClickListener(this, 3));
        oneItem.addView(new MyView(this).initMine("去给好评", "", false)
                .setOnRootClickListener(this, 4));
        //退出登录
        exitLayout = findViewById(R.id.exit);
        exitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("data1", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("user_name", "null");
                editor.putString("user_photo", "");
                editor.commit();
                finish();
                Toast.makeText(SettingActivity.this, "您已退出登录", Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("data1", Context.MODE_PRIVATE);
        final String user_name = sharedPreferences.getString("user_name", "");
        final String user_photo = sharedPreferences.getString("user_photo", "");
        if(user_name.equals("null") && user_photo.equals("")) {
            exitLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRootClick(View view) {
        switch ((int) view.getTag()) {
            case 1:
                break;
            case 2:
                break;
        }
    }
}
