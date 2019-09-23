package com.example.lenovo.englishstudy.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lenovo.englishstudy.R;
import com.example.lenovo.englishstudy.Util.GetRequest_Interface;
import com.example.lenovo.englishstudy.bean.LoginMessage;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {
    private ImageView cancel;
    private ImageView qq;
    private Tencent mTencent;
    //    private UserFragment userFragment = new UserFragment();
    private String user_name = "", user_photo = "", token, openID;
    private TextView register, forget_password;
    private EditText login_phone, login_password;
    private Button button_login;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        forget_password = findViewById(R.id.forget_password);
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
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
                mTencent.login(LoginActivity.this, "all", new BaseUiListener());


            }
        });
        login_phone = findViewById(R.id.login_phone);
        login_password = findViewById(R.id.login_password);
        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLogin(login_phone.getText().toString(), login_password.getText().toString());
            }
        });
        toggleButton = findViewById(R.id.l_toggle_button);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    login_password.setSelection(login_password.getText().toString().length());
                } else {
                    login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    login_password.setSelection(login_password.getText().toString().length());
                }
            }
        });
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }


    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object o) {
            initOpenIdAndToken(o);
            //getUserInfo();
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
        UserInfo info = new UserInfo(getApplicationContext(), qqToken);
        info.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                try {
                    user_name = ((JSONObject) o).getString("nickname");
                    user_photo = ((JSONObject) o).getString("figureurl_qq_2");

                    Intent intent = new Intent(LoginActivity.this, PhoneActivity.class);
                    intent.putExtra("id", openID);
                    intent.putExtra("name", user_name);
                    intent.putExtra("photo", user_photo);
                    startActivity(intent);
                    finish();

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
            openID = jb.getString("openid");  //openid用户唯一标识
            Log.d("7264653", openID);
            requestQQLogin(openID);
            String access_token = jb.getString("access_token");
            String expires = jb.getString("expires_in");
            mTencent.setOpenId(openID);
            mTencent.setAccessToken(access_token, expires);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestLogin(final String phoneNumber, final String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<LoginMessage> call = request.getLoginCall(phoneNumber, password);
        call.enqueue(new Callback<LoginMessage>() {
            @Override
            public void onResponse(Call<LoginMessage> call, Response<LoginMessage> response) {
                LoginMessage loginMessage = response.body();
                if(loginMessage != null) {
                    if(loginMessage.getStatus() == 0 && loginMessage.getMsg().equals("登录成功")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        token = loginMessage.getData().getToken();
                        int userID = loginMessage.getData().getUser().getId();
                        SharedPreferences sharedPreferences = getSharedPreferences("user_token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.putInt("userID", userID);
                        editor.apply();
//                        SharedPreferences sharedPreferences2 = getSharedPreferences("data", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
//                        editor2.putString("user_name", loginMessage.getData().getUser().getName());
//                        editor2.putString("user_photo", loginMessage.getData().getUser().getImg());
//                        editor2.commit();
                        Intent intent = new Intent();
                        intent.putExtra("user_name", loginMessage.getData().getUser().getName());
                        intent.putExtra("user_photo", loginMessage.getData().getUser().getImg());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if(loginMessage.getStatus() == 1) {
                        Toast.makeText(LoginActivity.this, loginMessage.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginMessage> call, Throwable t) {
                t.printStackTrace();
                Log.d("898989",t.toString());
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void requestQQLogin(final String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.zhangshuo.fun/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        Call<LoginMessage> call = request.getQQLoginCall(id);
        call.enqueue(new Callback<LoginMessage>() {
          @Override
            public void onResponse(Call<LoginMessage> call, Response<LoginMessage> response) {
                LoginMessage loginMessage = response.body();
                if(loginMessage != null) {
                    if(loginMessage.getStatus() == 0 && loginMessage.getMsg().equals("登录成功")) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        token = loginMessage.getData().getToken();
                        SharedPreferences sharedPreferences = getSharedPreferences("user_token", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", token);
                        editor.commit();
//                        SharedPreferences sharedPreferences2 = getSharedPreferences("data", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
//                        editor2.putString("user_name", loginMessage.getData().getUser().getName());
//                        editor2.putString("user_photo", loginMessage.getData().getUser().getImg());
//                        editor2.commit();
                        Intent intent = new Intent();
                        intent.putExtra("user_name", loginMessage.getData().getUser().getName());
                        intent.putExtra("user_photo", loginMessage.getData().getUser().getImg());
                        setResult(RESULT_OK, intent);
                        finish();
                    } else if(loginMessage.getStatus() == 1) {
                        Log.d("784636", loginMessage.getMsg());
                        Toast.makeText(LoginActivity.this, loginMessage.getMsg(), Toast.LENGTH_SHORT).show();
                    } else if(loginMessage.getStatus() == 20) {
                        getUserInfo();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginMessage> call, Throwable t) {
                t.printStackTrace();
                Log.d("898989",t.toString());
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
