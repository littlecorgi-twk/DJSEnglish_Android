package com.example.lenovo.englishstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.adapter.FragmentAdpter;
import com.example.lenovo.englishstudy.fragment.ListFragment;
import com.example.lenovo.englishstudy.userdefined.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

public class UserActivity extends AppCompatActivity {
    private ObservableScrollView mObservableScrollView;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView back_button;
    private View division;
    private CircleImageView u_photo;
    private TextView u_name, t_name;
    private Button edit_button;
    private String user_name;
    private String user_photo;
    private final int REFRESH_CODE = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_user);
        mViewPager = findViewById(R.id.user_viewpager);
        mObservableScrollView = findViewById(R.id.o_scrollView);
        mObservableScrollView.fullScroll(ScrollView.FOCUS_UP);
        edit_button = findViewById(R.id.edit);
        back_button = findViewById(R.id.u_back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        u_name = findViewById(R.id.u_name);
        u_photo = findViewById(R.id.u_photo);
        t_name = findViewById(R.id.t_name);
        t_name.setVisibility(View.GONE);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        user_name = sharedPreferences.getString("user_name", "");
        user_photo = sharedPreferences.getString("user_photo", "");
        u_name.setText(user_name);
        t_name.setText(user_name);
        Glide.with(getContext()).load(user_photo).into(u_photo);
        division = findViewById(R.id.division);
        division.setVisibility(View.GONE);
        mToolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
        initViewPager();
    }

    private void initView() {
        final float title_height = getResources().getDimension(R.dimen.title_height);
        final float head_height = getResources().getDimension(R.dimen.head_height);
        mObservableScrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView observableScrollView, int oldy, int dy, boolean isUp) {
                float move_distance = 280;
                if (!isUp && dy <= move_distance) {
                    mToolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_white));
                    TitleAlphaChange(dy, move_distance);

                } else if (!isUp && dy > move_distance) {
                    TitleAlphaChange(1, 1);
                    back_button.setImageResource(R.drawable.u_back);
                    t_name.setVisibility(View.VISIBLE);
                    division.setVisibility(View.VISIBLE);
                } else if (isUp && dy > move_distance) {//返回顶部
                    //  toolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_background));
                    // t_name.setVisibility(View.GONE);
                    // back_button.setImageResource(R.drawable.u_back_w);
                } else if (isUp && dy <= move_distance) {
                    TitleAlphaChange(dy, move_distance);
                    back_button.setImageResource(R.drawable.u_back_w);
                    t_name.setVisibility(View.GONE);
                    division.setVisibility(View.GONE);
                    //  toolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_background));
                }

            }
        });
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MessageActivity.class);
                intent.putExtra("u_name", user_name);
                intent.putExtra("u_photo", user_photo);
                startActivityForResult(intent, REFRESH_CODE);
            }
        });
    }


    private void initViewPager() {
        mTabLayout = findViewById(R.id.user_tab);
        List<String> titles = new ArrayList<>();
        titles.add("我的动态");
        titles.add("我的评论");
        for(int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }
        List<Fragment> fragments = new ArrayList<>();
        for(int i = 0; i < titles.size(); i++) {
            fragments.add(new ListFragment());
        }
        FragmentAdpter fragmentAdpter = new FragmentAdpter(getSupportFragmentManager(), fragments, titles);
        //把tablayout和viewpager关联起来
        mTabLayout.setupWithViewPager(mViewPager);
        //给viewpager设置适配器
        mViewPager.setAdapter(fragmentAdpter);
//        //给tablayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(fragmentAdpter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    private void TitleAlphaChange(int dy, float mHeaderHeight) {
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight);
        int alpha = (int) (percent * 255);
        mToolbar.getBackground().setAlpha(alpha);
    }

    private void HeaderTranslate(float distance) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REFRESH_CODE:
                    String name = data.getStringExtra("user_name");
                    user_photo = data.getStringExtra("user_photo");
                    u_name.setText(name);
                    t_name.setText(name);
                    Glide.with(getContext()).load(user_photo).into(u_photo);
            }
        }

    }
}