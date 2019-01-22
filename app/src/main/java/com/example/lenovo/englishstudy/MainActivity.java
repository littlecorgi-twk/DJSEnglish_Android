package com.example.lenovo.englishstudy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lenovo.englishstudy.fragment.ChatroomFragment;
import com.example.lenovo.englishstudy.fragment.HomeFragment;
import com.example.lenovo.englishstudy.fragment.SearchFragment;
import com.example.lenovo.englishstudy.fragment.UserFragment;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private ChatroomFragment chatroomFragment;
    private UserFragment userFragment;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showNav(R.id.navigation_home);
                    return true;
                case R.id.navigation_search:
                    showNav(R.id.navigation_search);
                    return true;
                case R.id.navigation_chatroom:
                    showNav(R.id.navigation_chatroom);
                    return true;
                case R.id.navigation_user:
                    showNav(R.id.navigation_user);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void init(){
        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        chatroomFragment = new ChatroomFragment();
        userFragment = new UserFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameLayout,homeFragment).add(R.id.frameLayout,searchFragment).add(R.id.frameLayout,chatroomFragment).add(R.id.frameLayout,userFragment);
        fragmentTransaction.hide(homeFragment).hide(searchFragment).hide(chatroomFragment).hide(userFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showNav(int navid){
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        switch (navid){
            case R.id.navigation_home:
                beginTransaction.hide(searchFragment).hide(chatroomFragment).hide(userFragment);
                beginTransaction.show(homeFragment);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_search:
                beginTransaction.hide(homeFragment).hide(chatroomFragment).hide(userFragment);
                beginTransaction.show(searchFragment);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_chatroom:
                beginTransaction.hide(homeFragment).hide(searchFragment).hide(userFragment);
                beginTransaction.show(chatroomFragment);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case R.id.navigation_user:
                beginTransaction.hide(homeFragment).hide(searchFragment).hide(chatroomFragment);
                beginTransaction.show(userFragment);
                beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
        }
    }

}
