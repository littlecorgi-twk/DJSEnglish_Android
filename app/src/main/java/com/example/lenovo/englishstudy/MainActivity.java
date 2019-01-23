package com.example.lenovo.englishstudy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lenovo.englishstudy.fragment.ChatroomFragment;
import com.example.lenovo.englishstudy.fragment.HomeFragment;
import com.example.lenovo.englishstudy.fragment.SearchFragment;
import com.example.lenovo.englishstudy.fragment.UserFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);

        //设置点击监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    //根据navagatin.xml中item的id进行case
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        //跳到对应ViewPager的page
                        break;
                    case R.id.navigation_search:
                        viewPager.setCurrentItem(1);

                        break;
                    case R.id.navigation_chatroom:
                        viewPager.setCurrentItem(2);

                        break;
                    case R.id.navigation_user:
                        viewPager.setCurrentItem(3);
                }
                return false;
            }
        });

        //底部导航栏有几项就有几个Fragment
        final ArrayList<Fragment> fragments = new ArrayList<>(4);
        fragments.add(new HomeFragment());
        fragments.add(new SearchFragment());
        fragments.add(new ChatroomFragment());
        fragments.add(new UserFragment());

        FragmentStatePagerAdapter mPagerAdapter=new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);  //得到Fragment
            }

            @Override
            public int getCount() {
                return fragments.size();  //得到数量
            }
        };
        viewPager.setAdapter(mPagerAdapter);   //设置适配器
        viewPager.setOffscreenPageLimit(3); //预加载剩下三页

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

}
