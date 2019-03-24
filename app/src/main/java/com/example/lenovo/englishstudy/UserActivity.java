package com.example.lenovo.englishstudy;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.lenovo.englishstudy.userdefined.ObservableScrollView;

import java.lang.invoke.ConstantCallSite;

public class UserActivity extends AppCompatActivity {
    private ObservableScrollView observableScrollView;
    private Toolbar toolbar;
    private ImageView back_button;
    private View division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final float title_height = getResources().getDimension(R.dimen.title_height);
        final float head_height = getResources().getDimension(R.dimen.head_height);
        back_button = findViewById(android.R.id.home);
        division = findViewById(R.id.division);
        division.setVisibility(View.GONE);
        toolbar = findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar =  getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        observableScrollView = findViewById(R.id.scrollView);
        observableScrollView.setOnScrollListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScroll(int oldy, int dy, boolean isUp) {
                float move_distance = head_height - title_height;
                if(!isUp && dy <= move_distance) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(UserActivity.this, R.color.color_white));
                    TitleAlphaChange(dy, move_distance);

                } else if(!isUp && dy > move_distance) {
                    TitleAlphaChange(1, 1);
                    back_button.setColorFilter(R.color.color_black);
                    division.setVisibility(View.VISIBLE);
                } else if(isUp && dy > move_distance) { //返回顶部
                    //不做处理
                } else if(isUp && dy <= move_distance) {
                    TitleAlphaChange(dy, move_distance);
                    back_button.setColorFilter(R.color.color_white);
                    division.setVisibility(View.GONE);
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
        back_button.getBackground().setAlpha(255 - alpha);
    }

    private void HeaderTranslate(float distance) {

    }
}
