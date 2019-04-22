package com.example.lenovo.englishstudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    private String user_name = "null", user_photo = " ";
    private TextView register, forget_password;
    private EditText login_phone, login_password;
    private Button button_login;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            getUserInfo();


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
        UserInfo info = new UserInfo(getApplicationContext(), qqToken);
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
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("user_photo", user_photo);
                    setResult(RESULT_OK, intent);
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
            String openID = jb.getString("openid");  //openid用户唯一标识
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
                            .baseUrl("http://47.102.206.19:8080/user/")
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
                        Intent intent = new Intent();
                        intent.putExtra("user_name", loginMessage.getData().getUser().getName() + " ");
                        intent.putExtra("user_photo", user_photo);
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
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
