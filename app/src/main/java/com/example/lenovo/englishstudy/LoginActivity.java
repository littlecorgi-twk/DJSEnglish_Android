package com.example.lenovo.englishstudy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.englishstudy.fragment.UserFragment;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class LoginActivity extends AppCompatActivity {
    private ImageView cancel;
    private ImageView qq;
    private Tencent mTencent;
//    private UserFragment userFragment = new UserFragment();
    private String user_name = "null" ,user_photo = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        cancel = findViewById(R.id.cancel);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTencent = Tencent.createInstance("1108246406", getApplicationContext());
        qq = findViewById(R.id.qq);
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("123",user_name);
//                Log.d("1234",user_photo);
                mTencent.login(LoginActivity.this, "all", new BaseUiListener() );


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }



    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            initOpenIdAndToken(o);
            Log.d("user_name",user_name);
            Log.d("user_photo",user_photo);
            getUserInfo();
            Log.d("123",user_name);
            Log.d("1234",user_photo);

            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "onError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserInfo() {
        QQToken qqToken = mTencent.getQQToken();
        UserInfo info = new UserInfo(getApplicationContext(),qqToken);
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    user_name = ((JSONObject) o).getString("nickname");
                    user_photo = ((JSONObject) o).getString("figureurl_qq_2");


//                    UserFragment userFragment = new UserFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("user_name",user_name);
//                    bundle.putString("user_photo",user_photo);
//                    userFragment.setArguments(bundle);
                    Intent intent = new Intent();
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_photo",user_photo);
                    Log.d("user_name",user_name);
                    Log.d("user_photo",user_photo);
                    setResult(RESULT_OK, intent);
                    Log.d("456","1");
                    finish();
//                    FragmentManager fragmentManager = getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.frameLayout,userFragment);
//                    fragmentTransaction.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });

    }

    private void initOpenIdAndToken(Object object) {
        JSONObject jb = (JSONObject) object;
        try {
            String openID = jb.getString("openid");  //openid用户唯一标识
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
