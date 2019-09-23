package com.example.lenovo.englishstudy;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.lenovo.englishstudy.fragment.ChatRoomFragment;
import com.example.lenovo.englishstudy.fragment.ChooseFragment;
import com.example.lenovo.englishstudy.fragment.HomeFragment;
import com.example.lenovo.englishstudy.fragment.SearchFragment;
import com.example.lenovo.englishstudy.fragment.UserFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {
    private BottomNavigationBar bottomNavigationBar;
    private HomeFragment homeFragment;
    private ChooseFragment chooseFragment;
    private ChatRoomFragment chatroomFragment;
    private UserFragment userFragment;
    private SearchFragment searchFragment;
    private static final String HOME_FRAGMENT_KEY = "homeFragment";
    private static final String CHOOSE_FRAGMENT_KEY = "chooseFragment";
    private static final String CHATROOM_FRAGMENT_KEY = "chatroomFragment";
    private static final String USER_FRAGMENT_KEY = "userFragment";
    private static final String SEARCH_FRAGMENT_KEY = "searchFragment";
    private int lastSelectedPosition = 0;


    //不保留活动
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        }

        bottomNavigationBar = findViewById(R.id.navigation_bar);

        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(this)
                .setMode(BottomNavigationBar.MODE_FIXED)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor("#11CDB5")//选中颜色
                .setInActiveColor("#7b7a7a") //未选中颜色
                .setBarBackgroundColor("#ffffff");//导航栏背景色

        /** 添加导航按钮 */
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home2, "首页").setInactiveIconResource(R.drawable.ic_home))
                .addItem(new BottomNavigationItem(R.drawable.ic_search2, "选词").setInactiveIconResource(R.drawable.ic_search))
                .addItem(new BottomNavigationItem(R.drawable.ic_search2, "搜索").setInactiveIconResource(R.drawable.ic_search))
                .addItem(new BottomNavigationItem(R.drawable.ic_chatroom2, "聊天室").setInactiveIconResource(R.drawable.ic_chatroom))
                .addItem(new BottomNavigationItem(R.drawable.ic_user2, "我的").setInactiveIconResource(R.drawable.ic_user))
                .setFirstSelectedPosition(0)
                .initialise(); //initialise 一定要放在 所有设置的最后一项

        init();
    }

    private void init() {
        homeFragment = new HomeFragment();
        chooseFragment = new ChooseFragment();
        chatroomFragment = new ChatRoomFragment();
        userFragment = new UserFragment();
        searchFragment = new SearchFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frameLayout, homeFragment).add(R.id.frameLayout, chooseFragment).add(R.id.frameLayout, searchFragment).add(R.id.frameLayout, chatroomFragment).add(R.id.frameLayout, userFragment);
        fragmentTransaction.hide(chooseFragment).hide(searchFragment).hide(chatroomFragment).hide(userFragment);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    /**
     * 设置导航选中的事件
     */
    @Override
    public void onTabSelected(int position) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                beginTransaction.hide(chooseFragment).hide(searchFragment).hide(chatroomFragment).hide(userFragment);
                beginTransaction.show(homeFragment);
                //beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case 1:
                beginTransaction.hide(homeFragment).hide(searchFragment).hide(chatroomFragment).hide(userFragment);
                beginTransaction.show(chooseFragment);
                //  beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case 2:
                beginTransaction.hide(homeFragment).hide(chooseFragment).hide(chatroomFragment).hide(userFragment);
                beginTransaction.show(searchFragment);
                // beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case 3:
                beginTransaction.hide(homeFragment).hide(chooseFragment).hide(searchFragment).hide(userFragment);
                beginTransaction.show(chatroomFragment);
                // beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
            case 4:
                beginTransaction.hide(homeFragment).hide(chooseFragment).hide(searchFragment).hide(chatroomFragment);
                beginTransaction.show(userFragment);
                // beginTransaction.addToBackStack(null);
                beginTransaction.commit();
                break;
        }
    }

    /**
     * 设置未选中Fragment 事务
     */
    @Override
    public void onTabUnselected(int position) {

    }

    /**
     * 设置释放Fragment 事务
     */
    @Override
    public void onTabReselected(int position) {

    }

    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            //   MyConfig.clearSharePre(this, "users");
            finish();
            System.exit(0);
        }
    }


}
