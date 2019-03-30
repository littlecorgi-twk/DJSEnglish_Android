package com.example.lenovo.englishstudy;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.englishstudy.userdefined.ObservableScrollView;

import java.lang.invoke.ConstantCallSite;

import de.hdodenhof.circleimageview.CircleImageView;

import static org.litepal.LitePalApplication.getContext;

public class UserActivity extends AppCompatActivity {
    private ObservableScrollView observableScrollView;
    private Toolbar toolbar;
    private ImageView back_button;
    private View division;
    private CircleImageView u_photo;
    private TextView u_name,t_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        observableScrollView = findViewById(R.id.o_scrollView);
        observableScrollView.fullScroll(ScrollView.FOCUS_UP);
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
        String user_name = getIntent().getStringExtra("u_name");
        u_name.setText(user_name);
        t_name.setText(user_name);
        String user_photo = getIntent().getStringExtra("u_photo");
        Glide.with(getContext()).load(user_photo).into(u_photo);
//        back_button = findViewById(android.R.id.home);
        division = findViewById(R.id.division);
        division.setVisibility(View.GONE);
        toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }

    private void initView() {
        Log.d("3333","1");
        final float title_height = getResources().getDimension(R.dimen.title_height);
        final float head_height = getResources().getDimension(R.dimen.head_height);
        observableScrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView observableScrollView, int oldy, int dy, boolean isUp) {
                Log.d("4444444","3");
                float move_distance = head_height - title_height;
                if(!isUp && dy <= move_distance) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_white));
                    TitleAlphaChange(dy, move_distance);

                } else if(!isUp && dy > move_distance) {
                    TitleAlphaChange(1, 1);
                    back_button.setImageResource(R.drawable.u_back);
                    t_name.setVisibility(View.VISIBLE);
                    division.setVisibility(View.VISIBLE);
                } else if(isUp && dy > move_distance) {//返回顶部
                  //  toolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_background));
                   // t_name.setVisibility(View.GONE);
                   // back_button.setImageResource(R.drawable.u_back_w);
                } else if(isUp && dy <= move_distance) {
                    TitleAlphaChange(dy, move_distance);
                    back_button.setImageResource(R.drawable.u_back_w);
                    t_name.setVisibility(View.GONE);
                    division.setVisibility(View.GONE);
                  //  toolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_background));
                }

            }
        });

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
        float percent = (float) Math.abs(dy) / Math.abs(mHeaderHeight) ;
        int alpha = (int) (percent * 255);
        toolbar.getBackground().setAlpha(alpha);
    }

    private void HeaderTranslate(float distance) {

    }
}
