package com.example.lenovo.englishstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.lenovo.englishstudy.userdefined.MyView;

public class SettingActivity extends AppCompatActivity implements MyView.OnRootClickListener{
    private LinearLayout oneItem;
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
